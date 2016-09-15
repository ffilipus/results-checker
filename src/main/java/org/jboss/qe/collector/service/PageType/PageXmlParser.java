package org.jboss.qe.collector.service.PageType;

import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.Tools;
import org.jboss.qe.collector.filter.Filter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PageXmlParser {
   private Filter filter;

   public PageXmlParser(Filter f) {
      this.filter = f;
   }

   public void run(String path) throws IOException {
      SAXBuilder builder = new SAXBuilder();
      List<File> files = Tools.fileLoader(path);

      for (File file : files) {
         System.out.println("**" + file.getAbsolutePath() + "lets start");
         try {
            if (file.exists()) {
               Document document = builder.build(file);
               List testSuite = document.getRootElement().getChildren();
               for (Object aTestSuite : testSuite) {
                  Element testCase = (Element) aTestSuite;
                  System.out.println(file.getAbsolutePath() + "\n" + testCase.getContent().toString());
                  if (testCase.getName().equals("testcase") && ((testCase.getChild("failure") != null) || testCase.getChild("error") != null)) {
                     String errorChildName = testCase.getChild("error") != null ? "error" : "failure";
                     String processedIssue = processIssues(new FailedTest(testCase.getAttributeValue("classname") + "#" + testCase.getAttributeValue("name"), "", testCase.getChildText(errorChildName)));
                     testCase.removeChild(errorChildName);
                     addErrorMessage(testCase, processedIssue, document, file);
                  }
               }
            }
         } catch (JDOMException jdomex) {
            jdomex.printStackTrace();
         }
      }
   }

   private void addErrorMessage(Element testCase, String processedIssue, Document document, File file) throws IOException {
      String[] processedIssues = processedIssue.split("-");
      if (processedIssues.length > 1) {
         testCase.addContent(new Element("errormessage").setText(processedIssues[1]));
         XMLOutputter xmlOutput = new XMLOutputter();
         xmlOutput.setFormat(Format.getPrettyFormat());
         xmlOutput.output(document, new FileWriter(file.getAbsolutePath()));
      }
   }

   private String processIssues(FailedTest failedTest) {
      return filter.filter(failedTest).getJobError();
   }
}