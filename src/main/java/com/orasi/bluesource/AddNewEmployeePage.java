package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.TestEnvironment;

/**
 * @summary Contains the fields and method for interacting with the AddNewEmployeePage
 * @author Jessica Marshall
 *
 */
public class AddNewEmployeePage extends com.orasi.utils.TestEnvironment{
	// *******************
	// *** Page Fields ***
	// *******************
	private StringBuffer verificationErrors = new StringBuffer();

	// *********************
	// *** Page Elements ***
	// *********************
	@FindBy(id = "employee_username")	
	private Textbox txtUsername;
	
	@FindBy(id = "employee_first_name")
	private Textbox txtFirstName;
	
	@FindBy(id = "employee_last_name")
	private Textbox txtLastName;
	
	@FindBy(id = "employee_title_id")
	private Listbox lstTitle;
	
	@FindBy(id = "employee_role")
	private Listbox lstRole;
	
	@FindBy(id = "employee_manager_id")
	private Listbox lstManager;
	
	@FindBy(id = "employee_status")
	private Listbox lstStatus;
	
	@FindBy(id = "employee_location")
	private Listbox lstLocation;
	
	@FindBy(id = "employee_start_date")
	private Textbox txtStartDate;
	
	@FindBy(id = "employee_cell_phone")
	private Textbox txtCellPhone;
	
	@FindBy(id = "employee_office_phone")
	private Textbox txtOfficePhone;
	
	@FindBy(id = "employee_email")
	private Textbox txtEmail;
	
	@FindBy(id = "employee_department_id")
	private Listbox lstDept;
	
	@FindBy(name = "commit")
	private Button btnCreateEmp;

	// *********************
	// ** Build page area **
	// *********************
	/**
	 * @summary constructor to instantiate the class
	 * @param te - TestEnvironment object containing, amongst other things, the WebDriver for the test, application, operating system, browser, etc
	 */
	public AddNewEmployeePage(TestEnvironment te){
		super(te);
		ElementFactory.initElements(getDriver(), this);  
	}

	// *************************
	// *** Page Interactions ***
	// *************************	

	/**
	 * @summary adds an employee to an already existing employee roster
	 * @param username - new employee username
	 * @param firstName - new employee first name
	 * @param lastName - new employee last name
	 * @param title - new employee title
	 * @param role - new employee role
	 * @param manager - new employee manager
	 * @param status - new employee status
	 * @param location - new employee location
	 * @param startDate - new employee start date
	 * @param cellPhone - new employee cell phone number
	 * @param officePhone - new employee office phone number
	 * @param email - new employee email address
	 * @param dept - new employee department
	 * @throws Exception
	 */
	public void addEmployee(String username, String firstName, String lastName, String title, String role, String manager,
							String status, String location, String startDate, String cellPhone, String officePhone, 
							String email, String dept) throws Exception {
		  
		  //Fill in the details
		  try {
			  txtUsername.safeSet(username);
			  txtUsername.safeSet(username);
			  txtFirstName.safeSet(firstName);
			  txtLastName.safeSet(lastName);
			  lstTitle.select(title);
			  lstRole.select(role);
			  lstManager.select(manager);
			  lstStatus.select(status);
			  lstLocation.select(location);
			  txtStartDate.safeSet(startDate);
			  txtCellPhone.safeSet(cellPhone);
			  txtOfficePhone.safeSet(officePhone);
			  txtEmail.safeSet(email);
			  lstDept.select(dept);
		  
			  //submit
			  btnCreateEmp.click();
			  
		  }catch (Exception e){
			  verificationErrors.append(e.toString());
			  Reporter.log("Element not found on the add employee frame: " + e);
			  throw(e);
		  }
		  
	  }
}
