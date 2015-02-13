package com.cox.work.sis.ursula.util;

import java.util.ArrayList;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class DummyData {
	
	public DummyData() {
	}
	
	public static class LineChart {
		public static LineData getDataSet() {
			int max = 10;
			String subjects[] = {"MTK", "IPA", "IPS", "Kesenian", "Agama", "Olahraga", "PPKN", "Seni Musik", "Seni Rupa", "Ekskul"};
			ArrayList<String> xVals = new ArrayList<String>();
			ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
			LineDataSet setComp = null;
			ArrayList<Entry> valsComp = new ArrayList<Entry>();
			
			for(int i=0; i<max; i++) {
				 xVals.add(""+i+1);
				 
				 valsComp.add(new Entry((float)(Math.random() * 10), i));
				 
				 setComp = new LineDataSet(valsComp, subjects[i]);
				 setComp.setColor(Color.RED);
				 
				 dataSets.add(setComp);
			}
			
		    LineData data = new LineData(xVals, dataSets);
			
			/*
			ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
		    ArrayList<Entry> valsComp2 = new ArrayList<Entry>();
		    
		    Entry c1e1 = new Entry((float)(Math.random() * 10), 0);
		    valsComp1.add(c1e1);
		    Entry c1e2 = new Entry((float)(Math.random() * 10), 1);
		    valsComp1.add(c1e2);
		    Entry c1e3 = new Entry((float)(Math.random() * 10), 2);
		    valsComp1.add(c1e3);
		    Entry c1e4 = new Entry((float)(Math.random() * 10), 3);
		    valsComp1.add(c1e4);
		    // and so on ...

		    Entry c2e1 = new Entry((float)(Math.random() * 10), 0);
		    valsComp2.add(c2e1);
		    Entry c2e2 = new Entry((float)(Math.random() * 10), 1);
		    valsComp2.add(c2e2);
		    Entry c2e3 = new Entry((float)(Math.random() * 10), 2);
		    valsComp2.add(c2e3);
		    Entry c2e4 = new Entry((float)(Math.random() * 10), 3);
		    valsComp2.add(c2e4);
		    //...
		    
		    LineDataSet setComp1 = new LineDataSet(valsComp1, "Math");
		    setComp1.setColor(Color.RED);
		    LineDataSet setComp2 = new LineDataSet(valsComp2, "Social");
		    setComp2.setColor(Color.BLACK);
		    
		    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		    dataSets.add(setComp1);
		    dataSets.add(setComp2);

			*/
			
		    return data;
		}
	}
}
