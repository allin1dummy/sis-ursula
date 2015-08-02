package com.cox.work.sis.ursula.model.json;

public class ResponseUser {
	public String Acknowledge;
	public String Build;
	public String Message;
	public String Version;
	public User User;

	public class User extends UserBase {
		public User(String usr, String pwd) {
			super(usr, pwd);
		}

		public String Id;
		public BaseEntity BaseEntity;
		public String Email;
		public Role Role;
	}

	public class BaseEntity {
		public String CreatedBy;
		public String CreateDate;
		public String UpdatedBy;
		public String UpdateDate;
	}

	public class Role {
		public boolean Status;
	}
}
