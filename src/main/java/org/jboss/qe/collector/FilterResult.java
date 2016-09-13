package org.jboss.qe.collector;

/**
 * Created by fjerabek on 13.9.16.
 */
public class FilterResult {
   private boolean match;
   private String jobError;

   public FilterResult(boolean match, String jobError) {
      this.jobError = jobError;
      this.match = match;
   }

   public String getJobError() {
      return jobError;
   }

   public boolean isMatch() {
      return match;
   }
}
