package org.jagsmanne.test.automation.framework.keyword;

import org.jagsmanne.test.automation.framework.keyword.keywords.KeywordBase;
import org.openqa.selenium.WebDriver;

public class SuiteKeywordBase extends KeywordBase {
	
	private WebDriver driver;

	public SuiteKeywordBase(WebDriver driver){
		this.driver = driver;
	}

	public void searchForString() {
		org.testng.Assert.assertNotNull(driver);
	}

	public void navigateToUrl() {
		org.testng.Assert.assertNotNull(driver);

	}
}
