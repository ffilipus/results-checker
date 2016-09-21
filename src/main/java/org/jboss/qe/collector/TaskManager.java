package org.jboss.qe.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jan Dobes
 */
public class TaskManager {
   public static final Map<String, ArrayList<String>> tasks;

   static {
      tasks = new HashMap<>();

      tasks.put("run", new ArrayList<>());
      tasks.get("run").add("eap-6x-as-testsuite-6.3.3-prepare");
      tasks.get("run").add("eap-6x-as-testsuite-solaris");

      tasks.put("eap7_installer", new ArrayList<>());
      tasks.get("eap7_installer").add("eap-7x-installer-test-gui-rhel");
      tasks.get("eap7_installer").add("eap-7x-installer-test-gui-solaris");
      tasks.get("eap7_installer").add("eap-7x-installer-test-gui-windows");
      tasks.get("eap7_installer").add("eap-7x-installer-test-console-hpux");
      tasks.get("eap7_installer").add("eap-7x-installer-test-console-rhel");
      tasks.get("eap7_installer").add("eap-7x-installer-test-console-solaris");

      tasks.put("eap7_installer_sustaining", new ArrayList<>());
      tasks.get("eap7_installer_sustaining").add("eap-7x-installer-sustaining-test-gui-rhel");
      tasks.get("eap7_installer_sustaining").add("eap-7x-installer-sustaining-test-gui-windows");
      tasks.get("eap7_installer_sustaining").add("eap-7x-installer-sustaining-test-gui-solaris");
      tasks.get("eap7_installer_sustaining").add("eap-7x-installer-sustaining-test-console-hpux");
      tasks.get("eap7_installer_sustaining").add("eap-7x-installer-sustaining-test-console-rhel");
      tasks.get("eap7_installer_sustaining").add("eap-7x-installer-sustaining-test-console-solaris");

      tasks.put("installer_regression", new ArrayList<>());
      tasks.get("installer_regression").add("EAP-REGRESSION-GUI");
      tasks.get("installer_regression").add("EAP-REGRESSION-CONSOLE");
      tasks.get("installer_regression").add("EAP-REGRESSION-GUI-NONFUNCTIONAL");

      tasks.put("eap6_all_tests", new ArrayList<>());
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-smoke-rhel-6.4.x");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-IPv6-rhel");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-IPv6-rhel-secman");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-IPv6-windows");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-IPv6-windows-secman");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-hpux");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-hpux-secman");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-rhel");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-rhel-database");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-rhel-jca-pool");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-rhel-jca-pool-lifo");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-rhel-patching");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-rhel-secman");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-solaris");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-solaris-secman");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-windows");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-windows-patching");
      tasks.get("eap6_all_tests").add("eap-6x-as-testsuite-windows-secman");

      tasks.put("EAP_7x_AS_TESTSUITE_MISC", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_MISC").add("eap-7x-as-testsuite-misc-jca_pool");
      tasks.get("EAP_7x_AS_TESTSUITE_MISC").add("eap-7x-as-testsuite-misc-offline");
      tasks.get("EAP_7x_AS_TESTSUITE_MISC").add("eap-7x-as-testsuite-misc-offline-core");
      tasks.get("EAP_7x_AS_TESTSUITE_MISC").add("eap-7x-as-testsuite-misc-src");
      tasks.get("EAP_7x_AS_TESTSUITE_MISC").add("eap-7x-as-testsuite-misc-src-core");

      tasks.put("EAP_7x_AS_TESTSUITE_TEST_UNIT", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_UNIT").add("eap-7x-as-testsuite-test-unit-hpux");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_UNIT").add("eap-7x-as-testsuite-test-unit-rhel");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_UNIT").add("eap-7x-as-testsuite-test-unit-solaris");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_UNIT").add("eap-7x-as-testsuite-test-unit-windows");

      tasks.put("EAP_7x_AS_TESTSUITE_TEST_CORE", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE").add("eap-7x-as-testsuite-test-core");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE").add("eap-7x-as-testsuite-test-core-hpux");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE").add("eap-7x-as-testsuite-test-core-rhel");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE").add("eap-7x-as-testsuite-test-core-solaris");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE").add("eap-7x-as-testsuite-test-core-windows");

      tasks.put("EAP_7x_AS_TESTSUITE_TEST_CORE_SECMAN", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_SECMAN").add("eap-7x-as-testsuite-test-core-hpux-secman");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_SECMAN").add("eap-7x-as-testsuite-test-core-rhel-secman");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_SECMAN").add("eap-7x-as-testsuite-test-core-solaris-secman");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_SECMAN").add("eap-7x-as-testsuite-test-core-windows-secman");

      tasks.put("EAP_7x_AS_TESTSUITE_TEST_CORE_IPv6", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_IPv6").add("eap-7x-as-testsuite-test-core-rhel-ipv6");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_IPv6").add("eap-7x-as-testsuite-test-core-windows-ipv6");

      tasks.put("EAP_7x_AS_TESTSUITE_TEST_INTEG", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG").add("eap-7x-as-testsuite-test-integ");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG").add("eap-7x-as-testsuite-test-integ-hpux");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG").add("eap-7x-as-testsuite-test-integ-rhel");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG").add("eap-7x-as-testsuite-test-integ-solaris");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG").add("eap-7x-as-testsuite-test-integ-windows");

      tasks.put("EAP_7x_AS_TESTSUITE_TEST_INTEG_SECMAN", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_SECMAN").add("eap-7x-as-testsuite-test-integ-hpux-secman");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_SECMAN").add("eap-7x-as-testsuite-test-integ-rhel-secman");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_SECMAN").add("eap-7x-as-testsuite-test-integ-solaris-secman");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_SECMAN").add("eap-7x-as-testsuite-test-integ-windows-secman");

      tasks.put("EAP_7x_AS_TESTSUITE_TEST_INTEG_IPv6", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_IPv6").add("eap-7x-as-testsuite-test-integ-rhel-ipv6");
      tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_IPv6").add("eap-7x-as-testsuite-test-integ-windows-ipv6");

      tasks.put("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN", new ArrayList<>());
      tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN").add("eap-7x-as-testsuite-test-mixed_domain-hpux");
      tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN").add("eap-7x-as-testsuite-test-mixed_domain-rhel");
      tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN").add("eap-7x-as-testsuite-test-mixed_domain-solaris");
      tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN").add("eap-7x-as-testsuite-test-mixed_domain-windows");
      tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN").add("eap-7x-as-testsuite-test-mixed_domain-rhel-ipv6");
      tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN").add("eap-7x-as-testsuite-test-mixed_domain-windows-ipv6");

      tasks.put("eap_7x_as_testsuite_misc", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_misc").addAll(tasks.get("EAP_7x_AS_TESTSUITE_MISC"));

      tasks.put("eap_7x_as_testsuite_test_unit", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_test_unit").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_UNIT"));

      tasks.put("eap_7x_as_testsuite_test_core", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_test_core").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_UNIT"));
      tasks.get("eap_7x_as_testsuite_test_core").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_IPv6"));

      tasks.put("eap_7x_as_testsuite_test_core_secman", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_test_core_secman").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE"));
      tasks.get("eap_7x_as_testsuite_test_core_secman").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_IPv6"));
      tasks.get("eap_7x_as_testsuite_test_core_secman").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_SECMAN"));

      tasks.put("eap_7x_as_testsuite_test_integ", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_test_integ").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG"));
      tasks.get("eap_7x_as_testsuite_test_integ").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_IPv6"));

      tasks.put("eap_7x_as_testsuite_test_integ_secman", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_test_integ_secman").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG"));
      tasks.get("eap_7x_as_testsuite_test_integ_secman").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_IPv6"));
      tasks.get("eap_7x_as_testsuite_test_integ_secman").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_SECMAN"));

      tasks.put("eap_7x_as_testsuite_test_mixed_domain", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_test_mixed_domain").addAll(tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN"));

      tasks.put("eap_7x_as_testsuite_test_all", new ArrayList<>());
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_MISC"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_UNIT"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_IPv6"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_CORE_SECMAN"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_IPv6"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_TEST_INTEG_SECMAN"));
      tasks.get("eap_7x_as_testsuite_test_all").addAll(tasks.get("EAP_7x_AS_TESTSUITE_MIXED_DOMAIN"));

      tasks.put("eap7_functional_tests", new ArrayList<>());
      tasks.get("eap7_functional_tests").add("eap7-artemis-qe-internal-ts-functional-tests");
      tasks.get("eap7_functional_tests").add("eap7-artemis-qe-internal-ts-functional-tests-matrix");

      tasks.put("resteasy", new ArrayList<>());
      tasks.get("resteasy").add("eap-7.x-resteasy-ts-request-verifier");

      tasks.put("artemis", new ArrayList<>());
      tasks.get("artemis").add("artemis-project-testsuite-prepare");

      tasks.put("artemis_eus", new ArrayList<>());
      tasks.get("artemis_eus").add("artemis-project-testsuite-rhel");
      tasks.get("artemis_eus").add("artemis-project-testsuite-hpux");
      tasks.get("artemis_eus").add("artemis-project-testsuite-solaris");
      tasks.get("artemis_eus").add("artemis-project-testsuite-win");

      tasks.put("eap7_ha", new ArrayList<>());
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-bridges");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-colocated-cluster");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-colocated-cluster-replicated-journal");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-colocated-cluster-replicated-journal-win");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-colocated-cluster-win");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-colocated-replicated-journal-rhel-ipv6");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-mdb");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-mdb-replicated-journal");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-network-disconnection");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-nfs");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-power-off-nfs");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-power-off-replicated-journal-aio");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-power-off-replicated-journal-nio");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-power-off-san");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-replicated-journal");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-replicated-journal-win");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-dedicated-xa-clients");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-replicated-journal-network-failures");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-colocated-cluster-newconf");
      tasks.get("eap7_ha").add("eap7-artemis-ha-failover-jms20-clients");
   }

   public static boolean isTask(String name) {
      if (tasks.get(name) != null) {
         return true;
      }
      return false;
   }
}
