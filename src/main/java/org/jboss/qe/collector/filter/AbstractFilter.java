package org.jboss.qe.collector.filter;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.json.JSONException;

import java.text.MessageFormat;

/**
 * Basic methods for filter model.
 *
 * @author Petr Kremensky pkremens@redhat.com on 28/07/2015
 */
public abstract class AbstractFilter implements Filter {

   /**
    * Dye the text with desired colour.
    *
    * @param text   Text to be dyed.
    * @param colour Colour which should be used for dyeing the text.
    * @return Dyed text.
    */
   protected static String dyeText(String text, Colour colour) {
      return colour.getColour() + text + Colour.RESET.getColour();
   }

   /**
    * Get text description with correct colour about test error.
    */
   public String getMessageFromFilterItem(FailedTest failedTest, FilterItem item) {
      String category = item.category != null ? " " + dyeText(item.category, Colour.GRAY) : "";
      String template = "{0} - {1} {2}";
      Object[] values = new Object[] {
          dyeText(failedTest.testName, item.colour), item.errorText, category
      };
      return MessageFormat.format(template, values);
   }

   /**
    * Check failed test and filter it based on filter item model. Dye text to red if the failure was not recognized.
    *
    * @param url        Url of failed configuration.
    * @param failedTest Test name.
    * @param items      Filter model
    * @return Error description for printing.
    */
   public FilterResult coreFilter(FailedTest failedTest, FilterItem... items) {
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
               return new FilterResult(true, getMessageFromFilterItem(failedTest, filterItem));
            }
         }
         for (Matcher testMatcher : filterItem.getTestMatchers()) {
            try {
               if (testMatcher.test(failedTest.testCase)) {
                  Category.increaseStatistics(filterItem.getCategory());
                  return new FilterResult(true, getMessageFromFilterItem(failedTest, filterItem));
               }
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }
         // TODO potreba vlastni implementace vyhodnocovani vyrazu
            /*for (Function testMatcher : filterItem.getTestMatchers()) {
                if (testMatcher.apply(failedTest.testCase);) {
                    Category.increaseStatistics(filterItem.getCategory());
                    return getMessageFromFilterItem(failedTest, filterItem);
                }
            }*/

      }
      return new FilterResult(false, dyeText(failedTest.testName, Colour.RED));
   }

   /**
    * Method is executed after aggregation of all results.
    */
   public void onFinish() {
      // nothing here by default
   }

}
