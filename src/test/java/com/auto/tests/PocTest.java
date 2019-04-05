package com.auto.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class PocTest {
	
	WebDriver driver;
	WebDriverWait wait=new WebDriverWait(driver, 20);
	public static final String USERNAME = "qa_user_43";
	public static final String ACCESS_KEY = "14fef92c-6c00-4937-a4c6-8f21737b63e0";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	
	@BeforeSuite
	public void setUp() throws MalformedURLException {
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-notifications");
			caps.setCapability(ChromeOptions.CAPABILITY, option);
			caps.setCapability("platform", "Windows 10");
			caps.setCapability("version", "latest");
			caps.setCapability("name", "pocAutomation");
			caps.setCapability("screen-resolution","1280x1024");
			driver = new RemoteWebDriver(new java.net.URL(URL),caps);
			driver.get("https://www.amazon.in");
	}
	
	@Test(priority=0, enabled=true)
	public void loginTest() {
		String title = driver.getTitle();
		assertEquals(title.replaceAll("\"", ""), "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in");
		driver.findElement(By.xpath("//span[contains(text(),\"Hello, Sign in\")]")).click();
		driver.findElement(By.id("ap_email")).sendKeys("testsids21@gmail.com");
		driver.findElement(By.id("continue")).click();
		driver.findElement(By.id("ap_password")).sendKeys("testing@123");
		driver.findElement(By.id("signInSubmit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),\"Hello, test\")]")));
		assertTrue(driver.findElement(By.xpath("//span[contains(text(),\"Hello, test\")]")).isDisplayed(), "User is not logged in");
	}
	
	@Test(priority=1, enabled=true)
	public void searchProductTest() {
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Mobile Phone");
		driver.findElements(By.xpath("//input[@type=\"submit\"]")).get(0).click();
		String text = driver.findElement(By.cssSelector("div.a-text-center h1")).getText();
		assertEquals(text, "Mobiles and Accessories");
	}
	
	@Test(priority=2, enabled=true)
	public void logOut() {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//span[contains(text(),\"Hello, test\")]"))).click(driver.findElement(By.xpath("//span[contains(text(),\"Sign Out\")]"))).build().perform();
	}
	
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

}
