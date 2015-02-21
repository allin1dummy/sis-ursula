package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.adapter.KeterampilanSimpleTabPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AspekPengetahuanFragment extends Fragment {
	private View root;
	private ViewPager viewPager;
	private boolean hasInitializedRootView = false;
	
	
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
