package com.cox.work.sis.ursula;

import com.cox.work.sis.ursula.adapter.SampleTableAdapter;
import com.cox.work.sis.ursula.util.DummyData;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		TableFixHeaders tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.table);
		tableFixHeaders.setAdapter(new MyAdapter(getActivity()));

		return rootView;
	}
	

	public class MyAdapter extends SampleTableAdapter {

		private final int width;
		private final int height;

		public MyAdapter(Context context) {
			super(context);

			Resources resources = context.getResources();

			width = resources.getDimensionPixelSize(R.dimen.table_width);
			height = resources.getDimensionPixelSize(R.dimen.table_height);
		}

		@Override
		public int getRowCount() {
			return DummyData.LineChart.NUM_SUBJECTS;
		}

		@Override
		public int getColumnCount() {
			return DummyData.LineChart.NUM_WEEKS;
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
		public String getCellString(int row, int column) {
			if(row==-1 && column==-1) {
				return "Minggu Ke \\ Mata Pelajaran";
			} else if(row==-1 && column>-1) {
				return ""+(column+1);
			} else if(row>-1 && column==-1) {
				return DummyData.LineChart.SUBJECTS[row];
			}
			
			return String.format("%.2f", Math.random() * 10);
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
	}
}
