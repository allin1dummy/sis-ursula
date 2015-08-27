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
import com.cox.work.sis.ursula.model.json.Nilai;
import com.cox.work.sis.ursula.model.json.ReqGetNilai;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseGetNilai;
import com.cox.work.sis.ursula.util.Util;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.app.ProgressDialog;
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
	private List<Nilai> listNilai;
	private int selAspek = 0, selJenis = 0, selTahun = 0, selSemester = 0;
	private String strSelAspek = "", strSelJenis = "", strSelTahun = "";
	private String selKelas = "";
	private String userName, namaSiswa;
	private int muridKelasId = -1;
	private TextView tv_NamaSiswa;
	private Button btnShowMarks;

	ArrayAdapter<String> dataAdapter;
	List<String> listSemester = new ArrayList<String>();
	List<String> listAspek = new ArrayList<String>();
	List<String> listKelas = new ArrayList<String>();
	List<String> listKategori = new ArrayList<String>();

	ArrayAdapter spninnerAdapter;
	
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
		
		tv_NamaSiswa = (TextView) rootView.findViewById(R.id.tv_name);
		tv_NamaSiswa.setText("Nama: " + namaSiswa);
		
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
				showStudentMarks();
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		doGetClassAndAspect();
	}

	private void showStudentMarks() {
		tableFixHeaders.setVisibility(View.VISIBLE);
		tableFixHeaders.setAdapter(new StudentMarkTableAdapter(getActivity(), loadDataStudentMark()));
		if(listNilai != null && listNilai.size() > 0) {
			//listNilai.get(0).
		}
	}
	
	private void doGetClassAndAspect() {
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage("Memuat data...");
		dialog.setCancelable(false);
		dialog.show();
		
		ReqUserClassAspect user = new ReqUserClassAspect("andrea.sutanto", "400");
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
				Log.e("cox", "failure = " + arg0.getMessage());
			}
		});
	}
	
	private float[][] loadDataStudentMark() {
		if(spClass != null) {
			MuridKelas mk = (MuridKelas) spClass.getSelectedItem();
			final ProgressDialog dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Memuat nilai siswa...");
			dialog.setCancelable(false);
			dialog.show();
			
			muridKelasId = mk.Id;
			ReqGetNilai req = new ReqGetNilai(String.valueOf(muridKelasId), "2", String.valueOf(selSemester));
			MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
			client.getNilai(req, new Callback<ResponseGetNilai>() {
				@Override
				public void success(ResponseGetNilai resp, Response arg1) {
					dialog.dismiss();
					//listNilai = resp.ListNilai;
				}
				@Override
				public void failure(RetrofitError arg0) {
					dialog.dismiss();
				}
			});
		
			
			
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
		} else return null;
	}

	private void loadDataToSpinner() {
		listSemester.add("1");
		listSemester.add("2");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSemester);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSemester.setOnItemSelectedListener(this);
		spSemester.setAdapter(dataAdapter);
		
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listAspekPenilaian);
		spAspect.setOnItemSelectedListener(this);
		spAspect.setAdapter(spninnerAdapter);

		listJenisNilai = listAspekPenilaian.get(selAspek).ListJenisNilai;
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listJenisNilai);
		spCategory.setAdapter(spninnerAdapter);
		spCategory.setSelection(0);
		
		spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listMuridKelas);
		spClass.setOnItemSelectedListener(this);
		spClass.setAdapter(spninnerAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.spin_aspect:
			selAspek = arg2;
			listJenisNilai = listAspekPenilaian.get(selAspek).ListJenisNilai;
			spninnerAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner, listJenisNilai);
			spCategory.setAdapter(spninnerAdapter);
			spCategory.setSelection(0);
			break;
		case R.id.spin_aspect_category:
			selJenis = arg2;
			strSelJenis = arg0.getItemAtPosition(selJenis).toString();
			break;
		case R.id.spin_semester:
			selSemester = arg2;
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
