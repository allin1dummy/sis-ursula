package com.cox.work.sis.ursula;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.ResponseUser;
import com.cox.work.sis.ursula.model.json.UserUpdateProfileEmailPwd;
import com.cox.work.sis.ursula.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChangePasswordActivity extends Activity implements OnClickListener{
	
	View v;
	Button btnChangePwd;
	EditText etUsername, etPwdLama, etPwdBaru;
	Context activity;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		activity = this;
		
		btnChangePwd = (Button) findViewById(R.id.btn_change_pwd);
		btnChangePwd.setOnClickListener(this);
		
		etUsername = (EditText) findViewById(R.id.et_username);
		etUsername.setText(getIntent().getStringExtra(Util.Constant.USERNAME));
		etPwdLama = (EditText) findViewById(R.id.et_old_pwd);
		etPwdBaru = (EditText) findViewById(R.id.et_new_pwd);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_change_pwd:
			final ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage("Memperbarui password...");
			dialog.setCancelable(false);
			dialog.show();
			
			UserUpdateProfileEmailPwd user = new UserUpdateProfileEmailPwd(etUsername.getText().toString(), null, etPwdLama.getText().toString(), etPwdBaru.getText().toString());
			MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
			client.updateProfileEmailPwd(user, new Callback<ResponseUser>() {
				@Override
				public void success(ResponseUser user, Response arg1) {
					dialog.dismiss();
					
					AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
					alertbox.setTitle(getResources().getString(R.string.change_pwd));
					alertbox.setMessage(getResources().getString(R.string.change_pwd_desc));
					alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
				        	dialog.dismiss();
				        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				        	startActivity(i);
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
				        public void onClick(DialogInterface dialog, int which) {
				        	dialog.dismiss();
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
