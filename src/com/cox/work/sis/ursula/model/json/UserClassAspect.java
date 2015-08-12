package com.cox.work.sis.ursula.model.json;

public class UserClassAspect {
	public String Username;
	public MutasiMasuk MutasiMasuk;
	
	public UserClassAspect(String usrName, String id) {
		this.Username = usrName;
		this.MutasiMasuk = new MutasiMasuk(id);
	}
}
