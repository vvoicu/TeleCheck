package com.apps.wiki.fe.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;

import com.apps.tools.file.ConfigUtils;
import com.apps.wiki.fe.pages.ArticlePage;
import com.apps.wiki.fe.pages.SearchPage;

import net.thucydides.core.annotations.Step;

public class WikipediaSteps {

	SearchPage searchPage;
	ArticlePage articlePage;

	@Step
	public void goToHomePage() {
		searchPage.getDriver().get(ConfigUtils.getProperty("BASE_URL_UI_WIKI"));
	}

	@Step
	public void typeSearchTerm(String keyword) {
		searchPage.searchKeyword(keyword);
	}

	@Step
	public void clickSearch() {
		searchPage.clickOnSearchButton();
	}

	@Step
	public void shouldSeeDefinitionForLanguageThatStartsWith(String definition, String language) {
		assertThat("List of definitions in article for language '" + language + "' should start with '" + definition + "'"  , 
				articlePage.getDefinitionList(language), hasItem(startsWith(definition)));
	}
	
	@Step
	public void searchFor(String term) {
		typeSearchTerm(term);
		clickSearch();
	}
}