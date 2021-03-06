package com.cox.work.sis.ursula;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.ResponseUser;
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

public class ForgotPasswordActivity extends Activity implements OnClickListener{
	
	Button btnResendPwd;
	EditText et_userName;
	String Username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Username = extras.getString(Util.Constant.USERNAME);
		}
		
		btnResendPwd = (Button) findViewById(R.id.btn_resend_pwd);
		btnResendPwd.setOnClickListener(this);
		et_userName = (EditText) findViewById(R.id.et_username);
		et_userName.setText(Username);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_resend_pwd:
			if(Username.isEmpty()) {
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
			
			doResendPwdAction(this);
			break;

		default:
			break;
		}
	}
	
	public class Username {
		String Username;
		
		Username(String usr) {
			Username = usr;
		}
	}

	private void doResendPwdAction(final Context ctx) {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Reset Password...");
		dialog.setCancelable(false);
		dialog.show();
		
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MASTER_STG);
		client.resetPassword(new Username(Username), new Callback<ResponseUser>() {
			@Override
			public void success(ResponseUser user, Response arg1) {
				dialog.dismiss();
				
				AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
				alertbox.setTitle(getResources().getString(R.string.reset_pwd));
				alertbox.setMessage(getResources().getString(R.string.reset_pwd_desc));
				alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			        	dialog.dismiss();
			        	Intent i = new Intent(ctx, LoginActivity.class);
			        	startActivity(i);
			        	finish();
			        }
				});
				alertbox.show();
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				dialog.dismiss();
				
				AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
				alertbox.setTitle("Reset Password");
				alertbox.setMessage("Terjadi masalah koneksi, silakan coba sesaat lagi.");
				alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			        	dialog.dismiss();
			        }
				});
				alertbox.show();
			}
		});
	}
}
