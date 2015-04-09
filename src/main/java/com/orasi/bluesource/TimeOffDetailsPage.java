package com.orasi.bluesource;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the TimeOffDetailsPage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class TimeOffDetailsPage extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(id = "new_vacation_date_requested")
	private Textbox txtDateRequested;
	
	@FindBy(id = "new_vacation_start_date")
	private Textbox txtStartDate;
	
	@FindBy(id = "new_vacation_end_date")
	private Textbox txtEndDate;
	
	@FindBy(className = "business-days")
	private Label lblTotalDays;
	
	@FindBy(id = "new_vacation_vacation_type")
	private Listbox lstVacationType;
	
	@FindBy(css = "input[class = 'half-day']")
	private Button btnHalfDay;
	
	@FindBy(css = "input[value = 'Save Time Off']")
	private Button btnSave;
	
	@FindBy(css = ".alert-success.alert-dismissable")
	private Label lblSuccessMsg;
	
	@FindBy(name = "new[vacation][reason]")
	private Textbox txtReason;
	
	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public TimeOffDetailsPage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	/**
	 * @summary determines if a success message is displayed
	 * @return boolean; true if it is displayed, flase otherwise
	 */
	public boolean isSuccessMsgDisplayed(){
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-success.alert-dismissable")));
		return lblSuccessMsg.isDisplayed();
	}
	
	/**
	 * @summary returns the message text
	 * @return String; message text
	 */
	public String getSuccessMsgText(){
		return lblSuccessMsg.getText();
	}
	
	/**
	 * @summary method to build an Action that hovers over a particular element
	 * @param element - WebElement over which to hover
	 */
	public void hoverOverElement(WebElement element){
		Actions builder = new Actions(getDriver());
		Actions hoverOverRegistrar = builder.moveToElement(element);
		hoverOverRegistrar.perform();
	}
	
	/**
	 * @summary enters and saves time off requests
	 * @param dateRequested - date that the request was submitted
	 * @param startDate - start date for the time off
	 * @param endDate - end date for the time off
	 * @param vacationType - type of time off request
	 * @param otherReason - field for user input for requests not covered by the enumerated types
	 * @param halfDay - boolean String; indicates whether the request is for a half day
	 */
	public void enterTimeOff(String dateRequested, String startDate, String endDate, String vacationType,
								String otherReason, String halfDay ) {
		//Enter the data
		txtDateRequested.safeSet(dateRequested);
		txtStartDate.safeSet(startDate);
		txtEndDate.safeSet(endDate);
		lstVacationType.select(vacationType);
		
		//If the vacation type is 'Other', then need to fill out the reason field
		if (vacationType.equalsIgnoreCase("Other")) {
			//first you must hover over the vacation type field after selecting other
			hoverOverElement(lstVacationType);
			txtReason.safeSet(otherReason);
		}

		//If its a half day
		if (halfDay.equalsIgnoreCase("TRUE")){
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOf(btnHalfDay));
			btnHalfDay.click();
		}

		btnSave.click();
	}
	
	/**
	 * @summary deletes a time off request
	 * @param startDate - start date for the time off to be deleted
	 * @param endDate - end date for the time off to be deleted
	 */
	public void DeleteTimeOff(String startDate, String endDate){
		
	}
	
	/**
	 * @summary deletes all time off requests
	 * @return N/A
	 */
	public void DeleteAllTimeOff(){
		
		List<WebElement> deleteIconsList = getDriver().findElements(By.cssSelector("a[data-method = 'delete']"));
		if (deleteIconsList.size() > 0){
			for(WebElement element:deleteIconsList){
				element.click();
				
				//accept the alert that pops up
				Alert alert = getDriver().switchTo().alert();
				alert.accept();
			}
		}
	}
}
