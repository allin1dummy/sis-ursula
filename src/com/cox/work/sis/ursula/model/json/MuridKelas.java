package com.cox.work.sis.ursula.model.json;

public class MuridKelas {
	public int Id;
	public boolean IsNaikKelas;
	public Kelas Kelas;
	public String KelasDisplayMember;
	public int MutasiMasukId;
	public int No;
	public boolean Status;
	public int TahunPelajaran;
	public String TahunPelajaranDisplayMember;
	
	@Override
	public String toString() {
		return KelasDisplayMember + " (" + TahunPelajaranDisplayMember + ")";
	}
}
