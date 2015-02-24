package com.cox.work.sis.ursula.adapter;

import com.cox.work.sis.ursula.KeterampilanLisanFragment;
import com.cox.work.sis.ursula.KeterampilanTulisFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class KeterampilanTabPagerAdapter extends AspekBaseTabPagerAdapter {
	
	KeterampilanTulisFragment tulisFragment;
	KeterampilanLisanFragment lisanFragment;
	
	public KeterampilanTabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			if(tulisFragment == null) {
				tulisFragment = new KeterampilanTulisFragment();
			}
			return tulisFragment;
		case 1:
			if(lisanFragment == null) {
				lisanFragment = new KeterampilanLisanFragment();
			}
			return lisanFragment;
		default:
			return new Fragment();
		}
	}
}
