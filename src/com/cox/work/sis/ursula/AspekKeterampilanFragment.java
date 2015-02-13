package com.cox.work.sis.ursula;

import java.lang.reflect.Field;

import com.cox.work.sis.ursula.adapter.KeterampilanSimpleTabPagerAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AspekKeterampilanFragment extends Fragment implements ActionBar.TabListener{
	private View root = null;
	private ActionBar actionBar;
	private KeterampilanSimpleTabPagerAdapter tabPagerAdapter;
	private ViewPager viewPager;

	public boolean hasInitializedRootView = false;
	
	private static final Field sChildFragmentManagerField;
	
	static {
		Field f = null;
		try {
			f = Fragment.class.getDeclaredField("mChildFragmentManager");
			f.setAccessible(true);
		} catch (NoSuchFieldException e) {
			Log.e("SISUR", "Error getting mChildFragmentManager field", e);
		}
		sChildFragmentManagerField = f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
			initView();
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		if (sChildFragmentManagerField != null) {
            try {
            	sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Log.e("SISUR", "Error setting mChildFragmentManager field", e);
            }
        }
	}

	private void initView() {
		tabPagerAdapter = new KeterampilanSimpleTabPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(tabPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});

    	actionBar = getActivity().getActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (int i = 0; i < tabPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(tabPagerAdapter.getPageTitle(i))					
					.setTabListener(this));
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
    	actionBar.removeAllTabs();
	}
	
	
	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction arg1) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	public View getPersistentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layout) {
		if (root == null) {
			// Inflate the layout for this fragment
			root = inflater.inflate(layout, container, false);
			viewPager = (ViewPager) root.findViewById(R.id.pager);
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
