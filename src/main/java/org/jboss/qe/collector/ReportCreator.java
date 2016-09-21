package org.jboss.qe.collector;

import org.jdom.Element;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by jbilek on 20/09/16.
 * create file similar to show report in jenkins but with
 */
public class ReportCreator {

   private String filename;

   private String finalContent;
   private String matchedTestsContent;
   private String notMatchedTestsContent;

   private int id_test;

   private int failed_not_matched;
   private int failed_matched;
   private int total_count_tests;

   ReportCreator(String filename) {
      this.filename = filename;
      finalContent = "";
      matchedTestsContent = "";
      notMatchedTestsContent = "";
      id_test = 0;
      failed_not_matched = 0;
      failed_matched = 0;
      total_count_tests = 0;
   }

   private String getTestResultContent(Element testCase, String filterMessage) {

      String errorChildName = testCase.getChild("error") != null ? "error" : "failure";
      String job_full_name = testCase.getAttributeValue("classname") + "." + testCase.getAttributeValue("name"); //;"org.jboss.manu.units.eap.tattletale.EapMultipleJarsReportParser.EvaluateMultipleJarsReport.searching_duplicate_classes_in_jars";
      String job_url = "https://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/" + Tools.getEnvironmentVariable("JOB_NAME");
      String run_time = testCase.getAttributeValue("time");
      String age = "#"; // TODO maybe is somehow possible find up this information
      String s_id_test = "id" + (id_test++);
      String errorDetails = testCase.getChild(errorChildName).getAttributeValue("message");
      String stackTrace = testCase.getChild(errorChildName).getValue();
      String outputContent = ""; // TODO file with this output is by this surfire report - same name, not starting with "TEST-", ending with ".txt"
      String errorContent = ""; // TODO file with this output is by this surfire report - same name, not starting with "TEST-", ending with ".txt"

      String testContentStart = "\n" +
            "            <tr>\n" +
            "               <td class=\"pane\">\n" +
            "                  \n" +
            "                  <a id=\"test-" + job_full_name + s_id_test + "/-showlink\" title=\"Show details\" onclick=\"showFailureSummary(&#39;test-" + job_full_name + s_id_test + "/&#39;,&#39;" + job_full_name + s_id_test + "//summary&#39;)\">\n" +
            "                  +\n" +
            "                  </a>\n" +
            "                  <a id=\"test-" + job_full_name + s_id_test + "/-hidelink\" title=\"Hide details\" style=\"display: none;\" onclick=\"hideFailureSummary(&#39;test-" + job_full_name + s_id_test + "/&#39;)\">\n" +
            "                  --\n" +
            "                  </a>\n" +
            "                  &nbsp;\n" +
            "                  <a class=\"model-link inside\" href=\"" + job_url + "/lastCompletedBuild/testReport/" + job_full_name + s_id_test + "/\">\n" +
            "                  " + job_full_name + "\n" +
            "                  </a>\n" +
            "                  <div id=\"test-" + job_full_name + s_id_test + "/\" style=\"display: none;\" class=\"failure-summary\">\n";

      String testContentEnd = "                  </div>\n" +
            "               </td>\n" +
            "               <td style=\"text-align:right;\" data=\"0.0\" class=\"pane no-wrap\">" + run_time + " ms</td>\n" +
            "               <td class=\"pane\" style=\"text-align:right;\">" + age + "</td>\n" +
            "            </tr>";

      String testContent = "";
      testContent += testContentStart;
      if (filterMessage != null && !filterMessage.equals("")) {
         testContent += getContentAttributeIntoTestResult(s_id_test, "filterMessage", "Filter Message", filterMessage, true);
         testContent += getContentAttributeIntoTestResult(s_id_test, "error", "Error Details", errorDetails, false);
      } else {
         testContent += getContentAttributeIntoTestResult(s_id_test, "error", "Error Details", errorDetails, true);
      }

      if (stackTrace != null && !stackTrace.equals("")) {
         testContent += getContentAttributeIntoTestResult(s_id_test, "stacktrace", "Stack Trace", stackTrace, false);
      }

      if (outputContent != null && !outputContent.equals("")) {
         testContent += getContentAttributeIntoTestResult(s_id_test, "outputContent", "Output", outputContent, false);
      }

      if (errorContent != null && !errorContent.equals("")) {
         testContent += getContentAttributeIntoTestResult(s_id_test, "errorContent", "Error Output", errorContent, false);
      }

      testContent += testContentEnd;

      return testContent;
   }

   /**
    *
    * @param s_id_test id of test in string
    * @param type : stacktrace, filterMessage, error
    * @param prompt Stack Trace, Filter Message, Error Details
    * @param cont content of attribute
    * @return piece of html code of specified attribute
    */
   private String getContentAttributeIntoTestResult(String s_id_test, String type, String prompt, String cont, boolean show) {
      return "                     <h4><a id=\"" + s_id_test + "-" + type + "-showlink\" title=\"Show " + prompt + "\" style=\"display: " + (show ? "none" : "") + "\" href=\"javascript:showFailureSummary(&#39;" + s_id_test + "-" + type + "&#39;)\">\n" +
            "                        +&nbsp;" + prompt + "\n" +
            "                        </a><a id=\"" + s_id_test + "-" + type + "-hidelink\" title=\"Hide " + prompt + "\" style=\"display: " + (show ? "" : "none") + "\" href=\"javascript:hideFailureSummary(&#39;" + s_id_test + "-" + type + "&#39;)\">--&nbsp;" + prompt + "\n" +
            "                        </a>\n" +
            "                     </h4>\n" +
            "                     <pre id=\"" + s_id_test + "-" + type + "\" style=\"display: " + (show ? "" : "none") + "\">" + cont + "</pre>\n";
   }

   public void addNotMatchedTestResult(Element testCase) {
      failed_not_matched++;
      total_count_tests++;
      notMatchedTestsContent += getTestResultContent(testCase,"");
   }

   public void addMatchedTestResult(Element testCase, String filterMessage) {
      failed_matched++;
      total_count_tests++;
      matchedTestsContent += getTestResultContent(testCase, filterMessage);
   }

   public void addSuccessTestResult() {
      total_count_tests++;
   }

   private String getHeadContent() {

      String job_name = Tools.getEnvironmentVariable("JOB_NAME");
      int run_number = 2;
      String job_url = "https://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-64x-patched-manu-acceptance-tattletale-jbilek/lastCompletedBuild/testReport/history";
      String run_time = "#";
      int test_failures = this.failed_not_matched + this.failed_matched;
      int sum_tests = this.total_count_tests;
      double matched_line = (double)test_failures / sum_tests * 100;
      double unmatched_line = (double)failed_not_matched / sum_tests * 100;

      return "<!DOCTYPE html>\n" +
            "<html>\n" +
            "   <head >\n" +
            "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "      <title>" + job_name + " #" + run_number + " Test Results [Jenkins]</title>\n" +
            "   </head>\n" +
            "   <body id=\"jenkins\">\n" +
            "      <h1>Test Result</h1>\n" +
            "      <div>\n" +
            "         <div>\n" +
            "            " + test_failures + " failures (known issues: " + this.failed_matched + ", unknown issues: " + this.failed_not_matched + ")\n" +
            "         </div>\n" +
            "         <div style=\"width:100%; height:1em; background-color: #729FCF\">\n" +
            "            <div style=\"width:" + matched_line + "%; height: 1em; background-color: #20941C; float: left\">\n" +
            "            </div>\n" +
            "            <div style=\"width:" + unmatched_line + "%; height: 1em; background-color: #EF2929; float: left\">\n" +
            "            </div>\n" +
            "            <div style=\"width:0.0%; height: 1em; background-color: #FCE94F; float: left\">\n" +
            "            </div>\n" +
            "         </div>\n" +
            "         <div align=\"right\">\n" +
            "            " + sum_tests + " tests\n" +
            "            (±0)\n" +
            "         </div>\n" +
            "      </div>\n" +
            "      <div style=\"text-align:right;\">\n" +
            "         <a href=\"" + job_url + "\">Trval " + run_time + " ms.</a>\n" +
            "      </div>\n" +
            "      <div id=\"description\">\n" +
            "      </div>\n" +
            "      <table style=\"margin-top: 1em; margin-left:0em;\">\n" +
            "      </table>\n";
   }

   void printReport() {
      try {
         String headMatchedContent =
               "      <h2>Known issues</h2>\n" +
                     "      <table class=\"pane sortable bigtable\">\n" +
                     "         <tbody>\n" +
                     "            <tr>\n" +
                     "               <td class=\"pane-header\">\n" +
                     "                  Test name\n" +
                     "                  </a>\n" +
                     "               </td>\n" +
                     "               <td class=\"pane-header\" style=\"width:4em\">\n" +
                     "                  Pending\n" +
                     "                  </a>\n" +
                     "               </td>\n" +
                     "               <td class=\"pane-header\" style=\"width:3em\">\n" +
                     "                  Age\n" +
                     "                  </a>\n" +
                     "               </td>\n" +
                     "            </tr>";
         String headNotMatchedContent =
               "      <h2>Unknown failures</h2>\n" +
                     "      <table class=\"pane sortable bigtable\">\n" +
                     "         <tbody>\n" +
                     "            <tr>\n" +
                     "               <td class=\"pane-header\">\n" +
                     "                  Test name\n" +
                     "                  </a>\n" +
                     "               </td>\n" +
                     "               <td class=\"pane-header\" style=\"width:4em\">\n" +
                     "                  Pending\n" +
                     "                  </a>\n" +
                     "               </td>\n" +
                     "               <td class=\"pane-header\" style=\"width:3em\">\n" +
                     "                  Age\n" +
                     "                  </a>\n" +
                     "               </td>\n" +
                     "            </tr>";

         String footTestsContent = "</tbody>\n" +
               "      </table>\n";
         String footContent = "      \n" +
               "      </div></div>\n" +
               "      <script type=\"text/javascript\">\n" +
               "                     function showFailureSummary(id,query) {\n" +
               "                       var element = document.getElementById(id)\n" +
               "                       element.style.display = \"\";\n" +
               "                       document.getElementById(id + \"-showlink\").style.display = \"none\";\n" +
               "                       document.getElementById(id + \"-hidelink\").style.display = \"\";\n" +
               "                     }\n" +
               "                     \n" +
               "                     function hideFailureSummary(id) {\n" +
               "                       document.getElementById(id).style.display = \"none\";\n" +
               "                       document.getElementById(id + \"-showlink\").style.display = \"\";\n" +
               "                       document.getElementById(id + \"-hidelink\").style.display = \"none\";\n" +
               "                     }\n" +
               "                  </script>\n" +
               "                  <style type=\"text/css\">\n" +
               "                     .failure-summary {\n" +
               "                     margin-left: 2em;\n" +
               "                     }\n" +
               "                     .failure-summary h4 {\n" +
               "                     margin: 0.5em 0 0.5em 0;\n" +
               "                     }\n" +
               "                     .failure-summary h4 a {\n" +
               "                     text-decoration: none;\n" +
               "                     color: inherit;\n" +
               "                     }\n" +
               "                     .failure-summary h4 a img {\n" +
               "                     width: 8px;\n" +
               "                     height: 8px;\n" +
               "                     }\n" +
               "                     .failure-summary pre {\n" +
               "                     margin-left: 2em;\n" +
               "                     }\n" +
               "                     \n" +
               "                     element.style {\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tA.model-link.inside, #breadcrumbs A.inside {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tpadding-right: 16px;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\n" +
               "\t\t\t\t\t\t\t\t\t\ta:visited {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\ttext-decoration: underline;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tcolor: #5c3566;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\ta:link {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\ttext-decoration: underline;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tcolor: #204A87;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\t* {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\t-webkit-box-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\t-moz-box-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tbox-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tuser agent stylesheet\n" +
               "\t\t\t\t\t\t\t\t\t\ta:-webkit-any-link {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tcolor: -webkit-link;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\ttext-decoration: underline;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tcursor: auto;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tInherited from td.pane\n" +
               "\t\t\t\t\t\t\t\t\t\tbody, table, form, td, th, p {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tcolor: #333;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tbody, table, form, input, td, th, p, textarea, select {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tfont-family: Helvetica, Arial, sans-serif;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tfont-size: 13px;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tInherited from table.pane.sortable.bigtable\n" +
               "\t\t\t\t\t\t\t\t\t\ttable.pane {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\twidth: 100%;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tborder-collapse: collapse;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tborder: 1px #bbb solid;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tbody, table, form, td, th, p {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tcolor: #333;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tbody, table, form, input, td, th, p, textarea, select {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tfont-family: Helvetica, Arial, sans-serif;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tfont-size: 13px;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tuser agent stylesheet\n" +
               "\t\t\t\t\t\t\t\t\t\ttable {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tdisplay: table;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tborder-collapse: separate;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tborder-spacing: 2px;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tborder-color: grey;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tInherited from body#jenkins.yui-skin-sam.jenkins-1.609.3.d6929063\n" +
               "\t\t\t\t\t\t\t\t\t\tbody, table, form, td, th, p {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tcolor: #333;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tbody, table, form, input, td, th, p, textarea, select {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tfont-family: Helvetica, Arial, sans-serif;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tfont-size: 13px;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tPseudo ::before element\n" +
               "\t\t\t\t\t\t\t\t\t\t*:before, *:after {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\t-webkit-box-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\t-moz-box-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tbox-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "\t\t\t\t\t\t\t\t\t\tPseudo ::after element\n" +
               "\t\t\t\t\t\t\t\t\t\t*:before, *:after {\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\t-webkit-box-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\t-moz-box-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t\t\tbox-sizing: border-box;\n" +
               "\t\t\t\t\t\t\t\t\t\t}\n" +
               "                  </style>\n" +
               "   </body>\n" +
               "</html>";
         finalContent = getHeadContent()
               + headNotMatchedContent + notMatchedTestsContent + footTestsContent
               + headMatchedContent + matchedTestsContent + footTestsContent
               + footContent;
         PrintWriter out = new PrintWriter(this.filename);
         out.println(finalContent);
         out.flush();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   }
}
