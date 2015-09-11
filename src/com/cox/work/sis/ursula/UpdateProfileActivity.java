package com.cox.work.sis.ursula;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.DataUser;
import com.cox.work.sis.ursula.model.json.ResponseUser;
import com.cox.work.sis.ursula.model.json.UserUpdateProfileEmailPwd;
import com.cox.work.sis.ursula.util.DateSerializerDeserializer;
import com.cox.work.sis.ursula.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UpdateProfileActivity extends Activity implements OnClickListener{
	
	private View v;
	private Button btnResetPwd;
	private EditText etUsername, etEmail, etPwdLama, etPwdBaru;
	private Context activity;
	private Intent i;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_profile);
		activity = this;
		i = getIntent();
		
		btnResetPwd = (Button) findViewById(R.id.btn_reset_pwd);
		btnResetPwd.setOnClickListener(this);
		
		etUsername = (EditText) findViewById(R.id.et_username);
		etUsername.setText(i.getStringExtra(Util.Constant.USERNAME)); // username should exist
		etEmail = (EditText) findViewById(R.id.et_email);
		etEmail.setText(i.getStringExtra(Util.Constant.EMAIL).isEmpty() ? "" : i.getStringExtra(Util.Constant.EMAIL));
		etPwdLama = (EditText) findViewById(R.id.et_old_pwd);
		etPwdBaru = (EditText) findViewById(R.id.et_new_pwd);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset_pwd:
			if(etUsername.getText().toString().isEmpty()) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("User Name");
				alert.setMessage("User Name harus diisi")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.dismiss();
						}
					})
					.show();
				return;
			}
			
			if(etEmail.getText().toString().isEmpty()) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Email");
				alert.setMessage("Email harus diisi")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.dismiss();
						}
					})
					.show();
				return;
			}

			if(!Util.isValidEmail(etEmail.getText())) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Email");
				alert.setMessage("Email tidak valid")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.dismiss();
						}
					})
					.show();
				return;
			}
			if(!Util.isPasswordValid((etPwdBaru.getText().toString())) || !Util.isPasswordValid((etPwdLama.getText().toString()))) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Password");
				alert.setMessage("Password minimal 6 karakter")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.dismiss();
						}
					})
					.show();
				return;
			}
			
			final ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage("Memperbarui profile...");
			dialog.setCancelable(false);
			dialog.show();
			
			UserUpdateProfileEmailPwd user = new UserUpdateProfileEmailPwd(etUsername.getText().toString(), etEmail.getText().toString(), etPwdLama.getText().toString(), etPwdBaru.getText().toString());
			MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
			client.updateProfileEmailPwd(user, new Callback<ResponseUser>() {
				@Override
				public void success(ResponseUser user, Response arg1) {
					dialog.dismiss();
					
					AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
					alertbox.setTitle(getResources().getString(R.string.reset_pwd));
					alertbox.setMessage("Data profile Anda sudah diperbarui, silakan login kembali.");
					alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dlg, int which) {
				        	dlg.dismiss();
				        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				        	startActivity(i);
				        	finish();
				        }
					});
					alertbox.show();
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					dialog.dismiss();
					
					AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
					alertbox.setTitle("Update Profile Gagal");
					alertbox.setMessage("Terjadi masalah koneksi, silakan coba sesaat lagi.");
					alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dlg, int which) {
				        	dlg.dismiss();
				        }
					});
					alertbox.show();
				}
			});
			break;

		default:
			break;
		}
	}
	
}
