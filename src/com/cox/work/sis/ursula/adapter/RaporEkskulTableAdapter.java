package com.cox.work.sis.ursula.adapter;

import java.util.List;

import com.cox.work.sis.ursula.R;
import com.cox.work.sis.ursula.model.json.RaporEkstrakurikuler;
import com.cox.work.sis.ursula.model.json.RaporMataPelajaran;
import com.cox.work.sis.ursula.model.json.RaporSikap;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RaporEkskulTableAdapter extends BaseTableAdapter {

	private final int width;
	private final int height;
	private List<RaporEkstrakurikuler> listRaporEkskul;
	private Resources resources;
	private final LayoutInflater inflater;
	

	public RaporEkskulTableAdapter(Context context, List<RaporEkstrakurikuler> list) {
		listRaporEkskul = list;
		resources = context.getResources();
		width = resources.getDimensionPixelSize(R.dimen.table_width);
		height = resources.getDimensionPixelSize(R.dimen.table_height);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getRowCount() {
		return listRaporEkskul.size();
	}

	@Override
	public int getColumnCount() {
		return 1; // Nama Ekskul | Nilai
	}

	@Override
	public int getWidth(int column) {
		return width;
	}

	@Override
	public int getHeight(int row) {
		return height;
	}

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
		
		setNilaiRaporMataPelajaran(row, column, converView);
		return converView;
	}
	
	public LayoutInflater getInflater() {
		return inflater;
	}
	
	private void setNilaiRaporMataPelajaran(int row, int column, View v) {
		TextView tv_mark = ((TextView) v.findViewById(android.R.id.text1));
		
		if(row == -1 && column == -1) { // HEADER Nama Ekskul
			tv_mark.setText("Nama Ekskul");
			tv_mark.setTextColor(Color.BLACK);
		} else if(row == -1 && column > -1) { // HEADER Nilai Angka dan Huruf
			if(column == 0) {
				tv_mark.setText("Nilai Angka");
			} else if(column == 1) {
				tv_mark.setText("Nilai Huruf");
			}
			tv_mark.setTextColor(Color.BLACK);
		} else if(row > -1 && column == -1) { // NAMA Ekskul
			tv_mark.setText(String.valueOf(listRaporEkskul.get(row).KelasEkstrakurikuler.Ekstrakurikuler.Nama));
			tv_mark.setTextColor(Color.BLACK);
		} else if(row > -1 && column > -1) { // NILAI Ekskul
			if(column == 0) {
				tv_mark.setText(String.valueOf(listRaporEkskul.get(row).NilaiEkstrakurikuler.RataRata));
			} else if(column == 1) {
				tv_mark.setText(String.valueOf(listRaporEkskul.get(row).NilaiEkstrakurikuler.NilaiHuruf.Nama));
			}
		}
	}
}
