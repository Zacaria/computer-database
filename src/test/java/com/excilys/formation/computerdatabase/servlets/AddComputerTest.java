package com.excilys.formation.computerdatabase.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AddComputerTest {
	private WebDriver driver;
	private JavascriptExecutor js;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		if (driver instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) driver;
		}
	}

	@Ignore
	@Test
	public void testAddComputer() throws Exception {
		driver.get(baseUrl + "/computer-database/dashboard?p=1&r=10");
		driver.findElement(By.id("addComputer")).click();

		assertEquals(baseUrl + "/computer-database/addComputer", driver.getCurrentUrl());

		driver.findElement(By.id("computerName")).clear();
		driver.findElement(By.id("computerName")).sendKeys("new Computer");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.linkText("OQO")).click();
		new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("OQO");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		driver.findElement(By.id("computerName")).clear();
		driver.findElement(By.id("computerName")).sendKeys("toto");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		driver.findElement(By.linkText("Lincoln Laboratory")).click();
		new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Lincoln Laboratory");
		driver.findElement(By.id("computerName")).clear();
		driver.findElement(By.id("computerName")).sendKeys("");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		driver.findElement(By.id("computerName")).clear();
		driver.findElement(By.id("computerName")).sendKeys("suc ces");
		setInputToVal("#introduced","1998-02-06");
		setInputToVal("#discontinued","2015-02-06");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private void setInputToVal(String selector, String value) {
		js.executeScript("document.querySelector('" + selector + "').value = \"" + value + "\"");
	}
	
	private void setSelectToVal(String selector, String value) {
		js.executeScript("$('" + selector + "').val('" + value + "')");
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