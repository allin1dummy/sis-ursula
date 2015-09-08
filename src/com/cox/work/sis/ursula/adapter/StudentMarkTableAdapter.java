package com.cox.work.sis.ursula.adapter;

import java.util.ArrayList;

import com.cox.work.sis.ursula.R;
import com.cox.work.sis.ursula.model.DataNilaiTableAdapter;
import com.cox.work.sis.ursula.model.NilaiDanTanggal;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StudentMarkTableAdapter extends SampleTableAdapter {

	private final int width;
	private final int height;
	private ArrayList<DataNilaiTableAdapter> dataStudentMark;
	private Resources resources;
	private int idxMaxNilaiKe;
	

	public StudentMarkTableAdapter(Context context, ArrayList<DataNilaiTableAdapter> data, int max) {
		super(context);
		dataStudentMark = data;
		resources = context.getResources();
		width = resources.getDimensionPixelSize(R.dimen.table_width);
		height = resources.getDimensionPixelSize(R.dimen.table_height);
		idxMaxNilaiKe = max;
	}

	@Override
	public int getRowCount() {
		return dataStudentMark.size();
	}

	@Override
	public int getColumnCount() {
		// adding 2 because: idxMaxNilaiKe is zero-based indexing; adding another 1 for Rata-Rata column
		return (idxMaxNilaiKe + 2);
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
			float ratarata = dataStudentMark.get(row).getRataRata();
			if(ratarata < 0) { // by default RataRata == -1, it means value of RataRata is empty
				tv_mark.setText("-");
				tv_mark.setTextColor(Color.BLACK);
			} else {
				tv_mark.setText(String.format("%.2f", ratarata));
				if((int)ratarata <= 59) {
					tv_mark.setTextColor(Color.RED);
				} else {
					tv_mark.setTextColor(Color.BLACK);
				}
			}
			tv_date.setText("");
		} else { // NILAI
			DataNilaiTableAdapter dt = dataStudentMark.get(row);
			if(column >= dt.getListNilai().length) {
				/*
				Handle data ex:
				1  | 2 | 3 | 4 | 5
				-------------------
				10 |20 | - | - | -
				*/
				tv_mark.setText("-");
				tv_mark.setTextColor(Color.BLACK);
			} else {
				NilaiDanTanggal itemNilai = dt.getListNilai()[column];
				if(itemNilai != null) {
					/*
					Handle data ex:
					1  | 2 | 3 | 4 | 5
					-------------------
					10 |20 |30 |40 |50
					*/
					float nilaiAngka = itemNilai.getNilaiAngka();
					String strNilaiAngka = String.format("%.2f", nilaiAngka);
					tv_mark.setText(strNilaiAngka);
					tv_date.setText(itemNilai.getTanggal());

					// set color
					if(itemNilai.isRemidi()) {
						tv_mark.setTextColor(resources.getColor(R.color.yellow));
					} else if((int)nilaiAngka <= 59) {
						tv_mark.setTextColor(Color.RED);
					} else {
						tv_mark.setTextColor(Color.BLACK);
					}
				} else {
					/*
					Handle data ex:
					1  | 2 | 3 | 4 | 5
					-------------------
					-  | - | - |40 |50
					*/
					tv_mark.setText("-");
					tv_date.setText("");
					tv_mark.setTextColor(Color.BLACK);
				}
			}
		}
	}
}
