package com.cox.work.sis.ursula.adapter;

import java.util.List;

import com.cox.work.sis.ursula.R;
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

public class RaporSikapTableAdapter extends BaseTableAdapter {

	private final int width;
	private final int height;
	private List<RaporSikap> listRaporSikap;
	private Resources resources;
	private final LayoutInflater inflater;
	

	public RaporSikapTableAdapter(Context context, List<RaporSikap> list) {
		listRaporSikap = list;
		resources = context.getResources();
		width = resources.getDimensionPixelSize(R.dimen.table_width);
		height = resources.getDimensionPixelSize(R.dimen.table_height);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getRowCount() {
		return listRaporSikap.size();
	}

	@Override
	public int getColumnCount() {
		return 3; // Kompetensi | Komp. Dasar | Komp. Inti | Nilai
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
		
		if(row == -1 && column == -1) { // HEADER Kompetensi
			tv_mark.setText("Kompetensi");
			tv_mark.setTextColor(Color.BLACK);
		} else if(row == -1 && column > -1) { // HEADER NILAI ANGKA ATAU HURUF
			if(column == 1) {
				tv_mark.setText("Kompetensi Dasar");
			} else if(column == 2) {
				tv_mark.setText("Kompetensi Inti");
			} else if(column == 3) {
				tv_mark.setText("Nilai");
			}
			tv_mark.setTextColor(Color.BLACK);
		} else if(row > -1 && column == -1) { // NAMA MATA PELAJARAN
			tv_mark.setText(String.valueOf(row));
			tv_mark.setTextColor(Color.BLACK);
		} else if(row > -1 && column > -1) { //
			if(column == 1) {
				tv_mark.setText(listRaporSikap.get(row).KompentensiDasar.Keterangan);
			} else if(column == 2) {
				tv_mark.setText(listRaporSikap.get(row).KompentensiInti.Keterangan);
			} else if(column == 3) {
				tv_mark.setText(listRaporSikap.get(row).Nilai.Deskripsi);
			}
		}
	}
}
