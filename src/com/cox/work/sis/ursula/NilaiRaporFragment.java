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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class NilaiRaporFragment extends Fragment implements OnItemSelectedListener{
	private View rootView;
	private LinearLayout ll_listNilaiRapor;
	private TextView tv_WaliKelas
					, tv_raporSaranMidDesc, tv_raporSaranAkhirDesc
					, tv_raporSikapDasar, tv_raporSikapInti, tv_raporSikapNilai;
	private TableFixHeaders tableMataPelajaran, tableEkskul, tableKehadiran;
	private String namaSiswa;
	private Spinner spClass;
	private Spinner spSemester;
	private Button btnShowMarks;
	private ArrayAdapter spninnerAdapter;
	private String userName;
	private String mutasiId;
	private int muridKelasId = -1;
	private List<MuridKelas> listMuridKelas = new ArrayList<MuridKelas>();
	private List<String> listSemester;

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

		ll_listNilaiRapor = (LinearLayout) rootView.findViewById(R.id.list_nilai_rapor);
		tv_WaliKelas = (TextView) rootView.findViewById(R.id.tv_wali);
		
		tv_raporSikapDasar = (TextView) rootView.findViewById(R.id.tv_raporSikapDasar);
		tv_raporSikapInti = (TextView) rootView.findViewById(R.id.tv_raporSikapInti);
		tv_raporSikapNilai = (TextView) rootView.findViewById(R.id.tv_raporSikapNilai);
		
		tableMataPelajaran = (TableFixHeaders) rootView.findViewById(R.id.tableMataPelajaran);
		tableMataPelajaran.setVisibility(View.GONE);
		tableEkskul = (TableFixHeaders) rootView.findViewById(R.id.tableEkskul);
		tableEkskul.setVisibility(View.GONE);
		tableKehadiran = (TableFixHeaders) rootView.findViewById(R.id.tableKehadiran);
		tableKehadiran.setVisibility(View.GONE);
		tv_raporSaranMidDesc = (TextView) rootView.findViewById(R.id.tv_raporSaranMidDesc);
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
					dialog.dismiss();
					
					if(respRapor.ListRaporMataPelajaran == null) {
						ll_listNilaiRapor.setVisibility(View.GONE);
						tv_WaliKelas.setVisibility(View.GONE);
						Util.CommonDialog.show(getActivity(), "Nilai Rapor", "Nilai Rapor Tidak Tersedia.");
						return;
					}

					ll_listNilaiRapor.setVisibility(View.VISIBLE);
					tv_WaliKelas.setVisibility(View.VISIBLE);
					tv_WaliKelas.setText("Wali Kelas : " + respRapor.WaliKelas);
					
					if(respRapor.ListRaporSikap != null) {
						tv_raporSikapDasar.setText("- Kompetensi Dasar : " + respRapor.ListRaporSikap.get(0).KompentensiDasar.Keterangan);
						tv_raporSikapInti.setText("- Kompetensi Inti : " + respRapor.ListRaporSikap.get(0).KompentensiInti.Keterangan);
						tv_raporSikapNilai.setText("- Nilai : " + respRapor.ListRaporSikap.get(0).Nilai.Deskripsi);
					}
					
					if(respRapor.ListRaporMataPelajaran != null) {
						tableMataPelajaran.setVisibility(View.VISIBLE);
						tableMataPelajaran.setAdapter(new RaporMataPelajaranTableAdapter(getActivity(), respRapor.ListRaporMataPelajaran));
					}
					
					if(respRapor.ListRaporEkstrakurikuler != null) {
						tableEkskul.setVisibility(View.VISIBLE);
						tableEkskul.setAdapter(new RaporEkskulTableAdapter(getActivity(), respRapor.ListRaporEkstrakurikuler));
					}
					
					RaporPerkembangan perkembangan = respRapor.RaporPerkembangan;
					if(perkembangan != null && perkembangan.PerkembanganFisikKehadiranPrestasi != null) {
						PerkembanganFisikKehadiranPrestasi fisikKehadiran = perkembangan.PerkembanganFisikKehadiranPrestasi;
						if(fisikKehadiran != null && fisikKehadiran.Kehadiran != null) {
							tableKehadiran.setVisibility(View.VISIBLE);
							tableKehadiran.setAdapter(new RaporKehadiranTableAdapter(getActivity(), fisikKehadiran.Kehadiran));
						}
						
						String saran = "";
						if(fisikKehadiran != null && fisikKehadiran.Saran != null) {
							tv_raporSaranMidDesc.setVisibility(View.VISIBLE);
							saran = fisikKehadiran.Saran.SaranMid;
							tv_raporSaranMidDesc.setText(saran == null || saran.isEmpty() ? "-Tidak Ada-" : saran);
						}
						if(fisikKehadiran != null && fisikKehadiran.Saran != null) {
							tv_raporSaranAkhirDesc.setVisibility(View.VISIBLE);
							saran = fisikKehadiran.Saran.SaranAkhir;
							tv_raporSaranAkhirDesc.setText(saran.isEmpty() ? "- Tidak Ada-" : saran);
						}
					}
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
		listSemester  = new ArrayList<String>();
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
