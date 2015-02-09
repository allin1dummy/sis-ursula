package com.cox.work.sis.ursula;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class KeterampilanTulisFragment extends Fragment implements OnClickListener {
	private View root;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.keterampilan_tulis_layout, container, false);
		
		return root;
	}

	@Override
	public void onClick(View v) {
		Log.e("cox", "onClick - id = " + v.getId());
	}

}
