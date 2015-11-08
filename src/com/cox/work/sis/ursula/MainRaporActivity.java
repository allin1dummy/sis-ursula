package com.cox.work.sis.ursula;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.cox.work.sis.ursula.adapter.NavDrawerListAdapter;
import com.cox.work.sis.ursula.model.NavDrawerItem;
import com.cox.work.sis.ursula.util.Util;

public class MainRaporActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	private String userName, namaSiswa, mutasiId, email;
	
	// fragments
	private int latestSelectedTab = -1;
	private NilaiRaporFragment nilaiRapor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_rapor);
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_rapor_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_rapor_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		userName = getIntent().getStringExtra(Util.Constant.USERNAME);
		namaSiswa = getIntent().getStringExtra(Util.Constant.NAMASISWA);
		mutasiId = getIntent().getStringExtra(Util.Constant.MUTASIID);
		email = getIntent().getStringExtra(Util.Constant.EMAIL);
		return super.onCreateView(name, context, attrs);
	}
	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
//		switch (item.getItemId()) {
//		case R.id.action_refresh:
//			Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
//			return true;
//		case R.id.action_logout:
//			doLogout();
//			
//			return true;
//		case R.id.action_update_profile:
//			Intent i = new Intent(getApplicationContext(), UpdateProfileActivity.class);
//			i.putExtra(Util.Constant.IS_FIRST_UPDATE_PROFILE, false);
//			i.putExtra(Util.Constant.USERNAME, userName);
//			i.putExtra(Util.Constant.EMAIL, email);
//			startActivity(i);
//			
//			return true;
//		default:
			return super.onOptionsItemSelected(item);
//		}
	}

	private void doLogout() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Logout")
				.setCancelable(true)
				.setMessage("Apakah Anda yakin logout dari Applikasi?")
				.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.dismiss();
						Intent i = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(i);
						finish();
					}
				})
				.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				})
				.show();
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_refresh).setVisible(!drawerOpenfalse);
//		menu.findItem(R.id.action_logout).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		if(latestSelectedTab == position) { // select same fragment
			mDrawerLayout.closeDrawer(mDrawerList);
			return;
		}
		// update the main content by replacing fragments
		Bundle b = new Bundle();
		b.putString(Util.Constant.USERNAME, userName);
		b.putString(Util.Constant.NAMASISWA, namaSiswa);
		b.putString(Util.Constant.MUTASIID, mutasiId);
		b.putString(Util.Constant.EMAIL, email);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		switch (position) {
		case 0: // Rapor
			if(nilaiRapor == null) {
				nilaiRapor = new NilaiRaporFragment();
				nilaiRapor.setArguments(b);
			}
			ft.replace(R.id.frame_container, nilaiRapor).commit();
			break;
		case 4: // Logout
			doLogout();
			break;
		default:
			break;
		}

		setTitle(navMenuTitles[position]);
		latestSelectedTab = position;
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onBackPressed() {
		doLogout();
	}
}