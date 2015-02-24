package com.cox.work.sis.ursula.adapter;

import com.cox.work.sis.ursula.PengetahuanLisanFragment;
import com.cox.work.sis.ursula.PengetahuanTulisFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PengetahuanTabPagerAdapter extends AspekBaseTabPagerAdapter {
	
	PengetahuanTulisFragment tulisFragment;
	PengetahuanLisanFragment lisanFragment;
	
	public PengetahuanTabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			if(tulisFragment == null) {
				tulisFragment = new PengetahuanTulisFragment();
			}
			return tulisFragment;
		case 1:
			if(lisanFragment == null) {
				lisanFragment = new PengetahuanLisanFragment();
			}
			return lisanFragment;
		default:
			return new Fragment();
		}
	}
}
