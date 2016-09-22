package org.jboss.qe.collector.filter.eap64xAcceptance;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;
import org.json.JSONObject;

/**
 * Created by jbilek on 22/09/16.
 * filter for http://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-64x-patched-delivered-files-check
 */
public class Eap64xPatchedDeliveredFilesCheck extends AbstractFilter {

   public FilterResult filter(FailedTest failedTest) {
      // model for filter
      FilterItem[] items = {

            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.naming.CheckDeliveredFilesEapCp.CheckDeliveredFiles.root_files_unexpectedfilestest.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().matches(" in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR..zip \n in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR.-maven-repository.zip \n in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR.-patched.zip \n in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR.-patched-maven-repository.zip \n"))
                  .setErrorText("Files added by jenkins jobs - not delivered "),
            new FilterItem(Colour.YELLOW)
                  //.addTest("org.jboss.manu.units.eap.naming.CheckDeliveredFilesEapCp.CheckDeliveredFiles.root_files_unexpectedfilestest.*")
                  .addTestMatcher((JSONObject errorDetails) -> errorDetails.get("errorDetails").toString().matches(" in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR..zip \n in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR.-maven-repository.zip \n in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR.-patched.zip \n in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR.-patched-maven-repository.zip \n in file:/home/hudson/static_build_env/eap/6.4....CP.CR./, unexpected file found: jboss-eap-6.4....CP.CR.-patched-rpm.zip \n"))
                  .setErrorText("Files added by jenkins jobs - not delivered "),
      };

      return coreFilter(failedTest, items);
   }
}
