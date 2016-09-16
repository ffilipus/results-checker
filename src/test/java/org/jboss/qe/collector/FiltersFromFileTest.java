package org.jboss.qe.collector;

import org.jboss.qe.collector.filter.Filter;
import org.jboss.qe.collector.filterslists.FiltersFromFile;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by fjerabek on 16.9.16.
 */
public class FiltersFromFileTest {
   @Test
   public void filtersFromFile() throws JSONException {
      Tools.setEnvironmentVariable("PACKAGE", "org.jboss.qe.collector.filter.testfilters.TestFilterName");
      List<Filter> filters = new FiltersFromFile().getFilters();
      Filter filter = filters.get(0);

      FailedTest failedTest = new FailedTest("testFailedJob", "buildUrl: " + "URL", testInfoFactory("errorDetails", "Test stack trace"));
      FilterResult result = filter.filter(failedTest);
      Assert.assertTrue("Test does not match failed test", result.isMatch());

      FailedTest failedTest2 = new FailedTest("Name", "buildUrl: " + "URL", testInfoFactory("errorDetails", "Test stack trace"));
      Assert.assertFalse("Filter matched when it should not", filter.filter(failedTest2).isMatch());
   }

   @Test
   public void multipleFilters() throws JSONException {
      Tools.setEnvironmentVariable("PACKAGE", "org.jboss.qe.collector.filter.testfilters");
      List<Filter> filters = new FiltersFromFile().getFilters();
      List <FailedTest> failedTests = new ArrayList<>();

      failedTests.add(new FailedTest("Name", "buildUrl: " + "URL", testInfoFactory("errorDetails", "Test stack trace")));
      failedTests.add(new FailedTest("testFailedJob", "buildUrl: " + "URL", testInfoFactory("errorDetails", "stackTrace")));
      failedTests.add(new FailedTest("Name", "buildUrl: " + "URL", testInfoFactory("Test error details filter", "errorStackTrace")));
      failedTests.add(new FailedTest("FilterNotMatch", "buildUrl: " + "asadsf", testInfoFactory("asdf", "asdfgh")));

      List<FilterResult> results = new ArrayList<>();

      for (FailedTest test : failedTests) {
         results.add(processIssues(test, filters));
      }

      Assert.assertTrue("Filter did not match", results.get(0).isMatch());
      Assert.assertTrue("Filter did not match", results.get(1).isMatch());
      Assert.assertTrue("Filter did not match", results.get(2).isMatch());
      Assert.assertFalse("Filter matched when it should not", results.get(3).isMatch());
   }

   private static FilterResult processIssues(FailedTest failedTest, List<Filter> filters) {
      FilterResult result = null;
      for (Filter filter : filters) {
         result = filter.filter(failedTest);
         if (result.isMatch()) {
            return result;
         }
      }
      assert result != null;
      return result;
   }

   private JSONObject testInfoFactory(String errorDetails, String errorStackTrace) throws JSONException {
      JSONObject testInfo = new JSONObject();
      testInfo.put("status","FAILED");
      testInfo.put("errorDetails",errorDetails);
      testInfo.put("errorStackTrace",errorStackTrace);
      return testInfo;
   }
}
