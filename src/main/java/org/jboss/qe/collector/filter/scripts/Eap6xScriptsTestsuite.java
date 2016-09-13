package org.jboss.qe.collector.filter.scripts;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * @author Marek Kopecky (mkopecky@redhat.com)
 */
public class Eap6xScriptsTestsuite extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {
      // model for filter
      FilterItem[] items = {

          new FilterItem().addTest(".*DomainCachedConfigurationTestCase.*")
              .setErrorText("tests was not finished").setColour(Colour.PURPLE),

          new FilterItem().addUrl(".*windows.*")
              .addTest("org.jboss.as.testsuite.integration.scripts.test.tools.JdrTestCase.*")
              .addTest(".*StandaloneRunTestCase#defaultDebugPort.*")
              .setErrorText("see testplan for more details").setColour(Colour.YELLOW),

      };

      return coreFilter(failedTest, items);
   }
}
