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

	private Resources resources;
	
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
		resources = context.getResources();
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
		result++; // add 1 for nilai Rata-Rata
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
		TextView tv_date = ((TextView) v.findViewById(android.R.id.text2));
		
		if(row==-1 && column==-1) { // HEADER NILAI KE
			tv_mark.setText("Mata Pelajaran");
			tv_mark.setTextColor(Color.BLACK);
		} else if(row==-1 && column>-1) { // HEADER RATA-RATA
			tv_mark.setText(column == getColumnCount() -1 ? "Rata-Rata" : String.valueOf(column + 1));
			tv_mark.setTextColor(Color.BLACK);
		} else if(row>-1 && column==-1) { // NAMA MATA PELAJARAN
			tv_mark.setText(dataStudentMark.get(row).getMataPelajaran());
			tv_mark.setTextColor(Color.BLACK);
		} else if(row>-1 && column == getColumnCount() - 1) { // NILAI RATA-RATA
			if(String.valueOf(dataStudentMark.get(row).calculateMeanValue()).equalsIgnoreCase("NAN")) {
				tv_mark.setText("-");
				tv_mark.setTextColor(Color.BLACK);
			} else {
				float means = dataStudentMark.get(row).calculateMeanValue();
				tv_mark.setText(String.format("%.2f", means));
				if((int)means <= 59) {
					tv_mark.setTextColor(Color.RED);
				} else {
					tv_mark.setTextColor(Color.BLACK);
				}
			}
		} else { // NILAI
			DataNilaiTableAdapter dt = dataStudentMark.get(row);
			if(column >= dt.getNilai().size()) {
				tv_mark.setText("-");
				tv_mark.setTextColor(Color.BLACK);
			} else {
				float nilai = dt.getNilai().get(column).getNilaiAngka();
				String strNilai = String.format("%.2f", nilai);
				tv_mark.setText(strNilai);
				tv_date.setText(dt.getNilai().get(column).getTanggal());

				//TODO : BUG setTextColor
				if(dt.getNilai().get(column).isRemidi()) {
					tv_mark.setTextColor(resources.getColor(R.color.yellow));
				} else if((int)nilai <= 59) {
					tv_mark.setTextColor(Color.RED);
				} else {
					tv_mark.setTextColor(Color.BLACK);
				}
			}
		}
	}
}
