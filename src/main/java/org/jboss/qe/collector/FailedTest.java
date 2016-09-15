package org.jboss.qe.collector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FailedTest {

   public String testName;

   public String buildUrl;
    /**
     * JSON Object which contains following properties:
     *   testActions - array
     *   age - number
     *   className - string
     *   duration - number
     *   errorDetails - number
     *   errorStackTrace - string
     *   failedSince - number
     *   name - string
     *   skipped - boolean
     *   skippedMessage - string
     *   status - string. Possible values: PASSED, REGRESSION
     *   stderr - string
     *   stdout - string
     */
   public JSONObject testCase;
   public FailedTest(String testName, String buildUrl, JSONObject testCase) {
      this.testName = testName;
      this.buildUrl = buildUrl;
      this.testCase = testCase;
   }

   public FailedTest(String testName, String buildUrl, Map testCase) {
      this.testName = testName;
      this.buildUrl = buildUrl;
      try {
         this.testCase = new JSONObject();
         this.testCase.put("errorDetails",testCase.get("errorDetails"));
         this.testCase.put("errorStackTrace",testCase.get("errorStackTrace"));
      } catch (JSONException e) {
         e.printStackTrace();
      }
   }
}
