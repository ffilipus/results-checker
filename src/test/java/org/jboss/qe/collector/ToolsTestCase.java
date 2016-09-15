package org.jboss.qe.collector;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Jan Dobes
 */
public class ToolsTestCase {
   @Test
   public void testGetEnvironmentVariable() {
      Assert.assertNotNull("Environment variable CACHE_TIME_VALIDITY is null", Tools.getEnvironmentVariable("CACHE_TIME_VALIDITY"));
      Assert.assertNotNull("Environment variable SERVER_NAME is null", Tools.getEnvironmentVariable("SERVER_NAME"));
      Assert.assertNotNull("Environment variable MATRIX_FULL is null", Tools.getEnvironmentVariable("MATRIX_FULL"));
   }

   @Test
   public void testSetEnvironmentVariable() {
      Tools.setEnvironmentVariable("TEST_VAR", "TEST");
      Assert.assertTrue("New environmet variable doesn't exist", Tools.isDefinedEnvironmentVariable("TEST_VAR"));
      Assert.assertEquals("New environment variable isn't equals", "TEST", Tools.getEnvironmentVariable("TEST_VAR"));
   }
}
