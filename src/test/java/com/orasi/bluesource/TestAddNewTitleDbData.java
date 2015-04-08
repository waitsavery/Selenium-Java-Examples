package com.orasi.bluesource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.utils.Base64Coder;
import com.orasi.utils.Constants;
import com.orasi.utils.TestReporter;
import com.orasi.utils.Screenshot;
import com.orasi.utils.WebDriverSetup;
import com.orasi.utils.dataProviders.CSVDataProvider;
import com.orasi.utils.dataProviders.DatabaseDataProvider;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.bluesource.ListingTitlesPage;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.NewTitlePage;
import com.orasi.bluesource.TopNavigationBar;

public class TestAddNewTitleDbData {

    private String application = "";
    private String browserUnderTest = "";
    private String browserVersion = "";
    private String operatingSystem = "";
    private String runLocation = "";
    private String environment = "";
    private Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();

    @DataProvider(name = "dataScenario")
    public Object[][] scenarios() {	
	return new DatabaseDataProvider(DatabaseDataProvider.MYSQL).getTestData("TestAddNewTitle");
    }

    @BeforeTest(groups = { "regression" })
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
	    String browserVersion, String operatingSystem, String environment) {
	this.application = "Bluesource";
	this.runLocation = runLocation;
	this.browserUnderTest = browserUnderTest;
	this.browserVersion = browserVersion;
	this.operatingSystem = operatingSystem;
	this.environment = environment;

    }
    
    @AfterMethod(groups = { "regression" })
    public synchronized void closeSession(ITestResult test){
	WebDriver driver = drivers.get(test.getMethod().getMethodName());   
	
	//if is a failure, then take a screenshot
	if (test.getStatus() == ITestResult.FAILURE){
		new Screenshot().takeScreenShot(test, driver);
	}
	driver.quit();
    }

    /**
     * @throws IOException 
     * @throws InterruptedException 
     * @throws Exception
     * @Summary: Adds a housekeeper to the schedule
     * @Precondition:NA
     * @Author: Jessica Marshall
     * @Version: 10/6/2014
     * @Return: N/A
     */
    @Test(dataProvider = "dataScenario", groups = { "regression" })
    public void testCreateNewTitle(String testScenario, String role,
	    String newTitle) throws InterruptedException, IOException {
	
	String testName = new Object() {
	}.getClass().getEnclosingMethod().getName();
	
	TestReporter.setPrintToConsole(true);
	
	WebDriverSetup setup = new WebDriverSetup(application,
		browserUnderTest, browserVersion, operatingSystem, runLocation,
		environment);
	WebDriver driver = setup.initialize();
	drivers.put(testName, driver);

	// Login
	LoginPage loginPage = new LoginPage(driver);
	TestReporter.assertTrue(loginPage.pageLoaded(),
		"Verify login page is displayed");
	loginPage.login(role);

	// Verify user is logged in
	TopNavigationBar topNavigationBar = new TopNavigationBar(driver);
	TestReporter.assertTrue(topNavigationBar.isLoggedIn(), "Validate the user logged in successfully");

	// Navigate to the title page
	topNavigationBar.clickAdminLink();
	topNavigationBar.clickTitlesLink();

	// Verify navigated to the title page
	ListingTitlesPage listingTitlesPage = new ListingTitlesPage(driver);
	TestReporter.assertTrue(listingTitlesPage.pageLoaded(),
		"Verify listing titles page is displayed");

	// Click new title
	listingTitlesPage.clickNewTitle();

	// Instantiate the New titles page and create a new title
	NewTitlePage newTitlePage = new NewTitlePage(driver);
	TestReporter.assertTrue(newTitlePage.pageLoaded(),
		"Verify create new title page is displayed");
	newTitlePage.createNewTitle(newTitle);

	// Verify the title was created
	TestReporter.assertTrue(listingTitlesPage.isSuccessMsgDisplayed(), "Validate success message appears");
	TestReporter.log("New Title was created: " + newTitle);

	// Verify the title is displayed on the title results table
	TestReporter.assertTrue(listingTitlesPage.searchTableByTitle(newTitle), "Validate new title appears in table");

	// Delete the new title
	listingTitlesPage.deleteTitle(newTitle);

	// Verify the title is deleted
	ListingTitlesPage refreshedPage = new ListingTitlesPage(driver);
	TestReporter.assertTrue(refreshedPage.isSuccessMsgDisplayed(), "Validate success message appears");

	// logout
	topNavigationBar.logout();

    }

}