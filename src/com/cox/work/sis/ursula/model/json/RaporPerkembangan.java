package com.cox.work.sis.ursula.model.json;

public class RaporPerkembangan {
	public String TahunPelajaranDisplay;
	public PerkembanganFisikKehadiranPrestasi PerkembanganFisikKehadiranPrestasi;
	
	private class PerkembanganFisikKehadiranPrestasi {
		public BaseEntity BaseEntity;
		public Kehadiran Kehadiran;
		public PerkembanganFisik PerkembanganFisik;
		public Prestasi Prestasi1;
		public Prestasi Prestasi2;
		public Saran Saran;
		public int Semester;
		public boolean Status;
	}
	
	private class Kehadiran {
		public int Ijin;
		public int Sakit;
		public int TanpaKeterangan;
		public int TerlambarHadir;
	}
	
	private class PerkembanganFisik {
		public int BeratBadan;
		public int Gigi;
		public String Penglihatan;
		public int TinggiBadan;
	}
	
	private class Prestasi {
		public String Deskripsi;
		public String Nama;
	}
	
	private class Saran {
		public String SaranAkhir;
		public String SaranMid;
	}
}
