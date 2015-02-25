package com.cox.work.sis.ursula.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cox.work.sis.ursula.PengetahuanDemoFragment;
import com.cox.work.sis.ursula.PengetahuanKerjaFragment;
import com.cox.work.sis.ursula.PengetahuanLisanFragment;
import com.cox.work.sis.ursula.PengetahuanPortfolioFragment;
import com.cox.work.sis.ursula.PengetahuanProyekFragment;
import com.cox.work.sis.ursula.PengetahuanTugasFragment;
import com.cox.work.sis.ursula.PengetahuanTulisFragment;

public class PengetahuanTabPagerAdapter extends AspekBaseTabPagerAdapter {
	
	PengetahuanTulisFragment tulisFragment;
	PengetahuanLisanFragment lisanFragment;
	PengetahuanTugasFragment tugasFragment;
	PengetahuanKerjaFragment kerjaFragment;
	PengetahuanDemoFragment demoFragment;
	PengetahuanProyekFragment proyekFragment;
	PengetahuanPortfolioFragment portFragment;
	
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
		case 2:
			if(tugasFragment == null) {
				tugasFragment = new PengetahuanTugasFragment();
			}
			return tugasFragment;
		case 3:
			if(kerjaFragment == null) {
				kerjaFragment = new PengetahuanKerjaFragment();
			}
			return kerjaFragment;
		case 4:
			if(demoFragment == null) {
				demoFragment = new PengetahuanDemoFragment();
			}
			return demoFragment;
		case 5:
			if(proyekFragment == null) {
				proyekFragment = new PengetahuanProyekFragment();
			}
			return proyekFragment;
		case 6:
			if(portFragment == null) {
				portFragment = new PengetahuanPortfolioFragment();
			}
			return portFragment;
		default:
			return new Fragment();
		}
	}
}
