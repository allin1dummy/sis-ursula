package com.cox.work.sis.ursula;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.adapter.StudentMarkTableAdapter;
import com.cox.work.sis.ursula.model.DataNilaiTableAdapter;
import com.cox.work.sis.ursula.model.NilaiDanTanggal;
import com.cox.work.sis.ursula.model.json.AspekPenilaian;
import com.cox.work.sis.ursula.model.json.ClassAndAspect;
import com.cox.work.sis.ursula.model.json.JenisNilai;
import com.cox.work.sis.ursula.model.json.MuridKelas;
import com.cox.work.sis.ursula.model.json.Nilai;
import com.cox.work.sis.ursula.model.json.NilaiDetilNonRubrik;
import com.cox.work.sis.ursula.model.json.ReqGetNilai;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseGetNilai;
import com.cox.work.sis.ursula.util.Util;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnItemSelectedListener {
	
	private Spinner spClass, spAspect, spCategory, spSemester;
	private List<AspekPenilaian> listAspekPenilaian;
	private List<JenisNilai> listJenisNilai;
	private List<MuridKelas> listMuridKelas;
	private List<Nilai> listNilai;
	private String userName, namaSiswa, mutasiId;
	private int muridKelasId = -1;
	private int selAspek;
	private boolean isSemesterChanged, isClassChanged, isAspectChanged;
	private TextView tv_NamaSiswa, tv_WaliKelas;
	private ImageButton btnShowFilter;
	private Button btnShowMarks;
	private LinearLayout llFilterNilai;
	private boolean isFilterShow = true;
	private int counterMaxNilaiKe = 0;

	List<String> listSemester = new ArrayList<String>();
	List<String> listAspek = new ArrayList<String>();
	List<String> listKelas = new ArrayList<String>();
	List<String> listKategori = new ArrayList<String>();

	private ArrayAdapter spninnerAdapter;
	
	private ArrayList<DataNilaiTableAdapter> listNilaiSiswa = new ArrayList<DataNilaiTableAdapter>();

	private TableFixHeaders tableFixHeaders;
	
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		userName = getArguments().getString(Util.Constant.USERNAME);
		namaSiswa = getArguments().getString(Util.Constant.NAMASISWA);
		mutasiId = getArguments().getString(Util.Constant.MUTASIID);
		
		tv_NamaSiswa = (TextView) rootView.findViewById(R.id.tv_name);
		tv_NamaSiswa.setText("Nama: " + namaSiswa);
		//tv_ShowFilter = (TextView) rootView.findViewById(R.id.tv_show_filter);
		tv_WaliKelas = (TextView) rootView.findViewById(R.id.tv_wali);
		
		tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.table);
		tableFixHeaders.setVisibility(View.GONE);
		
		spClass = (Spinner) rootView.findViewById(R.id.spin_class);
		spAspect = (Spinner) rootView.findViewById(R.id.spin_aspect);
		spCategory = (Spinner) rootView.findViewById(R.id.spin_aspect_category);
		spSemester = (Spinner) rootView.findViewById(R.id.spin_semester);

		btnShowMarks = (Button) rootView.findViewById(R.id.btn_show_marks);
		btnShowMarks.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listNilaiSiswa = new ArrayList<DataNilaiTableAdapter>(); // reset list nilai
				if(isNeedToLoadFromServer()) {
					loadDataStudentMarkFromServer();
				} else {
					loadDataStudentMarkFromLocal();
				}
			}
		});
		
		llFilterNilai = (LinearLayout) rootView.findViewById(R.id.layout_filter_nilai);
		
		btnShowFilter = (ImageButton) rootView.findViewById(R.id.btn_show_filter);
		btnShowFilter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Drawable img;
				if(isFilterShow) {
					btnShowMarks.setVisibility(View.GONE);
					llFilterNilai.setVisibility(View.GONE);
					img = getResources().getDrawable(R.drawable.ic_action_arrow_bottom);
				} else {
					btnShowMarks.setVisibility(View.VISIBLE);
					llFilterNilai.setVisibility(View.VISIBLE);
					img = getResources().getDrawable(R.drawable.ic_action_arrow_top);
				}
				btnShowFilter.setImageDrawable(img);
				isFilterShow = !isFilterShow;
				tableFixHeaders.setAdapter(new StudentMarkTableAdapter(getActivity(), listNilaiSiswa, counterMaxNilaiKe));
			}
		});
		
		return rootView;
	}
	
	protected void loadDataStudentMarkFromLocal() {
		JenisNilai jn = (JenisNilai) spCategory.getSelectedItem();
		calculateNilai(jn.Id);
	}

	protected boolean isNeedToLoadFromServer() {
		if(isClassChanged || isAspectChanged || isSemesterChanged) {
			return true;
		} else {
			return false;
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
				listAspekPenilaian = classAndAspect.ListAspekPenilaian;
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
	
	private void loadDataStudentMarkFromServer() {
		if(spClass != null) {
			MuridKelas mk = (MuridKelas) spClass.getSelectedItem();
			final ProgressDialog dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Memuat nilai siswa...");
			dialog.setCancelable(false);
			dialog.show();
			
			muridKelasId = mk.Id;
			final AspekPenilaian ap = (AspekPenilaian) spAspect.getSelectedItem();
			String smstr = (String) spSemester.getSelectedItem();
			final JenisNilai jn = (JenisNilai) spCategory.getSelectedItem();

			ReqGetNilai req = new ReqGetNilai(String.valueOf(muridKelasId), String.valueOf(ap.Id), smstr);
			MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
			client.getNilai(req, new Callback<ResponseGetNilai>() {
				@Override
				public void success(ResponseGetNilai resp, Response arg1) {
					dialog.dismiss();
					listNilai = resp.ListNilai;
					if(resp.WaliKelas != null && !resp.WaliKelas.isEmpty()) {
						tv_WaliKelas.setVisibility(View.VISIBLE);
						tv_WaliKelas.setText("Wali Kelas: " + resp.WaliKelas);
					} else {
						tv_WaliKelas.setVisibility(View.GONE);
					}
					calculateNilai(jn.Id);
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					Log.e("cox","getNilai failure = " + arg0.getMessage());
					dialog.dismiss();
				}
			});
		}
		
		isClassChanged = isAspectChanged = isSemesterChanged = false;
	}


	private void calculateNilai(int id) {
		int counterNilai = 0; // flag to know whether there is at least a Nilai for a Mata Pelajaran
		counterMaxNilaiKe = 0;
		for(Nilai nilai : listNilai) {
			int latestNilaiKe = -1, idxNilaiKe = -1;
			NilaiDanTanggal listDataNilai[] = new NilaiDanTanggal[Util.Constant.MAX_TOTAL_NILAI]; // set directly max total nilai == 100
			float nilaiRataRata = -1;
			for(NilaiDetilNonRubrik detilNonRubrik : nilai.ListNilaiDetilNonRubrik) {
				if(id == detilNonRubrik.JenisNilai.Id) {
					counterNilai++;
					String tglTest = "";
					if(detilNonRubrik.TanggalTes != null) {
						tglTest = detilNonRubrik.TanggalTes.replace("/Date(", "").replace(")/","");
						tglTest = Util.convertLongToDate(tglTest.split("-")[0], new SimpleDateFormat("dd-MMM-yyyy"));
					} else {
						tglTest = "";
					}
					
					idxNilaiKe = detilNonRubrik.NilaiKe - 1;
					nilaiRataRata = detilNonRubrik.RataRata;
					NilaiDanTanggal dataNilai = new NilaiDanTanggal(detilNonRubrik.NilaiAngka, tglTest, detilNonRubrik.IsRemidi, idxNilaiKe);
					listDataNilai[idxNilaiKe] = dataNilai; // set dataNilai according on NilaiKe INDEX which zero-based index
					
					if(idxNilaiKe > latestNilaiKe) { // find highest NilaiKe INDEX
						latestNilaiKe = idxNilaiKe;
					}
				}
			}
			if(latestNilaiKe > counterMaxNilaiKe) {
				counterMaxNilaiKe = latestNilaiKe;
			}
			DataNilaiTableAdapter dtNilaiAdapter = new DataNilaiTableAdapter(nilai.Id, nilai.MataPelajaran.Nama);
			dtNilaiAdapter.setListNilai(listDataNilai);
			dtNilaiAdapter.setLatestNilaiKe(latestNilaiKe);
			dtNilaiAdapter.setRataRata(nilaiRataRata);
			listNilaiSiswa.add(dtNilaiAdapter);
		}

		//showLog4ListNilai();
		
		if(listNilaiSiswa == null || listNilaiSiswa.size() == 0 || counterNilai == 0) {
			tableFixHeaders.setVisibility(View.GONE);
			
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setTitle("Nilai Siswa");
			alert.setMessage("List nilai tidak tersedia.")
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.dismiss();
					}
				})
				.show();
		} else {
			tableFixHeaders.setVisibility(View.VISIBLE);
			tableFixHeaders.setAdapter(new StudentMarkTableAdapter(getActivity(), listNilaiSiswa, counterMaxNilaiKe));
		}
	}

	private void showLog4ListNilai() {
		for(DataNilaiTableAdapter data : listNilaiSiswa) {
			Log.e("cox","getNilai MataPelajaran = " + data.getMataPelajaran());
			for(int i = 0; i < data.getLatestNilaiKe(); i++) {
				//Log.e("cox","getNilai Nilai = " + data.getListNilai()[i].getNilaiAngka());
				//Log.e("cox","getNilai Tanggal = " + data.getListNilai()[i].getTanggal());
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadDataToSpinner() {
		listSemester.add("1");
		listSemester.add("2");
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listSemester);
		spSemester.setOnItemSelectedListener(this);
		spSemester.setAdapter(spninnerAdapter);
		
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listAspekPenilaian);
		spAspect.setOnItemSelectedListener(this);
		spAspect.setAdapter(spninnerAdapter);

		listJenisNilai = listAspekPenilaian.get((int)selAspek).ListJenisNilai;
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listJenisNilai);
		spCategory.setAdapter(spninnerAdapter);
		spCategory.setSelection(0);
		
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listMuridKelas);
		spClass.setOnItemSelectedListener(this);
		spClass.setAdapter(spninnerAdapter);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.spin_semester:
			isSemesterChanged = true;
			break;
		case R.id.spin_class:
			isClassChanged = true;
			break;
		case R.id.spin_aspect:
			isAspectChanged = true;
			selAspek = arg2;
			listJenisNilai = listAspekPenilaian.get(arg2).ListJenisNilai;
			spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listJenisNilai);
			spCategory.setAdapter(spninnerAdapter);
			spCategory.setSelection(0);
			break;
		case R.id.spin_aspect_category:
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
