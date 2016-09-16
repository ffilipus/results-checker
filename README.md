#RESULTS-CHECKER


##How to build results-checker

> `mvn clean install`

##How to run results-checker

###Set environment variables:

> `export PACKAGE= package in jar file with custom filters`

###Run as client application:

> `java -jar target/results-checker-* <job name>

###Run as jenkinse post build action:

> `java -jar target/results-checker-* <path to filter unit> -reports <path to junit reports>`

###Possible optional settings:

`CHECKER_ENVIRONMENT` - Possible set environment for tests ie. database, os. This is empty in default.

`SERVER_NAME` - Set server name. Default value is "jenkins.mw.lab.eng.bos.redhat.com"

`MATRIX_FULL` - Set behavior of checking matrix jobs. If variable is set to false (default), results-checker will download configurations of matrix that run in last execution of matrix.
  If variable is set to true, it will download last run of each configuration.

`CACHE_TIME_VALIDITY` - Validity of local cache in seconds. In default it is 300sec=5min


###Examples:
```
export CHECKER_ENVIRONMENT="db2 jdk8 solaris10"
java -jar target/results-checker-* filter_Eap7xHA -reports **/out/**/report/*.xml

java -jar target/results-checker-* filter_Eap6xScriptsTestsuite.xml -reports reports/**

export CHECKER_ENVIRONMENT="oracle11 jdk7"
java -jar target/results-checker-* my_test_filter.xml -reports **/target/surefire-reports/*.xml

export CACHE_TIME_VALIDITY="0" # without cache
java -jar target/results-checker-* eap-70x-maven-repository-check-valid-POM-and-Metadata-files

java -jar target/results-checker-* eap-70x-acceptance-multinode-win listFilters/basic_filter

export CACHE_TIME_VALIDITY="600" # 600sec=10min
export SERVER_NAME="jenkinse.zloutek-soft.cz"
java -jar target/results-checker-* -eap-70x-acceptance-multinode-win filters_multinode-win eap-70x-super-job another_filter
```

##How to create your own filter

> TODO