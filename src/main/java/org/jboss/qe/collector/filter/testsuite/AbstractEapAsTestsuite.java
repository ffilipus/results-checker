package org.jboss.qe.collector.filter.testsuite;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.Category;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mkopecky on 8/17/15.
 */
abstract class AbstractEapAsTestsuite extends AbstractFilter {

    @Override
    public void onFinish() {
        if (Category.statistics.size() > 0) {
            System.out.println("\n"+dyeText("########    CATEGORIES STATISTICS    ########", Colour.CYAN_BOLD));

            Map<String, Integer> sortedStatistics = Category.statistics;
            Iterator entries = sortedStatistics.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Integer> thisEntry = (Map.Entry<String, Integer>) entries.next();
                // category statistics is increased twice (second in "AGGREGATED RESULTS PER JOB"
                System.out.println(dyeText(thisEntry.getKey(), Colour.BLUE)+" - "+(int)thisEntry.getValue());
            }
            System.out.println();
        }
    }

}
