package com.cox.work.sis.ursula.adapter;

import java.util.List;

import com.cox.work.sis.ursula.R;
import com.cox.work.sis.ursula.model.json.RaporMataPelajaran;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RaporMataPelajaranTableAdapter extends BaseTableAdapter {

	private final int width;
	private final int height;
	private List<RaporMataPelajaran> listNilaiMtPelajaran;
	private Resources resources;
	private final LayoutInflater inflater;
	

	public RaporMataPelajaranTableAdapter(Context context, List<RaporMataPelajaran> listRaporMataPelajaran) {
		listNilaiMtPelajaran = listRaporMataPelajaran;
		resources = context.getResources();
		width = resources.getDimensionPixelSize(R.dimen.table_width);
		height = resources.getDimensionPixelSize(R.dimen.table_height);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getRowCount() {
		return listNilaiMtPelajaran.size();
	}

	@Override
	public int getColumnCount() {
		return 2; // Mata Pelajaran | Nilai Angka | Nilai Huruf
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
		
		if(row == -1) { // HEADER : MATA PELAJARAN | NILAI ANGKA | NILAI HURUF
			if(column == -1) {
				tv_mark.setText("Mata Pelajaran");
			} else if(column == 0) {
				tv_mark.setText("Nilai Angka");
			} else if(column == 1) {
				tv_mark.setText("Nilai Huruf");
			}
		} else if(row > -1) { // VALUE
			if(column == -1) {
				tv_mark.setText(listNilaiMtPelajaran.get(row).KelasMataPelajaran.MataPelajaran.Nama);
			} else if(column == 0) {
				tv_mark.setText(listNilaiMtPelajaran.get(row) == null ? "" : String.valueOf(listNilaiMtPelajaran.get(row).KKMAngka));
			} else if(column == 1) {
				tv_mark.setText(listNilaiMtPelajaran.get(row) == null ? "" : listNilaiMtPelajaran.get(row).KKM);
			}
		}
		tv_mark.setTextColor(Color.BLACK);
	}
}
