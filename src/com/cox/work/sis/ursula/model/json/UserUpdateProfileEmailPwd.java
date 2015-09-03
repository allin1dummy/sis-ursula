package com.cox.work.sis.ursula.model.json;

public class UserUpdateProfileEmailPwd {
	DtUser User;
	
	public UserUpdateProfileEmailPwd(String string, String string2, String string3, String string4) {
		User = new DtUser(string, string2, string3, string4);
	}
	
	public UserUpdateProfileEmailPwd(String username, String email) {
		User = new DtUser(username, email);
	}

}

class DtUser {
	public String Username;
	public String Email;
	public String PasswordLama;
	public String Password;
	
	public DtUser(String usrName, String email, String pwdLama, String pwdBaru) {
		this(usrName, email);
		this.PasswordLama = pwdLama;
		this.Password = pwdBaru;
	}

	public DtUser(String usrName, String email) {
		this.Username = usrName;
		this.Email = email;
	}

}
