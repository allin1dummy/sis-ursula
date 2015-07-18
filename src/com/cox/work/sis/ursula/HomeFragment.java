package com.cox.work.sis.ursula;

import java.util.ArrayList;
import java.util.List;

import com.cox.work.sis.ursula.adapter.SampleTableAdapter;
import com.cox.work.sis.ursula.util.Util;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	private Spinner sp;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		TableFixHeaders tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.table);
		tableFixHeaders.setAdapter(new MyAdapter(getActivity()));
		
		sp = (Spinner) rootView.findViewById(R.id.spin_class_smst);
		loadDataToSpinner();

		return rootView;
	}
	
	private void loadDataToSpinner() {
		List<String> list = new ArrayList<String>();
		list.add("- Silakan Pilih -");
		list.add("1");
		list.add("2");
		list.add("3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(getActivity(), "Item #" + arg2, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		sp.setAdapter(dataAdapter);
	}
	

	public class MyAdapter extends SampleTableAdapter {

		private final int width;
		private final int height;
		private float total = 0f;

		public MyAdapter(Context context) {
			super(context);

			Resources resources = context.getResources();

			width = resources.getDimensionPixelSize(R.dimen.table_width);
			height = resources.getDimensionPixelSize(R.dimen.table_height);
		}

		@Override
		public int getRowCount() {
			return Util.Properties.NUM_SUBJECTS;
			//return Util.Properties.NUM_WEEKS + 1;
		}

		@Override
		public int getColumnCount() {
			return Util.Properties.NUM_WEEKS + 1;
			//return Util.Properties.NUM_SUBJECTS;
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
			Log.e("cox","row = " + row + "column = " + column);
			TextView tv_mark = ((TextView) v.findViewById(android.R.id.text1));
			if(row==-1 && column==-1) {
				tv_mark.setText("Mata Pelajaran");
			} else if(row==-1 && column>-1) {
				tv_mark.setText(column == getColumnCount()-1 ? "Rata-Rata" : String.valueOf(column + 1));
				//tv_mark.setText(Util.Properties.SUBJECTS[column]);
			} else if(row>-1 && column==-1) {
				tv_mark.setText(Util.Properties.SUBJECTS[row]);
				//tv_mark.setText(row == getRowCount()-1 ? "Rata-Rata" : String.valueOf(row + 1));
			} else if(column == getColumnCount()-1) { // latest column
				tv_mark.setText(String.format("%.2f", total / (getColumnCount() - 1)));
				total = 0f;
			} else {
				float num = (float) (Math.random() * 10);
				total += num;
				tv_mark.setText(String.format("%.2f", num) + (""));
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

		void setText1Red(View view) {
			TextView tv = ((TextView) view.findViewById(android.R.id.text1));
			tv.setTextColor(Color.RED);
		}
	}
}
