package com.cox.work.sis.ursula;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AspekSikapFragment extends Fragment implements OnClickListener{
	private View root;
	private ArrayList<View> buttonList;
	private TextView btnDetail1;
	private TextView btnDetail2;
	private TextView btnDetail3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		root =  inflater.inflate(R.layout.sikap_layout, container, false);

		btnDetail1 = (TextView) root.findViewById(R.id.tv_sikap_tulis);
    	btnDetail1.setOnClickListener(this);
    	btnDetail2 = (TextView) root.findViewById(R.id.tv_sikap_lisan);
    	btnDetail2.setOnClickListener(this);
    	btnDetail3 = (TextView) root.findViewById(R.id.tv_sikap_tugas);
    	btnDetail3.setOnClickListener(this);
    	
    	buttonList = new ArrayList<View>();
    	buttonList.add(btnDetail1);
    	buttonList.add(btnDetail2);
    	buttonList.add(btnDetail3);
    	
		
//	    LineSet lineSet = new LineSet();
//	    lineSet.addPoint(new Point("x", 10f));
//
//	    // Style dots
//	    lineSet.setDots(true);
//	    lineSet.setDotsColor(color.black);
//	    //lineSet.setDotsRadius(dimen);
//	    //lineSet.setDotsStrokeThickness(dimen);
//	    //lineSet.setDotsStrokeColor(color);
//
//	    // Style line
//	    lineSet.setLineThickness(2f);
//	    lineSet.setLineColor(color.black);
//
//	    // Style background fill
//	    lineSet.setFill(true);
//	    //lineSet.setFillColor(color);
//
//	    // Style type
//	    lineSet.setDashed(true);
//	    lineSet.setSmooth(true);
//	    
//	    LineChartView lc = (LineChartView)root.findViewById(R.id.chartview);
//	    lc.addData(lineSet);
		
		return root;
	}
	

	@Override
	public void onClick(View v) {
	}

}
