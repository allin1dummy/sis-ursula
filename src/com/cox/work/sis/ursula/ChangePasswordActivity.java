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
	EditText etUsername, etPwdLama, etPwdBaru, etPwdBaru2;
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
		etPwdBaru2 = (EditText) findViewById(R.id.et_new_pwd2);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_change_pwd:
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
			
			if(!etPwdBaru.getText().toString().equals(etPwdBaru2.getText().toString())) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Password");
				alert.setMessage("Password baru tidak cocok")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.dismiss();
						}
					})
					.show();
				return;
			}
			
			if(!Util.isPasswordValid((etPwdBaru.getText().toString())) || !Util.isPasswordValid((etPwdBaru2.getText().toString()))) {
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
			dialog.setMessage("Memperbarui password...");
			dialog.setCancelable(false);
			dialog.show();
			
			UserUpdateProfileEmailPwd user = new UserUpdateProfileEmailPwd(etUsername.getText().toString(), null, etPwdLama.getText().toString(), etPwdBaru.getText().toString());
			MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
			client.updateProfileEmailPwd(user, new Callback<ResponseUser>() {
				@Override
				public void success(ResponseUser user, Response arg1) {
					dialog.dismiss();

					final String msg = user.Message;
					AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
					alertbox.setTitle(getResources().getString(R.string.change_pwd));
					if(msg != null && !msg.isEmpty()) { // current password is not valid
						alertbox.setMessage(user.Message);
					} else {
						alertbox.setMessage(getResources().getString(R.string.change_pwd_desc));
					}
					alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dlg, int which) {
				        	dlg.dismiss();
				        	if(msg == null || msg.isEmpty()) {
					        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
					        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					        	startActivity(i);
				        	}
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
