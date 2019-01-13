package org.jagsmanne.test.automation.framework.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.jagsmanne.test.automation.framework.config.DefaultConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverProvider {
	DefaultConfig config = DefaultConfig.getDefaultConfig();
	private static DriverProvider instance = null;

	private DriverProvider() {

	}

	public static DriverProvider getInstance(){
		if(instance==null){
			instance = new DriverProvider();
		}
		return instance;
	}

	public AppiumDriver<WebElement> getDriver() throws MalformedURLException {
		return getPlatformSpecificDriver();
	}

	private AppiumDriver<WebElement> getPlatformSpecificDriver() throws MalformedURLException {
		String platform = System.getProperty("platform") != null ? System.getProperty("platform") : "Android";
		AppiumDriver<WebElement> driver = null;
		switch (platform.toLowerCase()) {
			case "ios":
				driver = getIOSDriver();
				break;
			case "android":
				driver = getAndroidDriver();
				break;
			default:
				throw new DriverNotAvailableException("Driver name: " + platform +" set to the property 'platform' is not supported");
		}
		return driver;
	}

	private class DriverNotAvailableException extends RuntimeException{
		public DriverNotAvailableException(String message){
			super(message);
		}
	}

	public AppiumDriver<WebElement> getAndroidDriver() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", config.getConfigValue("platform.android.name"));
		cap.setCapability("deviceName", config.getConfigValue("platform.android.deviceName"));
		cap.setCapability("appPackage", config.getConfigValue("platform.android.appPackage"));
		cap.setCapability("appActivity", config.getConfigValue("platform.android.appActivity"));
		AppiumDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL(config.getConfigValue("appium.server.url")), cap);
		Assert.assertNotNull(driver);
		return driver;
	}

	public AppiumDriver<WebElement> getIOSDriver() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", config.getConfigValue("platform.ios.name"));
		cap.setCapability("deviceName", config.getConfigValue("platform.ios.deviceName"));
		cap.setCapability("platformVersion", config.getConfigValue("platform.ios.version"));
		cap.setCapability("bundleId", config.getConfigValue("platform.ios.bundleId"));
		AppiumDriver<WebElement> driver = new IOSDriver<WebElement>(new URL(config.getConfigValue("appium.server.url")), cap);
		Assert.assertNotNull(driver);
		return driver;
	}
}
