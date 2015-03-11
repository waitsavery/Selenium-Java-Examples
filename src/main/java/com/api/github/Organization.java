package com.api.github;

public class Organization {
	private String login;
	private String id;
	
	public Organization() {
		super();
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(final String login){
		this.login=login;
	}
}
