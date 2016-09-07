package org.jboss.qe.collector;

import org.jboss.qe.collector.filter.*;
import org.jboss.qe.collector.filter.cli.*;
import org.jboss.qe.collector.filter.installer.*;
import org.jboss.qe.collector.filter.messaging.*;
import org.jboss.qe.collector.filter.resteasy.*;
import org.jboss.qe.collector.filter.scripts.*;
import org.jboss.qe.collector.filter.testsuite.*;
import org.jboss.qe.collector.service.*;
import org.jboss.qe.collector.service.PageType.PageParser;

import javax.ws.rs.client.Client;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
//import org.codehaus.jackson;



/**
 * // TODO store the results to cache on the first run, fetch them next time
 * // TODO we need a tool, that will go through all issues in filters and determine which are already resolved
 *
 * @author Petr Kremensky pkremens@redhat.com on 07/07/2015
 */
public class Main {
    //private static final Closure filterFailed = { test -> test.status.matches("(FAILED|REGRESSION)") };
    private static Map<String, List<String>> failures = new LinkedHashMap<String, List<String>>();
    private static Map<String, Integer> buildsPerMatrix = new HashMap<String, Integer>();
    private static boolean failedOrAborted = false;
    private static int totalBuilds = 0;
    private static Filter filter;
    private static final boolean printSecured = Boolean.valueOf(System.getProperty("print.secured", "true"));
    private static final boolean printErrorDetails = Boolean.valueOf(System.getProperty("print.error.details", "false"));

    public static void main(String[] args) {

        if (args.length < 1) {
            throw new IllegalArgumentException("You have to pass some job name for processing");
        }

        // Filter configuration
        // TODO - just temporary solution until we move core to separate project
        // use first job to determine the filter
        String firstJob = args[0];

        if (firstJob.contains("eap-7x-as-testsuite-")) {
            filter = new Eap7xAsTestsuiteTest710();

        } else if (firstJob.contains("eap-6x-as-testsuite-")) {
            filter = new Eap6xAsTestsuite();

        } else if (firstJob.contains("eap-7x-installer-") || firstJob.matches("EAP-REGRESSION-.*")) {
            filter = new Eap7xInstallerTestsuite();

        } else if (firstJob.contains("eap-7x-scripts-")) {
            filter = new Eap7xScriptsTestsuite();

        } else if (firstJob.contains("eap-64x-patched-scripts")) {
            filter = new Eap6xScriptsTestsuite();

        } else if (firstJob.contains("resteasy")) {
            filter = new Eap7xResteasyTestsuite();
        } else if (firstJob.contains("artemis-project-testsuite-prepare")) {
            filter = new Eap7xArtemisTestsuite();
        } else if (firstJob.contains("artemis-project-testsuite-rhel")) {
            filter = new Eap7xArtemisTestsuiteEUS();
        } else if (firstJob.contains("eap7-artemis-qe-internal")) {
            filter = new Eap7xMessagingInternalTestsuite();
        } else if (firstJob.contains("eap-7x-cli-embedded")) {
            filter = new Eap7xCliEmbeddedFilter();
        } else if (firstJob.contains("eap7-artemis-ha-failover-bridges")) {
            filter = new Eap7xHA();
        }
        // Print selected filter class name
        System.out.println(dyeText("Filter class:", Colour.BLACK_BOLD));
        System.out.println(filter == null ? " - no filter in use" : " - "+filter.getClass().getName());

        // Introduction
        System.out.println("\n"
        +dyeText("Legend:", Colour.BLACK_BOLD)+"\n"+
        " - "+dyeText("POSSIBLE REGRESSION", Colour.RED)+"\n"+
        " - "+dyeText("KNOWN ISSUE", Colour.YELLOW)+"\n"+
        " - "+dyeText("ENVIRONMENT ISSUES AND OTHERS WITHOUT BZ/JIRA", Colour.PURPLE)+"\n"+
        "");

        System.out.println(dyeText("Collect results for:", Colour.BLACK_BOLD));
        for (String it :args) {
            System.out.println(" - "+it);
        }

        // Handle phase - process the jobs
        for (String jobName :args) {
            String[] splitRes = jobName.split(":", 2);
            jobName = splitRes[0];
            String buildNum = splitRes.length > 1 ? splitRes[1] : "lastBuild";
            PageParser job = JobService.getJob(jobName, buildNum, JobService.getNewRESTClient());
            System.out.println("\n"+dyeText(jobName, Colour.BLACK_BOLD));
            if (JobService.isMatrix(job)) {
                handleMatrix(jobName, job);
            } else {
                try {
                    handleSingle(jobName, job, buildNum);
                } catch (JSONException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Results aggregation phase - process the results
        printResults(args);
    }

    /**
     * Process single executor job.
     * @param jobName Name of the single executor job.
     * @param job JSON representation of the job
     */
    private static void handleSingle(String jobName, PageParser job, String buildNum) throws JSONException {
        String printableUlr = getPrintableUrl((String)job.get("url"), (String)job.get("result"));
        System.out.println(printableUlr);
        // handle single
        Set<String> cases = new HashSet<String>();
        PageParser data = JobService.getTestReport(jobName, buildNum, null);
        // ignore runs without any results
        if (data != null) {
            totalBuilds++;
            System.out.println(" - "+dyeText("PASSED: "+data.get("passCount")+", FAILED: "+data.get("failCount")+", SKIPPED: "+data.get("skipCount"), Colour.BLACK_BOLD));
            /*for (List testCase : (List<List>) ((Map) data.get("suites")).get("cases")) {

                testCase.findAll(filterFailed).each {
                    // process the issue here to avoid mixing of platform specific issues in aggregation
                    String processedIssue = processIssues(new FailedTest("testName: "+it.className+"#"+it.name+", buildUrl: "+job..containsKey("url")+", testCase: "+it));
                    cases.add(processedIssue);
                    System.out.println(" - "+processedIssue);
                    if (printErrorDetails) {
                        print printError(it);
                    }
                }
            }*/
            
            //System.out.println(data.getObject().getJSONObject("suites").getJSONObject("cases").getString("age"));
            //System.out.println(data.getObject().getString("suites"));
            //System.out.println(data.getCases());
            JSONArray casesObject = data.getCases();
            for(int i = 0; i < casesObject.length(); i++) {
                //System.out.println(casesObject.getJSONObject(i));
                if(casesObject.getJSONObject(i).getString("status").equals("FAILED") || casesObject.getJSONObject(i).getString("status").equals("REGRESSION")) {
                    //System.out.println("Failed or regression: ");
                    String processedIssue = processIssues(new FailedTest(casesObject.getJSONObject(i).getString("className")+"#"+casesObject.getJSONObject(i).getString("name"), "buildUrl: "+job.get("url"), "testCase: "+casesObject.getJSONObject(i)));
                    cases.add(processedIssue);
                    System.out.println(" - "+processedIssue);
                    if (printErrorDetails) {
                        System.out.println(printError(casesObject.getJSONObject(i)));
                    }
                }
            }
        } else {
            System.out.println(dyeText(" - NO RESULTS AVAILABLE", Colour.BLACK_BOLD));
        }
        //failures.put(printableUlr, cases);
    }

    /**
     * Handle one configuration from the Matrix job.
     * @param configurationUrl Url of job configuration
     * @param jobName Name of the matrix parent
     * @param cases Failed test cases. Items are add to this set in this function
     * @return If this build was finished, returned value is 1. Otherwise 0.
     */
     private static int handleMatrixConfiguration(String configurationUrl, String jobName, List<String> cases) {
        int buildsInMatrix = 0;
        Client client = JobService.getNewRESTClient();
        StringBuilder result = new StringBuilder();
        String name = configurationUrl.substring(configurationUrl.indexOf(jobName));
        // keep url in clickable form
        String url = "";
        try{
            url = URLDecoder.decode(configurationUrl, "UTF-8").replaceAll("\\s", "%20");
        } catch (UnsupportedEncodingException uee){
            return -1;
        }
        PageParser matrixChild = JobService.getJob(name, "", client);
        result.append(" - "+getPrintableUrl(url, (String)matrixChild.get("result")));
         PageParser data = JobService.getTestReport(name, "", client);
        if (data != null) {
            totalBuilds++;
            buildsInMatrix++;
            result.append("  - "+dyeText("PASSED: "+data.get("passCount")+", FAILED: "+data.get("failCount")+", SKIPPED: +"+data.get("skipCount"), Colour.BLACK_BOLD)+"\n");
            /*for (List testCase : data.get("suites").get("cases")) {
                /*testCase.findAll(filterFailed).each {
                    // process the issue here to avoid mixing of platform specific issues in aggregation
                    String processedIssue = processIssues(new FailedTest(testName: "${it.className}#${it.name}", buildUrl: url, testCase: it));
                    synchronized (this) {
                        it.putAt("url", url);    // at least one item in case should be unique
                        cases.add(processedIssue);
                    }
                    result.append("  -- "+processedIssue+"\n");
                    if (printErrorDetails) {
                        result.append(printError(it));
                    }
                }
            }*/
            
            JSONArray casesObject = data.getCases();
            for(int i = 0; i < casesObject.length(); i++) {
                try {
                    //System.out.println(casesObject.getJSONObject(i));
                    if(casesObject.getJSONObject(i).getString("status").equals("FAILED") || casesObject.getJSONObject(i).getString("status").equals("REGRESSION")) {
                        //System.out.println("Failed or regression: ");
                        String processedIssue = processIssues(new FailedTest(casesObject.getJSONObject(i).getString("className") + "#" + casesObject.getJSONObject(i).getString("name"), "buildUrl: " + configurationUrl, "testCase: " + casesObject.getJSONObject(i)));
                        synchronized (Main.class) {
                            
                            cases.add(processedIssue);
                        }
                        result.append("  -- "+processedIssue+"\n");
                        System.out.println(" - "+processedIssue);
                        
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
     * @param jobName Name of the matrix parent,
     * @param job JSON representation of the job.
     */
    private static void handleMatrix(String jobName, PageParser job) {
        int buildsInMatrix = 0;

        String printableUlr = getPrintableUrl(job.get("url"), job.get("result"));
        Map<String, Set<Map>> jobFailures = new TreeMap<String, Set<Map>>();
        // use List to be able to count the occurrence of the failure in aggregated results
        List<String> cases = new LinkedList<String>();
        Set<String> matrixJobs = getMatrixJobUrls(job);
        for (String jobUrl: matrixJobs){
            int buildsInMatrixOneJob = handleMatrixConfiguration(jobUrl, jobName, cases);
            buildsInMatrix += buildsInMatrixOneJob;
        }
        //jobFailures.put(printableUlr, cases);
        buildsPerMatrix.put(printableUlr, buildsInMatrix);
        //failures.putAll(jobFailures);
    }

    /**
     * Get url and its result.
     */
    private static String getPrintableUrl(String url, String result) {
        return printSecured ? url.replace("http:", "https:") : url+" - "+dyeJobResult(result);
    }

    /**
     * Print details about test case failure
     * @param testCase Tast case which failed
     */
    private static String printError(JSONObject testCase) {
        String result = "";
        try {
            result+="Age = "+testCase.getString("age")+"\n";
            result+="Status = "+testCase.getString("status")+"\n";
            result+=testCase.getString("errorDetails") +"\n";
            result+=testCase.getString("errorStackTrace") +"\n";
        } catch (JSONException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Get URL for all active configurations triggered by current build of matrix job parent.
     * @param job Marix job parent.
     * @return URLs of all active configurations for current build of job.
     */
    private static Set<String> getMatrixJobUrls(PageParser job) {
        Set<String> triggeredConfiguration = new HashSet<String>();
        System.out.println(job.get("runs"));
        /*for (PageParser it: ((PageParser)job).get("runs")){
            triggeredConfiguration.add(it.get("url"));
        }*/
        return triggeredConfiguration;
    }

    /**
     * Print aggregated results of all jobs passed as the argument. Dye all issues using processIssues() method.
     */
    private static void printResults(String[] args) {
        System.out.println("\n"+dyeText("########    AGGREGATED RESULTS PER JOB   ########", Colour.CYAN_BOLD));
        List<String> totalIssues = new ArrayList<String>();
        Iterator entries = failures.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, List<String>> thisEntry = (Map.Entry<String, List<String>>) entries.next();

            String url = thisEntry.getKey();
            List<String> issues = thisEntry.getValue();
            System.out.println(url);
            if (buildsPerMatrix.keySet().contains(url)) {
                System.out.println(dyeText("TOTAL FINISHED BUILDS: ${buildsPerMatrix.get(url)}", Colour.BLACK_BOLD));
            } else {
                System.out.println(dyeText("Single-configuration project", Colour.BLACK_BOLD));
            }
            totalIssues.addAll(issues);
            for (String issue: issues){
                System.out.println(" - "+Collections.frequency(issues, issue)+"x "+issue);
            }
            System.out.println();
        }

        // do all results aggregation only in case there are more than one job to check
        if (args.length > 1) {
            System.out.println("\n"+dyeText("########    AGGREGATION OF ALL RESULTS    ########", Colour.CYAN_BOLD));
            System.out.println(dyeText("Collect results for:", Colour.BLACK_BOLD));
            for (String key:failures.keySet()){
                System.out.println(" - "+key);
            }
            System.out.println(dyeText("\nTOTAL FINISHED BUILDS: "+totalBuilds, Colour.BLACK_BOLD));

            for (String issue: new TreeSet<String>(totalIssues)){
                System.out.println(" - "+Collections.frequency(totalIssues, issue)+"x "+issue);
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
     * @param text Text to be dyed.
     * @param colour Colour which should be used for dyeing the text.
     * @return Dyed text.
     */
    private static String dyeText(String text, Colour colour) {
        return colour.getColour()+""+text+""+Colour.RESET.getColour();
    }

    /**
     * Use this to dye the job status. Change the value of failedOrAborted property to true if some Aborted or Failed
     * run was found. This property is used during processiong of results.
     * @param jobResult Result of the job.
     * @return Dyed String of job"s result.
     */
    private static String dyeJobResult(String jobResult) {
        String result = null;
        if (jobResult.equals("ABORTED")) {
            result = dyeText(jobResult, Colour.BLACK_BOLD);
            failedOrAborted = true;
        }else if(jobResult.equals("FAILURE")){
                result = dyeText(jobResult, Colour.RED_BOLD);
                failedOrAborted = true;
        }else if(jobResult.equals("SUCCESS")){
                result = dyeText(jobResult, Colour.BLUE_BOLD);
        }else if(jobResult.equals("UNSTABLE")){
                result = dyeText(jobResult, Colour.YELLOW_BOLD);
        }else if(jobResult.equals("")){
                result = dyeText("RUNNING", Colour.PURPLE_BOLD);
        }
        return result;
    }

    /**
     * Process the test failure:
     *  - dye the text according to the issue category
     *  - append Bugzilla/Jira tracker link to issue
     * @param failedTest Full qualified name of failed test case in format like org.package.test.TestCase#testMethod.
     * @return Pre-formatted test case report ready for printing.
     */
    private static String processIssues(FailedTest failedTest) {
        if (filter != null) {
            return filter.filter(failedTest);
        } else {
            return dyeText(failedTest.testName, Colour.RED);
        }
    }
}
