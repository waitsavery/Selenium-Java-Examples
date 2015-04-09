package com.orasi.bluesource;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the EmployeesPage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class EmployeesPage extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(css = "button[type = 'submit']")	
	private Button btnAdd;
	
	@FindBy(css = "input[id = 'search-bar']")
	private Textbox txtSearch;
	
	@FindBy(css = "a.ng-binding")
	private Element tableCell;
	
	@FindBy(css = ".alert-success.alert-dismissable")
	private Label lblSuccessMsg;

	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public EmployeesPage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	public void clickAddNewEmployee(){
		btnAdd.click();
	}
	
	/**
	 * @summary determines if the successmessage is displayed
	 * @return boolean; true if the message is displayed, false otherwise
	 */
	public boolean isSuccessMsgDisplayed(){
		return lblSuccessMsg.isDisplayed();
	}
	
	/**
	 * @summary returns the success message text
	 * @return String; success message text
	 */
	public String getSuccessMsgText(){
		return lblSuccessMsg.getText();
	}
	
	//
	/**
	 * @summary searches the employee results table by first & last name & clicks on a result that is found 
	 * @param firstName - employee first name
	 * @param lastName - employee last name
	 * @return boolean; true if the name is found, false otherwise
	 */
	public boolean searchTableByFirstAndLastName(String firstName, String lastName){
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		
		txtSearch.safeSet(firstName + " " + lastName);
		
		//wait for page to load/refresh
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.ng-binding")));
		
		//Get all the elements by CSS in the table that are the individual cells
		List<WebElement> elementList = getDriver().findElements(By.cssSelector("a.ng-binding"));
		for (int i = 0; i < elementList.size(); i++){
			
			//if it's the last element then just stop as it won't match
			if (i == elementList.size() - 1){	
				break;
			}
			if (elementList.get(i).getText().trim().equals(firstName)){
				Reporter.log("First name was found in table of employees");
				
				//If it matches, then see if the following element matches the last name
				if (elementList.get(i+1).getText().trim().equals(lastName)){
					Reporter.log("Last name was found in table of employees");
					//click on the element 
					elementList.get(i).click();
					
					//return true
					Reporter.log("The employee was found in the table of employees");
					return true;
				}
			}
		}
		
		//element not found that matches first and last name
		Reporter.log("The employee was not found in search results" + firstName + " " + lastName );
		return false;
		
	}
	
	/**
	 * @summary This method just selects the first employee in the employee table - so you don't need to know who the employee is
	 * @return N/A
	 */
	public void selectFirstEmployee(){
		//Get all the table elements
		List<WebElement> elementList = driver.findElements(By.cssSelector("a.ng-binding"));
		
		//click on the first one
		elementList.get(0).click();
	}

}
