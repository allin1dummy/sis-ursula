package com.cox.work.sis.ursula;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.adapter.RaporEkskulTableAdapter;
import com.cox.work.sis.ursula.adapter.RaporKehadiranTableAdapter;
import com.cox.work.sis.ursula.adapter.RaporMataPelajaranTableAdapter;
import com.cox.work.sis.ursula.adapter.RaporSikapTableAdapter;
import com.cox.work.sis.ursula.model.json.ClassAndAspect;
import com.cox.work.sis.ursula.model.json.MuridKelas;
import com.cox.work.sis.ursula.model.json.RaporPerkembangan;
import com.cox.work.sis.ursula.model.json.RaporPerkembangan.PerkembanganFisikKehadiranPrestasi;
import com.cox.work.sis.ursula.model.json.ReqGetNilaiRapor;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseGetNilaiRapor;
import com.cox.work.sis.ursula.util.Util;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class NilaiRaporFragment extends Fragment implements OnItemSelectedListener{
	private View rootView;
	private TextView tv_NamaSiswa, tv_WaliKelas
					, tv_raporMtPelajaran, tv_raporSikap, tv_raporEkskul, tv_raporKehadiran
					, tv_raporSaranMid, tv_raporSaranMidDesc, tv_raporSaranAkhir, tv_raporSaranAkhirDesc;
	private TableFixHeaders tableMataPelajaran, tableSikap, tableEkskul, tableKehadiran;
	private String namaSiswa;
	private Spinner spClass;
	private Spinner spSemester;
	private Button btnShowMarks;
	private ArrayAdapter spninnerAdapter;
	private String userName;
	private String mutasiId;
	private int muridKelasId = -1;
	private List<MuridKelas> listMuridKelas = new ArrayList<MuridKelas>();
	private List<String> listSemester = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_rapor, container, false);

		userName = getArguments().getString(Util.Constant.USERNAME);
		namaSiswa = getArguments().getString(Util.Constant.NAMASISWA);
		mutasiId = getArguments().getString(Util.Constant.MUTASIID);

		tv_NamaSiswa = (TextView) rootView.findViewById(R.id.tv_name);
		tv_NamaSiswa.setText("Nama : " + namaSiswa);
		tv_WaliKelas = (TextView) rootView.findViewById(R.id.tv_wali);
		
		tv_raporMtPelajaran = (TextView) rootView.findViewById(R.id.tv_raporMtPelajaran);
		tv_raporMtPelajaran.setVisibility(View.GONE);
		tableMataPelajaran = (TableFixHeaders) rootView.findViewById(R.id.tableMataPelajaran);
		tableMataPelajaran.setVisibility(View.GONE);
		tv_raporSikap = (TextView) rootView.findViewById(R.id.tv_raporSikap);
		tv_raporSikap.setVisibility(View.GONE);
		tableSikap = (TableFixHeaders) rootView.findViewById(R.id.tableSikap);
		tableSikap.setVisibility(View.GONE);
		tv_raporEkskul = (TextView) rootView.findViewById(R.id.tv_raporEkskul);
		tv_raporEkskul.setVisibility(View.GONE);
		tableEkskul = (TableFixHeaders) rootView.findViewById(R.id.tableEkskul);
		tableEkskul.setVisibility(View.GONE);
		tv_raporKehadiran = (TextView) rootView.findViewById(R.id.tv_raporKehadiran);
		tv_raporKehadiran.setVisibility(View.GONE);
		tableKehadiran = (TableFixHeaders) rootView.findViewById(R.id.tableKehadiran);
		tableKehadiran.setVisibility(View.GONE);
		tv_raporSaranMid = (TextView) rootView.findViewById(R.id.tv_raporSaranMid);
		tv_raporSaranMid.setVisibility(View.GONE);
		tv_raporSaranMidDesc = (TextView) rootView.findViewById(R.id.tv_raporSaranMidDesc);
		tv_raporKehadiran.setVisibility(View.GONE);
		tv_raporSaranAkhir = (TextView) rootView.findViewById(R.id.tv_raporSaranAkhir);
		tv_raporSaranAkhir.setVisibility(View.GONE);
		tv_raporSaranAkhirDesc = (TextView) rootView.findViewById(R.id.tv_raporSaranAkhirDesc);
		tv_raporSaranAkhirDesc.setVisibility(View.GONE);

		spClass = (Spinner) rootView.findViewById(R.id.spin_class);
		spSemester = (Spinner) rootView.findViewById(R.id.spin_semester);


		btnShowMarks = (Button) rootView.findViewById(R.id.btn_show_marks);
		btnShowMarks.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadDataStudentMarkFromServer();
			}
		});
		
		return rootView;
	}

	private void loadDataStudentMarkFromServer() {
		if(spClass != null) {
			MuridKelas mk = (MuridKelas) spClass.getSelectedItem();
			final ProgressDialog dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Memuat nilai siswa...");
			dialog.setCancelable(false);
			dialog.show();
			
			muridKelasId = mk.Id;
			String smstr = (String) spSemester.getSelectedItem();
			
			// show Table Rapor
			ReqGetNilaiRapor reqRapor = new ReqGetNilaiRapor(String.valueOf(muridKelasId), smstr);
			MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
			client.getNilaiRapor(reqRapor, new Callback<ResponseGetNilaiRapor>() {
				@Override
				public void failure(RetrofitError err) {
					Log.e("cox","getNilaiRaport failure = " + err.getMessage());
					dialog.dismiss();
				}

				@Override
				public void success(ResponseGetNilaiRapor respRapor, Response resp) {
					Log.e("cox","getNilaiRapor success");
					tv_WaliKelas.setVisibility(View.VISIBLE);
					tv_WaliKelas.setText("Wali Kelas : " + respRapor.WaliKelas);
					
					if(respRapor.ListRaporMataPelajaran != null) {
						tv_raporMtPelajaran.setVisibility(View.VISIBLE);
						tableMataPelajaran.setVisibility(View.VISIBLE);
						tableMataPelajaran.setAdapter(new RaporMataPelajaranTableAdapter(getActivity(), respRapor.ListRaporMataPelajaran));
					}
					
					if(respRapor.ListRaporSikap != null) {
						tv_raporSikap.setVisibility(View.VISIBLE);
						tableSikap.setVisibility(View.VISIBLE);
						tableSikap.setAdapter(new RaporSikapTableAdapter(getActivity(), respRapor.ListRaporSikap));
					}
					
					if(respRapor.ListRaporEkstrakurikuler != null) {
						tv_raporEkskul.setVisibility(View.VISIBLE);
						tableEkskul.setVisibility(View.VISIBLE);
						tableEkskul.setAdapter(new RaporEkskulTableAdapter(getActivity(), respRapor.ListRaporEkstrakurikuler));
					}
					
					RaporPerkembangan perkembangan = respRapor.RaporPerkembangan;
					if(perkembangan != null && perkembangan.PerkembanganFisikKehadiranPrestasi != null) {
						PerkembanganFisikKehadiranPrestasi fisikKehadiran = perkembangan.PerkembanganFisikKehadiranPrestasi;
						if(fisikKehadiran != null && fisikKehadiran.Kehadiran != null) {
							tv_raporKehadiran.setVisibility(View.VISIBLE);
							tableKehadiran.setVisibility(View.VISIBLE);
							tableKehadiran.setAdapter(new RaporKehadiranTableAdapter(getActivity(), fisikKehadiran.Kehadiran));
						}
						
						String saran = "";
						if(fisikKehadiran != null && fisikKehadiran.Saran != null) {
							tv_raporSaranMid.setVisibility(View.VISIBLE);
							tv_raporSaranMidDesc.setVisibility(View.VISIBLE);
							saran = fisikKehadiran.Saran.SaranMid;
							tv_raporSaranMidDesc.setText(saran == null || saran.isEmpty() ? "-Tidak Ada-" : saran);
						}
						if(fisikKehadiran != null && fisikKehadiran.Saran != null) {
							tv_raporSaranAkhir.setVisibility(View.VISIBLE);
							tv_raporSaranAkhirDesc.setVisibility(View.VISIBLE);
							saran = fisikKehadiran.Saran.SaranAkhir;
							tv_raporSaranAkhirDesc.setText(saran.isEmpty() ? "- Tidak Ada-" : saran);
						}
					}
					
					dialog.dismiss();
				}
				
			});
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		doGetClassAndAspect();
	}

	private void doGetClassAndAspect() {
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage("Memuat data...");
		dialog.setCancelable(false);
		dialog.show();
		
		ReqUserClassAspect user = new ReqUserClassAspect(userName, mutasiId);
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
		client.getClassAndAspect(user, new Callback<ClassAndAspect>() {
			@Override
			public void success(ClassAndAspect classAndAspect, Response arg1) {
				dialog.dismiss();
				listMuridKelas = classAndAspect.ListMuridKelas;
				loadDataToSpinner();
			}
			@Override
			public void failure(RetrofitError arg0) {
				dialog.dismiss();
				Log.e("cox", "getClassAndAspect failure = " + arg0.getMessage());
			}
		});
	}
	
	private void loadDataToSpinner() {
		listSemester.add("1");
		listSemester.add("2");
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listSemester);
		spSemester.setOnItemSelectedListener(this);
		spSemester.setAdapter(spninnerAdapter);

		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listMuridKelas);
		spClass.setOnItemSelectedListener(this);
		spClass.setAdapter(spninnerAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}
