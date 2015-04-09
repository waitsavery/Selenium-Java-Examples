package com.orasi.bluesource;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Link;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the ListingTitlesPage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class ListingTitlesPage  extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(linkText = "New Title")	
	private Link lnkNewTitle;
	
	@FindBy(xpath = "//h1[text() = 'Listing titles']")
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
	public ListingTitlesPage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	public void clickNewTitle(){
		lnkNewTitle.click();
	}
	
	/**
	 * @summary determines if the title header is displayed
	 * @return boolean; true if the header is displayed, false otherwise
	 */
	public boolean isTitleHeaderDisplayed(){
		return lblTitle.isDisplayed();
	}
	
	/**
	 * @summary determines if the success message is displayed
	 * @return boolean; true if the message is displayed, false otherwise
	 */
	public boolean isSuccessMsgDisplayed() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-success.alert-dismissable")));
		return lblSuccessMsg.isDisplayed();
	}
	
	/**
	 * @summary searches a table for a predefined value
	 * @return boolean; true if the value is found, false otherwise
	 */
	public boolean searchTableByTitle(String title){
		//Get all the rows in the table by CSS
		List<WebElement> elementList = getDriver().findElements(By.cssSelector("td"));
		for(WebElement element:elementList){
			//if it matches the title, then return true
			if(element.getText().equals(title)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @summary searches for a title and, if found, deletes it
	 * @param title - the title for which to search for and delete
	 * @return boolean; true is the title is found, false otherwise
	 */
	public boolean deleteTitle(String title){
		//Get all the rows in the table by CSS
		List<WebElement> elementList = getDriver().findElements(By.cssSelector("td"));
		for(WebElement element:elementList){
			//if it matches the title, then click on the trash element
			if(element.getText().equals(title)){
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
