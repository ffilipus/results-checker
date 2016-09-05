package org.jboss.qe.collector.filter.testsuite;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * @author Petr Kremensky pkremens@redhat.com on 28/07/2015
 */

public class Eap6xAsTestsuite extends AbstractEapAsTestsuite {

    public String filter(FailedTest failedTest) {
        // model for filter
        FilterItem[] items = {

                // Bug 1191493 - Invoking EJB2 stateful bean which is being destroyed leads to NullPointerException
                new FilterItem().addTest("org.jboss.as.test.integration.ejb.ejb2.stateful.StatefulPassivationExpirationTestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1191493").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.EJB),


                new FilterItem().addTest(
                        "org.jboss.as.test.integration.ejb.transaction.cmt.beforecompletion." +
                                "BeforeCompletionExceptionDestroysBeanTestCase#testExceptionInBeforeCompletionDestroysBean")
                        .setErrorText("java.lang.reflect.UndeclaredThrowableException: null - " +
                                "same stack trace as https://bugzilla.redhat.com/show_bug.cgi?id=1191493").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.EJB),

                // Bug 963610 - Intermittent failures in StatefulWithXPCFailoverTestCase
                new FilterItem().addTest("org.jboss.as.test.clustering.cluster.ejb3.xpc.StatefulWithXPCFailoverTestCase\\(SYNC-tcp\\).*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=963610").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.CLUSTER),

                // Bug 1172180 - Kerberos authentication into Management Console does not select correct keytab element with IPv6 address
                new FilterItem().addUrl(".*ipv6.*").addTest("org.jboss.as.test.manualmode.security.realms.KerberosHttpInterfaceTestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1172180").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.SECURITY),

                // Bug 1181615 - KerberosInCLITestCase fails with IPv6
                new FilterItem().addUrl(".*ipv6.*").addTest("org.jboss.as.test.manualmode.security.realms.KerberosInCLITestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1181615").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.SECURITY),

                // Bug 1206469 - Occasional failures of org.jboss.as.patching.installation.LayerTestCase.patchAndRollbackLayer()
                new FilterItem().addTest("org.jboss.as.patching.installation.LayerTestCase#patchAndRollbackLayer")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1206469 - NFS issue, see BZ").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.NFS),

                // Bug 1267243 - Address already in use while adding security-realm to http-interface (Windows 2k12 w/ Oracle JDK8)
                new FilterItem().addUrl(".*java18.*2k12r2.*")
                        .addTest("org.jboss.as.test.manualmode.security.LdapCacheInSecurityRealmsTestCase#.*")
                        .addTest("org.jboss.as.test.manualmode.security.realms.KerberosHttpInterfaceTestCase#.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1267243").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.OTHER),

                // Environment issue, see server logs in surefire report, full profile fails to start in 60s
                new FilterItem().addTest("org.jboss.as.test.smoke.jms.JMSBridgeTest.*")
                        .addTest("org.jboss.as.test.smoke.jms.SendToJMSQueueTest.*")
                        .addTest("org.jboss.as.test.smoke.jms.SendToJMSTopicTest.*")
                        .addTest("org.jboss.as.test.smoke.jms.SendToQueueFromWithinTransactionTest.*")
                        .addTest("org.jboss.as.test.smoke.jms.SendToTopicFromWithinTransactionTest.*")
                        .addTest("org.jboss.as.test.smoke.messaging.MessagingTestCase.*")
                        .addTest("org.jboss.as.test.smoke.messaging.client.jms.JmsClientTestCase.*")
                        .addTest("org.jboss.as.test.smoke.messaging.client.messaging.MessagingClientTestCase.*")
                        .setErrorText("env. issue, arquillian fails to start server in 60s, see server log in surefire-report").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.HARDWARE),

                // Connection timeout
                new FilterItem().addTest("org.jboss.as.test.integration.security.cli.JsseTestCase.*")
                        .addTest("org.jboss.as.test.compat.jpa.hibernate.Hibernate3EmbeddedProviderTestCase#testServletSubDeploymentRead")
                        .addTest("org.jboss.as.test.compat.jpa.hibernate.Dom4jLoadingTestCase#testServletSubDeploymentRead")
                        .addTest("org.jboss.as.test.compat.jpa.hibernate.Hibernate3EmbeddedProviderNullDataSourceTestCase#testServletSubDeploymentRead")
                        .addTest("org.jboss.as.test.integration.management.cli.TimeoutRedeployTestCase#org.jboss.as.test.integration.management.cli.TimeoutRedeployTestCase.*")
                        .addTest("org.jboss.as.test.integration.ejb.stateless.pooling.ejb2.EjbRemoveUnitTestCase#testEjbRemoveCalledForEveryCall.*")
                        .setErrorText("see connection timeout in logs").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.HARDWARE),

                // fails only on dev156 - @mchoma - domain name on dev156 starts with upper case
                // https://issues.jboss.org/browse/JBEAP-1580 - Kerberos authentication for remoting on hostname which contains uppercase letter
                new FilterItem().addUrl(".*windows.*")
                        .addTest("org.jboss.as.test.manualmode.security.realms.KerberosInCLITestCase.*")
                        .setErrorText("https://issues.jboss.org/browse/JBEAP-1580 - Kerberos authentication for remoting on hostname which contains uppercase letter - fails on dev156").setColour(Colour.RED)
                        .setCategory(AsTsCategory.OTHER),

                /*
                 * These tests secures the mgmt interface, arquillian is unable to use it in case of failure, separate
                 * "jbossas-cli-https" arquillian container is used for this.
                 */
                new FilterItem().addTest("org.jboss.as.test.manualmode.management.cli.HTTPSConnectioWithCLITestCase.*")
                        .addTest("org.jboss.as.test.manualmode.management.cli.VaultPasswordsInCLITestCase.*")
                        .addTest("org.jboss.as.test.manualmode.security.realms.KerberosInCLITestCase.*")
                        .setErrorText("tests secures the mgmt interface, failure leads to arquillian mulfuntion").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.TEST_CLEANUP),

                // slow hardware (vmg01)
                new FilterItem()
                        .addTest("org.jboss.as.test.integration.ejb.timerservice.mgmt.(Non)?PersistentIntervalTimerManagementTestCase#testSuspendAndTrigger")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1249629").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.HARDWARE),

                // Bug 1169786 - DenyModulePermissionsTestCase, GrantModulePermissionsTestCase, LimitedModulePermissionsTestCase cannot deploy jar
                new FilterItem().addTest("org.jboss.as.testsuite.integration.secman.module.DenyModulePermissionsTestCase.*")
                        .addTest("org.jboss.as.testsuite.integration.secman.module.GrantModulePermissionsTestCase.*")
                        .addTest("org.jboss.as.testsuite.integration.secman.module.LimitedModulePermissionsTestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1169786").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.SECURITY),

                // Bug 949440 - Intermittent failure of SingletonTestCase.testSingletonService
                new FilterItem().addTest("org.jboss.as.test.clustering.cluster.singleton.SingletonTestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=949440").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.CLUSTER),

                // Bug 1078246 - [QE] (6.3.0) org.jboss.as.test.integration.naming.ldap.LdapUrlInSearchBaseTestCase fails on IBMJDK 1.6 due to "Invalid root Dn given"
                new FilterItem().addUrl(".*ibm1\\.?7.*").addTest("org.jboss.as.test.integration.naming.ldap.LdapUrlInSearchBaseTestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1078246").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.SECURITY),

                // Bug 986310 - Random failure of testDomainSecurityAnnotation
                new FilterItem().addTest("org.jboss.as.test.clustering.cluster.ejb3.security.FailoverWithSecurityTestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=986310").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.CLUSTER),

                // Bug 1266498 - Reload operation fails with java.util.concurrent.ExecutionException
                new FilterItem().addTest("org.jboss.as.test.manualmode.messaging.mgmt.JMSBridgeManagementTestCase#testStopStart")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1266498").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.OTHER),

                // Bug 1267173 - Intermittent failures in PooledEJBLifecycleTestCase
                new FilterItem().addTest("org.jboss.as.test.integration.ejb.pool.lifecycle.PooledEJBLifecycleTestCase#testMDB")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1267173").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.EJB),

                // Bug 1269094 - Intermittent failures in ReloadWSDLPublisherTestCase (IPv6 w/ IBM8)
                new FilterItem().addTest("org.jboss.as.test.manualmode.ws.ReloadWSDLPublisherTestCase#testHelloStringAfterReload")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1269094").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.IPV6),

                // KerberosHttpInterfaceTestCase fails on w2k8-ipv6 w/ security manager
                new FilterItem().addUrl(".*ipv6-windows-secman.*").addTest("org.jboss.as.test.manualmode.security.realms.KerberosHttpInterfaceTestCase.*")
                        .setErrorText("fails on windows IPv6 with security manager, olukas is looking into this").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.SECURITY),

                // KerberosHttpInterfaceTestCase
                // https://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-6x-as-testsuite-rhel/RELEASE=6.4.0,jdk=ibm1.6,label_exp=eap-sustaining%20&&%20RHEL6%20&&%20x86_64/103/testReport/junit/org.jboss.as.test.manualmode.security.realms/KerberosHttpInterfaceTestCase/testUserWithTicketFromDifferentRealm/
                new FilterItem().addUrl(".*rhel.*")
                        .addTest("org.jboss.as.test.manualmode.security.realms.KerberosHttpInterfaceTestCase#testUserWithTicketFromDifferentRealm")
                        .setErrorText("olukas is looking into this, not serious issue, see for KrbException, status code: 6").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.SECURITY),

                // wrong IPv6 to hostname mapping
                new FilterItem().addUrl(".*ipv6.*").addUrl(".*rhel.*").addTest(".*AdvancedLdapLoginModuleTestCase.*")
                        .setErrorText("missing IPv6 to hostname mapping (possible fix is add record to /etc/hosts)").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.ENVIRONMENT),

                // wrong clean-up
                new FilterItem().addUrl(".*ipv6.*").addUrl(".*rhel.*")
                        .addTest("org.jboss.as.test.clustering.cluster.ejb3.stateful.passivation.ClusterPassivationTestCase(SYNC-tcp)#stopAndClean")
                        .setErrorText("wrong clean-up, mvinkler should look into this (after his PTO), could broke setting of other tests").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.CLUSTER),

                // protocol timeout
                new FilterItem().addTest("org.jboss.as.test.integration.ejb.async.AsyncMethodTestCase#testRemoteAsynchronousReturnFutureCall")
                        .setErrorText("see for \"5000 MILLISECONDS\", timeout should be increased in protocol module").setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.HARDWARE),

                // EJB client context intermittent failure
                new FilterItem().addTest("org.jboss.as.test.integration.ejb.timerservice.mgmt.NonPersistentIntervalTimerManagementTestCase.*")
                        .setErrorText("known intermittent failure for 6.x stream - should be fixed do 7.x by https://github.com/jbossas/jboss-ejb-client/commit/28d9f5489a3b187c71a093cd616a391ff0bf6478")
                        .setColour(Colour.PURPLE)
                        .setCategory(AsTsCategory.EJB),

                // https://bugzilla.redhat.com/show_bug.cgi?id=1277494  PassivationSucceedsUnitTestCase fails intermittently
                new FilterItem().addTest("org.jboss.as.test.integration.ejb.stateful.passivation.PassivationSucceedsUnitTestCase#testFailedPassivation")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1277494").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.EJB),

                // https://bugzilla.redhat.com/show_bug.cgi?id=1281492   Intermittent fail of OperationCancellationTestCase
                new FilterItem().addTest(".*OperationCancellationTestCase.*")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1281492").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.DOMAIN),

                // https://bugzilla.redhat.com/show_bug.cgi?id=1281744 Intermittent fail of DeploymentManagementTestCase
                new FilterItem().addTest("org.jboss.as.test.integration.domain.suites.DeploymentManagementTestCase#testFullReplaceViaHash")
                        .setErrorText("https://bugzilla.redhat.com/show_bug.cgi?id=1281744").setColour(Colour.YELLOW)
                        .setCategory(AsTsCategory.DOMAIN)
        };

        String output = coreFilter(failedTest, items);
        if (output != null) {
            return output;
        }

        // manual filter if filter model is not sufficient
        if ((failedTest.buildUrl.contains("solaris") && failedTest.buildUrl.contains("sparc")) &&
                (failedTest.testName.contains("org.jboss.as.test.integration.security.loginmodules.LdapExtLikeAdvancedLdapLMTestCase")
                        || failedTest.testName.contains("org.jboss.as.test.integration.security.loginmodules.LdapExtLoginModuleTestCase"))) {
            return dyeText(failedTest.testName, Colour.YELLOW)+" - https://bugzilla.redhat.com/show_bug.cgi?id=1251476";

        } else {
            return dyeText(failedTest.testName, Colour.RED);
        }
    }
}
