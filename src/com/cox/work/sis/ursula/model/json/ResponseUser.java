package com.cox.work.sis.ursula.model.json;

public class ResponseUser extends ResponseBase{
	public User User;

	public class User extends UserBase {
		public User(String usr, String pwd) {
			super(usr, pwd);
		}

		public String Id;
		public BaseEntity BaseEntity;
		public String Email;
		public Role Role;
		public boolean IsFirstTime;
		public boolean IsPasswordChanged;
		public boolean Status;
		public MutasiMasuk MutasiMasuk;
	}

	public class Role {
		public boolean Status;
	}
}
