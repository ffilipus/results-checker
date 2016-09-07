package org.jboss.qe.collector;

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
   public String testCase;
   public FailedTest(String testName, String buildUrl, String testCase) {
      this.testName = testName;    
      this.buildUrl = buildUrl;
      this.testCase = testCase;
   }
}