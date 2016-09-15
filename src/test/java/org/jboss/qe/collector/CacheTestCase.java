package org.jboss.qe.collector;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Jan Dobes
 */
public class CacheTestCase {

   @Test
   public void testCacheExists() {
      Cache cache = new Cache("test_exists");
      //cache.add("{}");
      Assert.assertTrue("Cache doesn't exist", cache.exist());
   }

   @Test
   public void testCacheIsActual() {
      Cache cache = new Cache("test_is_actual");
      cache.add("[]");
      Assert.assertTrue("Cache isn't actual", cache.isActual(10));
      Assert.assertFalse("Cache is actual when 0 time given", cache.isActual(0));
      Assert.assertFalse("Cache is actual when negative time given", cache.isActual(-1));
   }

   @Test
   public void testGetAll() {
      Cache cache = new Cache("test_get_all");
      cache.add("{\"testActions\":[],\"duration\":8564.812,\"empty\":false,\"failCount\":6,\"passCount\":4754,\"skipCount\":13,\"suites\":[{\"cases\":[{\"testActions\":[],\"age\":8,\"className\":\"org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest\",\"duration\":2.017,\"errorDetails\":\"broadcast not received\",\"errorStackTrace\":\"java.lang.AssertionError: broadcast not received\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.assertTrue(Assert.java:41)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryBaseTest.verifyBroadcast(DiscoveryBaseTest.java:60)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest.testSimpleBroadcast(DiscoveryTest.java:104)\\n\",\"failedSince\":14,\"name\":\"testSimpleBroadcast\",\"skipped\":false,\"skippedMessage\":null,\"status\":\"FAILED\",\"stderr\":null,\"stdout\":\"03:36:37,636 INFO  [org.apache.activemq.artemis.core.server] #*#*# Starting test: testSimpleBroadcast()...\\n#test testSimpleBroadcast\\n03:36:37,638 WARN  [org.apache.activemq.artemis.core.client] AMQ212056: local-bind-address specified for broadcast group but no local-bind-port. Using random port for UDP Broadcast (/10.16.95.79:3220)\\n03:36:39,646 INFO  [org.apache.activemq.artemis.core.server] #*#*# Finished test: testSimpleBroadcast()...\\n\"}]}]}");
      String expectedContent = "{\"testActions\":[],\"duration\":8564.812,\"empty\":false,\"failCount\":6,\"passCount\":4754,\"skipCount\":13,\"suites\":[{\"cases\":[{\"testActions\":[],\"age\":8,\"className\":\"org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest\",\"duration\":2.017,\"errorDetails\":\"broadcast not received\",\"errorStackTrace\":\"java.lang.AssertionError: broadcast not received\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.assertTrue(Assert.java:41)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryBaseTest.verifyBroadcast(DiscoveryBaseTest.java:60)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest.testSimpleBroadcast(DiscoveryTest.java:104)\\n\",\"failedSince\":14,\"name\":\"testSimpleBroadcast\",\"skipped\":false,\"skippedMessage\":null,\"status\":\"FAILED\",\"stderr\":null,\"stdout\":\"03:36:37,636 INFO  [org.apache.activemq.artemis.core.server] #*#*# Starting test: testSimpleBroadcast()...\\n#test testSimpleBroadcast\\n03:36:37,638 WARN  [org.apache.activemq.artemis.core.client] AMQ212056: local-bind-address specified for broadcast group but no local-bind-port. Using random port for UDP Broadcast (/10.16.95.79:3220)\\n03:36:39,646 INFO  [org.apache.activemq.artemis.core.server] #*#*# Finished test: testSimpleBroadcast()...\\n\"}]}]}";
      Assert.assertTrue("Cache doesn't exist",cache.exist());
      Assert.assertEquals("Cache content isn't equal", cache.getAll(), expectedContent);
   }
}
