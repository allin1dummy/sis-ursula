package com.cox.work.sis.ursula.model.json;

public class RaporSikap {
	public Kompetensi KompentensiDasar;
	public Kompetensi KompentensiInti;
	public Nilai Nilai;
	
	private class Kompetensi {
		public BaseEntity BaseEntity;
		public String Keterangan;
		public String No;
		public int Parent;
		public int Semester;
		public boolean Status;
	}
	
	private class Nilai extends BaseEntity {
		public NilaiHuruf NilaiHuruf;
		public float RataRata;
		public int Semester;
		public boolean Status;
	}
	
	private class NilaiHuruf extends BaseEntity {
		public String Nama;
		public int SortIndex;
		public boolean Status;
	}
}
