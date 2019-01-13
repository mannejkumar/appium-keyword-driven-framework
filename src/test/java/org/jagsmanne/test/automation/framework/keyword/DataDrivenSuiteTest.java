package org.jagsmanne.test.automation.framework.keyword;

import org.jagsmanne.test.automation.framework.config.DefaultConfig;
import org.jagsmanne.test.automation.framework.driver.DriverProvider;
import org.jagsmanne.test.automation.framework.keyword.suite.ISimpleTest;
import org.jagsmanne.test.automation.framework.keyword.suite.TestSuite;
import org.jagsmanne.test.automation.framework.keyword.executor.KeywordExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class DataDrivenSuiteTest {

    WebDriver driver;

    @BeforeSuite
    public void init() throws MalformedURLException {
        DefaultConfig config = DefaultConfig.getDefaultConfig();
        driver = DriverProvider.getInstance().getDriver();
        config.setConfigValue("listeners", "org.jagsmanne.test.automation.framework.keyword.SuiteKeywordBase");
    }

    @AfterSuite
    public void cleanup() throws MalformedURLException {
        driver.quit();
    }

    @Test(dataProvider="Suite")
    public void googleSearchTestSuite(ISimpleTest simpleTest) throws MalformedURLException {
        File file = new File(simpleTest.getTestFilePath());
        KeywordExecutor keyExecutor = new KeywordExecutor(driver,file);
        keyExecutor.execute();
    }

    @DataProvider(name="Suite")
    public Object[][] getTestData(){
        File file = new File("src/test/resources/keyword/webdriverbased/GoogleTestSuite.xls");
        TestSuite suiteReader = new TestSuite(file, new ArrayList<String>());
        return suiteReader.getTobeExecutedTests();
    }
}
