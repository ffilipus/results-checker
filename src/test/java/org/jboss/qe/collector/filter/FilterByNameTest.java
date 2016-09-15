package org.jboss.qe.collector.filter;

import org.jboss.qe.collector.CachePrepare;
import org.jboss.qe.collector.Main;
import org.jboss.qe.collector.Tools;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

/**
 *  Created by fjerabek on 12.9.16.
 */
public class FilterByNameTest {
   private ByteArrayOutputStream baos;
   private PrintStream old = System.out;

   @Before
   public void before() {
      baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);
      System.setOut(ps);
      Tools.setEnvironmentVariable("JAR_PATH", "./results-checker-filters");
      Tools.setEnvironmentVariable("PACKAGE", "org.jboss.qe.collector.filter.testfilters.TestFilterName");
   }

   @Test
   public void filterByNameTest() {
      String testName = "eap-70x-maven-repository-check-valid-POM-and-Metadata-files";
      String[] resourceFiles = new String[]{"failedJob/lastBuild.json", "failedJob/testReport.json"};

      CachePrepare.prepare(testName, resourceFiles, getClass());

      Main.main(new String[] {testName});
      String output = baos.toString();
      String[] split = output.split("\n");

      Pattern pattern = Pattern.compile("\u001B\\[33m");

      System.out.println(split[15]);
      Assert.assertTrue("Filter does have wrong color", pattern.matcher(split[15]).find());
      Assert.assertTrue("FiltersFromFile does not work", split[15].contains("Testing filter by name"));

      Assert.assertFalse("Filer does have wrong color", pattern.matcher(split[16]).find());
      Assert.assertFalse("FiltersFromFile does not work", split[16].contains("Testing filter by name"));
   }

   @After
   public void cleanup() {
      System.out.flush();
      System.setOut(old);
   }
}

