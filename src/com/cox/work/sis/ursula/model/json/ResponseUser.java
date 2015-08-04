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
		public boolean IsFirstTime;
		public boolean IsPasswordChanged;
		public boolean Status;
		public MutasiMasuk MutasiMasuk;
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

	public class MutasiMasuk {
		public String Id;
		public String NamaSiswa;
		public String NoInduk;
		public String NoPesertaUjian;
		public boolean Status;
		public String TanggalMasuk;
		public Tingkat Tingkat;
		public AsalSekolah AsalSekolah;
		public BaseEntity BaseEntity;
	}

	public class Tingkat {
		public int SortIndex;
		public boolean Status;
	}

	public class AsalSekolah {
		public String Alamat;
		public boolean Status;
		public Tingkat Tingkat;
		public BaseEntity BaseEntity;
	}
}
