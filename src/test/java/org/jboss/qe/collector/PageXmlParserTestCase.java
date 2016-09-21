package org.jboss.qe.collector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.plexus.util.FileUtils;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.jboss.qe.collector.service.PageType.PageXmlParser;

/**
 *
 * @author Jan Dobes
 */

public class PageXmlParserTestCase {

   private final TestFilter filter = new TestFilter();

   @Before
   public void init() {
      try {
         Files.copy(Paths.get("./src/test/resources/pageXmlParser/input/test.xml"), Paths.get("./src/test/resources/pageXmlParser/output/test.xml"), REPLACE_EXISTING);
         Files.copy(Paths.get("./src/test/resources/pageXmlParser/input/test2.xml"), Paths.get("./src/test/resources/pageXmlParser/output/test2.xml"), REPLACE_EXISTING);
         Files.copy(Paths.get("./src/test/resources/pageXmlParser/input/test3.xml"), Paths.get("./src/test/resources/pageXmlParser/output/test3.xml"), REPLACE_EXISTING);
      } catch (IOException ex) {
         Logger.getLogger(PageXmlParserTestCase.class.getName()).log(Level.SEVERE, null, ex);
      }
      Tools.setEnvironmentVariable("DELETE_IF_FILTERED","true");
      Tools.setEnvironmentVariable("JOB_NAME","eap-64x-patched-manu-acceptance-tattletale");
   }

   @Test
   public void testReportWithoutError() {
      ReportCreator rc = new ReportCreator("results-checker-report.html");
      PageXmlParser parser = new PageXmlParser(filter, rc);
      try {
         parser.run("./src/test/resources/pageXmlParser/output/test.xml");
         File testFile = new File("./src/test/resources/pageXmlParser/output/test.xml");
         File expectedOutputFile = new File("./src/test/resources/pageXmlParser/expectedOutput/test.xml");
         Assert.assertTrue("Files aren't equals", FileUtils.contentEquals(testFile, expectedOutputFile));
      } catch (IOException ex) {
         Logger.getLogger(PageXmlParserTestCase.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   @Test
   public void testReportOneError() {
      ReportCreator rc = new ReportCreator("results-checker-report.html");
      PageXmlParser parser = new PageXmlParser(filter, rc);
      try {
         parser.run("./src/test/resources/pageXmlParser/output/test2.xml");
         File testFile = new File("./src/test/resources/pageXmlParser/output/test2.xml");
         File expectedOutputFile = new File("./src/test/resources/pageXmlParser/expectedOutput/test2.xml");
         Assert.assertTrue("Files aren't equals", FileUtils.contentEquals(testFile, expectedOutputFile));
      } catch (IOException ex) {
         Logger.getLogger(PageXmlParserTestCase.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   @Test
   public void testReportMultipleErrors() {
      ReportCreator rc = new ReportCreator("results-checker-report.html");
      PageXmlParser parser = new PageXmlParser(filter, rc);
      try {
         parser.run("./src/test/resources/pageXmlParser/output/test3.xml");
         File testFile = new File("./src/test/resources/pageXmlParser/output/test3.xml");
         File expectedOutputFile = new File("./src/test/resources/pageXmlParser/expectedOutput/test3.xml");
         Assert.assertTrue("Files aren't equals", FileUtils.contentEquals(testFile, expectedOutputFile));
      } catch (IOException ex) {
         Logger.getLogger(PageXmlParserTestCase.class.getName()).log(Level.SEVERE, null, ex);
      }
   }


   private class TestFilter extends AbstractFilter {
      FilterItem[] items = {
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

      public FilterResult filter(FailedTest failedTest) {
         return coreFilter(failedTest, items);
      }
   }
}
