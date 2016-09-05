package org.jboss.qe.collector.service;

import groovy.json.internal.Map;
import groovyx.net.http.HttpResponseDecorator;
import groovyx.net.http.HttpResponseException;
import groovyx.net.http.RESTClient;

import java.util.Map;

/**
 * @author Petr Kremensky pkremens@redhat.com on 07/07/2015
 */
public class JobService {
    private static
    final String CLIENT_URL = System.getProperty("jenkins.dn", "http://jenkins.mw.lab.eng.bos.redhat.com/hudson")+"/job/";

//    private static waitResponseTime = 0
    private static RESTClient client = new RESTClient(CLIENT_URL);

    public static RESTClient getNewRESTClient() {
        return new RESTClient(CLIENT_URL);
    }
    /**
     * Get the test report of job in JSON format.
     * @param name Name of the Jenkins job.
     * @param build Build number for the job. "lastBuild" is used by default. Use "" to omit the build number from request.
     * @return Test report of job in JSON format.
     */
    public static Map getTestReport(String name, String build, RESTClient client) {
        String query = name+"/"+(build.equals("") ? "" : build + "/")+"testReport/api/json";
        return getResponseData(query, client);
    }

    /**
     * Get job in JSON format
     * @param name Name of the Jenkins job.
     * @param build Build number for the job. "lastBuild" is used by default. Use "" to omit the build number from request.
     * @return Get job in JSON format.
     */
    public static Map getJob(String name, String build, RESTClient client) {
        String query = name+"/"+(build.equals("") ? "" : build + "/")+"api/json";
        return getResponseData(query, client);
    }

    private static Map getResponseData(String query, RESTClient client_p) {
        if (client_p == null) {
            client_p = client;
        }
        query = URLDecoder.decode(query, "UTF-8");
        HttpResponseDecorator resp;
        try {
//            def start = System.currentTimeMillis()
            resp = (HttpResponseDecorator)client_p.get(path: query);
//            def time = (System.currentTimeMillis() - start) / 1000
//            waitResponseTime += time
//            println("Response took: ${time}s")

        } catch (HttpResponseException hre) {
            // remove these once all is tuned up
//            println('remove these once all is tuned up')
//            hre.printStackTrace()
//            System.err.println("Wrong query, \'${query}\' not found.")
            return null
        }
        assert resp.status == 200;
        return (Map)resp.data;
    }

    //    static void printTotalResponseWaitTime() {
    //        println("Total wait response time: ${waitResponseTime}s")
    //    }

    /**
     * Check whether current job is matrix parent or single job.
     * @param job JSON representation of job to be tested.
     * @return <code>true</code> if the job is just a matrix parent, <code>false</code> otherwise.
     */
    public static boolean isMatrix(Map job) {
        return job.containsKey("runs");
    }

}
