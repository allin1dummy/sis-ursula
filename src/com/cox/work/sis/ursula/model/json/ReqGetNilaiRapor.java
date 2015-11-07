package com.cox.work.sis.ursula.model.json;

public class ReqGetNilaiRapor {
	public String muridKelasId;
	public String semester;
	
	public ReqGetNilaiRapor(String kelas, String smstr) {
		muridKelasId = kelas;
		semester = smstr;
	}
}
