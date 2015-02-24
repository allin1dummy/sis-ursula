package com.cox.work.sis.ursula.adapter;

import com.cox.work.sis.ursula.SikapLisanFragment;
import com.cox.work.sis.ursula.SikapTulisFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class SikapTabPagerAdapter extends AspekBaseTabPagerAdapter {
	
	SikapTulisFragment tulisFragment;
	SikapLisanFragment lisanFragment;
	
	public SikapTabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			if(tulisFragment == null) {
				tulisFragment = new SikapTulisFragment();
			}
			return tulisFragment;
		case 1:
			if(lisanFragment == null) {
				lisanFragment = new SikapLisanFragment();
			}
			return lisanFragment;
		default:
			return new Fragment();
		}
	}
}
