package com.cox.work.sis.ursula.model.json;

public class UserClassAspect {
	public String UserName;
	public MutasiMasuk MutasiMasuk;
	
	public UserClassAspect(String usrName, String id) {
		this.UserName = usrName;
		this.MutasiMasuk = new MutasiMasuk(id);
	}
}
