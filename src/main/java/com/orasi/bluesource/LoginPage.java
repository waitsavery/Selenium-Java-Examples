package com.orasi.bluesource;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.Constants;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the LoginPage
 * @author Jessica Marshall
 *
 */
@SuppressWarnings("unused")
public class LoginPage  extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private int loopCounter = 0;
	private int timeout = getDefaultTestTimeout();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(id = "employee_username")
	private Textbox txtUsername;
	
	@FindBy(id = "employee_password")
	private Textbox txtPassword;
	
	@FindBy(name = "commit")
	private Button btnLogin;

	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public LoginPage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************
	/**
	 * @summary enters a username, password and clicks the login button to login to the application
	 * @param role - determines the level of access for the user login and retrieves an appropriate user
	 */
	public void login(String role) {
		final String username;
		final String password;
		final ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
		
		username = userCredentialRepo.getString("BLUESOURCE_" + role.toUpperCase());
		password = userCredentialRepo.getString("BLUESOURCE_ENCODED_PASSWORD");
		 
		getDriver().switchTo().defaultContent();

		if(getBrowserUnderTest().equalsIgnoreCase("safari")){
			txtUsername.set(username);
		}else{
			txtUsername.safeSet(username);
		}
		
		txtPassword.setSecure(password);
		btnLogin.click();
	}
}