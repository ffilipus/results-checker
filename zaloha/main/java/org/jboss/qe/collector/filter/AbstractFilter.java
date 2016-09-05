package org.jboss.qe.collector.filter;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.Main;

/**
 * Basic methods for filter model.
 * @author Petr Kremensky pkremens@redhat.com on 28/07/2015
 */
public abstract class AbstractFilter implements Filter {

    /**
     * Dye the text with desired colour.
     * @param text Text to be dyed.
     * @param colour Colour which should be used for dyeing the text.
     * @return Dyed text.
     */
    protected static String dyeText(String text, Colour colour) {
        return "${colour.getColour()}${text}${Colour.RESET.getColour()}";
    }

    /**
     * Get text description with correct colour about test error.
     */
    public String getMessageFromFilterItem(FailedTest failedTest, FilterItem item) {
        String category = item.category != null ? " " + dyeText("<${item.category}>", Colour.GRAY) : "";
        return "${dyeText(failedTest.getTestName(), item.colour)} - ${item.errorText}${category}";
    }

    /**
     * Check failed test and filter it based on filter item model. Dye text to red if the failure was not recognized.
     * @param failedTest Test name.
     * @param url Url of failed configuration.
     * @param items Filter model
     * @return Error description for printing.
     */
    public String coreFilter(FailedTest failedTest, FilterItem... items) {
        filterLoop:
        for (FilterItem filterItem : items) {
            boolean mainContinue = false;
            for (String urlPart : filterItem.getUrlRegEx()) {
                if (!(failedTest.buildUrl.matches(urlPart))) {
                    mainContinue = true;
                    break;
                }
            }
            if (mainContinue) {
                continue;
            }
            for (String regExpTest : filterItem.getTestsRegEx()) {
                if (failedTest.testName.matches(regExpTest)) {
                    Category.increaseStatistics(filterItem.getCategory());
                    return getMessageFromFilterItem(failedTest, filterItem);
                }
            }
            for (Closure<Boolean> testMatcher : filterItem.getTestMatchers()) {
                if (testMatcher(failedTest.testCase)) {
                    Category.increaseStatistics(filterItem.getCategory());
                    return getMessageFromFilterItem(failedTest, filterItem);
                }
            }
        }
        return dyeText(failedTest.testName, Colour.RED);
    }

    /**
     * Method is executed after aggregation of all results.
     */
    public void onFinish() {
        // nothing here by default
    }

}
