package com.cox.work.service;

import com.cox.work.sis.ursula.ForgotPasswordActivity.Username;
import com.cox.work.sis.ursula.model.json.ClassAndAspect;
import com.cox.work.sis.ursula.model.json.DataUser;
import com.cox.work.sis.ursula.model.json.ReqGetNilai;
import com.cox.work.sis.ursula.model.json.ReqGetNilaiRapor;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseGetNilai;
import com.cox.work.sis.ursula.model.json.ResponseGetNilaiRapor;
import com.cox.work.sis.ursula.model.json.ResponseGetProfile;
import com.cox.work.sis.ursula.model.json.ResponseUser;
import com.cox.work.sis.ursula.model.json.UserUpdateProfileEmailPwd;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface MobileServiceClient {
	@POST("/UserMobileService.svc/login")
	public void login(@Body DataUser user, Callback<ResponseUser> cb);
	
	@POST("/MobileService.svc/getNilaiRapor")
	public void getNilaiRapor(@Body ReqGetNilaiRapor rapor, Callback<ResponseGetNilaiRapor> cb);

	@POST("/EmailService.svc/resetPassword")
	public void resetPassword(@Body Username user, Callback<ResponseUser> cb);

	@POST("/MobileService.svc/getKelasDanAspek")
	public void getClassAndAspect(@Body ReqUserClassAspect reqUserClassAspect, Callback<ClassAndAspect> cb);
	
	@POST("/MobileService.svc/updateProfile")
	public void updateProfileEmailPwd(@Body UserUpdateProfileEmailPwd user, Callback<ResponseUser> callback);
	
	@POST("/MobileService.svc/updateProfile")
	public void updateProfileEmail(@Body UserUpdateProfileEmailPwd user, Callback<ResponseUser> callback);
	
	@POST("/MobileService.svc/getNilai")
	public void getNilai(@Body ReqGetNilai user, Callback<ResponseGetNilai> callback);
	
	@POST("/MobileService.svc/getProfile")
	public void getProfileImg(@Body ReqUserClassAspect reqUserClassAspect, Callback<ResponseGetProfile> cb);
}
