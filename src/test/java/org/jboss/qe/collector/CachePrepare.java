package org.jboss.qe.collector;

import org.apache.cxf.helpers.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *  Created by fjerabek on 12.9.16.
 */
public abstract class CachePrepare {

   public static void prepare(String testName, String[] fileNames, Class it) {
      String[] resources = getContentResourceFiles(fileNames, it);
      String[] names = getCachedNames(fileNames, testName);

      for (int i = 0; i < names.length; i++) {
         Cache cache = new Cache(names[i]);
         cache.add(resources[i]);
      }
   }

   private static String[] getContentResourceFiles(String[] fileNames, Class it) {
      String[] resources = new String[fileNames.length];
      for (int i = 0; i < fileNames.length; i++) {
         try (FileInputStream is = new FileInputStream(it.getClassLoader().getResource(fileNames[i]).getFile())) {
            resources[i] = IOUtils.toString(is);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return resources;
   }

   private static String[] removeSuffix(String[] fileNames) {
      String[] rawFilenames = new String[fileNames.length];
      for (int i = 0; i < fileNames.length; i++) {
         String[] name = fileNames[i].split("\\.");
         rawFilenames[i] = name[0];
      }
      return rawFilenames;
   }

   private static String[] getCachedNames(String[] fileNames, String testName) {
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
