package org.jboss.qe.collector.filter.cli;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * @author Petr Kremensky pkremens@redhat.com on 15/10/2015
 */

public class Eap7xCliEmbeddedFilter extends AbstractFilter {
    private static final String JIRA_JBOSS_ISSUE = "https://issues.jboss.org/browse/";

    private static final FilterItem[] FILTER_ITEMS = {

            // https://issues.jboss.org/browse/JBEAP-5382 - Embedded server started non-modular use only first --jboss-home for FS paths
            new FilterItem(Colour.YELLOW).setErrorText(JIRA_JBOSS_ISSUE + "JBEAP-5382 - Embedded server started non-modular use only first --jboss-home for FS paths")
                    .addTest("org.jboss.qe.cli.embed.common.NonModularServerTestCase#testMultipleInstances"),

            // https://issues.jboss.org/browse/JBEAP-5390 - Demote the ERROR message about unavailable HTTP/2 on IBM JDK
            new FilterItem(Colour.YELLOW).addUrl(".*ibm1.8.*").setErrorText(JIRA_JBOSS_ISSUE +
                    "JBEAP-5390 - Demote the ERROR message about unavailable HTTP/2 on IBM JDK")
                    .addTest("org.jboss.qe.cli.embed.common.ModularServerTestCase#multipleReloadOfRunningModeTest")
                    .addTest("org.jboss.qe.cli.embed.common.NonModularServerTestCase#multipleReloadOfRunningModeTest")

    };

    //@Override
    public String filter(FailedTest failedTest) {

        return coreFilter(failedTest, FILTER_ITEMS);
    }
}
