package com.orasi.bluesource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.selenium.Constants;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestNgTestClassMethods;
import com.orasi.utils.TestReporter;
import com.orasi.utils.Screenshot;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.bluesource.ListingTitlesPage;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.NewTitlePage;
import com.orasi.bluesource.TopNavigationBar;

@SuppressWarnings("unused")
public class TestAddNewTitle extends com.selenium.testClassTemplates.TestClassTemplate{
    private String application = "Bluesource";
	
	/*
	 * Define a collection of webdrivers and test names inside a Map.
	 * This allows for more than one driver to be used within a test class.
	 * This also allows for a particular driver to be tied to a specific test 
	 * based on test name.
	 */
	private Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	
	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		return new ExcelDataProvider(Constants.BLUESOURCE_DATAPROVIDER_PATH
				+ "TestAddNewTitle.xlsx", "TestAddNewTitle").getTestData();
	}

	// *********************
	// Before-Test Behavior
	// *********************
	@BeforeTest(groups = { "regression" })
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion",
			"operatingSystem", "environment" })
	public void setupClass(String runLocation, String browserUnderTest,
			String browserVersion, String operatingSystem, String environment) {
		this.te = new TestEnvironment(application, browserUnderTest, browserVersion, operatingSystem,
				runLocation, environment);
		this.test = new TestNgTestClassMethods(application, this.te);
	}

	/**
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 * @Summary: Adds and deletes a title to the Orasi Blue Source website
	 * @Precondition:NA
	 * @Author: Jessica Marshall
	 * @Version: 10/6/2014
	 * @Return: N/A
	 */
	@Test(dataProvider = "dataScenario", groups = { "regression" })
	public void testCreateNewTitle(String testScenario, String role,
			String newTitle) throws InterruptedException, IOException {
	
    	this.testName = new Object(){}.getClass().getEnclosingMethod().getName() 
				+ "_" + this.te.getOperatingSystem()
				+ "_" + this.te.getBrowserUnderTest()
				+ "_" + this.te.getBrowserVersion();

		this.te.setDriver(this.test.testStart(this.testName, this.te));

		// Login
		LoginPage loginPage = new LoginPage(this.te);
		TestReporter.assertTrue(this.te.pageLoaded(), "Verify login page is displayed");
		loginPage.login(role);

		// Verify user is logged in
		TopNavigationBar topNavigationBar = new TopNavigationBar(this.te);
		TestReporter.assertTrue(topNavigationBar.isLoggedIn(), "Validate the user logged in successfully");

		// Navigate to the title page
		topNavigationBar.clickAdminLink();
		topNavigationBar.clickTitlesLink();

		// Verify navigated to the title page
		ListingTitlesPage listingTitlesPage = new ListingTitlesPage(this.te);
		TestReporter.assertTrue(this.te.pageLoaded(), "Verify listing titles page is displayed");

		// Click new title
		listingTitlesPage.clickNewTitle();

		// Instantiate the New titles page and create a new title
		NewTitlePage newTitlePage = new NewTitlePage(this.te);
		TestReporter.assertTrue(this.te.pageLoaded(), "Verify create new title page is displayed");
		newTitlePage.createNewTitle(newTitle);

		// Verify the title was created
		TestReporter.assertTrue(listingTitlesPage.isSuccessMsgDisplayed(),
				"Validate success message appears");
		TestReporter.log("New Title was created: " + newTitle);

		// Verify the title is displayed on the title results table
		TestReporter.assertTrue(listingTitlesPage.searchTableByTitle(newTitle),
				"Validate new title appears in table");

		// Delete the new title
		listingTitlesPage.deleteTitle(newTitle);

		// Verify the title is deleted
		ListingTitlesPage refreshedPage = new ListingTitlesPage(this.te);
		TestReporter.assertTrue(refreshedPage.isSuccessMsgDisplayed(),
				"Validate success message appears");

		// logout
		topNavigationBar.logout();
	}
}