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

public class KeterampilanLisanFragment extends Fragment {
	private View root;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.keterampilan_lisan_layout, container, false);
		
		createLineChart();
		//createBarChart();
		//BarChart chart= (BarChart) root.findViewById(R.id.chart);
		//chart.setData(getBarData());
		
		return root;
	}

//	private void createBarChart() {
//		BarChart mChart = (BarChart) root.findViewById(R.id.barchart);
//
//        // enable the drawing of values
//        mChart.setDrawYValues(true);
//
//        mChart.setDescription(" ");
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        //mChart.setMaxVisibleValueCount(60);
//
//        // sets the number of digits for values inside the chart
//        //mChart.setValueDigits(2);
//
//        // disable 3D
//        mChart.set3DEnabled(false);
//
//        // scaling can now only be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//
//        // draw shadows for each bar that show the maximum value
////        mChart.setDrawBarShadow(true);
//
//        //mChart.setUnit(" €");
//
//        // change the position of the y-labels
//        //YLabels yLabels = mChart.getYLabels();
//        //yLabels.setPosition(YLabelPosition.LEFT);
//
//        //XLabels xLabels = mChart.getXLabels();
//        //xLabels.setPosition(XLabelPosition.TOP);
//        // mChart.setDrawXLabels(false);
//
//        mChart.setDrawGridBackground(true);
//        mChart.setDrawHorizontalGrid(true);
//        mChart.setDrawVerticalGrid(true);
//        mChart.setDrawYLabels(true);
//        
//        // sets the text size of the values inside the chart
//        mChart.setValueTextSize(10f);
//
//        mChart.setDrawBorder(true);
//        // mChart.setBorderPositions(new BorderPosition[] {BorderPosition.LEFT,
//        // BorderPosition.RIGHT});
//        
//        BarData data = createBarData();
//        mChart.setData(data);
//
//        XLabels xl = mChart.getXLabels();
//        xl.setPosition(XLabelPosition.BOTTOM);
//        xl.setCenterXLabelText(true);
//        
//        YLabels yl = mChart.getYLabels();
//        yl.setLabelCount(10);
//        Log.e("cox", yl.toString());
//        
//        Legend l = mChart.getLegend();
//        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
//        l.setFormSize(8f);
//        l.setXEntrySpace(4f);
//	}

//	private BarData createBarData() {
//		ArrayList<String> xVals = new ArrayList<String>();
//        xVals.add("IPA");
//        xVals.add("IPS");
//        xVals.add("IPB");
//
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        yVals1.add(new BarEntry((float) (Math.random() * 10), 0));
//        yVals1.add(new BarEntry((float) (Math.random() * 10), 1));
//        yVals1.add(new BarEntry((float) (Math.random() * 10), 2));
//        
//        BarDataSet set1 = new BarDataSet(yVals1, "Dataset");
//        set1.setBarSpacePercent(35f);
//        //set1.setColor(Color.RED);
//
//        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
//        dataSets.add(set1);
//
//        BarData data = new BarData(xVals, dataSets);
//		return data;
//	}

	private void createLineChart() {
		LineChart chart = (LineChart) root.findViewById(R.id.linechart);
		chart.setData(Util.LineChart.getDataSet());
		chart.setDescription("Mata Pelajaran:");
		//chart.setMaxScaleY(10f);

        XLabels xl = chart.getXLabels();
        xl.setPosition(XLabelPosition.BOTTOM);
	}

}
