package com.cox.work.sis.ursula.model.json;

public class RaporMataPelajaran {
	public String Kelas;
	public String TahunPelajaranDisplay;
	public String KKM;
	public int KKMAngka;
	public KelasMataPelajaran KelasMataPelajaran;
	public Nilai Nilai;
	
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
	
	private class KelasMataPelajaran {
		public BaseEntity BaseEntitiy;
		public boolean IsAktif;
		public MataPelajaran MataPelajaran;
		public int NoUrut;
		public boolean Status;
	}
}

