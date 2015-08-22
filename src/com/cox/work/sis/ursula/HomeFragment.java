package com.cox.work.sis.ursula;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.adapter.StudentMarkTableAdapter;
import com.cox.work.sis.ursula.model.json.AspekPenilaian;
import com.cox.work.sis.ursula.model.json.ClassAndAspect;
import com.cox.work.sis.ursula.model.json.JenisNilai;
import com.cox.work.sis.ursula.model.json.MuridKelas;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.util.Util;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnItemSelectedListener {
	
	private Spinner spClass, spAspect, spCategory, spSemester, spTahun;
	private List<AspekPenilaian> listAspekPenilaian;
	private List<JenisNilai> listJenisNilai;
	private List<MuridKelas> listMuridKelas;
	private int selAspek = 0, selJenis = 0, selTahun = 0;
	private String selKelas = "";
	private String userName, namaSiswa;
	private TextView tv_NamaSiswa;
	private Button btnShowMarks;

	ArrayAdapter<String> dataAdapter;
	List<String> listSemester = new ArrayList<String>();
	List<String> listAspek = new ArrayList<String>();
	List<String> listKelas = new ArrayList<String>();
	List<String> listTahun = new ArrayList<String>();
	List<String> listKategori = new ArrayList<String>();
	

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		userName = getArguments().getString(Util.Constant.USERNAME);
		namaSiswa = getArguments().getString(Util.Constant.NAMASISWA);
		
		tv_NamaSiswa = (TextView) rootView.findViewById(R.id.tv_name);
		tv_NamaSiswa.setText("Nama: " + namaSiswa);
		
		final TableFixHeaders tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.table);
		tableFixHeaders.setAdapter(new StudentMarkTableAdapter(getActivity(), loadDataStudentMark()));
		tableFixHeaders.setVisibility(View.GONE);
		
		spClass = (Spinner) rootView.findViewById(R.id.spin_class);
		spAspect = (Spinner) rootView.findViewById(R.id.spin_aspect);
		spCategory = (Spinner) rootView.findViewById(R.id.spin_aspect_category);
		spTahun = (Spinner) rootView.findViewById(R.id.spin_tahun);
		spSemester = (Spinner) rootView.findViewById(R.id.spin_semester);

		btnShowMarks = (Button) rootView.findViewById(R.id.btn_show_marks);
		btnShowMarks.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tableFixHeaders.setVisibility(View.VISIBLE);
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		doGetClassAndAspect();
	}
	
	private void doGetClassAndAspect() {
		ReqUserClassAspect user = new ReqUserClassAspect("andrea.sutanto", "400");
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
		client.getClassAndAspect(user, new Callback<ClassAndAspect>() {
			@Override
			public void success(ClassAndAspect classAndAspect, Response arg1) {
				listAspekPenilaian = classAndAspect.ListAspekPenilaian;
				listMuridKelas = classAndAspect.ListMuridKelas;
				loadDataToSpinner();
				Log.e("cox", "success user = " + classAndAspect.ListAspekPenilaian.get(0).Nama);
			}
			@Override
			public void failure(RetrofitError arg0) {
				Log.e("cox", "failure = " + arg0.getMessage());
			}
		});
	}
	
	private float[][] loadDataStudentMark() {
		// this is dummy data
		int rows = Util.Properties.NUM_SUBJECTS;
		int cols = Util.Properties.NUM_WEEKS;
		float tot = 0f;
		float[][] result = new float[rows][cols + 1]; // add 1 column to insert means value each Subject
		for(int i = 0; i < result.length; i ++) {
			tot = 0f;
			for(int j = 0; j < result[0].length; j ++) {
				if(j < result.length - 1) {
					result[i][j] = (float) (Math.random() * 10);
					tot = tot + result[i][j];
				} else {
					result[i][j] = tot / Util.Properties.NUM_WEEKS;
				}
			}
		}
		return result;
	}

	private void loadDataToSpinner() {
		listSemester.add("-Pilih Semester-");
		listSemester.add("1");
		listSemester.add("2");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSemester);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSemester.setOnItemSelectedListener(this);
		spSemester.setAdapter(dataAdapter);
		
		listAspek.add("-Pilih Aspek-");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listAspek);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for(int i = 0; i < listAspekPenilaian.size(); i ++) {
			listAspek.add(listAspekPenilaian.get(i).Nama);
		}
		spAspect.setOnItemSelectedListener(this);
		spAspect.setAdapter(dataAdapter);
		
		listKategori.add("-Pilih Kategori-");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listKategori);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategory.setOnItemSelectedListener(this);
		spCategory.setAdapter(dataAdapter);

		listKelas.add("-Pilih Kelas-");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listKelas);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for(int i = 0; i < listMuridKelas.size(); i ++) {
			listKelas.add(listMuridKelas.get(i).KelasDisplayMember);
		}
		spClass.setOnItemSelectedListener(this);
		spClass.setAdapter(dataAdapter);
		
		listTahun.add("-Pilih Tahun-");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listTahun);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spTahun.setAdapter(dataAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.spin_class:
			selKelas = arg0.getItemAtPosition(arg2).toString();
			// get tahun pelajaran
			listTahun = new ArrayList<String>();
			listTahun.add("- Pilih Tahun -");
			for(int i = 0; i < listMuridKelas.size(); i ++) {
				if(selKelas.equalsIgnoreCase(listMuridKelas.get(i).KelasDisplayMember)) {
					listTahun.add(listMuridKelas.get(i).TahunPelajaranDisplayMember);
				}
			}
			dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listTahun);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spTahun.setAdapter(dataAdapter);
			break;

		case R.id.spin_aspect:
			selAspek = arg2;
			if (selAspek != 0) {
				listJenisNilai = listAspekPenilaian.get(selAspek - 1).ListJenisNilai;
				listKategori = new ArrayList<String>();
				listKategori.add("- Pilih Kategori -");
				for(int i = 0; i < listJenisNilai.size(); i ++) {
					listKategori.add(listJenisNilai.get(i).Nama);
				}
				dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listKategori);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spCategory.setAdapter(dataAdapter);
			}
			spCategory.setSelection(0);
		
			break;
			
		case R.id.spin_aspect_category:
			selJenis = arg2;
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
