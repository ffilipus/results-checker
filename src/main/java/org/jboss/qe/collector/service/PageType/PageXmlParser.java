package org.jboss.qe.collector.service.PageType;

import org.jboss.qe.collector.test_filter.TestFilter;
import org.jboss.qe.collector.filter.Filter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.util.List;
import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.Tools;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;



public class PageXmlParser {
   private static Filter filter;
   private static List<File> files;
   public static void run(String path) throws IOException {
      filter = new TestFilter();
      SAXBuilder builder = new SAXBuilder();
      files = Tools.fileLoader(path);
      for (File file : files) {
         System.out.println(file.getAbsolutePath());
      }

      for (File file : files) {
         try {
            if (file.exists()) {
               Document document = (Document) builder.build(file);
               List testSuite = document.getRootElement().getChildren();
               //System.out.println(file.getName());
               for (int i = 0; i < testSuite.size(); i++) {
                  Element testCase = (Element) testSuite.get(i);
                  if (testCase.getName().equals("testcase") && ((testCase.getChild("failure") != null) || testCase.getChild("error") != null)) {
                     String errorChildName = "failure";
                     if (testCase.getChild("error") != null) {
                        errorChildName = "error";
                     }
                     String processedIssue = processIssues(new FailedTest(testCase.getAttributeValue("classname") + "#" + testCase.getAttributeValue("name"), "", testCase.getChildText(errorChildName)));
                     System.out.println(" - " + processedIssue);
                     String[] processedIssues = processedIssue.split("-");
                     testCase.removeChild(errorChildName);
                     if (processedIssues.length > 1) {
                        testCase.addContent(new Element("errormessage").setText(processedIssues[1]));
                        System.out.println(testCase.getChildText("errormessage"));
                        XMLOutputter xmlOutput = new XMLOutputter();
                        xmlOutput.setFormat(Format.getPrettyFormat());
                        xmlOutput.output(document, new FileWriter(file.getAbsolutePath()));
                     }
                  }
               }
           //System.out.println(count);
            }
         }
         catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
         }
      }
   }

   private static String dyeText(String text, Colour colour) {
      return colour.getColour() + "" + text + "" + Colour.RESET.getColour();
   }

   private static String processIssues(FailedTest failedTest) {
      if (filter != null) {
         return filter.filter(failedTest);
      } else {
         return dyeText(failedTest.testName, Colour.RED);
      }
   }
}