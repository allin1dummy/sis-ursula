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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	private Spinner spClassSmst;
	private Spinner spAspect, spCategory;
	private List<MuridKelas> listMuridKelas;
	private List<AspekPenilaian> listAspekPenilain;
	private String userName;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		userName = getArguments().getString(Util.Constant.USERNAME);
		
		TableFixHeaders tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.table);
		tableFixHeaders.setAdapter(new StudentMarkTableAdapter(getActivity(), loadDataStudentMark()));
		
		spClassSmst = (Spinner) rootView.findViewById(R.id.spin_class_smst);
		spAspect = (Spinner) rootView.findViewById(R.id.spin_aspect);
		spCategory = (Spinner) rootView.findViewById(R.id.spin_aspect_category);

		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		doGetClassAndAspect();
	}
	
	private void doGetClassAndAspect() {
		ReqUserClassAspect user = new ReqUserClassAspect(userName, "400");
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MASTER_STG);
		client.getClassAndAspect(user, new Callback<ClassAndAspect>() {
			@Override
			public void success(ClassAndAspect classAndAspect, Response arg1) {
				listMuridKelas = classAndAspect.ListMuridKelas;
				listAspekPenilain = classAndAspect.ListAspekPenilain;
				loadDataToSpinner();
				Log.e("cox", "success user = " + classAndAspect.ListAspekPenilain.get(0).Nama);
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
		List<String> list = new ArrayList<String>();
		list.add("- Silakan Pilih -");
		for(int i = 0; i < listMuridKelas.size(); i ++) {
			list.add(listMuridKelas.get(i).TahunPelajaranDisplayMember);
		}
//		list.add("1");
//		list.add("2");
//		list.add("3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spClassSmst.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(getActivity(), "Item #" + arg2, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		spClassSmst.setAdapter(dataAdapter);
		
		list = new ArrayList<String>();
		list.add("- Silakan Pilih -");
		for(int i = 0; i < listAspekPenilain.size(); i ++) {
			list.add(listAspekPenilain.get(i).Nama);
		}
//		list.add("Pengetahuan");
//		list.add("Keterampilan");
//		list.add("Sikap");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spAspect.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(getActivity(), "Item #" + arg2, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		spAspect.setAdapter(dataAdapter);
		
		list = new ArrayList<String>();
		list.add("- Silakan Pilih -");
		list.add("Tertulis");
		list.add("Lisan");
		list.add("Tugas");
		list.add("Unjuk Kerja");
		list.add("Demonstrasi");
		list.add("Proyek");
		list.add("Portfolio");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(getActivity(), "Item #" + arg2, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		spCategory.setAdapter(dataAdapter);
	}
}
