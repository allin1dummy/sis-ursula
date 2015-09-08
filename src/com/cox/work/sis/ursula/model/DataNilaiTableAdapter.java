package com.cox.work.sis.ursula.model;

public class DataNilaiTableAdapter {
	private int Id;
	private String mataPelajaran;
	private NilaiDanTanggal[] listNilai;
	private int latestNilaiKe;
	private float RataRata;

	public DataNilaiTableAdapter(int i, String m) {
		Id = i;
		mataPelajaran = m;
		listNilai = null;
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
	public NilaiDanTanggal[] getListNilai() {
		return listNilai;
	}
	public void setListNilai(NilaiDanTanggal[] n) {
		listNilai = n;
	}
	public int getLatestNilaiKe() {
		return latestNilaiKe;
	}
	public void setLatestNilaiKe(int latestNilaiKe) {
		this.latestNilaiKe = latestNilaiKe;
	}
	public float getRataRata() {
		return RataRata;
	}
	public void setRataRata(float rataRata) {
		RataRata = rataRata;
	}
	
}
