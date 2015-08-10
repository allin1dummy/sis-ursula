package com.cox.work.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class MobileServiceGenerator {

	// No need to instantiate this class.
	private MobileServiceGenerator() {
	}

	public static <S> S createService(Class<S> serviceClass, String baseUrl) {
		Gson gson = new GsonBuilder()
						.create();
		
		RestAdapter.Builder builder = new RestAdapter.Builder()
											.setEndpoint(baseUrl)
											.setConverter(new GsonConverter(gson));
		RestAdapter adapter = builder.build();
		adapter.setLogLevel(RestAdapter.LogLevel.FULL);
		return adapter.create(serviceClass);
	}
}
