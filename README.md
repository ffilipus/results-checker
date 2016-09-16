#RESULTS-CHECKER


##How to build results-checker

> `mvn clean install`

##How to run results-checker

###Set environment variables:

> `export PACKAGE= package in jar file with custom filters`

###Run as client application:

> `java -jar target/results-checker-* <job name>`

###Run as jenkinse post build action:

> `java -jar target/results-checker-* <path to filter unit> -reports <path to junit reports>`

###Possible optional settings:

`CHECKER_ENVIRONMENT` - Possible set environment for tests ie. database, os. This is empty in default.

`SERVER_NAME` - Set server name. Default value is "jenkins.mw.lab.eng.bos.redhat.com"

`MATRIX_FULL` - Set behavior of checking matrix jobs. If variable is set to false (default), results-checker will download configurations of matrix that run in last execution of matrix.
  If variable is set to true, it will download last run of each configuration.

`CACHE_TIME_VALIDITY` - Validity of local cache in seconds. In default it is 300sec=5min

`JAR_PATH` - Path to jar file containing custom filters. In default ./result-checker-filters.jar


###Examples:
```
export CHECKER_ENVIRONMENT="db2 jdk8 solaris10"
export JAR_PATH="./filters.jar"

export PACKAGE="org.jboss.qe.collector.filter.messaging.Eap7xHA"

export CACHE_TIME_VALIDITY="0" # without cache
java -jar target/results-checker-* eap-70x-maven-repository-check-valid-POM-and-Metadata-files

java -jar target/results-checker-* eap-70x-acceptance-multinode-win

export PACKAGE=""
export CACHE_TIME_VALIDITY="600" # 600sec=10min
export SERVER_NAME="jenkinse.zloutek-soft.cz"
java -jar target/results-checker-* -eap-70x-acceptance-multinode-win
```

##How to create your own filter
* You have to make class with name of your filter. It must extend AbstractFilter.
* In filter method create FilterItem array. It will contain your conditions for filter.
* Add filter items into the array.
* Each filter item has methods:
    * `addURL(String url)` Add regular expression. It must match with url of configuration with failed test.
    * `addTest(String test)` Add regular expression. One of all added regular expression must match with failed test name.
    * `addTestMatcher(Matcher matcher)` Add lambda expression.
    * `setErrorText(String inErrorText)` Set description of error
    * `setColour(Colour colour)` Set color for printed results
    * `setCategory(String category)` Sets category of error. Optional.
* Use these methods to modify what to filter.
* At the end return core filters with arguments failedTest and your array of filter items.

###Example:
```java
package org.jboss.qe.collector.filter;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;

import java.util.regex.Pattern;

public class FilterTestGit extends AbstractFilter {
   @Override
   public FilterResult filter(FailedTest failedTest) {
      FilterItem[] items = new FilterItem[]{
          new FilterItem(Colour.YELLOW)
              .addTest("FailedTestName")
              .setErrorText("Filtered failed tests with FailedTestName"),

          new FilterItem().setColour(Colour.GRAY)
              .addTestMatcher(jsonObject-> Pattern.compile("(Regexp to match).*").matcher((CharSequence) jsonObject.get("errorDetails")).find())
              .setErrorText("Match to regular expression in errorDetails of the failed test"),

          new FilterItem(Colour.BLUE)
              .addUrl(".*URL.*")
              .setErrorText("Url contains substring URL"),
      };
      return coreFilter(failedTest, items);//return method coreFilter with arguments failedTest and your FilterItem array
   }
}
```