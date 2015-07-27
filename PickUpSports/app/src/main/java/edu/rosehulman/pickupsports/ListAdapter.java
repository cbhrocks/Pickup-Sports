package edu.rosehulman.pickupsports;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
public class ListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<SportEvent> sports;

	public ListAdapter(Context context, ArrayList<SportEvent> sports) {
		mContext = context;
		this.sports=sports;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sports.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return sports.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return sports.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RowView view;
		if (convertView == null) {
			view = new RowView(mContext);
			
		} else {
			view = (RowView) convertView;
		}
		
		view.setLeftText(sports.get(position).getSport());
		view.setRightText(sports.get(position).getDistance());
		if (sports.get(position).isInterested()) {
			view.setBack(true);
		}else{
			view.setBack(false);
		}
		return view;
	}

	
}
