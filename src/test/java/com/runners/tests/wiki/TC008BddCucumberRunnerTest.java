package com.runners.tests.wiki;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(glue = { "com.apps.wiki.fe.defs"}, features="src/test/resources/features/search_by_keyword.feature")
public class TC008BddCucumberRunnerTest {

}
