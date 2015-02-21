package com.cox.work.sis.ursula;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AspekSikapFragment extends Fragment {
	private View root;
	private ArrayList<View> buttonList;
	private TextView btnDetail1;
	private TextView btnDetail2;
	private TextView btnDetail3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return root;
	}

}
