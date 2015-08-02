package com.cox.work.sis.ursula.model.json;

public class DataUser {
	public UserBase User;
	
	public DataUser(String usr, String pwd) {
		User = new UserBase(usr, pwd);
	}
}
