package org.jboss.qe.collector.filter.eap64xAcceptance;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * Created by jbilek on 22/09/16.
 * filter for https://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-64x-patched-repository-maven-check-scm-revision/
 */
public class Eap64xPatchedRepositoryMavenCheckScmRevision extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {
      // model for filter
      FilterItem[] items = {

            new FilterItem(Colour.YELLOW)
                  .addTest("async-http-servlet-3.0-2.3.15.Final-redhat-1.*.async-http-servlet-3.0-2.3.15.Final-redhat-1.*")
                  .addTest("hibernate-core-4.2.24.Final-redhat-1.*#hibernate-core-4.2.24.Final-redhat-1.*")
                  .addTest("hibernate-entitymanager-4.2.24.Final-redhat-1.*#hibernate-entitymanager-4.2.24.Final-redhat-1.*")
                  .addTest("hibernate-envers-4.2.24.Final-redhat-1.*#hibernate-envers-4.2.24.Final-redhat-1.*")
                  .addTest("hibernate-infinispan-4.2.24.Final-redhat-1.*#hibernate-infinispan-4.2.24.Final-redhat-1.*")
                  .addTest("jaxrs-api-2.3.15.Final-redhat-1.*#jaxrs-api-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-atom-provider-2.3.15.Final-redhat-1.*#resteasy-atom-provider-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-cdi-2.3.15.Final-redhat-1.*#resteasy-cdi-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-hibernatevalidator-provider-2.3.15.Final-redhat-1.*#resteasy-hibernatevalidator-provider-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-jackson-provider-2.3.15.Final-redhat-1.*#resteasy-jackson-provider-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-jaxb-provider-2.3.15.Final-redhat-1.*#resteasy-jaxb-provider-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-jaxrs-2.3.15.Final-redhat-1.*#resteasy-jaxrs-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-jettison-provider-2.3.15.Final-redhat-1.*#resteasy-jettison-provider-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-jsapi-2.3.15.Final-redhat-1.*#resteasy-jsapi-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-multipart-provider-2.3.15.Final-redhat-1.*#resteasy-multipart-provider-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-spring-2.3.15.Final-redhat-1.*#resteasy-spring-2.3.15.Final-redhat-1.*")
                  .addTest("resteasy-yaml-provider-2.3.15.Final-redhat-1.*#resteasy-yaml-provider-2.3.15.Final-redhat-1.*")
                  .addTest("arjunacore-4.17.35.Final-redhat-1.*#arjunacore-4.17.35.Final-redhat-1.*")
                  .addTest("jbossjts-integration-4.17.35.Final-redhat-1.*#jbossjts-integration-4.17.35.Final-redhat-1.*")
                  .addTest("jbossjts-jacorb-4.17.35.Final-redhat-1.*#jbossjts-jacorb-4.17.35.Final-redhat-1.*")
                  .addTest("jbosstxbridge-4.17.35.Final-redhat-1.*#jbosstxbridge-4.17.35.Final-redhat-1.*")
                  .addTest("jbosstxbridge-4.17.35.Final-redhat-1#jbosstxbridge-4.17.35.Final-redhat-1")
                  .addTest("jbossxts-4.17.35.Final-redhat-1.*#jbossxts-4.17.35.Final-redhat-1.*")
                  .addTest("narayana-jta-4.17.35.Final-redhat-1.*#narayana-jta-4.17.35.Final-redhat-1.*")
                  .setErrorText("<a href=\"https://bugzilla.redhat.com/show_bug.cgi?id=1017635\" >BZ1017635</a>"),
      };

      return coreFilter(failedTest, items);
   }
}
