package com.excilys.formation.computerdatabase.servlets;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class DashboardTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testDashboard() throws Exception {
		String relativeUrl = baseUrl + "/computer-database/dashboard";
		driver.get(relativeUrl);
		driver.findElement(By.linkText("2")).click();
		assertEquals(relativeUrl + "?p=2&r=10", driver.getCurrentUrl());
		driver.findElement(By.xpath("//li[7]/a/span")).click();
		assertEquals(relativeUrl + "?p=7&r=10", driver.getCurrentUrl());
		driver.findElement(By.linkText("50")).click();
		assertEquals(relativeUrl + "?p=7&r=50", driver.getCurrentUrl());
		driver.findElement(By.linkText("2")).click();
		assertEquals(relativeUrl + "?p=2&r=50", driver.getCurrentUrl());
		driver.findElement(By.linkText("«")).click();
		assertEquals(relativeUrl + "?p=1&r=50", driver.getCurrentUrl());
		driver.findElement(By.linkText("100")).click();
		assertEquals(relativeUrl + "?p=1&r=100", driver.getCurrentUrl());
		driver.findElement(By.cssSelector("a.navbar-brand")).click();
		assertEquals(relativeUrl, driver.getCurrentUrl());

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
