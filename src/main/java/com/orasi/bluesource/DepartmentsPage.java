package com.orasi.bluesource;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Link;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the DepartmentsPage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class DepartmentsPage extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(linkText = "Add Department")
	private Link lnkAddDept;
	
	@FindBy(xpath = "//h1[text() = 'Departments']")
	private Label lblTitle;

	@FindBy(css = ".alert-success.alert-dismissable")
	private Label lblSuccessMsg;

	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public DepartmentsPage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	
	public void clickAddDeptLink(){
		lnkAddDept.click();
	}
	
	/**
	 * @summary determines if the title header is displayed
	 * @return boolean; true if displayed, false otherwise
	 */
	public boolean isTitleHeaderDisplayed(){
		return lblTitle.isDisplayed();
	}
	
	/**
	 * @summary determines if the success message is displayed
	 * @return boolean; true if displayed, false otherwise
	 */
	public boolean isSuccessMsgDisplayed(){
	    Sleeper.sleep(1000);
		return lblSuccessMsg.isDisplayed();
	}
	
	/**
	 * @summary searches a webtable for a given department name
	 * @param dept - department name for which to search
	 * @return boolean; true if it is found, false otherwise
	 */
	public boolean searchTableByDept(String dept){
		//Get all the rows in the table by CSS
		List<WebElement> elementList = getDriver().findElements(By.cssSelector(".list-group-item"));
		for(WebElement element:elementList){
			//if it matches the title, then return true
			if(element.getText().contains(dept)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @summary searches a webtable for a given department name
	 * @param dept - department name for which to search
	 * @return boolean; true if it is found, false otherwise
	 */
	public boolean deleteDept(String dept){
		//Get all the rows in the table by CSS
		List<WebElement> elementList = getDriver().findElements(By.cssSelector(".list-group-item"));
		for(WebElement element:elementList){
			
			//if it matches the title, then click on the trash element
		    	// Justin: Updated to use contains as new text is appearing in fields
			//if(element.getText().equals(dept)){
		    	if(element.getText().contains(dept)){
		
				//click on the trash element
				element.findElement(By.cssSelector("a[data-method = 'delete']")).click();
				
				//accept the alert that pops up
				Alert alert = getDriver().switchTo().alert();
				alert.accept();
				return true;
			}
		}
		return false;
	}
}
