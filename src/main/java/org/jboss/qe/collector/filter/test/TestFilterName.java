package org.jboss.qe.collector.filter.test;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

public class TestFilterName extends AbstractFilter {
   @Override
   public String filter(FailedTest failedTest) {
      FilterItem[] items = new FilterItem[]{
          new FilterItem(Colour.YELLOW)
              .addTest(".*infinispan-directory-provider-8.1.4.Final-redhat-1")
              .setErrorText("Testing filter"),

          new FilterItem(Colour.GREEN)
              .addTest(".*infinispan-parent-8.1.4.Final-redhat-1")
              .setErrorText("Testing filter"),
      };

      return coreFilter(failedTest, items);
   }
}