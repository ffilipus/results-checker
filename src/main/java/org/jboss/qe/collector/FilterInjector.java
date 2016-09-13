package org.jboss.qe.collector;

import com.google.inject.AbstractModule;
import org.jboss.qe.collector.filter.Filter;
import org.jboss.qe.collector.filter.cli.Eap7xCliEmbeddedFilter;
import org.jboss.qe.collector.filter.installer.Eap7xInstallerTestsuite;
import org.jboss.qe.collector.filter.messaging.Eap7xArtemisTestsuite;
import org.jboss.qe.collector.filter.messaging.Eap7xArtemisTestsuiteEUS;
import org.jboss.qe.collector.filter.messaging.Eap7xHA;
import org.jboss.qe.collector.filter.messaging.Eap7xMessagingInternalTestsuite;
import org.jboss.qe.collector.filter.resteasy.Eap7xResteasyTestsuite;
import org.jboss.qe.collector.filter.scripts.Eap6xScriptsTestsuite;
import org.jboss.qe.collector.filter.scripts.Eap7xScriptsTestsuite;
import org.jboss.qe.collector.filter.testsuite.Eap6xAsTestsuite;
import org.jboss.qe.collector.filter.testsuite.Eap7xAsTestsuiteTest710;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *  Created by fjerabek on 13.9.16.
 */
public class FilterInjector extends AbstractModule {
   private String[] jobs;
   private String path;
   private String pckg;

   public FilterInjector(String[] jobs) {
      this.jobs = jobs;
   }
   public FilterInjector(String path, String pckg) {
      this.path = path;
      this.pckg = pckg;
   }

   private Filter choseFilter(String firstJob) {
      if (firstJob.contains("eap-7x-as-testsuite-")) {
         return new Eap7xAsTestsuiteTest710();

      } else if (firstJob.contains("eap-6x-as-testsuite-")) {
         return new Eap6xAsTestsuite();

      } else if (firstJob.contains("eap-7x-installer-") || firstJob.matches("EAP-REGRESSION-.*")) {
         return new Eap7xInstallerTestsuite();

      } else if (firstJob.contains("eap-7x-scripts-")) {
         return new Eap7xScriptsTestsuite();

      } else if (firstJob.contains("eap-64x-patched-scripts")) {
         return new Eap6xScriptsTestsuite();
      } else if (firstJob.contains("resteasy")) {
         return new Eap7xResteasyTestsuite();
      } else if (firstJob.contains("artemis-project-testsuite-prepare")) {
         return new Eap7xArtemisTestsuite();
      } else if (firstJob.contains("artemis-project-testsuite-rhel")) {
         return new Eap7xArtemisTestsuiteEUS();
      } else if (firstJob.contains("eap7-artemis-qe-internal")) {
         return new Eap7xMessagingInternalTestsuite();
      } else if (firstJob.contains("eap-7x-cli-embedded")) {
         return new Eap7xCliEmbeddedFilter();
      } else if (firstJob.contains("eap7-artemis-ha-failover-bridges")) {
         return new Eap7xHA();
      } else
         throw new IllegalStateException("No filter chosen");
   }

   private Filter getFilterFromFile(String path, String pckg) {
      File filterJar = new File(path);
      Filter filter = null;

      try {
         URL url = filterJar.toURI().toURL();
         ClassLoader loader = URLClassLoader.newInstance(new URL[] {url}, getClass().getClassLoader());
         Class<?> filterClass = Class.forName(pckg, true, loader);
         filter = (Filter) filterClass.newInstance();
      } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
      }

      if (filter == null) {
         throw new IllegalStateException("No filter chosen");
      }
      return filter;
   }

   @Override
   protected void configure() {
      //Filter filter = choseFilter(jobs[0]);
      Filter filter = getFilterFromFile(path, pckg);
      bind(Filter.class).to(filter.getClass());
   }
}
