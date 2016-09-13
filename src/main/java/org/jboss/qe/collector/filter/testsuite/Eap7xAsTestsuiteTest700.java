package org.jboss.qe.collector.filter.testsuite;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.jboss.qe.collector.filter.FilterItem;

/**
 * @author Petr Kremensky pkremens@redhat.com on 28/07/2015
 */
public class Eap7xAsTestsuiteTest700 extends AbstractEapAsTestsuite {

   public FilterResult filter(FailedTest failedTest) {
      return coreFilter(failedTest, FILTER_ITEMS);
   }

   private static final FilterItem[] FILTER_ITEMS = {
       // https://issues.jboss.org/browse/JBEAP-526 - Some tests throws "Permission check failed" while run with security manager
       new FilterItem().addUrl(".*secman.*").addUrl(".*core.*")
           .addTest("org.jboss.as.test.integration.management.http.HttpDeploymentUploadUnitTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.profiles.LoggingProfilesTestCase#testDeploymentConfigurationResource")
           .addTest("org.jboss.as.test.integration.domain.slavereconnect.SlaveReconnectTestCase#test01_OrderedExtensionsAndDeployments")
           .addTest("org.jboss.as.test.integration.domain.slavereconnect.SlaveReconnectTestCase#test02_DeploymentOverlays")
           .addTest("org.jboss.as.test.integration.domain.rbac.JmxRBACProviderHostScopedRolesTestCase.*")
           .addTest("org.jboss.as.test.integration.domain.rbac.JmxRBACProviderServerGroupScopedRolesTestCase.*")
           .addTest("org.jboss.as.test.integration.domain.DomainControllerMigrationTestCase#testDCFailover")
           .addTest("org.jboss.as.test.integration.domain.suites.DeploymentRolloutFailureTestCase#test")
           .addTest("org.jboss.as.test.manualmode.logging.Log4jAppenderTestCase#logAfterReload")
           .addTest("org.jboss.as.test.manualmode.logging.LoggingPreferencesTestCase#loggingPreferences")
           .addTest("org.jboss.as.test.manualmode.logging.PerDeployLoggingTestCase#disablePerDeployLogging")
           .addTest("org.jboss.as.test.manualmode.logging.SizeAppenderRestartTestCase#rotateFileOnRestartTest")
           .addTest("org.jboss.as.test.integration.mgmt.access.JmxSensitiveTestCase.*")
           .addTest("org.jboss.as.test.integration.mgmt.access.DeploymentScannerTestCase#testFilesystemDeployment_Auto.*")
           .addTest("org.jboss.as.test.integration.logging.profiles.LoggingProfilesTestCase#testProfiles")
           .addTest("org.jboss.as.test.integration.logging.operations.Log4jCustomHandlerTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.operations.CustomHandlerTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.operations.CustomHandlerOperationsTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.operations.CustomFormattersTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.perdeploy.LoggingPropertiesTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.perdeploy.JBossLog4jXmlTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.perdeploy.Log4jPropertiesTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.perdeploy.JBossLoggingPropertiesTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.perdeploy.Log4jXmlTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.syslog.SyslogHandlerTestCase.*")
           .addTest("org.jboss.as.test.integration.logging.profiles.NonExistingProfileTestCase.*")
           .addTest("org.wildfly.core.test.standalone.mgmt.api.DeploymentTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-526 - Some tests throws \"Permission check failed\" while run with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/JBEAP-530 - "XNIO001001: No XNIO provider found" by some tests running with security manager
       new FilterItem().addUrl(".*secman.*").addUrl(".*core.*")
           .addTest("org.wildfly.core.test.standalone.suspend.web.SuspendResumeTestCase.*")
           .addTest("org.jboss.as.test.integration.domain.suspendresume.DomainGracefulShutdownTestCase.*")
           .addTest("org.jboss.as.test.integration.domain.suspendresume.DomainSuspendResumeTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-530 - \"XNIO001001: No XNIO provider found\" by some tests running with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/WFLY-4936 - JGroups: failed setting ip_ttl on windows with IPv6
       new FilterItem().addUrl(".*integ.*").addTest("org.jboss.as.test.clustering.messaging.ClusteredMessagingTestCase.*")
           .setErrorText("Could not start container in DR6 (-Dnode), " +
               "but ignored due to https://issues.jboss.org/browse/WFLY-4936").setColour(Colour.PURPLE)
           .setCategory(AsTsCategory.CLUSTER),

       // https://issues.jboss.org/browse/JBEAP-322 - Clustering integration tests fails once -Dnode0 and -Dnode1 are used
       new FilterItem().addUrl(".*misc-src.*")
           .addTest("org.jboss.as.test.clustering.cluster.web.CoarseWebFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.GranularWebFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.NonDistributableTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.ReplicationForNegotiationAuthenticatorTestCase\\(SYNC-tcp\\).*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-322 - Clustering integration tests fails once -Dnode0 and -Dnode1 are used").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.CLUSTER),

       // Connection timeout
       new FilterItem().addTest("org.jboss.as.test.integration.domain.ServerManagementTestCase.*")
           .setErrorText("see connection timeout in logs").setColour(Colour.PURPLE)
           .setCategory(AsTsCategory.HARDWARE),

       // https://issues.jboss.org/browse/JBEAP-790 -
       //DenyModulePermissionsTestCase, GrantModulePermissionsTestCase, LimitedModulePermissionsTestCase cannot deploy jar
       new FilterItem().addTest("org.jboss.as.testsuite.integration.secman.module.DenyModulePermissionsTestCase.*")
           .addTest("org.jboss.as.testsuite.integration.secman.module.GrantModulePermissionsTestCase.*")
           .addTest("org.jboss.as.testsuite.integration.secman.module.LimitedModulePermissionsTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-790 - cannot deploy jar").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // IP multicast network discovery issue on Jenkins slaves from beaker
       // jca tests doesn"t use $MYTEST_IPs
       new FilterItem().addUrl(".*(integ|misc-src).*")
           .addTest(".*SingletonDeploymentJBossAllTestCase.*")
           .addTest(".*SingletonDeploymentDescriptorTestCase.*")
           .addTest("org.jboss.as.test.clustering.cluster.cdi.CdiFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.dispatcher.CommandDispatcherTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.ejb.remote.RemoteFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.ejb.stateful.StatefulFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.ejb.xpc.StatefulWithXPCFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.ejb.stateful.StatefulTimeoutTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.jsf.JSFFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.provider.ServiceProviderRegistrationTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.registry.RegistryTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.singleton.SingletonServiceTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.sso.ClusteredSingleSignOnTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.ClusteredWebSimpleTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.async.AsyncServletTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.authentication.FormAuthenticationWebFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.context.InvalidateConversationTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.expiration.CoarseSessionExpirationTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.expiration.FineSessionExpirationTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.cluster.web.shared.SharedSessionFailoverTestCase\\(SYNC-tcp\\).*")
           .addTest("org.jboss.as.test.clustering.xsite.XSiteSimpleTestCase.*")
           .addTest("org.jboss.as.test.clustering.cluster.web.ExternalizerTestCase.*")
           .addTest("org.jboss.as.test.clustering.cluster.singleton.SingletonPolicyServiceTestCase\\(SYNC-tcp\\).testSingletonService")
           .addTest("org.jboss.as.test.clustering.cluster.web.GranularWebFailoverTestCase\\(SYNC-tcp\\)#testGraceful(Undeploy|Simple)Failover")
           .addTest("org.jboss.as.test.clustering.cluster.web.CoarseWebFailoverTestCase\\(SYNC-tcp\\)#testGraceful(Undeploy|Simple)Failover")
           .addTest("org.jboss.as.test.clustering.cluster.web.ReplicationForNegotiationAuthenticatorTestCase\\(SYNC-tcp\\)#test(OneRequestSimple|GracefulUndeploy|GracefulSimple)Failover")
           .setErrorText("JBEAP-798, RT-374128, RT-388028 - IP multicast network discovery issue on Jenkins slaves from beaker").setColour(Colour.PURPLE)
           .setCategory(AsTsCategory.OTHER),

       // https://issues.jboss.org/browse/JBEAP-819 - IIOPNamingInContainer*TestCase can not connect to ORB with security manager
       new FilterItem().addUrl(".*secman.*")
           .addTest("org.jboss.as.test.integration.ejb.iiop.naming.IIOPNamingInContainerDDNameTestCase.*")
           .addTest("org.jboss.as.test.integration.ejb.iiop.naming.IIOPNamingInContainerTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-819 - IIOPNamingInContainer*TestCase can not connect to ORB with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/JBEAP-821 - "XNIO001001: No XNIO provider found" by some tests in main TS with security manager
       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.ejb.mdb.MDBTestCase#testSuspendResumeWithMDB")
           .addTest("org.jboss.as.test.integration.ejb.mdb.deliveryactive.MDBTestCase.*")
           .addTest("org.jboss.as.test.integration.batch.chunk.ChunkPartitionTestCase#testSuspend")
           .addTest("org.jboss.as.test.integration.batch.deployment.JobControlTestCase.*")
           .addTest("org.jboss.as.test.integration.ee.concurrent.EEConcurrentManagementTestCase.*")
           .addTest("org.jboss.as.test.integration.naming.remote.multiple.MultipleClientRemoteJndiTestCase.*")
           .addTest("org.jboss.as.test.integration.naming.remote.multiple.NestedRemoteContextTestCase.*")
           .addTest("org.jboss.as.test.integration.ejb.timerservice.suspend.TimerServiceSuspendTestCase#testIntervalTimersDoNotBackUp")
           .addTest("org.jboss.as.test.integration.ejb.timerservice.suspend.TimerServiceSuspendTestCase#testSingleActionTimerWhenSuspended")
           .addTest("org.jboss.as.test.integration.ejb.timerservice.suspend.TimerServiceSuspendTestCase#testSuspendWithIntervalTimer")
           .addTest("org.jboss.as.test.integration.jca.capacitypolicies.DatasourceCapacityPoliciesTestCase#testNonDefaultDecrementerAndIncrementer")
           .addTest("org.jboss.as.test.integration.jca.capacitypolicies.ResourceAdapterCapacityPoliciesTestCase#testNonDefaultDecrementerAndIncrementer")
           .addTest("org.jboss.as.test.integration.jca.capacitypolicies.XADatasourceCapacityPoliciesTestCase#testNonDefaultDecrementerAndIncrementer")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-821 - \"XNIO001001: No XNIO provider found\" by some tests running with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/JBEAP-823 - WildFlySecurityManager throws exception by some tests with security manager
       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.remote.ejb.RemoteNamingEjbTestCase#testDeploymentBinding")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-823 - WildFlySecurityManager throws exception by some tests with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.mgmt.access.AccessConstraintUtilizationTestCase.*")
           .setErrorText("No fail, if only RBAC module is executed")
           .setColour(Colour.PURPLE).setCategory(AsTsCategory.TEST_COLLISION),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.manualmode.web.ssl.CertificateRolesLoginModuleTestCase.*")
           .addTest("org.jboss.as.test.manualmode.web.ssl.DatabaseCertLoginModuleTestCase.*")
           .addTest("org.jboss.as.test.manualmode.web.ssl.HTTPSWebConnectorTestCase.*")
           .setErrorText("test pass with -Dtest=${TEST_NAME} option")
           .setColour(Colour.PURPLE).setCategory(AsTsCategory.TEST_COLLISION),

       // JBEAP-397 - IPv6ScopeIdMatchUnitTestCase fails if some IPv6 subinterface exists
       new FilterItem().addUrl(".*solaris11.*").addTest("org.jboss.as.controller.interfaces.IPv6ScopeIdMatchUnitTestCase#testNonLoopback")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-397").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.IPV6),

       new FilterItem().addUrl(".*-ipv6.*")
           .addTest("org.jboss.as.test.integration.ws.wsse.trust.WSTrustTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1386 - WSTrustTestCase fails on pure IPv6 machine")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.IPV6),

       new FilterItem().addUrl(".*-ipv6.*")
           .addTest(".*DomainDeploymentOverlayTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1399 - DomainDeploymentOverlayTestCase fails on pure IPv6")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.IPV6),

       new FilterItem().addUrl(".*secman.*")
           .addTest(".*\\.clustering\\..*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1430 - Some clustering tests fails with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/JBEAP-1645 - DefaultContextServiceServletTestCase fails on IBM jdk
       new FilterItem().addUrl(".*integ.*").addTest("org.jboss.as.test.integration.ee.concurrent.DefaultContextServiceServletTestCase#testServlet")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-1645 - DefaultContextServiceServletTestCase fails on IBM jdk").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.OTHER),

       // https://issues.jboss.org/browse/JBEAP-2335 - PersistanceResourceTestCase fails on some windows machines
       new FilterItem().addUrl(".*core.*").addUrl(".*windows.*").addTest(".*PersistanceResourceTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2335 - PersistanceResourceTestCase fails on some windows machines")
           .setColour(Colour.YELLOW).setCategory(AsTsCategory.OTHER),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.MixedDomainDeployment640TestCase#testJsfWorks")
           .addTest("org.jboss.as.test.integration.domain.suites.ReadEnvironmentVariablesTestCase#testReadEnvironmentVariablesForServers")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2470 - Some domain tests fail with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.ws.basic.EJBEndpointTestCase#testHelloError")
           .addTest("org.jboss.as.test.integration.ws.basic.PojoEndpointTestCase#testHelloError")
           .addTest("org.jboss.as.test.integration.ws.serviceref.ServiceRefEarTestCase.*")
           .addTest("org.jboss.as.test.integration.ws.serviceref.ServiceRefSevletTestCase.*")
           .addTest("org.jboss.as.test.integration.ws.serviceref.ServiceRefTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2472 - Some ws tests fail with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.multinode.remotecall.scoped.context.DynamicJNDIContextEJBInvocationTestCase.*")
           .addTest("org.jboss.as.test.multinode.transaction.TransactionInvocationTestCase.*")
           .addTest("org.jboss.as.test.multinode.remotecall.RemoteLocalCallProfileTestCase#testStatelessRemoteFromRemote")
           .addTest("org.jboss.as.test.multinode.remotecall.RemoteLocalCallTestCase#testStatelessRemoteFromRemote")
           .addTest("org.jboss.as.test.multinode.remotecall.RemoteLocalCallProfileTestCase#testStatelessRemoteFromRemote")
           .addTest("org.jboss.as.test.multinode.remotecall.RemoteLocalCallTestCase#testStatelessRemoteFromRemote")
           .addTest("org.jboss.as.test.multinode.ejb.timer.database.DatabaseTimerServiceMultiNodeTestCase#testEjbTimeoutOnOtherNode")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2478 - Some multinode tests fail with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*ibm.*").addUrl(".*core.*")
           .addTest(".*LoggingSubsystemRollbackTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2834 - LoggingSubsystemRollbackTestCase fails intermittently")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.IBM),

       // https://issues.jboss.org/browse/JBEAP-2841 - SlaveReconnectTestCase fails intermittently on slow systems
       new FilterItem().addUrl(".*core.*")
           .addTest(".*SlaveReconnectTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2841 - SlaveReconnectTestCase fails intermittently with IBM JDK")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.IBM),

       new FilterItem().addUrl(".*ibm.*").addUrl(".*integ.*")
           .addTest(".*InvocationContextTestCase.*")
           .addTest(".*TimerServiceInterceptorOrderTestCase.*")
           .addTest(".*TimerServiceCancellationTestCase.*")
           .addTest(".*CDIRequestScopeTimerServiceTestCase.*")
           .addTest(".*BMPEntityBeanTimerTestCase.*")
           .addTest(".*PersistentIntervalTimerManagementTestCase.*")
           .addTest(".*TimerManagementTestCase.*")
           .addTest(".*TimerServicePersistenceFirstTestCase.*")
           .addTest(".*TimerServicePersistenceSecondTestCase.*")
           .addTest(".*LegacyTimerFormatTestCase.*")
           .addTest(".*SimpleScheduleFirstTestCase.*")
           .addTest(".*TimerServiceCallerPrincipleTestCase.*")
           .addTest(".*TimeoutMethodWithRunAsAnnotationTestCase.*")
           .addTest(".*TimerServiceSerializationFirstTestCase.*")
           .addTest(".*TimerServiceSerializationSecondTestCase.*")
           .addTest(".*SimpleTimerServiceTestCase.*")
           .addTest(".*TimerRetryTestCase.*")
           .addTest(".*TxTimeoutTimerServiceTestCase.*")
           .addTest(".*ViewTimerServiceTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2902 - Ejb tests fails with IBM JDK")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.IBM),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.hibernate.Hibernate2LCacheStatsTestCase#testHibernateStatistics")
           .addTest("org.jboss.as.test.integration.hibernate.Hibernate4NativeAPIProviderTestCase#testSimpleOperation")
           .addTest("org.jboss.as.test.integration.hibernate.HibernateNativeAPITransactionTestCase#testRollBackOperation")
           .addTest("org.jboss.as.test.integration.hibernate.HibernateNativeAPITransactionTestCase#testSimpleOperation")
           .addTest("org.jboss.as.test.integration.hibernate.envers.Hibernate4NativeAPIEnversTestCase#testEnversonHibernateNativeAPI")
           .addTest("org.jboss.as.test.integration.hibernate.search.HibernateSearchJPATestCase#testFullTextQuery")
           .addTest("org.jboss.as.test.integration.hibernate.secondlevelcache.HibernateSecondLevelCacheTestCase#testSecondLevelCache")
           .addTest("org.jboss.as.test.integration.jpa.dsrestart.JpaDsRestartTestCase#testRestartDataSource")
           .addTest("org.jboss.as.test.integration.jpa.hibernate.envers.AuditJoinTableoverBidirectionalTest#testRevisionsforValidityStrategyoverManytoOne")
           .addTest("org.jboss.as.test.integration.jpa.hibernate.envers.AuditJoinTableoverOnetoManyJoinColumnTest#testRevisionsfromAuditJoinTable")
           .addTest("org.jboss.as.test.integration.jpa.hibernate.envers.basicenverstest.BasicEnversTestCase#testSimpleEnversOperation")
           .addTest("org.jboss.as.test.integration.jpa.hibernate.envers.implementvalidityauditstrategytest.ImplementValidityAuditStrategyTestCase#testEnversforValidityStrategy")
           .addTest("org.jboss.as.test.integration.jpa.hibernate.envers.validityauditstrategyoninheritancetest.ValidityAuditStrategyonInheritanceTestCase#testValidityStrategyonInheritance")
           .addTest("org.jboss.as.test.integration.jpa.mockprovider.classtransformer.ClassFileTransformerTestCase#test_persistenceUnitInfoURLS")
           .addTest("org.jboss.as.test.integration.jpa.hibernate.envers.basicselectiveenverstest.BasicSelectiveEnversTestCase#testSelectiveEnversOperations")
           .addTest("org.jboss.as.test.compat.jpa.hibernate.HibernateJarsInDeploymentTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3367 - \"java.lang.RuntimePermission\" \"createClassLoader\" by some tests running with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.management.cli.DeploymentOverlayCLITestCase#testSimpleOverrideInEarAtEarLevel")
           .addTest("org.jboss.as.test.integration.management.cli.DeploymentOverlayCLITestCase#testSimpleOverrideInEarAtEarLevelExploded")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3369 - \"org.jboss.vfs.VirtualFilePermission\" by some tests in TS with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.ejb.remote.async.RemoteAsyncInvocationTestCase#testRemoteAsyncInvocationByValue")
           .addTest("org.jboss.as.test.integration.ejb.remote.async.RemoteAsyncInvocationTestCase#testRemoteAsyncInvocationByValueFromEjbInjcation")
           .addTest("org.jboss.as.test.integration.ejb.remote.async.RemoteAsyncInvocationTestCase#testReturnAsyncInvocationReturningValue")
           .addTest("org.jboss.as.test.integration.ejb.remote.async.classloading.AsyncFutureTestCase#testAsyncNullResultInServlet")
           .addTest("org.jboss.as.test.integration.ejb.remote.async.classloading.AsyncFutureTestCase#testAsyncResultInServlet")
           .addTest("org.jboss.as.test.integration.ejb.remote.client.api.interceptor.EJBClientInterceptorTestCase#testEJBClientInterceptionFromInVMClient")
           .addTest("org.jboss.as.test.integration.ejb.remote.client.selector.EJBClientContextLockedSelectorTestCase#testChangingLockedEJBClientContextSelector")
           .addTest("org.jboss.as.test.integration.ejb.remote.security.RemoteIdentityTestCase#testSwitched")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3371 - Some tests from \"org.jboss.as.test.integration.ejb.remote.*\" fail with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.wildfly.test.integration.security.picketlink.core.PartitionManagerProducerTestCase#testAuthentication")
           .addTest("org.wildfly.test.integration.security.picketlink.core.PartitionManagerProducerTestCase#testProducedPartitionManagerInstance")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3373 - org.wildfly.test.integration.security.picketlink.core.PartitionManagerProducerTestCase fails with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.ee.lifecycle.servlet.FilterLifecycleCallbackInterceptionTestCase#testFilterPreDestroyInterception")
           .addTest("org.jboss.as.test.integration.ee.lifecycle.servlet.ListenerLifecycleCallbackInterceptionTestCase#testListenerPreDestroyInterception")
           .addTest("org.jboss.as.test.integration.ee.lifecycle.servlet.ServletLifecycleCallbackInterceptionTestCase#testServletPreDestroyInterception")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3375 - Some tests fail with \"java.net.SocketPermission\" when is security manager enabled")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.ejb.singleton.reentrant.SingletonReentrantTestCase#testReadCall")
           .addTest("org.jboss.as.test.integration.ejb.singleton.reentrant.SingletonReentrantTestCase#testWriteCall")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3376 - SingletonReentrantTestCase fails with security manager enabled")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.management.api.expression.ExpressionSubstitutionInContainerTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3377 - ExpressionSubstitutionInContainerTestCase fails due to AccessControlException")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.jaxb.unit.JAXBUsageTestCase#testJAXBServlet")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3378 - JAXBUsageTestCase fails with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.jaxrs.provider.preference.CustomProviderPreferenceTest#testCustomMessageBodyWriterIsUsed")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3380 - Test fails due to ReflectPermission")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.ee.injection.support.websocket.WebSocketInjectionSupportTestCase#testWebSocketInjectionAndInterception")
           .addTest("org.jboss.as.test.integration.ee.suspend.EEConcurrencySuspendTestCase#testRequestInShutdown")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3381 - Tests fail with \"java.util.PropertyPermission\"")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.batch.chunk.ChunkPartitionTestCase#chunks")
           .addTest("org.jboss.as.test.integration.batch.deployment.DeploymentDescriptorTestCase#definedInMemoryTest")
           .addTest("org.jboss.as.test.integration.batch.deployment.DeploymentDescriptorTestCase#namedInMemoryTest")
           .addTest("org.jboss.as.test.integration.batch.deployment.DeploymentDescriptorTestCase#namedJdbcTest")
           .addTest("org.jboss.as.test.integration.ejb.container.interceptor.security.SwitchIdentityTestCase#testClientLoginModule")
           .addTest("org.jboss.as.test.integration.ejb.container.interceptor.security.SwitchIdentityTestCase#testSecurityContextAssociation")
           .addTest("org.jboss.as.test.integration.ejb.container.interceptor.security.api.SwitchIdentityTestCase#testClientLoginModule")
           .addTest("org.jboss.as.test.integration.ejb.container.interceptor.security.api.SwitchIdentityTestCase#testSecurityContextAssociation")
           .addTest("org.jboss.as.test.integration.batch.flow.FlowTestCase#flow")
           .addTest("org.jboss.as.test.integration.web.websocket.WebSocketTestCase#testWebSocket")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3382 - Tests fail on IBM JDK with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.manualmode.weld.extension.BeforeShutdownJNDILookupTestCase#org.jboss.as.test.manualmode.weld.extension.BeforeShutdownJNDILookupTestCase")
           .addTest("org.jboss.as.test.manualmode.weld.extension.BeforeShutdownJNDILookupTestCase#testTransactionJNDILookupDuringShutdownEvent")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3390 - Tests fails in manualmode with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),


       new FilterItem().addUrl(".*ibm.*").addUrl(".*ipv6.*").addUrl(".*integ.*")
           .addTest(".*org.jboss.as.test.clustering.single.web.SimpleWebTestCase.*")
           .addTest(".*org.jboss.as.test.clustering.single.provider.ServiceProviderRegistrationTestCase.*")
           .addTest(".*org.jboss.as.test.clustering.single.registry.RegistryTestCase.*")
           .addTest(".*org.jboss.as.test.clustering.single.dispatcher.CommandDispatcherTestCase.*")
           .addTest(".*org.jboss.as.test.clustering.single.singleton.SingletonServiceTestCase.*")
           .addTest(".*org.jboss.as.test.clustering.xsite.XSiteSimpleTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-2904 - Some clustering tests fails on pure-IPv6 machines with IBM JDK")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.CLUSTER),

       // https://issues.jboss.org/browse/JBEAP-3687 - JCETestCase fails with security manager enabled on OracleJDK
       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.deployment.jcedeployment.JCETestCase#testJCE")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-3687 - JCETestCase fails with security manager enabled on OracleJDK").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/JBEAP-4013 - Cli output of HTTPSConnectionWithCLITestCase contains only "-2147483648: " (instead of correct result)
       new FilterItem().addUrl(".*integ.*")
           .addTest("org.wildfly.core.test.standalone.mgmt.HTTPSConnectionWithCLITestCase#testTrustedCLICertificate")
           .addTest("org.wildfly.core.test.standalone.mgmt.HTTPSConnectionWithCLITestCase#org.wildfly.core.test.standalone.mgmt.HTTPSConnectionWithCLITestCase")
           .addTest("org.wildfly.core.test.standalone.mgmt.HTTPSManagementInterfaceTestCase#org.wildfly.core.test.standalone.mgmt.HTTPSManagementInterfaceTestCase")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4013 - Cli output of HTTPSConnectionWithCLITestCase contains only \"-2147483648: \" (instead of correct result)").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.HARDWARE),

       // https://issues.jboss.org/browse/JBEAP-4021 - CoarseSessionPassivationTestCase fails intermittently
       new FilterItem().addUrl(".*integ.*").addUrl(".*ipv6.*").addUrl(".*windows.*")
           .addTest("org.jboss.as.test.clustering.cluster.web.passivation.CoarseSessionPassivationTestCase(SYNC-tcp)#test")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4021 - CoarseSessionPassivationTestCase fails intermittently").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.CLUSTER),

       // https://issues.jboss.org/browse/JBEAP-4186 - ReloadWSDLPublisherTestCase.testHelloStringAfterReload fails intermittently on slower machines
       new FilterItem().addUrl(".*integ.*")
           .addTest(".*WSAttributesChangesTestCase.*")
           .addTest(".*ReloadWSDLPublisherTestCase#testHelloStringAfterReload")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4186 - ReloadWSDLPublisherTestCase.testHelloStringAfterReload fails intermittently on slower machines").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.CLUSTER),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.ExternalContextBindingTestCase#testBasicWithCache")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4076 - ExternalContextBindingTestCase misses JndiPermission and fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.URLBindingTestCase#testURLBinding")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4075 - URLBindingTestCase misses RemotingPermission and fails when run with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.connector.JMXConnectorTestCase#testMBeanCount")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4057 - JMXConnectorTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.injection.LinkRefResourceInjectionTestCase#test")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4074 - LinkRefResourceInjectionTestCase misses JndiPermission and fails when run with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.ldap.LdapUrlInSearchBaseTestCase#testDir")
           .addTest("org.jboss.as.test.integration.naming.ldap.LdapUrlInSearchBaseTestCase#testLdap")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4055 - LdapUrlInSearchBaseTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.local.simple.DeploymentWithBindTestCase#testBasicsNamespaces")
           .addTest("org.jboss.as.test.integration.naming.local.simple.DeploymentWithBindTestCase#testEjb")
           .addTest("org.jboss.as.test.integration.naming.local.simple.DeploymentWithBindTestCase#testServlet")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4062 - DeploymentWithBindTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.naming.shared.SharedBindingTestCase#test")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4073 - SharedBindingTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.pojo.test.BeanFactoryTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4061 - BeanFactoryTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.sar.JNDIBindingMBeanTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4071 - JNDIBindingMBeanTestCase misses JndiPermission when run with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.sar.context.classloader.MBeanTCCLTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4069 - MBeanTCCLTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.sar.servicembean.ServiceMBeanSupportTestCase#testSarWithServiceMBeanSupport")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4068 - ServiceMBeanSupportTestCase misses MBeanPermission when run with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.security.jacc.context.PolicyContextTestCase#testHttpServletRequestFromPolicyContext")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4067 - PolicyContextTestCase misses SecurityPermission when run with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.security.loginmodules.CustomLoginModuleTestCase#testSuccessfulAuth")
           .addTest("org.jboss.as.test.integration.security.loginmodules.CustomLoginModuleTestCase#testUnsuccessfulAuth")
           .addTest("org.jboss.as.test.integration.security.loginmodules.RunAsLoginModuleTestCase#testCleartextPassword1")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4046 - Some tests from \"org.jboss.as.test.integration.security.loginmodules.*\" fail with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.security.passwordmasking.PasswordMaskingInContainerTestCase#datasourceOperationsTest")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4065 - PasswordMaskingInContainerTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.security.vault.ExternalPasswordCommandsTestCase#testCmdPassword")
           .addTest("org.jboss.as.test.integration.security.vault.ExternalPasswordCommandsTestCase#testExtPassword")
           .addTest("org.jboss.as.test.integration.security.vault.VaultDatasourceTestCase#testAccessThroughVaultDatasource")
           .addTest("org.jboss.as.test.integration.security.vault.VaultDatasourceTestCase#testRejectWrongPasswordThroughVaultDatasource")
           .addTest("org.jboss.as.test.integration.security.vault.VaultSystemPropertiesTestCase#testVaultedSystemProperty")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4050 - Some tests from \"org.jboss.as.test.integration.security.vault.*\" fail with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.security.xacml.EjbXACMLAuthorizationModuleTestCase#testAuthenticationCache")
           .addTest("org.jboss.as.test.integration.security.xacml.EjbXACMLAuthorizationModuleTestCase#testAuthz")
           .addTest("org.jboss.as.test.integration.security.xacml.EjbXACMLAuthorizationModuleTestCase#testNotAuthn")
           .addTest("org.jboss.as.test.integration.security.xacml.EjbXACMLAuthorizationModuleTestCase#testNotAuthz")
           .addTest("org.jboss.as.test.integration.security.xacml.JBossPDPInteroperabilityTestCase#testInteropTestWithObjects")
           .addTest("org.jboss.as.test.integration.security.xacml.JBossPDPInteroperabilityTestCase#testInteropTestWithXMLRequests")
           .addTest("org.jboss.as.test.integration.security.xacml.JBossPDPInteroperabilityTestCase#testPoliciesLoadedFromDir")
           .addTest("org.jboss.as.test.integration.security.xacml.JBossPDPServletInitializationTestCase#testPdpServlet")
           .addTest("org.jboss.as.test.integration.security.xacml.WebXACMLAuthorizationModuleTestCase#testWebUsingCustomXACMLAuthz")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4052 - Some tests from \"org.jboss.as.test.integration.security.xacml.*\" fail with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.web.suspend.WebSuspendTestCase#testRequestInShutdown")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4064 - WebSuspendTestCase fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*core.*")
           .addTest("org.jboss.as.test.manualmode.logging.LoggingPreferencesTestCase.*")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4077 - LoggingPreferencesTestCase from core test suite fails with security manager").setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/JBEAP-4041 - ReadFullModelTestCase (from jboss-eap) fails intermittently on IBM JDK
       new FilterItem(Colour.YELLOW).addUrl(".*ibm.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.manualmode.model.ReadFullModelTestCase#test")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4041 - ReadFullModelTestCase (from jboss-eap) fails intermittently on IBM JDK"),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.domain.management.cli.RolloutPlanTestCase#testMaxFailServersPercentageRolloutPlan")
           .addTest("org.jboss.as.test.integration.domain.management.cli.RolloutPlanTestCase#testMaxFailServersRolloutPlan")
           .addTest("org.jboss.as.test.integration.domain.management.cli.RolloutPlanTestCase#testRollbackAcrossGroupsRolloutPlan")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4208 - RolloutPlanTestCase fails with security manager")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       new FilterItem().addUrl(".*secman.*").addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.DeploymentOverlay640TestCase#testDeploymentOverlayInDomainMode")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.ReadEnvironmentVariables640TestCase#testReadEnvironmentVariablesForServers")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.DomainDeploymentOverlay640TestCase#testMultipleLinks")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.DomainDeploymentOverlay640TestCase#testRedeployAffected")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.DomainDeploymentOverlay640TestCase#testSimpleOverride")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.DomainDeploymentOverlay640TestCase#testSimpleOverrideWithRedeployAffected")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.DomainDeploymentOverlay640TestCase#testWildcardOverride")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.DomainDeploymentOverlay640TestCase#testWildcardOverrideWithRedeployAffected")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.RolloutPlan640TestCase#testInSeriesRolloutPlan")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.RolloutPlan640TestCase#testMaxFailServersPercentageRolloutPlan")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.RolloutPlan640TestCase#testMaxFailServersRolloutPlan")
           .addTest("org.jboss.as.test.integration.domain.mixed.eap640.cli.RolloutPlan640TestCase#testRollbackAcrossGroupsRolloutPlan")
           .setErrorText("https://issues.jboss.org/browse/JBQA-12786 - Fix test issues with security manager in QA fork")
           .setColour(Colour.YELLOW)
           .setCategory(AsTsCategory.SECURITY),

       // https://issues.jboss.org/browse/JBEAP-4058 - PassivationTestCase fails intermittently
       new FilterItem(Colour.YELLOW).addUrl(".*integ.*")
           .addTest("org.jboss.as.test.integration.ejb.stateful.passivation.PassivationTestCase#testPassivationMaxSize")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4058 - PassivationTestCase fails intermittently"),

       // https://issues.jboss.org/browse/JBEAP-4282 - Intermittent failures of SingletonDeploymentJBossAllTestCase
       new FilterItem(Colour.YELLOW).addUrl(".*integ.*")
           .addTest("org.jboss.as.test.clustering.cluster.singleton.SingletonDeploymentJBossAllTestCase\\(SYNC-tcp\\)#test")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4282 - Intermittent failures of SingletonDeploymentJBossAllTestCase"),

       // https://issues.jboss.org/browse/JBEAP-4494 - ClusteredWebSimpleTestCase fails if tests run in certain order
       new FilterItem(Colour.YELLOW).addUrl(".*integ.*")
           .addTest("org.jboss.as.test.clustering.cluster.web.ClusteredWebSimpleTestCase#testGracefulServeOn(Undeploy|Shutdown)")
           .setErrorText("https://issues.jboss.org/browse/JBEAP-4494 - ClusteredWebSimpleTestCase fails if tests run in certain order")
   };
}
