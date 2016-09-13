package org.jboss.qe.collector.filter.testFilters;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 *  Created by fjerabek on 12.9.16.
 */
public class TestFilterStackTrace extends AbstractFilter {

   @Override
   public FilterResult filter(FailedTest failedTest) {
      FilterItem[] items = new FilterItem[] {
          new FilterItem(Colour.CYAN)
              .addTestMatcher(stackTrace -> stackTrace.get("errorStackTrace").equals("Test stack trace"))
              .setErrorText("Test filter by stack trace")
      };
      return coreFilter(failedTest, items);
   }
}
