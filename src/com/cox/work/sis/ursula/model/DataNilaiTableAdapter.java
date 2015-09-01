package com.cox.work.sis.ursula.model;

import java.util.ArrayList;

public class DataNilaiTableAdapter {
	private int Id;
	private String mataPelajaran;
	private ArrayList<Float> nilai;

	public DataNilaiTableAdapter(int i, String m) {
		Id = i;
		mataPelajaran = m;
		nilai = null;
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
	public ArrayList<Float> getNilai() {
		return nilai;
	}
	public void setNilai(ArrayList<Float> n) {
		nilai = n;
	}
	
}