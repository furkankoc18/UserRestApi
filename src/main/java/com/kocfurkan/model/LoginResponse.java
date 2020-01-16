package com.kocfurkan.model;

public class LoginResponse {

	private boolean status;
	private String token;
	
	public LoginResponse(boolean status, String token) {
		this.status = status;
		this.token = token;
	}
	
	
	//Getters and Setters
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
