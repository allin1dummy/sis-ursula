package com.cox.work.service;

import com.cox.work.sis.ursula.model.json.ClassAndAspect;
import com.cox.work.sis.ursula.model.json.DataUser;
import com.cox.work.sis.ursula.model.json.ReqUserClassAspect;
import com.cox.work.sis.ursula.model.json.ResponseUser;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface MobileServiceClient {
	@POST("/UserMobileService.svc/login")
	public void login(@Body DataUser user, Callback<ResponseUser> cb);

	@POST("/UserMobileService.svc/getClassAndAspect")
	public void getClassAndAspect(@Body ReqUserClassAspect reqUserClassAspect, Callback<ClassAndAspect> cb);
}
