**RESULTS-CHECKER**


**How to build results-checker**

`mvn clean install
`
**How to run results-checker**

Run as client application:

`java -jar target/results-checker-* -j <job name> [-f <path to filter> {<path to filter>}] {-j <job name> [-f <path to filter> {<path to filter>}]}
`
Run as jenkinse post build action

`java -jar target/results-checker-* -f <path to filter> {<path to filter>} -reports <path to junit reports>
`
Possible optional settings:

`CHECKER_ENVIRONMENT
CACHE_TIME_VALIDITY`


Examples:

`export CHECKER_ENVIRONMENT="db2 jdk8 solaris10"
java -jar target/results-checker-* -f filter_Eap7xHA.xml -reports **/out/**/report/*.xml
`

`java -jar target/results-checker-* -f filter_Eap6xScriptsTestsuite.xml filter_ScriptsTestsuite.xml filter_basic.xml -reports reports/**
`

`export CHECKER_ENVIRONMENT="oracle11 jdk7"
java -jar target/results-checker-* -f my_test_filter.xml -reports **/target/surefire-reports/*.xml
`

`export CACHE_TIME_VALIDITY="0" # without cache
java -jar target/results-checker-* -j eap-70x-maven-repository-check-valid-POM-and-Metadata-files
`

`java -jar target/results-checker-* -j eap-70x-acceptance-multinode-win -f filters/basic_filter.xml filters/multinode_filters.xml filters_multinode-win.xml
`

`export CACHE_TIME_VALIDITY="600" # 600sec=10min
java -jar target/results-checker-* -j eap-70x-acceptance-multinode-win -f filters_multinode-win.xml -j eap-70x-super-job -f another_filter.xml
`