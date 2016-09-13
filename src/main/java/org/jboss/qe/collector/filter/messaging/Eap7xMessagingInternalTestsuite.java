package org.jboss.qe.collector.filter.messaging;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

public class Eap7xMessagingInternalTestsuite extends AbstractFilter {

   static FilterItem[] items = {
       new FilterItem()
           .addTest("org.jboss.qa.hornetq.test.failover.Lodh5TestCase.testMysql5(5|7)")
           .setErrorText("MySql prepared transaction issue -  http://bugs.mysql.com/bug.php?id=12161")
           .setCategory(MessagingTsCategory.LODH)
           .setColour(Colour.YELLOW),

       new FilterItem()
           .addTest("org.jboss.qa.hornetq.test.cluster.ClusterTestCase.*MessageGrouping.*")
           .setErrorText("Issue with message grouping")
           .setCategory(MessagingTsCategory.MESSAGE_GROUPING)
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.apache.activemq.artemis.core.config.impl.WrongRoleFileConfigurationParserTest#testParsingDefaultServerConfig")
           .addTest("org.apache.activemq.artemis.tests.integration.client.PagingTest#testFailMessagesNonDurable")
           .addTest("org.apache.activemq.artemis.tests.integration.clientcrash.ClientCrashTest.*")
           .setErrorText("Fixed in master")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.jboss.qa.artemis.test.security.SslAuthenticationTestCase#testHttpsConnection.+")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1288")
           .setColour(Colour.YELLOW)
   };

   public FilterResult filter(FailedTest failedTest) {
      return coreFilter(failedTest, items);
   }
}
