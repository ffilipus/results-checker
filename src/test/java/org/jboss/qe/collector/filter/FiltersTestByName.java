package org.jboss.qe.collector.filter;

import org.apache.cxf.helpers.IOUtils;
import org.jboss.qe.collector.Cache;
import org.jboss.qe.collector.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Created by fjerabek on 12.9.16.
 */
public class FiltersTestByName {
   private ByteArrayOutputStream baos;
   private PrintStream old = System.out;

   @Test
   public void testFilters() {
      String testName = "eap-70x-maven-repository-check-valid-POM-and-Metadata-files";
      String[] resourceFiles = new String[]{"lastBuild.json","testReport.json"};

      prepare(testName, resourceFiles);

      new Main().main(new String[] {testName});
      String output = baos.toString();
      String[] split = output.split("\n");

      Pattern pattern = Pattern.compile("\u001B\\[33m");

      Assert.assertTrue("Filer does have wrong color", pattern.matcher(split[14]).find());
      Assert.assertTrue("Filer does have wrong color", pattern.matcher(split[15]).find());
      pattern = Pattern.compile("\u001B\\[32m");
      Assert.assertTrue("Filer does have wrong color", pattern.matcher(split[16]).find());
      pattern = Pattern.compile("\u001B\\[30m");
      Assert.assertFalse("Filer does have wrong color", pattern.matcher(split[16]).find());

      for (int i = 0; i < split.length; i++) {
         split[i] = split[i].replaceAll("\u001B\\[[;\\d]*m", "");
      }

      Assert.assertTrue("Filters does not work", split[14].contains("Testing filter"));
      Assert.assertTrue("Filters does not work", split[15].contains("Testing filter"));
      Assert.assertTrue("Filters does not work", split[16].contains("Testing filter"));

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
         try (FileInputStream is = new FileInputStream(getClass().getClassLoader().getResource("failedJob/" + fileNames[i]).getFile())) {
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

