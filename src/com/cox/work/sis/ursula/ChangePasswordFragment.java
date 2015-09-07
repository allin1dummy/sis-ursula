package com.cox.work.sis.ursula;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.cox.work.service.MobileServiceClient;
import com.cox.work.service.MobileServiceGenerator;
import com.cox.work.sis.ursula.model.json.ResponseUser;
import com.cox.work.sis.ursula.model.json.UserUpdateProfileEmailPwd;
import com.cox.work.sis.ursula.util.Util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangePasswordFragment extends Fragment implements OnClickListener{
	private View root;
	private Button btnChangePwd;
	private EditText etUsername;
	private EditText etPwdLama;
	private EditText etPwdBaru2;
	private EditText etPwdBaru;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.activity_change_password, container, false);
		
		btnChangePwd = (Button) root.findViewById(R.id.btn_change_pwd);
		btnChangePwd.setOnClickListener(this);
		
		etUsername = (EditText) root.findViewById(R.id.et_username);
		etUsername.setText(getArguments().getString(Util.Constant.USERNAME));
		etPwdLama = (EditText) root.findViewById(R.id.et_old_pwd);
		etPwdBaru = (EditText) root.findViewById(R.id.et_new_pwd);
		etPwdBaru2 = (EditText) root.findViewById(R.id.et_new_pwd2);
		
		return root;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_change_pwd:
			if(etUsername.getText().toString().isEmpty()) {
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
			
			final ProgressDialog dialog = new ProgressDialog(getActivity());
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
					AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());
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
					        	Intent i = new Intent(getActivity(), LoginActivity.class);
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
					
					AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());
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
