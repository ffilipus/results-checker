package org.jboss.qe.collector;

import java.util.List;

public class FailedTest {

   public String testName;

   public String buildUrl;

   /**
    * JSON Object which contains following properties:
    * testActions - array
    * age - number
    * className - string
    * duration - number
    * errorDetails - number
    * errorStackTrace - string
    * failedSince - number
    * name - string
    * skipped - boolean
    * skippedMessage - string
    * status - string. Possible values: PASSED, REGRESSION
    * stderr - string
    * stdout - string
    */
   public List testCase;

}
