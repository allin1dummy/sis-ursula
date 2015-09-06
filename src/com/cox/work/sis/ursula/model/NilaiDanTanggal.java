package com.cox.work.sis.ursula.model;

public class NilaiDanTanggal {
	private float nilaiAngka;
	private String tanggal;
	private boolean isRemidi;
	private int nilaiKe;
	
	public NilaiDanTanggal(float n, String t, boolean i, int k) {
		nilaiAngka = n;
		tanggal = t;
		isRemidi = i;
		setNilaiKe(k);
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
	public int getNilaiKe() {
		return nilaiKe;
	}
	public void setNilaiKe(int nilaiKe) {
		this.nilaiKe = nilaiKe;
	}
	
	
}
