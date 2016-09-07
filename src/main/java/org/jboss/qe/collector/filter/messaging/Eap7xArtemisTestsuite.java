package org.jboss.qe.collector.filter.messaging;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

public class Eap7xArtemisTestsuite extends AbstractFilter {

   static FilterItem[] items = {
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.paging.NettyPagingSendTest#testPagingDoesNotDuplicateBatchMessages")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3526"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.cluster.TemporaryQueueClusterTest#testClusteredQueue")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3512"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.bridge.BridgeReconnectTest#testDeliveringCountOnBridgeConnectionFailure")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3194"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.distribution.NettySymmetricClusterWithBackupTest#testMixtureLoadBalancedAndNonLoadBalancedQueuesAddQueuesAndConsumersBeforeAllServersAreStarted")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3581"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.remoting.ReconnectTest#testInterruptReconnectInVMInterruptMainThread")
           .addTest("org.apache.activemq.artemis.tests.integration.remoting.ReconnectTest#testInterruptReconnectNettyInterruptMainThread")
           .addTest("org.apache.activemq.artemis.tests.integration.remoting.ReconnectTest#testInterruptReconnectInVM")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3746"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management.TopicControlUsingJMSTest#testGetXXXMessagesCount")
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management.TopicControlUsingJMSTest#testCountMessagesForSubscription")
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management.TopicControlUsingJMSTest#testGetMessagesAdded")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3757"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.extras.byteman.ClosingConnectionTest#testKillConnection")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3775"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.failover.BackupSyncLargeMessageTest#testReserveFileIdValuesOnBackup")
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.failover.BackupSyncPagingTest#testReserveFileIdValuesOnBackup")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2689"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.client.MultipleThreadFilterOneTest.testSendingInVMPaging")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1662"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.client.PagingTest#testDLAOnLargeMessageAndPaging")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3240"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.client.ConcurrentCreateDeleteProduceTest#testConcurrentProduceCreateAndDelete")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3403"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.failover.ReplicatedManyMultipleServerFailoverNoNodeGroupNameTest#testStartBackupFirst")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2708"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.ssl.CoreClientOverOneWaySSLTest#testOneWaySSLWithGood(Client|Server)CipherSuite.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2719"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.reattach.MultiThreadRandomReattachTest#testD")
           .addTest("org.apache.activemq.artemis.tests.integration.client.ConsumerWindowSizeTest#testFlowControlLargeMessage")
           .addTest("org.apache.activemq.artemis.tests.integration.client.NettyConsumerWindowSizeTest#testFlowControlLargeMessage")
           .setErrorText("is related to https://issues.jboss.org/browse/JBEAP-3240"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.distribution.ClusteredGroupingTest#.*")
           .addTest("org.apache.activemq.artemis.tests.integration.server.ScaleDownTest#.*")
           .addTest("org.apache.activemq.artemis.tests.integration.server.ScaleDownDirectTest#.*")
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.reattach.MultiThreadRandomReattachTest#testO")
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.reattach.MultiThreadRandomReattachTest#testN")
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.distribution.ClusterHeadersRemovedTest#testHeadersRemoved")
           .addTest("org.apache.activemq.artemis.tests.extras.byteman.GroupingTest#.*")
           .addTest("org.apache.activemq.artemis.tests.integration.jms.cluster.BindingsClusterTest#testRemoteBindingRemovedOnReconnectLocalAvailable[crash=false]")
           .setErrorText("Ignored"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.client.PagingTest#testDeletePhysicalPages")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3848"),
       new FilterItem(Colour.YELLOW_BOLD)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management.JMSQueueControlUsingJMSTest#testDeleteWithPaging")
           .setErrorText("Try add session.commit after messages are sent."),
   };

   public String filter(FailedTest failedTest) {
      return coreFilter(failedTest, items);
   }
}
