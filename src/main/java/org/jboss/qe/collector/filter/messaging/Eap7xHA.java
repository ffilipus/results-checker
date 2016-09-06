package org.jboss.qe.collector.filter.messaging;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

public class Eap7xHA extends AbstractFilter {

   static FilterItem[] items = {
       new FilterItem()
           .addTest("org.jboss.qa.hornetq.test.failover.DedicatedFailoverTestCase#testMultipleFailoverWithQueueShutdown")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1982")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.jboss.qa.hornetq.test.failover.ReplicatedDedicatedFailoverTestWithMdb#testKillWithFailback")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1239")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.jboss.qa.hornetq.test.failover.ColocatedClusterFailoverTestCase#.*")
           .addTest("org.jboss.qa.artemis.test.failover.DedicatedFailoverTestCase20#.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1881")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest(".*[fF]ailback.*")
           .addTest(".*[mM]ultipleFailover.*")
           .addTest("org.jboss.qa.hornetq.test.failover.ReplicatedDedicatedFailoverTestWithMdb#testShutdown")
           .addTest("org.jboss.qa.hornetq.test.failover.ReplicatedColocatedClusterFailoverTestCaseWithBlockPolicy#testFailoverWithMdbsShutdown")
           .addTest("org.jboss.qa.hornetq.test.failover.ReplicatedColocatedClusterFailoverTestCaseWithBlockPolicy#testFailoverWithMdbsKill")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1991 https://issues.jboss.org/browse/JBEAP-1982")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.jboss.qa.artemis.test.failover.DedicatedFailoverCoreBridges#.+WithBridgeWithNonStatic.*Connectors")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1360")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.jboss.qa.hornetq.test.failover.ReplicatedDedicated(Failover|Failback)TestCase#testFailoverWithDivertsTransAck.*")
           .addTest("org.jboss.qa.hornetq.test.failover.ReplicatedDedicated(Failover|Failback)WithBlockPolicyTestCase#testFailoverWithDivertsTransAck.*")
           .addTest("org.jboss.qa.hornetq.test.failover.ReplicatedDedicated(Failover|Failback)TestCaseWithBlockPolicy#testFailoverWithDivertsTransAck.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2317")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.jboss.qa.hornetq.test.failover.XAFailoverTestCase#testFailoverWithXAConsumers?KillAfterStart")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2531")
           .setColour(Colour.YELLOW),
       new FilterItem()
           .addTest("org.jboss.qa.artemis.test.failover.NewConfColocatedClusterFailoverTestCase#testGroupingFailoverNodeOneDownSdLM")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2548")
           .setColour(Colour.YELLOW)
   };

   public String filter(FailedTest failedTest) {
      return coreFilter(failedTest, items);
   }
}
