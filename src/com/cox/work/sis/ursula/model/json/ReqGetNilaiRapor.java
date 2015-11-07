package com.cox.work.sis.ursula.model.json;

public class ReqGetNilaiRapor {
	public String muridKelasId;
	public String aspekPenilaianId;
	public String semester;
	
	public ReqGetNilaiRapor(String kelas, String aspekPenilaian, String smstr) {
		muridKelasId = kelas;
		aspekPenilaianId = aspekPenilaian;
		semester = smstr;
	}
}
