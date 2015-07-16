package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ForgotPasswordActivity extends Activity implements OnClickListener{
	
	Button btnResendPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		btnResendPwd = (Button) findViewById(R.id.btn_resend_pwd);
		btnResendPwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_resend_pwd:
			doResendPwdAction(this);
			break;

		default:
			break;
		}
	}

	private void doResendPwdAction(Context ctx) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setTitle(getResources().getString(R.string.forgot_pwd));
		alertbox.setMessage(getResources().getString(R.string.forgot_pwd_msg));
		alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	dialog.dismiss();
	        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
	        	startActivity(i);
	        }
		});
		alertbox.show();
	}
}
