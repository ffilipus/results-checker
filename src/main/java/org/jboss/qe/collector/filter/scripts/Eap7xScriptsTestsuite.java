package org.jboss.qe.collector.filter.scripts;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * @author Marek Kopecky (mkopecky@redhat.com)
 */
public class Eap7xScriptsTestsuite extends AbstractFilter {

   public String filter(FailedTest failedTest) {
      // model for filter
      FilterItem[] items = {

          new FilterItem()
              .addTest(".*StandaloneRunTestCase#debugPortEnv")
              .setErrorText("https://issues.jboss.org/browse/JBEAP-1093")
              .setColour(Colour.YELLOW),

          new FilterItem()
              .addTest(".*StandaloneProduction.*testThisMemory")
              .setErrorText("https://issues.jboss.org/browse/JBEAP-5374")
              .setColour(Colour.YELLOW),

          new FilterItem()
              .addTest(".*CliEchoCommandTestCase.*")
              .setErrorText("Petrův test")
              .setColour(Colour.PURPLE),


          new FilterItem()
              .addTest(".*StandaloneGCLoggingTestCase.*")
              .setErrorText("https://issues.jboss.org/browse/JBEAP-5374")
              .setColour(Colour.YELLOW),


          new FilterItem().addUrl(".*ipv6.*").addUrl(".*ibm.*")
              .addTest(".*RunTestCase.*ReloadTest")
              .setErrorText("https://issues.jboss.org/browse/JBEAP-5374")
              .setColour(Colour.YELLOW),


          new FilterItem()
              .addTest(".*StandaloneJavaOptsTestCase#testExpectedOpts")
              .setErrorText("https://issues.jboss.org/browse/JBEAP-5374")
              .setColour(Colour.YELLOW),


          new FilterItem()
              .addTest(".*DomainRunTestCase#domainReloadTest")
              .addTest(".*StandaloneRunTestCase#serverReloadTest")
              .setErrorText("https://issues.jboss.org/browse/JBEAP-5390 && https://issues.jboss.org/browse/JBEAP-5391")
              .setColour(Colour.YELLOW)
      };

      return coreFilter(failedTest, items);
   }
}
