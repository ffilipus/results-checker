package org.jboss.qe.collector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
   public void prepare(){
      baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);
      old = System.out;
      System.setOut(ps);
   }
   @Test
   public void testBasicFunctionality() {
      String test = "eap-70x-maven-repository-check-valid-POM-and-Metadata-files";
      new Main().main(new String[]{test});
      String output = baos.toString();
      String[] split = output.split("\n");
      String[] correct;
      for (int i = 0; i < split.length; i++) {
         split[i] = split[i].replaceAll("\u001B\\[[;\\d]*m", "");
      }
      Assert.assertEquals(split[1]," - no filter in use");
      Assert.assertEquals(split[4]," - POSSIBLE REGRESSION");
      Assert.assertEquals(split[5]," - KNOWN ISSUE");
      Assert.assertEquals(split[6]," - ENVIRONMENT ISSUES AND OTHERS WITHOUT BZ/JIRA");
      Assert.assertEquals(split[9]," - eap-70x-maven-repository-check-valid-POM-and-Metadata-files");
      Assert.assertEquals(split[11],"eap-70x-maven-repository-check-valid-POM-and-Metadata-files");
      Assert.assertEquals(split[12],"https://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-70x-maven-repository-check-valid-POM-and-Metadata-files/9/");
      Assert.assertEquals(split[14]," - infinispan-directory-provider-8.1.4.Final-redhat-1#infinispan-directory-provider-8.1.4.Final-redhat-1");
      Assert.assertEquals(split[15]," - infinispan-parent-8.1.4.Final-redhat-1#infinispan-parent-8.1.4.Final-redhat-1");
   }
   @After
   public void cleanup(){
      System.out.flush();
      System.setOut(old);
   }


}