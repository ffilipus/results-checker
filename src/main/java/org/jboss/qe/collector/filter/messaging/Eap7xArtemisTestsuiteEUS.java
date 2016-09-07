package org.jboss.qe.collector.filter.messaging;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

import java.util.regex.Pattern;

public class Eap7xArtemisTestsuiteEUS extends AbstractFilter {

   static FilterItem[] items = {
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.paging.NettyPagingSendTest#testPagingDoesNotDuplicateBatchMessages")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3526"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.cluster.TemporaryQueueClusterTest#testClusteredQueue")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3512"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.bridge.BridgeReconnectTest#testDeliveringCountOnBridgeConnectionFailure")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3194"),
       new FilterItem(Colour.YELLOW)
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
           .addTest("org.apache.activemq.artemis.tests.integration.client.ConcurrentCreateDeleteProduceTest#testConcurrentProduceCreateAndDelete")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3403"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.failover.ReplicatedManyMultipleServerFailoverNoNodeGroupNameTest#testStartBackupFirst")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2708"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.ssl.CoreClientOverOneWaySSLTest#testOneWaySSLWithGood(Client|Server)CipherSuite.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2719"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.client.PagingTest#testDeletePhysicalPages")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3848"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.client.PagingTest#testExpireLargeMessageOnPaging")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4503"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.client.MultipleThreadFilterOneTest#testSendingNettyPaging")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1662"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.ra.ActiveMQMessageHandlerTest#testServerShutdownAndReconnect")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4598"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management.TopicControlTest#.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4629"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.cluster.JMSFailoverListenerTest#testManualFailover")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4633"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management.JMSServerControl2Test#testCloseConnectionsForUserFor.*")
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management.JMSServerControl2Test#testCloseConsumerConnectionsForAddressFor.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-5153"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.connection.ConnectionFactorySerializationTest#testConnectionFactoryUDP")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-5182"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management#JMSServerControlUsingJMSTest#testRemoteClientIDConnection")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-5214"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.jms.server.management#JMSQueueControlUsingJMSTest#testDeleteWithPaging")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-5219"),
       new FilterItem(Colour.PURPLE)
           .addTest("org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest#.*")
           .addUrl(".*/artemis-project-testsuite-solaris/.*")
           .addUrl(".*/artemis-project-testsuite-hpux/.*")
           .setErrorText("Environment issue"),
       new FilterItem(Colour.PURPLE)
           .addTest("org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest#.*")
           .addUrl(".*/artemis-project-testsuite-hpux/.*")
           .setErrorText("Environment issue"),
       new FilterItem(Colour.GREEN)
           .addTest("org.apache.activemq.artemis.tests.integration.security.NettySecurityClientTest#testProducerConsumerClientWithSecurityManager")
//                        .setErrorText("https://issues.jboss.org/browse/JBEAP-2686"),
           .setErrorText("https://issues.jboss.org/browse/JBEAP-5296"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.cluster.distribution.ClusteredGroupingTest#testGroupingSendTo3queuesNoConsumerOnLocalQueue")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-5353"),
       new FilterItem(Colour.YELLOW)
           .addTest("org.apache.activemq.artemis.tests.integration.server.ScaleDownTest#.*")
           .addTest("org.apache.activemq.artemis.tests.integration.server.ScaleDownDirectTest#.*")
           .setErrorText("Not supported features"),
       new FilterItem(Colour.YELLOW)
               .addTestMatcher((String errorDetails) -> errorDetails == "Thread leaked")
               .setErrorText("Thread leaked"),
       new FilterItem(Colour.YELLOW)
               .addTestMatcher((String errorDetails) -> errorDetails == "broadcast not received")
               .setErrorText("broadcast not received"),
       new FilterItem(Colour.YELLOW)
               .addTestMatcher((String errorDetails) -> Pattern.compile("(libAIO is not loaded).*").matcher(errorDetails).find())
               .setErrorText("libAIO is not loaded"),
       new FilterItem(Colour.RED_BOLD)
               .addTestMatcher((String errorDetails) -> errorDetails == "Didn\"t get the expected number of bindings, look at the logging for more information")
               .setErrorText("Didn\"t get the expected number of bindings, look at the logging for more information"),
       new FilterItem(Colour.YELLOW)
               .addTestMatcher((String errorDetails) -> Pattern.compile("(AMQ119007:).*").matcher(errorDetails).find())
               .setErrorText("Cannot connect to server"),
       new FilterItem(Colour.GREEN)
               .addTest(".*ReplicatedNettyAsynchronousFailoverTest#testNonTransactional")
               .addTest(".*ClientCrashTest#testCrashClient")
               .addTest(".*ClusteredGroupingTest#testGroupingSendTo3queuesNoConsumerOnLocalQueue")
               .addTest("org.apache.activemq.artemis.tests.integration.ssl.CoreClientOverOneWaySSLTest#.*")
               .addTest(".*ReplicatedManyMultipleServerFailoverTest#test.*")
               .addTest(".*ClusteredGroupingTest#testGroupingWith3Nodes")
               .setErrorText("DEBUG"),
                /*
                TODO vyresit

                new FilterItem(Colour.YELLOW)
                        .addTestMatcher { it.errorDetails == "Thread leaked" }
                        .setErrorText("Thread leaked"),
                new FilterItem(Colour.YELLOW)
                        .addTestMatcher { it.errorDetails == "broadcast not received" }
                        .setErrorText("broadcast not received"),
                new FilterItem(Colour.YELLOW)
                        .addTestMatcher { it.errorDetails?.startsWith("libAIO is not loaded") }
                        .setErrorText("libAIO is not loaded"),
                new FilterItem(Colour.RED_BOLD)
                        .addTestMatcher { it.errorDetails == "Didn\"t get the expected number of bindings, look at the logging for more information" }
                        .setErrorText("Didn\"t get the expected number of bindings, look at the logging for more information"),
                new FilterItem(Colour.YELLOW)
                        .addTestMatcher { it.errorDetails?.startsWith("AMQ119007:") }
                        .setErrorText("Cannot connect to server"),
                new FilterItem(Colour.GREEN)
                        .addTest(".*ReplicatedNettyAsynchronousFailoverTest#testNonTransactional")
                        .addTest(".*ClientCrashTest#testCrashClient")
                        .addTest(".*ClusteredGroupingTest#testGroupingSendTo3queuesNoConsumerOnLocalQueue")
                        .addTest("org.apache.activemq.artemis.tests.integration.ssl.CoreClientOverOneWaySSLTest#.*")
                        .addTest(".*ReplicatedManyMultipleServerFailoverTest#test.*")
                        .addTest(".*ClusteredGroupingTest#testGroupingWith3Nodes")
                        .setErrorText("DEBUG"),*/

   };

   public String filter(FailedTest failedTest) {
      return coreFilter(failedTest, items);
   }
}
