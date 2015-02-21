package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.adapter.KeterampilanSimpleTabPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AspekKeterampilanFragment extends Fragment {
	private View root = null;
	private KeterampilanSimpleTabPagerAdapter tabPagerAdapter;
	private ViewPager viewPager;

	public boolean hasInitializedRootView = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		tabPagerAdapter = new KeterampilanSimpleTabPagerAdapter(getActivity().getSupportFragmentManager());
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return getPersistentView(inflater, container, savedInstanceState, R.layout.keterampilan_pager);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (!hasInitializedRootView) {
			hasInitializedRootView = true;
			// Do initial setup of UI
		}
	}
	

	public View getPersistentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layout) {
		if (root == null) {
			// Inflate the layout for this fragment
			root = inflater.inflate(layout, container, false);
			viewPager = (ViewPager) root.findViewById(R.id.pager);
			viewPager.setAdapter(tabPagerAdapter);
		} else {
			// Do not inflate the layout again.
			// The returned View of onCreateView will be added into the
			// fragment.
			// However it is not allowed to be added twice even if the
			// parent is same.
			// So we must remove rootView from the existing parent view
			// group
			// (it will be added back).
			((ViewGroup) root.getParent()).removeView(root);
		}

		return root;
	}
}
