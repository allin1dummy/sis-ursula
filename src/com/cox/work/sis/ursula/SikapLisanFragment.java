package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.util.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SikapLisanFragment extends Fragment {
	private View root;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.sikap_lisan_layout, container, false);
		
		createLineChart();
		
		return root;
	}

	private void createLineChart() {
		LineChart chart = (LineChart) root.findViewById(R.id.linechart);
		chart.setData(Util.LineChart.getDataSetFromJSON());
		chart.setDescription("Mata Pelajaran:");
		//chart.setMaxScaleY(10f);

        XLabels xl = chart.getXLabels();
        xl.setPosition(XLabelPosition.BOTTOM);
	}

}
