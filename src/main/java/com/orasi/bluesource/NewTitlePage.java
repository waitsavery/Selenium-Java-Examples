package com.orasi.bluesource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.Constants;
import com.orasi.utils.PageLoaded;

import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the NewTitlePage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class NewTitlePage  extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(id = "title_name")
	private Textbox txtTitle;
	
	@FindBy(name = "commit")
	private Button btnCreateTitle;
	
	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public NewTitlePage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	/**
	 * @summary enters a new title and clicks the button to create it
	 * @param newTitle - name of the title to create
	 */
	public void createNewTitle(String newTitle){
		if(getBrowserUnderTest().equalsIgnoreCase("safari")){
			txtTitle.set(newTitle);	
		}else{
			txtTitle.safeSet(newTitle);
		}
		btnCreateTitle.click();
	}
}
