package org.jboss.qe.collector;

import org.junit.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiri bilek on 12. 09. 2016.
 */
public class ServerBasicTest {
   private PrintStream old = System.out;

   @After
   public void cleanup() {
      System.out.flush();
      System.setOut(old);

      Map<String, String> env = new HashMap<>();
      // TODO set environment variables:
      // set servername
      env.put("SERVER_NAME","jenkinse.zloutek-soft.cz");
      // set cache validity to 0
      env.put("CACHE_TIME_VALIDITY","300");

      set(env);
   }

   @Before
   public void prepare() {
      Map<String, String> env = new HashMap<>();
      // TODO set environment variables:
      // set servername
      env.put("SERVER_NAME","jenkinse.zloutek-soft.cz");
      // set cache validity to 0
      env.put("CACHE_TIME_VALIDITY","0");

      set(env);
   }

   @SuppressWarnings("unchecked")
   private void set(Map<String, String> newenv) {
      try {
         try {
            Class[] classes = Collections.class.getDeclaredClasses();
            Map<String, String> env = System.getenv();
            for (Class cl : classes) {
               if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                  Field field = cl.getDeclaredField("m");
                  field.setAccessible(true);
                  Object obj = field.get(env);
                  Map<String, String> map = (Map<String, String>) obj;
                  map.clear();
                  map.putAll(newenv);
               }
            }
         } catch (NoSuchFieldException e) {
            e.printStackTrace();
         }
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }

   @Test
   //@Ignore("server does not work maybe")
   public void testBasicFunctionalityWithDataFromServer() {
      String testName = "eap-70x-maven-repository-check-boms-for-dependency-tree-zip-plus-central";

      ByteArrayOutputStream ba_stream = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(ba_stream);
      System.setOut(ps);

      Main.main(new String[]{testName});
      String output = ba_stream.toString();

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
}
