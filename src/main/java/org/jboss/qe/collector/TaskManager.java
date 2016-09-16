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
   }
}
