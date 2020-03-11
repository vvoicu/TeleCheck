package com.runners.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.runners.tests.wiki.TC001WikiSearchAppleTest;
import com.runners.tests.wiki.TC003DdtWikiSearchTest;
import com.runners.tests.wiki.TC008BddCucumberRunnerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TC001WikiSearchAppleTest.class, TC003DdtWikiSearchTest.class, TC008BddCucumberRunnerTest.class })
public class RegressionSuite {

}
