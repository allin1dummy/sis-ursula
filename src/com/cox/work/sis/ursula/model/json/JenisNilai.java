package com.cox.work.sis.ursula.model.json;

public class JenisNilai {
	public int Id;
	public boolean IsAktif;
	public int JumlahNilaiRubrik;
	public String Nama;
	public boolean Status;
	
	@Override
	public String toString() {
		return Nama;
	}
}
