package com.cox.work.sis.ursula.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cox.work.sis.ursula.KeterampilanDemoFragment;
import com.cox.work.sis.ursula.KeterampilanKerjaFragment;
import com.cox.work.sis.ursula.KeterampilanLisanFragment;
import com.cox.work.sis.ursula.KeterampilanPortfolioFragment;
import com.cox.work.sis.ursula.KeterampilanProyekFragment;
import com.cox.work.sis.ursula.KeterampilanTugasFragment;
import com.cox.work.sis.ursula.KeterampilanTulisFragment;

public class KeterampilanTabPagerAdapter extends FragmentPagerAdapter {
	
	KeterampilanTulisFragment tulisFragment;
	KeterampilanLisanFragment lisanFragment;
	KeterampilanTugasFragment tugasFragment;
	KeterampilanKerjaFragment kerjaFragment;
	KeterampilanDemoFragment demoFragment;
	KeterampilanProyekFragment proyekFragment;
	KeterampilanPortfolioFragment portFragment;
	
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
		case 2:
			if(tugasFragment == null) {
				tugasFragment = new KeterampilanTugasFragment();
			}
			return tugasFragment;
		case 3:
			if(kerjaFragment == null) {
				kerjaFragment = new KeterampilanKerjaFragment();
			}
			return kerjaFragment;
		case 4:
			if(demoFragment == null) {
				demoFragment = new KeterampilanDemoFragment();
			}
			return demoFragment;
		case 5:
			if(proyekFragment == null) {
				proyekFragment = new KeterampilanProyekFragment();
			}
			return proyekFragment;
		case 6:
			if(portFragment == null) {
				portFragment = new KeterampilanPortfolioFragment();
			}
			return portFragment;
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
