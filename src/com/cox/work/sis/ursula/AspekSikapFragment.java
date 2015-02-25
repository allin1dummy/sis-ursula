package com.cox.work.sis.ursula;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cox.work.sis.ursula.adapter.SikapTabPagerAdapter;

public class AspekSikapFragment extends Fragment {
	private View root;
	private SikapTabPagerAdapter tabPagerAdapter;
	private ViewPager viewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		tabPagerAdapter = new SikapTabPagerAdapter(getActivity().getSupportFragmentManager());
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return getPersistentView(inflater, container, savedInstanceState, R.layout.sikap_layout);
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
