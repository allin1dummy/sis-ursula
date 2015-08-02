package com.cox.work.sis.ursula;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.DataUser;
import com.cox.work.sis.ursula.model.json.ResponseUser;
import com.cox.work.sis.ursula.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	Activity activity;
	Button btnLogin;
	TextView tvForgotPwd;
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
		default:
			break;
		}
	}

	private void doForgotPwdAction() {
		Intent i = new Intent(this, ForgotPasswordActivity.class);
		startActivity(i);
	}

	private void doLoginAction() {
		//=========================== TEST API ===========================//
		DataUser user = new DataUser(etUsername.getText().toString(), etPassword.getText().toString());
		MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_STG);
		client.login(user, new Callback<ResponseUser>() {
			@Override
			public void success(ResponseUser user, Response arg1) {
				if(user != null && user.Message == null) {
					Log.e("cox", "SUCCESS #  = " + user.User.Email);
					Intent i = new Intent(activity, MainActivity.class);
					startActivity(i);
				} else {
					Log.e("cox", "FAIL!!! # message = " + user.Message);
					Util.CommonDialog.show(getBaseContext(), "Login", "Login Gagal");
				}
			}
			@Override
			public void failure(RetrofitError arg0) {
				Log.e("cox", "ERROR!!! # message = " + arg0.getMessage());
				Util.CommonDialog.show(getApplicationContext(), "Login", "Login Gagal");
			}
		});
		//=========================== TEST API ===========================//
		
//		if(isFirstLogin()) {
//			Intent i = new Intent(this, ResetPasswordActivity.class);
//			startActivity(i);
//		}
	}

	private boolean isFirstLogin() {
		return true;
	}
	
}
