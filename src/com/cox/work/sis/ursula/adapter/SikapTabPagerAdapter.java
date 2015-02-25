package com.cox.work.sis.ursula.adapter;

import com.cox.work.sis.ursula.SikapDemoFragment;
import com.cox.work.sis.ursula.SikapKerjaFragment;
import com.cox.work.sis.ursula.SikapLisanFragment;
import com.cox.work.sis.ursula.SikapPortfolioFragment;
import com.cox.work.sis.ursula.SikapProyekFragment;
import com.cox.work.sis.ursula.SikapTugasFragment;
import com.cox.work.sis.ursula.SikapTulisFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SikapTabPagerAdapter extends FragmentPagerAdapter {
	
	SikapTulisFragment tulisFragment;
	SikapLisanFragment lisanFragment;
	SikapTugasFragment tugasFragment;
	SikapKerjaFragment kerjaFragment;
	SikapDemoFragment demoFragment;
	SikapProyekFragment proyekFragment;
	SikapPortfolioFragment portFragment;
	
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
		case 2:
			if(tugasFragment == null) {
				tugasFragment = new SikapTugasFragment();
			}
			return tugasFragment;
		case 3:
			if(kerjaFragment == null) {
				kerjaFragment = new SikapKerjaFragment();
			}
			return kerjaFragment;
		case 4:
			if(demoFragment == null) {
				demoFragment = new SikapDemoFragment();
			}
			return demoFragment;
		case 5:
			if(proyekFragment == null) {
				proyekFragment = new SikapProyekFragment();
			}
			return proyekFragment;
		case 6:
			if(portFragment == null) {
				portFragment = new SikapPortfolioFragment();
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
