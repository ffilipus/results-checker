package org.jboss.qe.collector.filter.jbcs;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * author: fgoldefu@redhat.com
 */
public class JbcsRhelTests extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {

      FilterItem[] items = {
           new FilterItem(Colour.YELLOW)
                  .addTest("noe.httpd.tests.ApxsHttpdTest.*")
                  .setErrorText("In progress: https://issues.jboss.org/browse/JBCS-160"),
           new FilterItem(Colour.YELLOW)
                  .addTest("noe.httpd.bugfix.tests.JWS279.*")
                  .setErrorText("In progress: https://issues.jboss.org/browse/JWS-529"),
      };

      return coreFilter(failedTest, items);
   }
}
