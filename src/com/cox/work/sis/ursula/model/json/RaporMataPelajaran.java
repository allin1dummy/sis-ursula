package com.cox.work.sis.ursula.model.json;

public class RaporMataPelajaran {
	public String Kelas;
	public String TahunPelajaranDisplay;
	public String KKM;
	public int KKMAngka;
	public KelasMataPelajaran KelasMataPelajaran;
	public Nilai Nilai;
	
	public class Nilai extends BaseEntity {
		public NilaiHuruf NilaiHuruf;
		public float RataRata;
		public int Semester;
		public boolean Status;
	}
	
	public class NilaiHuruf {
		public String Nama;
		public int SortIndex;
		public boolean Status;
	}
	
	public class KelasMataPelajaran {
		public BaseEntity BaseEntitiy;
		public boolean IsAktif;
		public MataPelajaran MataPelajaran;
		public int NoUrut;
		public boolean Status;
	}
}


