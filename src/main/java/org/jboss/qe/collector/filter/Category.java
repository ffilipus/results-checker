package org.jboss.qe.collector.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Statistics of categories of errors. Categories is optional.
 */
public class Category {
    /**
     * Key is category name. Value is number of occurrence of error of category.
     */
    public static Map<String, Integer> statistics = new HashMap<String, Integer>();

    /**
     * Increment category occurrence.
     * @param category Incremented category.
     */
    public synchronized static void increaseStatistics(String category) {
        if (category == null) {
            return;
        }
        int count = statistics.containsKey(category) ? statistics.get(category) : 0;
        statistics.put(category, count + 1);
    }
}
