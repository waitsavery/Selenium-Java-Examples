package com.selenium.testClassTemplates;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestNgTestClassMethods;
import com.saucelabs.testng.SauceOnDemandTestListener;

/**
 * @summary contains reusable code for test classes that use Sauce Labs as the remote VM farm
 * @author Jonathan Doll & Waightstill W Avery
 *
 */
@Listeners({SauceOnDemandTestListener.class})
public class TestClassTemplate {
	//********************
	//*** Class Fields ***
	//********************
	//Test name to be defined in the test class
	protected String testName = null;
	//TestNevironment object to be used throughout the test
	protected TestEnvironment te = null;
	//Invoke the class whose methods contain TestNG annotated method-bodies
	protected TestNgTestClassMethods test = null;

	// **********************
	// After Method Behavior
	// **********************
	/**
	 * @summary processes the actions to be taken after a test is run on a typical Selenium grid
	 * @param results - object containing the results of a test, passed by the @Test TestNG method
	 */
	@AfterMethod(groups = { "regression" })
	public synchronized void tearDownClass(ITestResult results) {
		test.after(results, te.getDriver());
	}
}
