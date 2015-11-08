package com.cox.work.sis.ursula.adapter;

import java.util.List;

import com.cox.work.sis.ursula.R;
import com.cox.work.sis.ursula.model.json.RaporMataPelajaran;
import com.cox.work.sis.ursula.model.json.RaporPerkembangan;
import com.cox.work.sis.ursula.model.json.RaporPerkembangan.Kehadiran;
import com.cox.work.sis.ursula.model.json.RaporSikap;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RaporKehadiranTableAdapter extends BaseTableAdapter {

	private final int width;
	private final int height;
	private Kehadiran kehadiran;
	private Resources resources;
	private final LayoutInflater inflater;
	

	public RaporKehadiranTableAdapter(Context context, Kehadiran data) {
		kehadiran = data;
		resources = context.getResources();
		width = resources.getDimensionPixelSize(R.dimen.table_width);
		height = resources.getDimensionPixelSize(R.dimen.table_height);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getRowCount() {
		return 4; // Ijin | Sakit | Tanpa Keterangan | Terlambat
	}

	@Override
	public int getColumnCount() {
		return 1;
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
		
		setNilaiKehadiran(row, column, converView);
		return converView;
	}
	
	public LayoutInflater getInflater() {
		return inflater;
	}
	
	private void setNilaiKehadiran(int row, int column, View v) {
		TextView tv_mark = ((TextView) v.findViewById(android.R.id.text1));
		
		if(row == -1 && column == -1) { // HEADER Kehadiran
			tv_mark.setText("Kehadiran");
			tv_mark.setTextColor(Color.BLACK);
		} else if(row == -1 && column > -1) { // HEADER Total
			tv_mark.setText("Total");
			tv_mark.setTextColor(Color.BLACK);
		} else if(row > -1 && column == -1) {
			if(row == 0) { // Ijin
				tv_mark.setText("Ijin");
			} else if(row == 1) { // Sakit
				tv_mark.setText("Sakit");
			} else if(row == 2) { // Tanpa keterangan
				tv_mark.setText("Tanpa keterangan");
			} else if(row == 3) { // Terlambat
				tv_mark.setText("Terlambat");
			}
			tv_mark.setTextColor(Color.BLACK);
		} else if(row > -1 && column > -1) { //
			if(row == 0) { // Ijin
				tv_mark.setText(String.valueOf(kehadiran.Ijin));
			} else if(row == 1) { // Sakit
				tv_mark.setText(String.valueOf(kehadiran.Sakit));
			} else if(row == 2) { // Tanpa keterangan
				tv_mark.setText(String.valueOf(kehadiran.TanpaKeterangan));
			} else if(row == 3) { // Terlambat
				tv_mark.setText(String.valueOf(kehadiran.TerlambarHadir));
			}
		}
	}
}
