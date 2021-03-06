package com.cox.work.sis.ursula;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseGetProfile;
import com.cox.work.sis.ursula.util.Util;

public class ProfileDanPilihNilaiActivity extends Activity implements OnClickListener{
	ImageView imgProfile;
	Button btnHarian, btnRapor, btnUpdateProfile, btnChangePwd;
	TextView tv_nama, tv_noInduk, tv_sisn, tv_kelas, tv_waliKelas;
	Bundle bundle;
	public static Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		
		setContentView(R.layout.activity_profile_pilihnilai);
		imgProfile = (ImageView) findViewById(R.id.img_profile);
		btnHarian = (Button) findViewById(R.id.btn_harian);
		btnHarian.setOnClickListener(this);
		btnRapor = (Button) findViewById(R.id.btn_rapor);
		btnRapor.setOnClickListener(this);
		btnUpdateProfile = (Button) findViewById(R.id.btn_updateprofile);
		btnUpdateProfile.setOnClickListener(this);
		btnChangePwd = (Button) findViewById(R.id.btn_changepwd);
		btnChangePwd.setOnClickListener(this);

		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Memuat profile siswa...");
		dialog.setCancelable(false);
		dialog.show();
		
		bundle = getIntent().getExtras();
		tv_nama = (TextView) findViewById(R.id.tv_nama);
		tv_nama.setText(bundle.getString(Util.Constant.NAMASISWA));
		tv_noInduk = (TextView) findViewById(R.id.tv_no_induk);
		tv_noInduk.setText("No Induk : " + bundle.getString(Util.Constant.NOINDUK));
		tv_sisn = (TextView) findViewById(R.id.tv_sisn);
		tv_kelas = (TextView) findViewById(R.id.tv_kelas);
		tv_waliKelas = (TextView) findViewById(R.id.tv_wali_kelas);

		ReqUserClassAspect profile = new ReqUserClassAspect(bundle.getString(Util.Constant.USERNAME), bundle.getString(Util.Constant.MUTASIID));
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
		client.getProfileImg(profile, new Callback<ResponseGetProfile>() {
			@Override
			public void success(ResponseGetProfile respGetProfile, Response resp) {
				if(respGetProfile.BukuIndukMurid.ImageString != null && !respGetProfile.BukuIndukMurid.ImageString.isEmpty()) {
					byte[] byteArray = Base64.decode(respGetProfile.BukuIndukMurid.ImageString, Base64.DEFAULT);
					Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
					imgProfile.setImageBitmap(bmp);
				}
				
				tv_sisn.setText("NISN : " + respGetProfile.BukuIndukMurid.NISN);
				tv_kelas.setText("Kelas : " + respGetProfile.MuridKelas.KelasDisplayMember);
				tv_waliKelas.setText("Wali Kelas : " + respGetProfile.WaliKelas);
				
				dialog.dismiss();
				Log.e("cox", "getProfileImg success");
			}
			
			@Override
			public void failure(RetrofitError error) {
				dialog.dismiss();
				Log.e("cox", "getProfileImg failure message = " + error.getMessage());
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		Intent i = new Intent();
		i.setClass(getApplicationContext(), MainActivity.class);
		i.putExtra(Util.Constant.USERNAME, bundle.getString(Util.Constant.USERNAME));
		i.putExtra(Util.Constant.NAMASISWA, bundle.getString(Util.Constant.NAMASISWA));
		i.putExtra(Util.Constant.MUTASIID, bundle.getString(Util.Constant.MUTASIID));
		i.putExtra(Util.Constant.EMAIL, bundle.getString(Util.Constant.EMAIL));
		
		switch (v.getId()) {
		case R.id.btn_harian:
			i.putExtra(Util.Constant.NILAI, 0);
			break;
		case R.id.btn_rapor:
			i.putExtra(Util.Constant.NILAI, 1);
			break;
		case R.id.btn_updateprofile:
			i.putExtra(Util.Constant.NILAI, 2);
			break;
		case R.id.btn_changepwd:
			i.putExtra(Util.Constant.NILAI, 3);
			break;
		default:
			break;
		}

		startActivity(i);
	}
	
	@Override
	public void onBackPressed() {
//		Util.CommonDialog.doLogout(this);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Logout")
			.setCancelable(true)
			.setMessage("Apakah Anda yakin logout dari Applikasi?")
			.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.dismiss();
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
}
