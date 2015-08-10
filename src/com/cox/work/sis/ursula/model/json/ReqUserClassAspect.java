package com.cox.work.sis.ursula.model.json;

public class ReqUserClassAspect {
	public UserClassAspect User;
	
	public ReqUserClassAspect(String usrName, String id) {
		this.User = new UserClassAspect(usrName, id);
	}
}
