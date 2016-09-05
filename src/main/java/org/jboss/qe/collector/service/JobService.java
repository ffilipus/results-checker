package org.jboss.qe.collector.service;

import org.jboss.qe.collector.service.PageType.PageParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author Petr Kremensky pkremens@redhat.com on 07/07/2015
 */
public class JobService {
    private static
    //final String CLIENT_URL = System.getProperty("jenkins.dn", "http://jenkins.mw.lab.eng.bos.redhat.com/hudson")+"/job/";
    final String CLIENT_URL = System.getProperty("jenkins.dn", "http://jenkinse.zloutek-soft.cz//hudson")+"/job/";

//    private static waitResponseTime = 0
    private static Client client = ClientBuilder.newClient();

    public static Client getNewRESTClient() {
        return ClientBuilder.newClient();
    }
    /**
     * Get the test report of job in JSON format.
     * @param name Name of the Jenkins job.
     * @param build Build number for the job. "lastBuild" is used by default. Use "" to omit the build number from request.
     * @return Test report of job in JSON format.
     */
    public static PageParser getTestReport(String name, String build, Client client) {
        //String query = name+"/"+(build.equals("") ? "" : build + "/")+"testReport/api/json";
        // temporary solution
        String query = name+"/"+(build.equals("") ? "" : build + "/")+"testReport/api/json/index.html";

        return getResponseData(query, client);
    }

    /**
     * Get job in JSON format
     * @param name Name of the Jenkins job.
     * @param build Build number for the job. "lastBuild" is used by default. Use "" to omit the build number from request.
     * @return Get job in JSON format.
     */
    public static PageParser getJob(String name, String build, Client client) {
        //String query = name+"/"+(build.equals("") ? "" : build + "/")+"api/json";
        // temporary solution
        String query = name+"/"+(build.equals("") ? "" : build + "/")+"api/json/index.html";

        return getResponseData(query, client);
    }

    private static PageParser getResponseData(String query, Client client_p) {
        if (client_p == null) {
            client_p = client;
        }
        try {
            query = URLDecoder.decode(query, "UTF-8");
        } catch (UnsupportedEncodingException uee){
            return null;
        }

        WebTarget target = client_p.target(CLIENT_URL);

        target = target.path(query);
        Invocation.Builder builder = target.request();
        Response response = builder.get();
        String data = builder.get(String.class);
        PageParser page = new PageParser(data);

        assert response.getStatus() == 200;
        return page;
    }

    //    static void printTotalResponseWaitTime() {
    //        println("Total wait response time: ${waitResponseTime}s")
    //    }

    /**
     * Check whether current job is matrix parent or single job.
     * @param job JSON representation of job to be tested.
     * @return <code>true</code> if the job is just a matrix parent, <code>false</code> otherwise.
     */
    public static boolean isMatrix(PageParser job) {
        return job.isMatrix();
    }

}
