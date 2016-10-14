package org.jboss.qe.collector.filter.eap64xAcceptance;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;
//import org.json.JSONObject;

/**
 * Created by jbilek on 22/09/16.
 * filter for https://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-64x-patched-repository-maven-check-unique-artifactId/
 */
public class Eap64xPatchedRepositoryMavenCheckUniqueArtifactId extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {
      // model for filter
      FilterItem[] items = {

            new FilterItem(Colour.YELLOW)
                  .addTest("*")
                  //.addTestMatcher((JSONObject errorDetails) -> errorDetails.get("name").toString().contains("jboss-as.*-uniq_artifact_version"))
                  //.addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorStackTrace").toString().contains("is duplicated in Maven repo"))
                  .setErrorText("tracked in several BZs, see TCMS and results from 6.4.0.GA run for details"),
      };

      return coreFilter(failedTest, items);
   }
}
