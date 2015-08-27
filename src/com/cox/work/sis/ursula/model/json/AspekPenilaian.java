package com.cox.work.sis.ursula.model.json;

import java.util.List;

public class AspekPenilaian {
	public int Id;
	public BaseEntity BaseEntity;
	public boolean IsAktif;
	public String Nama;
	public int SortIndex;
	public boolean Status;
	public TipeKKM TipeKKM;
	public List<JenisNilai> ListJenisNilai;
	
	@Override
	public String toString() {
		return Nama;
	}
}
