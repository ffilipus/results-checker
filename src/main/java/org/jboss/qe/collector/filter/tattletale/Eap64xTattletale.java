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
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),

            new FilterItem(Colour.YELLOW)
                  .addTest("org.jboss.manu.units.eap.tattletale.EapMultipleLocationsReportParser.EvaluateMultipleLocationsReport.jarswithmultiplelocations.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("Total JARs with multiple locations found: 3\n"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),
            /*new FilterItem(Colour.YELLOW)
                  .addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().contains("39 duplicate classes found in JARs: [commons-beanutils-1.8.3.redhat-6.jar, commons-collections-3.2.1.redhat-7.jar]\n"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),
*/
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("39 duplicate classes found in JARs: [commons-beanutils-1.8.3.redhat-6.jar, commons-collections-3.2.1.redhat-7.jar]\n" +
                        "org.apache.commons.collections.ArrayStack\n" +
                        "org.apache.commons.collections.Buffer\n" +
                        "org.apache.commons.collections.BufferUnderflowException\n" +
                        "org.apache.commons.collections.FastHashMap\n" +
                        "org.apache.commons.collections.FastHashMap$1\n" +
                        "org.apache.commons.collections.FastHashMap$CollectionView\n" +
                        "org.apache.commons.collections.FastHashMap$CollectionView$CollectionViewIterator\n" +
                        "org.apache.commons.collections.FastHashMap$EntrySet\n" +
                        "org.apache.commons.collections.FastHashMap$KeySet\n" +
                        "org.apache.commons.collections.FastHashMap$Values"))
                  .setErrorText("https://issues.jboss.org/browse/WFLY-7095"),
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("39 duplicate classes found in JARs: [org.apache.felix.configadmin-1.2.8.redhat-4.jar, org.osgi.enterprise-4.2.0.redhat-10.jar]\n" +
                        "org.osgi.service.cm.Configuration\n" +
                        "org.osgi.service.cm.ConfigurationAdmin\n" +
                        "org.osgi.service.cm.ConfigurationEvent\n" +
                        "org.osgi.service.cm.ConfigurationException\n" +
                        "org.osgi.service.cm.ConfigurationListener\n" +
                        "org.osgi.service.cm.ConfigurationPermission\n" +
                        "org.osgi.service.cm.ConfigurationPermissionCollection\n" +
                        "org.osgi.service.cm.ConfigurationPermissionCollection$1\n" +
                        "org.osgi.service.cm.ConfigurationPlugin\n" +
                        "org.osgi.service.cm.ManagedService\n" +
                        "org.osgi.service.cm.ManagedServiceFactory"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("39 duplicate classes found in JARs: [jboss-as-osgi-http-7.5.11.Final-redhat-2.jar, org.osgi.enterprise-4.2.0.redhat-10.jar]\n" +
                        "org.osgi.service.http.HttpContext\n" +
                        "org.osgi.service.http.HttpService\n" +
                        "org.osgi.service.http.NamespaceException"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("39 duplicate classes found in JARs: [jboss-as-osgi-jpa-7.5.11.Final-redhat-2.jar, org.osgi.enterprise-4.2.0.redhat-10.jar]\n" +
                        "org.osgi.service.jpa.EntityManagerFactoryBuilder"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("39 duplicate classes found in JARs: [org.apache.felix.log-1.0.0.redhat-3.jar, org.osgi.enterprise-4.2.0.redhat-10.jar]\n" +
                        "org.osgi.service.log.LogEntry\n" +
                        "org.osgi.service.log.LogListener\n" +
                        "org.osgi.service.log.LogReaderService\n" +
                        "org.osgi.service.log.LogService"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("39 duplicate classes found in JARs: [jbosgi-resolver-api-3.0.1.Final-redhat-2.jar, org.apache.felix.configadmin-1.2.8.redhat-4.jar, org.osgi.enterprise-4.2.0.redhat-10.jar]\n" +
                        "org.osgi.util.tracker.AbstractTracked\n" +
                        "org.osgi.util.tracker.BundleTracker\n" +
                        "org.osgi.util.tracker.BundleTracker$Tracked\n" +
                        "org.osgi.util.tracker.BundleTrackerCustomizer\n" +
                        "org.osgi.util.tracker.ServiceTracker\n" +
                        "org.osgi.util.tracker.ServiceTracker$1\n" +
                        "org.osgi.util.tracker.ServiceTracker$AllTracked\n" +
                        "org.osgi.util.tracker.ServiceTracker$Tracked\n" +
                        "org.osgi.util.tracker.ServiceTrackerCustomizer"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().equals("39 duplicate classes found in JARs: [jbosgi-resolver-api-3.0.1.Final-redhat-2.jar, org.osgi.enterprise-4.2.0.redhat-10.jar]\n" +
                        "org.osgi.util.xml.XMLParserActivator"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),

            new FilterItem(Colour.YELLOW)
                  .addTest("org.jboss.manu.units.eap.tattletale.EapNoVersionReportParser.EvaluateNoVersionReport.jarswithoutversion.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().contains("Total JARs without a version found: 13"))
                  .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=918566 https://bugzilla.redhat.com/show_bug.cgi?id=918564"),

      };

      return coreFilter(failedTest, items);
   }
}
