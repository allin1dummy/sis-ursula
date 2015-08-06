package com.cox.work.sis.ursula.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class DateSerializerDeserializer extends TypeAdapter<Date> implements
		JsonSerializer<Object> {
	public JsonElement serialize(Date date, Type typeOfT,
			JsonSerializationContext context) {
		return new JsonPrimitive("/Date(" + date.getTime() + ")/");
	}

	public JsonElement serialize(Object arg0, Type arg1,
			JsonSerializationContext arg2) {

		Date date = (Date) arg0;
		return new JsonPrimitive("/Date(" + date.getTime() + ")/");
	}

	@Override
	public Date read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		Date result = null;
		String str = reader.nextString();
		str = str.replaceAll("[^0-9]", "");
		if (!TextUtils.isEmpty(str)) {
			try {
				result = new Date(Long.parseLong(str));
			} catch (NumberFormatException e) {
			}
		}
		return result;
	}

	@Override
	public void write(JsonWriter arg0, Date arg1) throws IOException {

	}

}