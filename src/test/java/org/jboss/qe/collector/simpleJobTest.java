package org.jboss.qe.collector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by fjerabek on 8.9.16.
 */
public class simpleJobTest {
   String filename = "test";
   String data = "This is testing string";
   @Test
   public void cacheMayCreateFile() {
      Cache cache = new Cache(filename);
      cache.add(data);
      Assert.assertTrue(cache.exist());
   }
}