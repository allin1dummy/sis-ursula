package com.cox.work.sis.ursula.model;

public class DataNilaiTableAdapter {

	private String mataPelajaran;
	private float[][] nilai;

	public DataNilaiTableAdapter(String m, float[][] n) {
		mataPelajaran = m;
		nilai = n;
	}

	public String getMataPelajaran() {
		return mataPelajaran;
	}

	public void setMataPelajaran(String mataPelajaran) {
		this.mataPelajaran = mataPelajaran;
	}
	
	public float[][] getNilai() {
		return nilai;
	}

	public void setNilai(float[][] nilai) {
		this.nilai = nilai;
	}
}
