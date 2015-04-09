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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.utils.Constants;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestNgTestClassMethods;
import com.orasi.utils.TestReporter;
import com.orasi.utils.Screenshot;
import com.orasi.utils.dataProviders.DatabaseDataProvider;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.bluesource.DepartmentsPage;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.NewDeptPage;
import com.orasi.bluesource.TopNavigationBar;

@SuppressWarnings("unused")
public class TestAddNewDeptDbData  extends com.selenium.testClassTemplates.TestClassTemplate{
    private String application = "Bluesource";
	
	/*
	 * Define a collection of webdrivers and test names inside a Map.
	 * This allows for more than one driver to be used within a test class.
	 * This also allows for a particular driver to be tied to a specific test 
	 * based on test name.
	 */
    private Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();

    @DataProvider(name = "dataScenario")
    public Object[][] scenarios() {
    	return new DatabaseDataProvider(DatabaseDataProvider.MYSQL).getTestData("TestAddNewDepartment");
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
     * @Summary: Adds a housekeeper to the schedule
     * @Precondition:NA
     * @Author: Jessica Marshall
     * @Version: 10/6/2014
     * @Return: N/A
     */
    @Test(dataProvider = "dataScenario", groups = { "regression" })
    public void testCreateNewDept(String testScenario, String role,
	    String newDept) throws InterruptedException, IOException {
	
    	this.testName = new Object(){}.getClass().getEnclosingMethod().getName() 
				+ "_" + this.te.getOperatingSystem()
				+ "_" + this.te.getBrowserUnderTest()
				+ "_" + this.te.getBrowserVersion();

		this.te.setDriver(this.test.testStart(this.testName, this.te));
	
		// Login
		LoginPage loginPage = new LoginPage(this.te);
		TestReporter.assertTrue(this.te.pageLoaded(),	"Verify login page is displayed");
		loginPage.login(role);
	
		// Verify user is logged in
		TopNavigationBar topNavigationBar = new TopNavigationBar(this.te);
		TestReporter.assertTrue(topNavigationBar.isLoggedIn(), "Validate the user logged in successfully");
	
		// Navigate to the dept page
		topNavigationBar.clickAdminLink();
		topNavigationBar.clickDepartmentsLink();
	
		// Verify navigated to the dept page
		DepartmentsPage deptPage = new DepartmentsPage(this.te);
		TestReporter.assertTrue(this.te.pageLoaded(), "Verify list of departments page is displayed");
	
		// Add a new dept
		deptPage.clickAddDeptLink();
		NewDeptPage newDeptPage = new NewDeptPage(this.te);
		TestReporter.assertTrue(this.te.pageLoaded(), "Verify add new department page is displayed");
		newDeptPage.CreateNewDept(newDept);
	
		// Verify the dept is added
		TestReporter.assertTrue(deptPage.isSuccessMsgDisplayed(), "Validate success message appears");
		TestReporter.log("New Dept was created: " + newDept);
	
		// Verify the dept is displayed on the dept results table
		TestReporter.assertTrue(deptPage.searchTableByDept(newDept), "Validate new department exists in table");
	
		// Delete the new dept
		deptPage.deleteDept(newDept);
	
		// Verify the department is deleted
		DepartmentsPage refreshedPage = new DepartmentsPage(this.te);
		TestReporter.assertTrue(refreshedPage.isSuccessMsgDisplayed(), "Validate success message appears");
	
		// logout
		topNavigationBar.logout();
    }
}