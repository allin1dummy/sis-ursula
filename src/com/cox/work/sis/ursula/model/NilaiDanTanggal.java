package com.cox.work.sis.ursula.model;

public class NilaiDanTanggal {
	private float nilaiAngka;
	private String tanggal;
	
	public NilaiDanTanggal(float n, String t) {
		nilaiAngka = n;
		tanggal = t;
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
	
	
}
