package org.jboss.qe.collector;

import org.apache.cxf.helpers.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Created by fjerabek on 8.9.16.
 */

public class SimpleJobTest {
   private String filename = "test";
   private String data = "This is testing string";
   private ByteArrayOutputStream baos;
   private PrintStream old;
   @Test
   public void cacheMayCreateFile() {
      Cache cache = new Cache(filename);
      cache.add(data);
      Assert.assertTrue(cache.exist());
   }

   @Test
   public void cacheMayWriteTheSameAsRead() {
      Cache cache = new Cache(filename);
      cache.add(data);
      String read = cache.getAll();
      Assert.assertEquals(data,read);
   }

   @Test
   public void cacheMayBeActual() {
      Cache cache = new Cache(filename);
      cache.add("Actual");
      Assert.assertTrue(cache.isActual(1));
   }
   @Before
   public void prepare() {
      baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);
      old = System.out;
      System.setOut(ps);

      String[] fileNames = new String[]{"jobPage.json","lastBuild.json","testReport.json"};
      String[] resources = getContentResourceFiles(fileNames);
      String[] names = getCachedNames(fileNames);

      for (int i = 0; i < names.length; i++) {
         Cache cache = new Cache(names[i]);
         cache.add(resources[i]);
      }
   }

   @Test
   public void testBasicFunctionality() {
      String test = "eap-70x-maven-repository-check-valid-POM-and-Metadata-files";


      new Main().main(new String[]{test});
      String output = baos.toString();
      String[] split = output.split("\n");
      for (int i = 0; i < split.length; i++) {
         split[i] = split[i].replaceAll("\u001B\\[[;\\d]*m", "");
      }
      Assert.assertEquals("There is possibly filter in use", " - no filter in use", split[1]);
      Assert.assertEquals("Legend was not shown", " - POSSIBLE REGRESSION", split[4]);
      Assert.assertEquals("Legend was not shown", " - KNOWN ISSUE", split[5]);
      Assert.assertEquals("Legend was not shown", " - ENVIRONMENT ISSUES AND OTHERS WITHOUT BZ/JIRA", split[6]);
      Assert.assertEquals("Wrong name of job shown", " - " + test, split[9]);
      Assert.assertEquals("Wrong name of job shown", test, split[11]);
      Assert.assertEquals("Wrong URL", "https://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-70x-maven-repository-check-valid-POM-and-Metadata-files/9/", split[12]);
      Assert.assertEquals("Bad print of test status", " - PASSED: 308, FAILED: 2, SKIPPED: 0", split[13]);
      Assert.assertEquals("Error was not shown", " - infinispan-directory-provider-8.1.4.Final-redhat-1#infinispan-directory-provider-8.1.4.Final-redhat-1", split[14]);
      Assert.assertEquals("Error was not shown", " - infinispan-parent-8.1.4.Final-redhat-1#infinispan-parent-8.1.4.Final-redhat-1", split[15]);
   }

   @After
   public void cleanup() {
      System.out.flush();
      System.setOut(old);
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

   private String[] getCachedNames(String[] fileNames) {
      String[] rawFileNames = removeSuffix(fileNames);
      String[] names = new String[rawFileNames.length];
      for (int i = 0; i < rawFileNames.length; i++) {
         if (rawFileNames[i].equals("testReport")) {
            names[i] = "result_checker_temp_eap-70x-maven-repository-check-valid-POM-and-Metadata-files"
                + ".lastBuild." + rawFileNames[i] + ".api.json.index.html";
         } else {
            names[i] = "result_checker_temp_eap-70x-maven-repository-check-valid-POM-and-Metadata-files"
                + "." + rawFileNames[i] + ".api.json.index.html";
         }
      }
      return names;
   }
}