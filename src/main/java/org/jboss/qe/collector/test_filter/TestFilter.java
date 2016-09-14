/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.qe.collector.test_filter;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.FilterItem;

/**
 *
 * @author Jan Dobeš
 */

public class TestFilter extends AbstractFilter {
   static FilterItem[] items = {
    new FilterItem(Colour.RED).setErrorText("Wrong phone number size").addTest("org.jboss.as.quickstarts.bean_validation.test.FfValidationTest#testPhoneViolation"),
    new FilterItem(Colour.RED).setErrorText("Wrong phone number size").addTest("org.jboss.as.quickstarts.bean_validation.test.MemberValidationTest#testPhoneViolation"),
    new FilterItem(Colour.RED).setErrorText("Wrong name").addTest("org.jboss.as.quickstarts.bean_validation.test.FfValidationTest#testNameViolation"),
    new FilterItem(Colour.RED).setErrorText("Wrong email format").addTest("org.jboss.as.quickstarts.bean_validation.test.MemberValidationTest#testEmailViolation"),
    new FilterItem(Colour.RED).setErrorText("").addTest("org.jboss.manu.units.eap.tattletale.EapEliminateJarsReportParser#EvaluateEliminateJarsReport.jarswithdifferentversions"),
    new FilterItem(Colour.RED).setErrorText("Version wasn't reported").addTest("org.jboss.manu.units.eap.tattletale.EapNoVersionReportParser#EvaluateNoVersionReport.jarswithoutversion"),
    new FilterItem(Colour.RED).setErrorText("Multiple locations report").addTest("org.jboss.manu.units.eap.tattletale.EapMultipleLocationsReportParser#EvaluateMultipleLocationsReport.jarswithmultiplelocations"),
    new FilterItem(Colour.RED).setErrorText("No signed jars found").addTest("org.jboss.manu.units.eap.tattletale.EapSigningInformationReportParser#EvaluateSigningInformationReport.signedjars"),
    new FilterItem(Colour.RED).setErrorText("No black listed archives found.").addTest("org.jboss.manu.units.eap.tattletale.EapBlackListedReportParser#org.jboss.manu.units.eap.tattletale.EapBlackListedReportParser"),
    new FilterItem(Colour.RED).setErrorText("Eliminate jars report").addTest("org.jboss.manu.units.eap.tattletale.EapEliminateJarsReportParser#EvaluateEliminateJarsReport.jarswithdifferentversions"),
    new FilterItem(Colour.RED).setErrorText("Some classes was duplicated").addTest("org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser#EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars"),
   };

   public String filter(FailedTest failedTest) {
      return coreFilter(failedTest, items);
   }
}
