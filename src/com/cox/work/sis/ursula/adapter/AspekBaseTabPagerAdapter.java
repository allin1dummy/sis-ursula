package com.cox.work.sis.ursula.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AspekBaseTabPagerAdapter extends FragmentStatePagerAdapter {
	
	public AspekBaseTabPagerAdapter(FragmentManager fm) {
		super(fm);
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

	@Override
	public Fragment getItem(int arg0) {
		return new Fragment();
	}
}
