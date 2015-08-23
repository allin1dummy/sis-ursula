package com.cox.work.sis.ursula;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.cox.work.sis.ursula.model.json.ClassAndAspect;
import com.cox.work.sis.ursula.model.json.DataUser;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseUser;
import com.cox.work.sis.ursula.util.DateSerializerDeserializer;
import com.cox.work.sis.ursula.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		i.putExtra(Util.Constant.USERNAME, etUsername.getText().toString());
		startActivity(i);
	}

	private void doLoginAction() {
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
					Log.e("cox", "SUCCESS #  = " + user.User.Email);
					
					Intent i = new Intent();
					i.putExtra(Util.Constant.USERNAME, user.User.Username);
					i.putExtra(Util.Constant.NAMASISWA, user.User.MutasiMasuk.NamaSiswa);
					if(user.User.IsFirstTime) {

//						try {
//					        GsonBuilder gsonb = new GsonBuilder();
//					        DateSerializerDeserializer ds = new DateSerializerDeserializer();
//					        gsonb.registerTypeAdapter(Date.class, ds);
//					        Gson gson = gsonb.create();
//					        JSONObject json = new JSONObject(gson.toJson(user.User.BaseEntity.CreateDate));
//					    } catch (JSONException e) {
//					        e.printStackTrace();
//					    }
//						String json = "\"\\/Date(736032869080)\\/\"";
//						Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ds).create();
//						Log.e("cox","Date = " + gson.fromJson(json, Date.class));

						i.setClass(activity, ResetPasswordActivity.class);
						//doGetClassAndAspect();
					} else {
						i.setClass(activity, MainActivity.class);
					}

					startActivity(i);
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
				alertbox.setMessage("Login Gagal!");
				alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			        	dialog.dismiss();
			        }
				});
				alertbox.show();
			}
		});
		
//		if(isFirstLogin()) {
//			Intent i = new Intent(this, ResetPasswordActivity.class);
//			startActivity(i);
//		}
	}

	private boolean isFirstLogin() {
		return true;
	}
}
