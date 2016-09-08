package org.jboss.qe.collector;

import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;
import org.jboss.qe.collector.filter.Filter;
import org.jboss.qe.collector.service.PageType.PageParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by pmackay on 8.9.2016.
 */
// TODO: refactor
public class MatcherTestCase {

   @BeforeClass
   public static void prepareCache() {
      Cache cache = new Cache("test_json_bnc");
      String data = "{\"testActions\":[],\"duration\":8564.812,\"empty\":false,\"failCount\":6,\"passCount\":4754,\"skipCount\":13,\"suites\":[{\"cases\":[{\"testActions\":[],\"age\":8,\"className\":\"org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest\",\"duration\":2.017,\"errorDetails\":\"broadcast not received\",\"errorStackTrace\":\"java.lang.AssertionError: broadcast not received\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.assertTrue(Assert.java:41)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryBaseTest.verifyBroadcast(DiscoveryBaseTest.java:60)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest.testSimpleBroadcast(DiscoveryTest.java:104)\\n\",\"failedSince\":14,\"name\":\"testSimpleBroadcast\",\"skipped\":false,\"skippedMessage\":null,\"status\":\"FAILED\",\"stderr\":null,\"stdout\":\"03:36:37,636 INFO  [org.apache.activemq.artemis.core.server] #*#*# Starting test: testSimpleBroadcast()...\\n#test testSimpleBroadcast\\n03:36:37,638 WARN  [org.apache.activemq.artemis.core.client] AMQ212056: local-bind-address specified for broadcast group but no local-bind-port. Using random port for UDP Broadcast (/10.16.95.79:3220)\\n03:36:39,646 INFO  [org.apache.activemq.artemis.core.server] #*#*# Finished test: testSimpleBroadcast()...\\n\"}]}]}";
      cache.add(data);

      cache = new Cache("test_json_nm");
      data = "{\"testActions\":[],\"duration\":8564.812,\"empty\":false,\"failCount\":6,\"passCount\":4754,\"skipCount\":13,\"suites\":[{\"cases\":[{\"testActions\":[],\"age\":8,\"className\":\"org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest\",\"duration\":2.017,\"errorDetails\":\"this definitely does not match anything\",\"errorStackTrace\":\"java.lang.AssertionError: broadcast not received\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.assertTrue(Assert.java:41)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryBaseTest.verifyBroadcast(DiscoveryBaseTest.java:60)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest.testSimpleBroadcast(DiscoveryTest.java:104)\\n\",\"failedSince\":14,\"name\":\"testSimpleBroadcast\",\"skipped\":false,\"skippedMessage\":null,\"status\":\"FAILED\",\"stderr\":null,\"stdout\":\"03:36:37,636 INFO  [org.apache.activemq.artemis.core.server] #*#*# Starting test: testSimpleBroadcast()...\\n#test testSimpleBroadcast\\n03:36:37,638 WARN  [org.apache.activemq.artemis.core.client] AMQ212056: local-bind-address specified for broadcast group but no local-bind-port. Using random port for UDP Broadcast (/10.16.95.79:3220)\\n03:36:39,646 INFO  [org.apache.activemq.artemis.core.server] #*#*# Finished test: testSimpleBroadcast()...\\n\"}]}]}";
      cache.add(data);

      cache = new Cache("test_json_nv");
      data = "{\"testActions\":[],\"duration\":8564.812,\"empty\":false,\"failCount\":6,\"passCount\":4754,\"skipCount\":13,\"suites\":[{\"cases\":[{\"testActions\":[],\"age\":8,\"className\":\"org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest\",\"duration\":2.017,\"errorDetails\":null,\"errorStackTrace\":\"java.lang.AssertionError: broadcast not received\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.assertTrue(Assert.java:41)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryBaseTest.verifyBroadcast(DiscoveryBaseTest.java:60)\\n\\tat org.apache.activemq.artemis.tests.integration.discovery.DiscoveryTest.testSimpleBroadcast(DiscoveryTest.java:104)\\n\",\"failedSince\":14,\"name\":\"testSimpleBroadcast\",\"skipped\":false,\"skippedMessage\":null,\"status\":\"FAILED\",\"stderr\":null,\"stdout\":\"03:36:37,636 INFO  [org.apache.activemq.artemis.core.server] #*#*# Starting test: testSimpleBroadcast()...\\n#test testSimpleBroadcast\\n03:36:37,638 WARN  [org.apache.activemq.artemis.core.client] AMQ212056: local-bind-address specified for broadcast group but no local-bind-port. Using random port for UDP Broadcast (/10.16.95.79:3220)\\n03:36:39,646 INFO  [org.apache.activemq.artemis.core.server] #*#*# Finished test: testSimpleBroadcast()...\\n\"}]}]}";
      cache.add(data);
   }

   @Test
   public void testMatch() throws JSONException {
      Cache cache = new Cache("test_json_bnc");
      Assert.assertTrue(cache.exist());

      String result = getFilterResult(cache);
      String expected = Colour.BLUE.getColour() + "test" + Colour.RESET.getColour() + " - broadcast not received ";
      Assert.assertEquals(expected, result);
   }

   @Test
   public void testNoMatch() throws JSONException {
      Cache cache = new Cache("test_json_nm");
      Assert.assertTrue(cache.exist());

      String result = getFilterResult(cache);
      String expected = Colour.RED.getColour() + "test" + Colour.RESET.getColour();
      Assert.assertEquals(expected, result);
   }

   @Test
   public void testNullValue() throws JSONException {
      Cache cache = new Cache("test_json_nv");
      Assert.assertTrue(cache.exist());

      String result = getFilterResult(cache);
      String expected = Colour.RED.getColour() + "test" + Colour.RESET.getColour();
      Assert.assertEquals(expected, result);
   }

   private static JSONObject parsePage(Cache cache) throws JSONException {
      PageParser parsedPage = new PageParser(cache.getAll());
      return parsedPage.getCases().getJSONObject(0);
   }

   private static String getFilterResult(Cache cache) throws JSONException {
      return getFilterResult(cache, new TestFilter());
   }

   private static String getFilterResult(Cache cache, Filter filter) throws JSONException {
      JSONObject jsonObject = parsePage(cache);
      FailedTest failedTest = new FailedTest("test", "http://test.url", jsonObject);
      return filter.filter(failedTest);
   }

   private static class TestFilter extends AbstractFilter {

      private FilterItem[] items = {
          new FilterItem(Colour.BLUE)
              .setErrorText("broadcast not received")
          .addTestMatcher(it -> {
             try {
                return it.get("errorDetails").equals("broadcast not received");
             } catch (JSONException e) {
                e.printStackTrace();
                return false;
             }
          })
      };

      @Override
      public String filter(FailedTest failedTest) {
         return coreFilter(failedTest, items);
      }
   }
}
