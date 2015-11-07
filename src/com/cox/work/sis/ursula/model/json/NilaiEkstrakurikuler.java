package com.cox.work.sis.ursula.model.json;

public class NilaiEkstrakurikuler extends BaseEntity {
	public Ekstrakurikuler Ekstrakurikuler;
	public String KeteranganUbah;
	public NilaiHuruf NilaiHuruf;
	public float RataRata;
	public int Semester;
	public boolean Status;
	
	public class NilaiHuruf {
		public String Nama;
		public int SortIndex;
		public boolean Status;
	}
}
