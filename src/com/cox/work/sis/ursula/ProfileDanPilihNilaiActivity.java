package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProfileDanPilihNilaiActivity extends Activity implements OnClickListener{
	Button btnHarian, btnRapor;
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_pilihnilai);
		btnHarian = (Button) findViewById(R.id.btn_harian);
		btnHarian.setOnClickListener(this);
		btnRapor = (Button) findViewById(R.id.btn_rapor);
		btnRapor.setOnClickListener(this);
		
		bundle = getIntent().getExtras();
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent();
		i.putExtra(Util.Constant.USERNAME, bundle.getString(Util.Constant.USERNAME));
		i.putExtra(Util.Constant.NAMASISWA, bundle.getString(Util.Constant.NAMASISWA));
		i.putExtra(Util.Constant.MUTASIID, bundle.getString(Util.Constant.MUTASIID));
		i.putExtra(Util.Constant.EMAIL, bundle.getString(Util.Constant.EMAIL));
		
		switch (v.getId()) {
		case R.id.btn_harian:
			i.setClass(getApplicationContext(), MainActivity.class);
			break;
		case R.id.btn_rapor:
			i.setClass(getApplicationContext(), MainRaporActivity.class);
			break;
		default:
			break;
		}

		startActivity(i);
		finish();
	}
}
