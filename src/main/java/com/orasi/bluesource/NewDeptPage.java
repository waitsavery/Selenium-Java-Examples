package com.orasi.bluesource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the NewDeptPage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class NewDeptPage extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(id = "department_name")
	private Textbox txtDept;
	
	@FindBy(id = "department_department_id")
	private Textbox txtParentDept;
	
	@FindBy(name = "commit")
	private Button btnCreateDept;
	
	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public NewDeptPage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	/**
	 * @summary enters a new department name and clicks the button to create it
	 * @param dept - name of the department to be created
	 */
	public void CreateNewDept(String dept){
		txtDept.safeSet(dept);
		btnCreateDept.click();
	}
}
