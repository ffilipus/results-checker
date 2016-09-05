package org.jboss.qe.collector.filter;

import org.jboss.qe.collector.FailedTest;

/**
 * @author Petr Kremensky pkremens@redhat.com on 28/07/2015
 */
public interface Filter {
    /**
     * Process the test failure:
     *  - dye the text according to the issue category
     *  - append Bugzilla/Jira tracker link to issue
     * @param failedTest Full qualified name of failed test case in format like org.package.test.TestCase#testMethod.
     * @return Pre-formatted test case report ready for printing.
     */
    String filter(FailedTest failedTest);

    // TODO move it from interface
    /**
     * Method is executed after aggregation of all results.
     */
    void onFinish();
}
