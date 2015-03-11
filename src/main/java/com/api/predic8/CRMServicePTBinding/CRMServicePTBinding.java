package com.api.predic8.CRMServicePTBinding;

import com.orasi.api.soapServices.core.*;

public class CRMServicePTBinding extends SoapService{
	public CRMServicePTBinding(String environment) {	
		setEnvironmentServiceURL("http://www.predic8.com:8080/crm/CustomerService");	
	}
}
