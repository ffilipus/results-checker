package org.jboss.qe.collector.filter.testfilters;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 *  Created by fjerabek on 12.9.16.
 */
public class TestFilterTestReport extends AbstractFilter {
   @Override
   public FilterResult filter(FailedTest failedTest) {
      FilterItem[] items = new FilterItem[] {
          new FilterItem(Colour.BLUE)
              .addTestMatcher(errorDetails -> errorDetails.get("errorDetails").equals("Test error details filter"))
              .setErrorText("Test filter by test report")
      };
      return coreFilter(failedTest, items);
   }
}
