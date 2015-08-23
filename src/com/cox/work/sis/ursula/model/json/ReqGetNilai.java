package com.cox.work.sis.ursula.model.json;

public class ReqGetNilai {
	public String muridKelasId;
	public String aspekPenilaianId;
	public String semester;
	
	public ReqGetNilai(String kelas, String aspekPenilaian, String smstr) {
		muridKelasId = kelas;
		aspekPenilaianId = aspekPenilaian;
		semester = smstr;
	}
}
