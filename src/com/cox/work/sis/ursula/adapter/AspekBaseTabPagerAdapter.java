package com.cox.work.sis.ursula.adapter;

import com.cox.work.sis.ursula.R;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class AspekBaseTabPagerAdapter extends FragmentStatePagerAdapter {
	Context ctx;
	
	public AspekBaseTabPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		ctx = context;
	}
	
	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ctx.getResources().getStringArray(R.array.tabs_title)[position];				
	}

}
