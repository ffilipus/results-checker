package org.jboss.qe.collector.filter.tattletale;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;
import org.json.JSONObject;

/**
 * Created by jbilek on 14/09/16.
 * filter for tattletale job for eap 6.4.x
 */
public class Eap64xTattletale  extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {
      // model for filter
      FilterItem[] items = {

            new FilterItem(Colour.YELLOW)
                  .addTest("org.jboss.manu.units.eap.tattletale.EapEliminateJarsReportParser.EvaluateEliminateJarsReport.jarswithdifferentversions.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().contains("Total JARs with different versions found: 3"))
                  .setErrorText("TODO add bugzilla ticket"),

            new FilterItem(Colour.YELLOW)
                  .addTest("org.jboss.manu.units.eap.tattletale.EapMultipleLocationsReportParser.EvaluateMultipleLocationsReport.jarswithmultiplelocations.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().contains("Total JARs with multiple locations found: 3\n"))
                  .setErrorText("TODO add bugzilla ticket"),
            /*new FilterItem(Colour.YELLOW)
                  .addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().contains("39 duplicate classes found in JARs: [commons-beanutils-1.8.3.redhat-6.jar, commons-collections-3.2.1.redhat-7.jar]\n"))
                  .setErrorText("TODO add bugzilla ticket"),
*/
            new FilterItem(Colour.YELLOW)
                  .addTest("org.jboss.manu.units.eap.tattletale.EapNoVersionReportParser.EvaluateNoVersionReport.jarswithoutversion.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().contains("Total JARs without a version found: 13\n"))
                  .setErrorText("TODO add bugzilla ticket"),

      };

      return coreFilter(failedTest, items);
   }
}
