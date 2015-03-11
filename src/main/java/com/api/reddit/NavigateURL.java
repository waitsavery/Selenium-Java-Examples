package com.api.reddit;

public class NavigateURL {
    private String baseURL = "http://www.reddit.com";
    
    public String register(String username, String email, String password, String destination, boolean rememberUser ,String reason){
	return baseURL + "/api/register?user=" + username + "&email=" + email + "&passwd=" + password +"&passwd2=" + password +"+&dest="+ destination +"&rem=" + rememberUser + "&reason=" +reason;
    }
}
