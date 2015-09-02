package com.cox.work.sis.ursula.adapter;

import java.util.ArrayList;

import com.cox.work.sis.ursula.R;
import com.cox.work.sis.ursula.model.DataNilaiTableAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StudentMarkTableAdapter extends SampleTableAdapter {

	private final int width;
	private final int height;
//	private float[][] marks;
	private ArrayList<DataNilaiTableAdapter> dataStudentMark;
	
//	public StudentMarkTableAdapter(Context context, float[][] marks) {
//		super(context);
//		this.marks = marks;
//
//		Resources resources = context.getResources();
//		width = resources.getDimensionPixelSize(R.dimen.table_width);
//		height = resources.getDimensionPixelSize(R.dimen.table_height);
//	}

	public StudentMarkTableAdapter(Context context, ArrayList<DataNilaiTableAdapter> data) {
		super(context);
		dataStudentMark = data;

		Resources resources = context.getResources();
		width = resources.getDimensionPixelSize(R.dimen.table_width);
		height = resources.getDimensionPixelSize(R.dimen.table_height);

	}

	@Override
	public int getRowCount() {
		return dataStudentMark.size();
	}

	@Override
	public int getColumnCount() {
		// find the highest items of list nilai
		int result = 0;
		for(DataNilaiTableAdapter nilai : dataStudentMark) {
			if(nilai.getNilai().size() > result) {
				result = nilai.getNilai().size();
			}
		}
		return result;
	}

	@Override
	public int getWidth(int column) {
		return width;
	}

	@Override
	public int getHeight(int row) {
		return height;
	}

	@Override
	public int getLayoutResource(int row, int column) {
		final int layoutResource;
		switch (getItemViewType(row, column)) {
			case 0:
				layoutResource = R.layout.item_table1_header;
			break;
			case 1:
				layoutResource = R.layout.item_table1;
			break;
			default:
				throw new RuntimeException("wtf?");
		}
		return layoutResource;
	}

	@Override
	public int getItemViewType(int row, int column) {
		if (row < 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public View getView(int row, int column, View converView, ViewGroup parent) {
		if (converView == null) {
			converView = getInflater().inflate(getLayoutResource(row, column), parent, false);
		}
		
		setStudentMark(row, column, converView);
		return converView;
	}
	
	private void setStudentMark(int row, int column, View v) {
		TextView tv_mark = ((TextView) v.findViewById(android.R.id.text1));
		if(row==-1 && column==-1) {
			tv_mark.setText("Mata Pelajaran");
		} else if(row==-1 && column>-1) {
			tv_mark.setText(column == getColumnCount()-1 ? "Rata-Rata" : String.valueOf(column + 1));
		} else if(row>-1 && column==-1) { // SUBJECT
			tv_mark.setText(dataStudentMark.get(row).getMataPelajaran());
		} else if(row>-1 && column == getColumnCount() - 1) { // MEAN
			if(String.valueOf(dataStudentMark.get(row).calculateMeanValue()).equalsIgnoreCase("NAN")) {
				tv_mark.setText("-");
			} else {
				tv_mark.setText(String.format("%.2f", dataStudentMark.get(row).calculateMeanValue()));
			}
		} else { // MARK
			DataNilaiTableAdapter dt = dataStudentMark.get(row);
			if(column >= dt.getNilai().size()) {
				tv_mark.setText("-");
			} else {
				tv_mark.setText(String.format("%.2f", dt.getNilai().get(column)));
			}
		} 

		if(column > -1 && row > -1 && column < dataStudentMark.get(row).getNilai().size() && dataStudentMark.get(row).getNilai().get(column) < 60.00) {
			//tv_mark.setTextColor(Color.RED);
		} else {
			//tv_mark.setTextColor(Color.BLACK);
		}
		
		TextView tv_date = ((TextView) v.findViewById(android.R.id.text2));
		if(tv_date != null) {
			if(column == getColumnCount()-1 || column == -1) {
				tv_date.setText("");
			} else {
				tv_date.setText("(01-Sept-2015)");
				
			}
		}
	}
}
