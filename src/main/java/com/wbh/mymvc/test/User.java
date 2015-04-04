package com.wbh.mymvc.test;

import com.wbh.mymvc.annotation.validator.NotBlank;

public class User {
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
