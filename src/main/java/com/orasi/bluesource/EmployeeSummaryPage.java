package com.orasi.bluesource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Link;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the EmployeeSummaryPage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class EmployeeSummaryPage  extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(xpath = "//*[@id='accordion']/div/div[6]/div[1]/a[2]")
	private Link lnkManageTimeOff;
	
	@FindBy(linkText = "View")
	private Link lnkViewTimeOff;
	private Label lblSuccessMsg;

	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public EmployeeSummaryPage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	public void ClickManageTimeOff(){
		lnkManageTimeOff.click();
	}
	
	public void ViewManageTimeOff(){
		lnkViewTimeOff.click();
	}
	
	
}
