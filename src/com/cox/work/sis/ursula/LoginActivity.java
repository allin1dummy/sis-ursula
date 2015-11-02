package com.cox.work.sis.ursula;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.DataUser;
import com.cox.work.sis.ursula.model.json.ResponseUser;
import com.cox.work.sis.ursula.util.Util;

public class LoginActivity extends Activity implements OnClickListener{
	Activity activity;
	Button btnLogin;
	TextView tvForgotPwd;
	TextView tvChangePwd;
	EditText etUsername, etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_login);
		
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);
		tvForgotPwd = (TextView) findViewById(R.id.tv_forgotpwd);
		tvForgotPwd.setOnClickListener(this);
		tvChangePwd = (TextView) findViewById(R.id.tv_changepwd);
		tvChangePwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			doLoginAction();
			break;
		case R.id.tv_forgotpwd:
			doForgotPwdAction();
			break;
		case R.id.tv_changepwd:
			doChangePwdAction();
			break;
		default:
			break;
		}
	}

	private void doForgotPwdAction() {
		Intent i = new Intent(this, ForgotPasswordActivity.class);
		i.putExtra(Util.Constant.USERNAME, etUsername.getText().toString());
		startActivity(i);
	}

	private void doChangePwdAction() {
		Intent i = new Intent(this, ChangePasswordActivity.class);
		i.putExtra(Util.Constant.USERNAME, etUsername.getText().toString());
		startActivity(i);
	}

	private void doLoginAction() {
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
		
		if(etPassword.getText().toString().isEmpty()) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Password");
			alert.setMessage("Password harus diisi")
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
		dialog.setMessage("Login...");
		dialog.setCancelable(false);
		dialog.show();
		
		DataUser user = new DataUser(etUsername.getText().toString(), etPassword.getText().toString());
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MASTER_STG);
		client.login(user, new Callback<ResponseUser>() {
			@Override
			public void success(ResponseUser user, Response arg1) {
				dialog.dismiss();
				if(user != null && user.Message == null) {
					Intent i = new Intent();
					i.putExtra(Util.Constant.USERNAME, user.User.Username);
					i.putExtra(Util.Constant.NAMASISWA, user.User.MutasiMasuk.NamaSiswa);
					i.putExtra(Util.Constant.MUTASIID, user.User.MutasiMasuk.Id);
					i.putExtra(Util.Constant.EMAIL, user.User.Email);
					if(user.User.IsFirstTime) {
						i.setClass(activity, UpdateProfileActivity.class);
					} else {
						i.setClass(activity, MainActivity.class);
					}
					startActivity(i);
					finish();
				} else {
					Log.e("cox", "FAIL!!! # message = " + user.Message);
					AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
					alertbox.setTitle("Login");
					alertbox.setMessage("User ID atau Password salah. Cek kembali User ID dan Password Anda.");
					alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
				        	dialog.dismiss();
				        }
					});
					alertbox.show();
				}
			}
			@Override
			public void failure(RetrofitError arg0) {
				Log.e("cox", "ERROR!!! # message = " + arg0.getMessage());
	        	dialog.dismiss();

				AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
				alertbox.setTitle("Login");
				if(arg0.isNetworkError()) {
					alertbox.setMessage("Login gagal, periksa kembali jaringan internet Anda.");
				} else {
					alertbox.setMessage("User ID atau Password salah. Cek kembali User ID dan Password Anda.");
				}
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
