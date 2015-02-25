package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.adapter.PengetahuanTabPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AspekPengetahuanFragment extends Fragment {
	private View root;
	private ViewPager viewPager;
	private PengetahuanTabPagerAdapter tabPagerAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		tabPagerAdapter = new PengetahuanTabPagerAdapter(getActivity().getSupportFragmentManager());
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return getPersistentView(inflater, container, savedInstanceState, R.layout.pengetahuan_layout);
	}
	
	public View getPersistentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layout) {
		if (root == null) {
			// Inflate the layout for this fragment
			root = inflater.inflate(layout, container, false);
			viewPager = (ViewPager) root.findViewById(R.id.pager);
			viewPager.setAdapter(tabPagerAdapter);
		} else {
			((ViewGroup) root.getParent()).removeView(root);
		}

		return root;
	}
}
