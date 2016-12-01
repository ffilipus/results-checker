package org.jboss.qe.collector.filter.jws;

import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * author: fgoldefu@redhat.com
 */
public class JwsWindowsTests extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {

      FilterItem[] items = {
      };

      return coreFilter(failedTest, items);
   }
}
