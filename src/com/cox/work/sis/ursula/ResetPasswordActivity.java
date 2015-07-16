package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ResetPasswordActivity extends Activity implements OnClickListener{
	
	View v;
	Button btnResetPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		btnResetPwd = (Button) findViewById(R.id.btn_reset_pwd);
		btnResetPwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset_pwd:
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setTitle(getResources().getString(R.string.reset_pwd));
			alertbox.setMessage(getResources().getString(R.string.reset_pwd_desc));
			alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	dialog.dismiss();
		        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
		        	startActivity(i);
		        }
			});
			alertbox.show();
			break;

		default:
			break;
		}
	}
	
}
