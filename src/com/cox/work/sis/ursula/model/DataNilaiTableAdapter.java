package com.cox.work.sis.ursula.model;

import java.util.ArrayList;

public class DataNilaiTableAdapter {
	private int Id;
	private String mataPelajaran;
	private ArrayList<NilaiDanTanggal> nilai;
	private int latestNilaiKe;

	public DataNilaiTableAdapter(int i, String m) {
		Id = i;
		mataPelajaran = m;
		nilai = null;
	}
	
	public float calculateMeanValue() {
		int i = 0;
		float tot = 0f;
		for( ; i < nilai.size(); i ++) {
			tot += nilai.get(i).getNilaiAngka();
		}
		return ((float)tot/i);
	}
	
	public String getMataPelajaran() {
		return mataPelajaran;
	}
	public void setMataPelajaran(String mataPelajaran) {
		this.mataPelajaran = mataPelajaran;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public ArrayList<NilaiDanTanggal> getNilai() {
		return nilai;
	}
	public void setNilai(ArrayList<NilaiDanTanggal> n) {
		nilai = n;
	}
	public int getLatestNilaiKe() {
		return latestNilaiKe;
	}
	public void setLatestNilaiKe(int latestNilaiKe) {
		this.latestNilaiKe = latestNilaiKe;
	}
	
}
