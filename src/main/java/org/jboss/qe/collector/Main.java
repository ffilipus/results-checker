package org.jboss.qe.collector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jboss.qe.collector.filter.Filter;
import org.jboss.qe.collector.service.JobService;
import org.jboss.qe.collector.service.PageType.PageParser;
import org.jboss.qe.collector.service.PageType.PageXmlParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * // TODO store the results to cache on the first run, fetch them next time
 * // TODO we need a tool, that will go through all issues in filters and determine which are already resolved
 *
 * @author Petr Kremensky pkremens@redhat.com on 07/07/2015
 */
public class Main {
   //private static final Closure filterFailed = { test -> test.status.matches("(FAILED|REGRESSION)") };
   private static Map<String, List<String>> failures = new LinkedHashMap<>();
   private static Map<String, Integer> buildsPerMatrix = new HashMap<>();
   private static boolean failedOrAborted = false;
   private static int totalBuilds = 0;
   public static Filter filter;
   private static final boolean printSecured = Boolean.valueOf(System.getProperty("print.secured", "true"));
   private static final boolean printErrorDetails = Boolean.valueOf(System.getProperty("print.error.details", "false"));
   private static Injector injector;

   public static void main(String[] args) {

      if (args.length < 1) {
         throw new IllegalArgumentException("You have to pass some job name for processing");
      }

      // Filter configuration
      if (filter == null) {
         injector = Guice.createInjector(new FilterInjector(args));
         filter = injector.getInstance(Filter.class);
      }

      // TODO solve it
      boolean app_use_case = true;

      if (app_use_case) { // using like client application - will download data from server and show list of failed tests
         // Print selected filter class name
         printSelectedFilters();
         // Introduction
         printHelp();
         printJobs(args);
         // Handle phase - process the jobs
         handleJobsFromServer(args);
         // Results aggregation phase - process the results
         printResults(args);
      } else { // using like jenkins post build action - will modify surfire reports
         printWellcomeScreen();
         // Print selected filter class name
         printSelectedFilters();
         // Handle phrase - modify surfire-reports on server
         String path_to_reports = args[0];
         PageXmlParser xmlParser = new PageXmlParser(filter);
         try {
            xmlParser.run(path_to_reports);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   private static void printWellcomeScreen() {
      System.out.println("*** RESULTS-CHECKER ***");
   }

   private static void printSelectedFilters() {
      System.out.println(dyeText("Filter class:", Colour.BLACK_BOLD));
      System.out.println(filter == null ? " - no filter in use" : " - " + filter.getClass().getName());
   }

   private static void printHelp() {
      System.out.println("\n"
            + dyeText("Legend:", Colour.BLACK_BOLD) + "\n" +
            " - " + dyeText("POSSIBLE REGRESSION", Colour.RED) + "\n" +
            " - " + dyeText("KNOWN ISSUE", Colour.YELLOW) + "\n" +
            " - " + dyeText("ENVIRONMENT ISSUES AND OTHERS WITHOUT BZ/JIRA", Colour.PURPLE) + "\n" +
            "");
   }

   private static void printJobs(String[] args) {
      System.out.println(dyeText("Collect results for:", Colour.BLACK_BOLD));
      for (String it : args) {
         System.out.println(" - " + it);
      }
   }

   private static void handleJobsFromServer(String[] args) {
      for (String jobName : args) {
         String[] splitRes = jobName.split(":", 2);
         jobName = splitRes[0];
         String buildNum = splitRes.length > 1 ? splitRes[1] : "lastBuild";
         PageParser job = JobService.getJob(jobName, buildNum, JobService.getNewRESTClient());
         System.out.println("\n" + dyeText(jobName, Colour.BLACK_BOLD));
         if (JobService.isMatrix(job)) {
            handleMatrix(jobName, job);
         } else {
            handleSingle(jobName, job, buildNum);
         }
      }
   }

   /**
    * Process single executor job.
    *
    * @param jobName Name of the single executor job.
    * @param job     JSON representation of the job
    */
   //failures.put(printableUlr, cases);
   private static void handleSingle(String jobName, PageParser job, String buildNum) {
      String printableUlr = getPrintableUrl(job.get("url"), job.get("result"));
      System.out.println(printableUlr);
      // handle single
      List<String> cases = new LinkedList<>();
      PageParser data = JobService.getTestReport(jobName, buildNum, null);
      // ignore runs without any results
      if (data != null) {
         totalBuilds++;
         System.out.println(" - " + dyeText("PASSED: " + data.get("passCount") + ", FAILED: " + data.get("failCount") + ", SKIPPED: " + data.get("skipCount"), Colour.BLACK_BOLD));
         JSONArray casesObject = data.getCases();
         for (int i = 0; i < casesObject.length(); i++) {
            try {
               //System.out.println(casesObject.getJSONObject(i));
               if (casesObject.getJSONObject(i).getString("status").equals("FAILED") || casesObject.getJSONObject(i).getString("status").equals("REGRESSION")) {
                  //System.out.println("Failed or regression: ");
                  String processedIssue = processIssues(new FailedTest(casesObject.getJSONObject(i).getString("className") + "#" + casesObject.getJSONObject(i).getString("name"), "buildUrl: " + job.get("url"), casesObject.getJSONObject(i)));
                  cases.add(processedIssue);
                  System.out.println(" - " + processedIssue);
                  if (printErrorDetails) {
                     System.out.println(printError(casesObject.getJSONObject(i)));
                  }
               }
            } catch (JSONException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      } else {
         System.out.println(dyeText(" - NO RESULTS AVAILABLE", Colour.BLACK_BOLD));
      }
      failures.put(printableUlr, cases);
   }

   /**
    * Handle one configuration from the Matrix job.
    *
    * @param configurationUrl Url of job configuration
    * @param jobName          Name of the matrix parent
    * @param cases            Failed test cases. Items are add to this set in this function
    * @return If this build was finished, returned value is 1. Otherwise 0.
    */
   private static int handleMatrixConfiguration(String configurationUrl, String jobName, List<String> cases) {
      int buildsInMatrix = 0;
      Client client = JobService.getNewRESTClient();
      StringBuilder result = new StringBuilder();
      String name = configurationUrl.substring(configurationUrl.indexOf(jobName));
      // keep url in clickable form
      String url;
      try {
         url = URLDecoder.decode(configurationUrl, "UTF-8").replaceAll("\\s", "%20");
      } catch (UnsupportedEncodingException uee) {
         return -1;
      }
      PageParser matrixChild = JobService.getJob(name, "", client);
      result.append(" - ").append(getPrintableUrl(url, matrixChild.get("result")));
      PageParser data = JobService.getTestReport(name, "", client);
      if (data != null) {
         totalBuilds++;
         buildsInMatrix++;
         result.append("  - ").append(dyeText("PASSED: " + data.get("passCount") + ", FAILED: " + data.get("failCount") + ", SKIPPED: +" + data.get("skipCount"), Colour.BLACK_BOLD)).append("\n");

         JSONArray casesObject = data.getCases();
         for (int i = 0; i < casesObject.length(); i++) {
            try {
               //System.out.println(casesObject.getJSONObject(i));
               if (casesObject.getJSONObject(i).getString("status").equals("FAILED") || casesObject.getJSONObject(i).getString("status").equals("REGRESSION")) {
                  //System.out.println("Failed or regression: ");
                  String processedIssue = processIssues(new FailedTest(casesObject.getJSONObject(i).getString("className") + "#" + casesObject.getJSONObject(i).getString("name"), "buildUrl: " + configurationUrl, casesObject.getJSONObject(i)));
                  synchronized (Main.class) {
                     cases.add(processedIssue);
                  }
                  result.append("  -- ").append(processedIssue).append("\n");
                  System.out.println(" - " + processedIssue);

                  if (printErrorDetails) {
                     result.append(casesObject.getJSONObject(i));
                  }
               }
            } catch (JSONException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      } else {
         result.append(dyeText("  -- NO RESULTS AVAILABLE", Colour.BLACK_BOLD));
      }
      System.out.println(result);
      return buildsInMatrix;
   }

   /**
    * Handle the Matrix job.
    *
    * @param jobName Name of the matrix parent,
    * @param job     JSON representation of the job.
    */
   private static void handleMatrix(String jobName, PageParser job) {
      int buildsInMatrix = 0;

      String printableUlr = getPrintableUrl(job.get("url"), job.get("result"));
      Map<String, List<String>> jobFailures = new HashMap<>();
      // use List to be able to count the occurrence of the failure in aggregated results
      List<String> cases = new LinkedList<>();
      List<String> matrixJobs = getMatrixJobUrls(job);
      for (String jobUrl : matrixJobs) {
         int buildsInMatrixOneJob = handleMatrixConfiguration(jobUrl, jobName, cases);
         buildsInMatrix += buildsInMatrixOneJob;
      }
      jobFailures.put(printableUlr, cases);
      buildsPerMatrix.put(printableUlr, buildsInMatrix);
      failures.putAll(jobFailures);
   }

   /**
    * Get url and its result.
    */
   private static String getPrintableUrl(String url, String result) {
      return printSecured ? url.replace("http:", "https:") : url + " - " + dyeJobResult(result);
   }

   /**
    * Print details about test case failure
    *
    * @param testCase Tast case which failed
    */
   private static String printError(JSONObject testCase) {
      String result = "";
      try {
         result += "Age = " + testCase.getString("age") + "\n";
         result += "Status = " + testCase.getString("status") + "\n";
         result += testCase.getString("errorDetails") + "\n";
         result += testCase.getString("errorStackTrace") + "\n";
      } catch (JSONException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
      }
      return result;
   }

   /**
    * Get URL for all active configurations triggered by current build of matrix job parent.
    *
    * @param job Marix job parent.
    * @return URLs of all active configurations for current build of job.
    */
   private static List<String> getMatrixJobUrls(PageParser job) {
      List<String> triggeredConfiguration = new LinkedList<>();
      String buildNumber = job.get("number");
      for (int i = 0;i < job.getRuns().length();i++) {
         try {
            JSONObject it = job.getRuns().getJSONObject(i);
            Boolean fullMatrix = Boolean.getBoolean(Tools.getEnvironmentVariable("MATRIX_FULL"));
            if (it.get("number").equals(buildNumber) || fullMatrix) {
               triggeredConfiguration.add(it.getString("url"));
            }
         } catch (JSONException e) {
            e.printStackTrace();
         }
      }
      return triggeredConfiguration;
   }

   /**
    * Print aggregated results of all jobs passed as the argument. Dye all issues using processIssues() method.
    */
   private static void printResults(String[] args) {
      System.out.println("\n" + dyeText("########    AGGREGATED RESULTS PER JOB   ########", Colour.CYAN_BOLD));
      List<String> totalIssues = new ArrayList<>();
      for (Map.Entry<String,List<String>> thisEntry : failures.entrySet()) {
         String url = thisEntry.getKey();
         List<String> issues = thisEntry.getValue();
         System.out.println(url);
         if (buildsPerMatrix.keySet().contains(url)) {
            System.out.println(dyeText("TOTAL FINISHED BUILDS: " + buildsPerMatrix.get(url), Colour.BLACK_BOLD));
         } else {
            System.out.println(dyeText("Single-configuration project", Colour.BLACK_BOLD));
         }
         totalIssues.addAll(issues);
         for (String issue : issues) {
            System.out.println(" - " + Collections.frequency(issues, issue) + "x " + issue);
         }
         System.out.println();
      }

      // do all results aggregation only in case there are more than one job to check
      if (args.length > 1) {
         System.out.println("\n" + dyeText("########    AGGREGATION OF ALL RESULTS    ########", Colour.CYAN_BOLD));
         System.out.println(dyeText("Collect results for:", Colour.BLACK_BOLD));
         for (String key : failures.keySet()) {
            System.out.println(" - " + key);
         }
         System.out.println(dyeText("\nTOTAL FINISHED BUILDS: " + totalBuilds, Colour.BLACK_BOLD));

         for (String issue : new TreeSet<>(totalIssues)) {
            System.out.println(" - " + Collections.frequency(totalIssues, issue) + "x " + issue);
         }
         System.out.println();
      }

      if (filter != null) filter.onFinish();

      if (failedOrAborted) {
         System.out.println("Some job(s) finished with status FAILURE or ABORTED, further investigation is needed!");
      }

   }

   /**
    * Dye the text with desired colour.
    *
    * @param text   Text to be dyed.
    * @param colour Colour which should be used for dyeing the text.
    * @return Dyed text.
    */
   private static String dyeText(String text, Colour colour) {
      return colour.getColour() + "" + text + "" + Colour.RESET.getColour();
   }

   /**
    * Use this to dye the job status. Change the value of failedOrAborted property to true if some Aborted or Failed
    * run was found. This property is used during processiong of results.
    *
    * @param jobResult Result of the job.
    * @return Dyed String of job"s result.
    */
   private static String dyeJobResult(String jobResult) {
      String result = null;
      switch (jobResult) {
         case "ABORTED":
            result = dyeText(jobResult, Colour.BLACK_BOLD);
            failedOrAborted = true;
            break;
         case "FAILURE":
            result = dyeText(jobResult, Colour.RED_BOLD);
            failedOrAborted = true;
            break;
         case "SUCCESS":
            result = dyeText(jobResult, Colour.BLUE_BOLD);
            break;
         case "UNSTABLE":
            result = dyeText(jobResult, Colour.YELLOW_BOLD);
            break;
         case "":
            result = dyeText("RUNNING", Colour.PURPLE_BOLD);
            break;
      }
      return result;
   }

   /**
    * Process the test failure:
    * - dye the text according to the issue category
    * - append Bugzilla/Jira tracker link to issue
    *
    * @param failedTest Full qualified name of failed test case in format like org.package.test.TestCase#testMethod.
    * @return Pre-formatted test case report ready for printing.
    */
   private static String processIssues(FailedTest failedTest) {
      return filter.filter(failedTest).getJobError();
   }
}