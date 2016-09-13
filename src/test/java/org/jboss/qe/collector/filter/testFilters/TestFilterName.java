package org.jboss.qe.collector.filter.testFilters;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

public class TestFilterName extends AbstractFilter {
   @Override
   public FilterResult filter(FailedTest failedTest) {
      FilterItem[] items = new FilterItem[]{
          new FilterItem(Colour.YELLOW)
              .addTest("testFailedJob.*")
              .setErrorText("Testing filter by name"),
      };

      return coreFilter(failedTest, items);
   }
}