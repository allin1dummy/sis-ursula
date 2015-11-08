package com.cox.work.sis.ursula;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseGetProfile;
import com.cox.work.sis.ursula.util.Util;
import com.squareup.picasso.Picasso;

public class ProfileDanPilihNilaiActivity extends Activity implements OnClickListener{
	ImageView imgProfile;
	Button btnHarian, btnRapor;
	TextView tv_nama, tv_noInduk;
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_pilihnilai);
		imgProfile = (ImageView) findViewById(R.id.img_profile);
		btnHarian = (Button) findViewById(R.id.btn_harian);
		btnHarian.setOnClickListener(this);
		btnRapor = (Button) findViewById(R.id.btn_rapor);
		btnRapor.setOnClickListener(this);
		
		bundle = getIntent().getExtras();
		tv_nama = (TextView) findViewById(R.id.tv_nama);
		tv_nama.setText("Nama Siswa : " + bundle.getString(Util.Constant.NAMASISWA));
		tv_noInduk = (TextView) findViewById(R.id.tv_no_induk);
		tv_noInduk.setText("No Induk : " + bundle.getString(Util.Constant.NOINDUK));

		ReqUserClassAspect profile = new ReqUserClassAspect(bundle.getString(Util.Constant.USERNAME), bundle.getString(Util.Constant.MUTASIID));
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
		client.getProfileImg(profile, new Callback<ResponseGetProfile>() {
			@Override
			public void success(ResponseGetProfile respGetProfile, Response resp) {
				Log.e("cox", "getProfileImg success");
			}
			
			@Override
			public void failure(RetrofitError error) {
				Log.e("cox", "getProfileImg failure message = " + error.getMessage());
			}
		});
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		//Picasso.with(getApplicationContext()).load(uri);
		
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
