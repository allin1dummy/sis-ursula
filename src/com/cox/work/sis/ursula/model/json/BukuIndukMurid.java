package com.cox.work.sis.ursula.model.json;

public class BukuIndukMurid {
	public BaseEntity BaseEntity;
	public String ImageString;
	public int JumlahSaudaraAngkat;
	public int JumlahSaudaraKandung;
	public MutasiMasuk MutasiMasuk;
	public boolean Status;
	public int TahunTamat;
	public String TanggalLahir;
	public String NISN;
	public TempatLahir TempaLahir;


	public class TempatLahir {
		public BaseEntity BaseEntity;
		public boolean Status;
	}
}