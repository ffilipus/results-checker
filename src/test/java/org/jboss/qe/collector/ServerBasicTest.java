package org.jboss.qe.collector;

import org.jboss.qe.collector.filter.testFilters.EmptyFilter;
import org.junit.*;
import java.io.*;
import java.util.Arrays;

/**
 * Created by jiri bilek on 12. 09. 2016.
 * test if application can reach the server and without caching parse a data
 */
public class ServerBasicTest {
   private PrintStream old = System.out;

   @After
   public void cleanup() {
      System.out.flush();
      System.setOut(old);

      // set servername
      Tools.setEnvironmentVariable("SERVER_NAME","jenkinse.zloutek-soft.cz");
      // set cache validity to default value 300
      Tools.setEnvironmentVariable("CACHE_TIME_VALIDITY","300");
   }

   @Before
   public void prepare() {
      // set servername
      Tools.setEnvironmentVariable("SERVER_NAME","jenkinse.zloutek-soft.cz");
      // set cache validity to 0
      Tools.setEnvironmentVariable("CACHE_TIME_VALIDITY","0");
   }



   @Test
   //@Ignore("server does not work maybe")
   public void testBasicFunctionalityWithDataFromServer() {
      String testName = "eap-70x-maven-repository-check-boms-for-dependency-tree-zip-plus-central";

      ByteArrayOutputStream ba_stream = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(ba_stream);
      System.setOut(ps);

      Main.filter = new EmptyFilter();
      Main.main(new String[]{testName});
      String output = ba_stream.toString();

      String[] split = output.split("\n");
      for (int i = 0; i < split.length; i++) {
         split[i] = split[i].replaceAll("\u001B\\[[;\\d]*m", "");
      }

      Assert.assertEquals("There is possibly filter in use", " - org.jboss.qe.collector.filter.testFilters.EmptyFilter", split[Arrays.asList(split).indexOf("Filter class:") + 1]);
      Assert.assertEquals("Legend was not shown", " - POSSIBLE REGRESSION", split[Arrays.asList(split).indexOf("Legend:") + 1]);
      Assert.assertEquals("Legend was not shown", " - KNOWN ISSUE", split[Arrays.asList(split).indexOf("Legend:") + 2]);
      Assert.assertEquals("Legend was not shown", " - ENVIRONMENT ISSUES AND OTHERS WITHOUT BZ/JIRA", split[Arrays.asList(split).indexOf("Legend:") + 3]);
      Assert.assertEquals("Wrong name of job shown", " - " + testName, split[Arrays.asList(split).indexOf("Collect results for:") + 1]);
      Assert.assertEquals("Wrong name of job shown", testName, split[Arrays.asList(split).indexOf("Collect results for:") + 3]);
      Assert.assertTrue("Wrong URL printed", split[12].contains(testName));
      Assert.assertTrue("Test report was not shown", split[13].contains("PASSED:"));
      Assert.assertTrue("Test report was not shown", split[13].contains("FAILED:"));
      Assert.assertTrue("Test report was not shown", split[13].contains("SKIPPED:"));
   }
}
