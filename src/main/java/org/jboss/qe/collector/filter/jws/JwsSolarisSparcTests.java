package org.jboss.qe.collector.filter.jws;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * author: fgoldefu@redhat.com
 */
public class JwsSolarisSparcTests extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {

      FilterItem[] items = {
           new FilterItem(Colour.YELLOW)
                  .addTest("noe.tomcat.tests.VirtualHostTomcatTest.*")
                  .setErrorText("In progress: https://issues.jboss.org/browse/JBEWS-105")
      };

      return coreFilter(failedTest, items);
   }
}
