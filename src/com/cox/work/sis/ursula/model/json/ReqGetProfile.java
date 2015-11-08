package com.cox.work.sis.ursula.model.json;

public class ReqGetProfile {
	public User User;
	
	public ReqGetProfile(String username, String mutasimasuk) {
		User = new User(username, mutasimasuk);
	}
}

class User {
	public String Username;
	public MutasiMasuk MutasiMasuk;
	
	public User(String user) {
		Username = user;
	}
	
	public User(String user, String mutasi) {
		this(user);
		MutasiMasuk = new MutasiMasuk(mutasi);
	}
}