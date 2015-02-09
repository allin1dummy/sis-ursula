package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.adapter.KeterampilanSimpleTabPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AspekKeterampilanFragment extends Fragment implements ActionBar.TabListener{
	private View root;
	private ActionBar actionBar;
	private KeterampilanSimpleTabPagerAdapter tabPagerAdapter;
	private android.support.v4.view.ViewPager viewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.keterampilan_pager, container, false);
		viewPager = (ViewPager) root.findViewById(R.id.pager);
		tabPagerAdapter = new KeterampilanSimpleTabPagerAdapter(getActivity().getSupportFragmentManager());
		
    	initView();

		return root;
	}

	private void initView() {
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
    	actionBar.removeAllTabs();
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
}
