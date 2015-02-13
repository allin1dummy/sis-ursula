package com.cox.work.sis.ursula;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.cox.work.sis.ursula.adapter.KeterampilanSimpleTabPagerAdapter;
import com.cox.work.sis.ursula.adapter.SimpleTabPagerAdapter;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AspekPengetahuanFragment extends Fragment implements OnClickListener, ActionBar.TabListener{
	private View root;
	private ViewPager viewPager;
	private KeterampilanSimpleTabPagerAdapter tabPagerAdapter;
	
	private ArrayList<View> buttonList;
	private TextView btnDetail1;
	private TextView btnDetail2;
	private TextView btnDetail3;
	private TextView btnDetail4;
	private TextView btnDetail5;
	private TextView btnDetail6;
	private TextView btnDetail7;
	private ActionBar actionBar;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
    	actionBar.removeAllTabs();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		root =  inflater.inflate(R.layout.pengetahuan_layout, container, false);
		viewPager = (ViewPager) root.findViewById(R.id.pager);
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
		
//		btnDetail1 = (TextView) root.findViewById(R.id.tv_pengetahuan_tulis);
//    	btnDetail1.setOnClickListener(this);
//    	btnDetail2 = (TextView) root.findViewById(R.id.tv_pengetahuan_lisan);
//    	btnDetail2.setOnClickListener(this);
//    	btnDetail3 = (TextView) root.findViewById(R.id.tv_pengetahuan_tugas);
//    	btnDetail3.setOnClickListener(this);
//		btnDetail4 = (TextView) root.findViewById(R.id.tv_pengetahuan_kerja);
//    	btnDetail4.setOnClickListener(this);
//    	btnDetail5 = (TextView) root.findViewById(R.id.tv_pengetahuan_demonstrasi);
//    	btnDetail5.setOnClickListener(this);
//    	btnDetail6 = (TextView) root.findViewById(R.id.tv_pengetahuan_proyek);
//    	btnDetail6.setOnClickListener(this);
//		btnDetail7 = (TextView) root.findViewById(R.id.tv_pengetahuan_portofolio);
//    	btnDetail7.setOnClickListener(this);
//    	
//    	buttonList = new ArrayList<View>();
//    	buttonList.add(btnDetail1);
//    	buttonList.add(btnDetail2);
//    	buttonList.add(btnDetail3);
//    	buttonList.add(btnDetail4);
//    	buttonList.add(btnDetail5);
//    	buttonList.add(btnDetail6);
//    	buttonList.add(btnDetail7);
    	
		
//	    LineSet lineSet = new LineSet();
//	    lineSet.addPoint(new Point("x", 10f));
//
//	    // Style dots
//	    lineSet.setDots(true);
//	    lineSet.setDotsColor(color.black);
//	    //lineSet.setDotsRadius(dimen);
//	    //lineSet.setDotsStrokeThickness(dimen);
//	    //lineSet.setDotsStrokeColor(color);
//
//	    // Style line
//	    lineSet.setLineThickness(2f);
//	    lineSet.setLineColor(color.black);
//
//	    // Style background fill
//	    lineSet.setFill(true);
//	    //lineSet.setFillColor(color);
//
//	    // Style type
//	    lineSet.setDashed(true);
//	    lineSet.setSmooth(true);
//	    
//	    LineChartView lc = (LineChartView)root.findViewById(R.id.chartview);
//	    lc.addData(lineSet);
		
		return root;
	}
	

	@Override
	public void onClick(View v) {
		Log.e("cox", "onClick - id = " + v.getId());
//		setBackground(v);
//		setActiveTab(v);
	}

//    private void setBackground(View v){
//		for(int i = 0 ; i < buttonList.size(); i++){
//			if (v.getId() == buttonList.get(i).getId()) {
//				v.setBackgroundResource(R.drawable.button_rounded_left_grey_selected);
//			} else {
//				buttonList.get(i).setBackgroundResource(R.drawable.button_rounded_left_grey_unselected);
//			}
//		}
//	}
//
//	private void setActiveTab(View v) {
//		Log.e("cox", "setActiveTab");
//		for(int i=0; i<buttonList.size(); i++) {
//			View view = root.findViewById(arr[i]);
//			if(v.getId()==buttonList.get(i).getId()) {
//				view.setVisibility(View.VISIBLE);
//			} else {
//				view.setVisibility(View.GONE);
//			}
//			
//		}
//	}


	@Override
	public void onTabReselected(android.app.ActionBar.Tab arg0,
			android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(android.app.ActionBar.Tab arg0,
			android.app.FragmentTransaction arg1) {
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab arg0,
			android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
}
