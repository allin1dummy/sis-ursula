package com.cox.work.sis.ursula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	
	Button btnLogin;
	TextView tvForgotPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
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
		if(isFirstLogin()) {
			Intent i = new Intent(this, ResetPasswordActivity.class);
			startActivity(i);
		}
	}

	private boolean isFirstLogin() {
		return true;
	}
	
}
