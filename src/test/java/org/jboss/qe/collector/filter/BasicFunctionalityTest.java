package org.jboss.qe.collector.filter;

import org.apache.cxf.helpers.IOUtils;
import org.jboss.qe.collector.Cache;
import org.jboss.qe.collector.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;

/**
 * Created by fjerabek on 8.9.16.
 */

public class BasicFunctionalityTest {

   private ByteArrayOutputStream baos;
   private PrintStream old = System.out;

   @Test
   public void testBasicFunctionality() {
      String testName = "eap-70x-maven-repository-check-boms-for-dependency-tree-zip-plus-central";
      String[] resourceFiles = new String[] {"lastBuild.json","testReport.json"};
      prepare(testName, resourceFiles);

      new Main().main(new String[]{testName});
      String output = baos.toString();
      String[] split = output.split("\n");
      for (int i = 0; i < split.length; i++) {
         split[i] = split[i].replaceAll("\u001B\\[[;\\d]*m", "");
      }
      Assert.assertEquals("There is possibly filter in use", " - no filter in use", split[Arrays.asList(split).indexOf("Filter class:") + 1]);
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

   @After
   public void cleanup() {
      System.out.flush();
      System.setOut(old);
   }

   private void prepare(String testName, String[] fileNames) {
      baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);

      System.setOut(ps);

      String[] resources = getContentResourceFiles(fileNames);
      String[] names = getCachedNames(fileNames, testName);

      for (int i = 0; i < names.length; i++) {
         Cache cache = new Cache(names[i]);
         cache.add(resources[i]);
      }
   }

   private String[] getContentResourceFiles(String[] fileNames) {
      String[] resources = new String[fileNames.length];
      for (int i = 0; i < fileNames.length; i++) {
         try (FileInputStream is = new FileInputStream(getClass().getClassLoader().getResource("simpleJob/" + fileNames[i]).getFile())) {
            resources[i] = IOUtils.toString(is);
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return resources;
   }

   private String[] removeSuffix(String[] fileNames) {
      String[] rawFilenames = new String[fileNames.length];
      for (int i = 0; i < fileNames.length; i++) {
         String[] name = fileNames[i].split("\\.");
         rawFilenames[i] = name[0];
      }
      return rawFilenames;
   }

   private String[] getCachedNames(String[] fileNames, String testName) {
      String[] rawFileNames = removeSuffix(fileNames);
      String[] names = new String[rawFileNames.length];
      for (int i = 0; i < rawFileNames.length; i++) {
         if (rawFileNames[i].contains("testReport")) {
            names[i] = "result_checker_temp_" + testName
                + ".lastBuild.testReport.api.json.index.html";
         } else if (rawFileNames[i].contains("lastBuild")) {
            names[i] = "result_checker_temp_" + testName
                + ".lastBuild.api.json.index.html";
         } else {
            names[i] = "result_checker_temp_" + testName
                + "." + rawFileNames[i] + ".api.json.index.html";
         }
      }
      return names;
   }
}