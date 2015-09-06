package com.cox.work.sis.ursula.model;

public class NilaiDanTanggal {
	private float nilaiAngka;
	private String tanggal;
	private boolean isRemidi;
	
	public NilaiDanTanggal(float n, String t, boolean i) {
		nilaiAngka = n;
		tanggal = t;
		isRemidi = i;
	}
	
	public float getNilaiAngka() {
		return nilaiAngka;
	}
	public void setNilaiAngka(float nilai) {
		this.nilaiAngka = nilai;
	}
	public String getTanggal() {
		return tanggal;
	}
	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}
	public boolean isRemidi() {
		return isRemidi;
	}
	public void setRemidi(boolean isRemidi) {
		this.isRemidi = isRemidi;
	}
	
	
}
