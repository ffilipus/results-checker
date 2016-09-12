package org.jboss.qe.collector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 *  Created by fjerabek on 8.9.16.
 */

public class BasicFunctionalityTest {

   private ByteArrayOutputStream baos;
   private PrintStream old = System.out;

   @Before
   public void before() {
      baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);
      System.setOut(ps);
   }

   @Test
   public void testBasicFunctionality() {
      String testName = "eap-70x-maven-repository-check-boms-for-dependency-tree-zip-plus-central";
      String[] resourceFiles = new String[] {"simpleJob/lastBuild.json","simpleJob/testReport.json"};
      CachePrepare.prepare(testName, resourceFiles, getClass());

      Main.main(new String[]{testName});
      String output = baos.toString();
      String[] split = output.split("\n");
      for (int i = 0; i < split.length; i++) {
         split[i] = split[i].replaceAll("\u001B\\[[;\\d]*m", "");
      }
      Assert.assertEquals("There is filter in use", " - no filter in use",split[1]);
      Assert.assertEquals("Legend was not shown", " - POSSIBLE REGRESSION", split[4]);
      Assert.assertEquals("Legend was not shown", " - KNOWN ISSUE", split[5]);
      Assert.assertEquals("Legend was not shown", " - ENVIRONMENT ISSUES AND OTHERS WITHOUT BZ/JIRA", split[6]);
      Assert.assertEquals("Wrong name of job shown", " - " + testName, split[9]);
      Assert.assertEquals("Wrong name of job shown", testName, split[11]);
      Assert.assertTrue("Wrong URL printed", split[12].contains(testName));
      Assert.assertTrue("Test report was not shown", split[13].contains("PASSED:"));
      Assert.assertTrue("Test report was not shown", split[13].contains("FAILED:"));
      Assert.assertTrue("Test report was not shown", split[13].contains("SKIPPED:"));
   }

   @After
   public void cleanup() {
      System.out.flush();
      System.setOut(old);
   }
}