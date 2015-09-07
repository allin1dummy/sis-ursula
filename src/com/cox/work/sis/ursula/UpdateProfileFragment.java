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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UpdateProfileFragment extends Fragment implements OnClickListener{
	private View root;
	private EditText etUsername, etEmail;
	private LinearLayout ll_password;
	private Button btnResetPwd;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.activity_update_profile, container, false);
		
		etUsername = (EditText) root.findViewById(R.id.et_username);
		etUsername.setText(getArguments().getString(Util.Constant.USERNAME)); // username should exist
		etEmail = (EditText) root.findViewById(R.id.et_email);
		etEmail.setText(getArguments().getString(Util.Constant.EMAIL).isEmpty() ? "" : getArguments().getString(Util.Constant.EMAIL));
		ll_password = (LinearLayout) root.findViewById(R.id.layout_password);
		ll_password.setVisibility(View.GONE);
		btnResetPwd = (Button) root.findViewById(R.id.btn_reset_pwd);
		btnResetPwd.setOnClickListener(this);
		
		return root;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset_pwd:
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
			
			if(etEmail.getText().toString().isEmpty()) {
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("Email");
				alert.setMessage("Email harus diisi")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.dismiss();
						}
					})
					.show();
				return;
			}

			if(!Util.isValidEmail(etEmail.getText())) {
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("Email");
				alert.setMessage("Email tidak valid")
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
			dialog.setMessage("Memperbarui profile...");
			dialog.setCancelable(false);
			dialog.show();
			
			UserUpdateProfileEmailPwd user = new UserUpdateProfileEmailPwd(etUsername.getText().toString(), etEmail.getText().toString());
			MobileServiceClient client = MobileServiceGenerator.createService(MobileServiceClient.class, Util.Properties.SERVICE_URL_MOBILE_STG);
			client.updateProfileEmail(user, new Callback<ResponseUser>() {
				@Override
				public void success(ResponseUser user, Response arg1) {
					dialog.dismiss();
					
					AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());
					alertbox.setTitle("Update Profile");
					alertbox.setMessage("Email sudah diupdate.");
					alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dlg, int which) {
				        	dlg.dismiss();
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
