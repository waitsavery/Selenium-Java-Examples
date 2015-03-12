package com.orasi.bluesource;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.Constants;

public class LoginPage {
	private WebDriver driver;
	
	//all the page elements
	@FindBy(id = "employee_username")
	private Textbox txtUsername;
	
	@FindBy(id = "employee_password")
	private Textbox txtPassword;
	
	@FindBy(name = "commit")
	private Button btnLogin;
	
	// *********************
	// ** Build page area **
	// *********************
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	public boolean pageLoaded() {
		return new PageLoaded().isElementLoaded(this.getClass(), driver, btnLogin);
	}

	public LoginPage initialize() {
		return ElementFactory.initElements(driver, this.getClass());
	}

	// *****************************************
	// ***Page Interactions ***
	// *****************************************
	
	public void login(String role) {
		final String username;
		final String password;
		final ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
		
		username = userCredentialRepo.getString("BLUESOURCE_" + role.toUpperCase());
		//password = userCredentialRepo.getString("BLUESOURCE_PASSWORD");
		password = userCredentialRepo.getString("BLUESOURCE_ENCODED_PASSWORD");
		 
		driver.switchTo().defaultContent();
		
		if(System.getProperty(Constants.BROWSER).equalsIgnoreCase("safari")){
			txtUsername.set(username);	
		}else{
			txtUsername.safeSet(username);
		}
		//txtPassword.safeSet(password);
		txtPassword.setSecure(password);
		btnLogin.click();
	}
}