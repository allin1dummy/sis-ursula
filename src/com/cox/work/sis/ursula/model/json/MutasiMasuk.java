package com.cox.work.sis.ursula.model.json;

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
	
	public MutasiMasuk(String id) {
		this.Id = id;
	}
}
