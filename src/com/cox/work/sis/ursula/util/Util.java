package com.cox.work.sis.ursula.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class Util {
	
	public Util() {
	}
	
	public static boolean isNumeric(String str)	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	
	public static final class Properties {
		public static final int NUM_SUBJECTS = 11;
		public static final int NUM_WEEKS = 10;
		public static final String SUBJECTS[] = {"MTK", "IPA", "IPS", "BI", "AGAMA", "PENJAS", "PPKn", "SMUSIK", "PRAKARYA", "BIng", "Komp"};
		public static final String SERVICE_URL_STG = "http://112.78.145.43/sini-sdursula/Master";
	}
	
	public static final class LineChart {
		public static final LineData getDataSet() {
			int colors[] = {Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.LTGRAY, Color.DKGRAY, Color.BLUE, Color.RED};
			ArrayList<String> xVals = new ArrayList<String>();
			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			LineDataSet setComp = null;
			ArrayList<Entry> valsComp = null;
			
			for(int i=0; i<Properties.NUM_SUBJECTS; i++) {
				 xVals.add(""+(i+1));
				 valsComp = new ArrayList<Entry>();
				 for(int j=0; j<Properties.NUM_WEEKS; j++) {
					 valsComp.add(new Entry((float)(Math.random() * 10), j));
				 }
				 setComp = new LineDataSet(valsComp, Properties.SUBJECTS[i]);
				 setComp.setColor(colors[i]);
				 dataSets.add(setComp);
			}
			
		    LineData data = new LineData(xVals, dataSets);
			
		    return data;
		}

		public static final LineData getDataSetFromJSON() {
			int colors[] = {Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW};
			ArrayList<String> xVals = new ArrayList<String>();
			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			LineDataSet setComp = null;
			ArrayList<Entry> valsComp = null;
			int numOfWeeks = 0;
			
			try {
				JSONObject obj =  new JSONObject(JSON.doGetRequest("www.ursula.com"));
				JSONArray arr = obj.getJSONArray("data");
				numOfWeeks = arr.length();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
//			for(int i=0; i<1; i++) {
//				 xVals.add(""+(i+1));
//				 valsComp = new ArrayList<Entry>();
//				 for(int j=0; j<numOfWeeks; j++) {
//					 valsComp.add(new Entry((float)(Math.random() * 10), j));
//				 }
//				 setComp = new LineDataSet(valsComp, Properties.SUBJECTS[i]);
//				 setComp.setColor(colors[i]);
//				 dataSets.add(setComp);
//			}
			
		    LineData data = new LineData(xVals, dataSets);
			
		    return data;
		}
	}
	
	public static class JSON {
		public static final String DATA_ASPEK_PENGETAHUAN = 
				"{" +
				"'type':'pengetahuan'," +
				"'semester':'1'," +
				"'data':[" +
				"		{'subject':'IPA', 'nilai':'[9.50,6.40,8.90,10.00,5.80]'}," +
				"		{'subject':'IPS', 'nilai':'[5.50,3.40,9.90,7.00,8.80]'},]" +
				"		{'subject':'MAT', 'nilai':'[8.50,9.40,6.90,4.00,5.80]'}]" +
				"}";
		
		//public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		public static final String doGetRequest(String url) throws IOException {
//			OkHttpClient client = new OkHttpClient();
//			//HttpURLConnection con = client.open(new URL(url));
//			
//			Request request = new Request.Builder().url(url).build();
//			Response response = client.newCall(request).execute();
//			return response.body().string();
			return DATA_ASPEK_PENGETAHUAN;
		}
		
	}
	
	public static class CommonDialog {
		public static final void show(Context ctx, String title, String msg) {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
			//alertbox.setTitle(ctx.getResources().getString(string));
			//alertbox.setMessage(ctx.getResources().getString(string2));
			alertbox.setTitle(title);
			alertbox.setMessage(msg);
			alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	dialog.dismiss();
		        }
			});
			alertbox.show();
		}
	}
}
