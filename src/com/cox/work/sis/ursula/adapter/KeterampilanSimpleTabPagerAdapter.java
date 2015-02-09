package com.cox.work.sis.ursula.adapter;

import com.cox.work.sis.ursula.KeterampilanLisanFragment;
import com.cox.work.sis.ursula.KeterampilanTulisFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class KeterampilanSimpleTabPagerAdapter extends FragmentPagerAdapter {
	
	public KeterampilanSimpleTabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			return new KeterampilanTulisFragment();
		case 1:
			return new KeterampilanLisanFragment();
		case 2:
			return new KeterampilanTulisFragment();
		case 3:
			return new KeterampilanLisanFragment();
		default:
			return new Fragment();
		}
	}

	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Tes Tertulis";
		case 1:
			return "Tes Lisan";
		case 2:
			return "Tugas";
		case 3:
			return "Unjuk Kerja";
		case 4:
			return "Demonstrasi";
		case 5:
			return "Proyek";
		case 6:
			return "Portfolio";
		default:
			return "";				
		}
	}
}
