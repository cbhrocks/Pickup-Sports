package edu.rosehulman.pickupsports;

import java.util.ArrayList;

import com.appspot.pickupsports_copy.pickupsports.model.Sport;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
public class ListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Sport> sports;

	public ListAdapter(Context context, ArrayList<Sport> sports) {
		mContext = context;
		this.sports=sports;
	}

	@Override
	public int getCount() {

		return sports.size();
	}

	@Override
	public Object getItem(int position) {

		return sports.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		RowView view;
		if (convertView == null) {
			view = new RowView(mContext);
			
		} else {
			view = (RowView) convertView;
		}
		
		view.setLeftText(sports.get(position).getName());
		view.setRightText(getDistance(sports.get(position).getLatLon()));
		//TODO Re-implement interest
//		if (sports.get(position).isInterested()) {
//			view.setBack(true);
//		}else{
//			view.setBack(false);
//		}
		return view;
	}

	//TODO Get distance from current location to address of sport
	private String getDistance(String location){
		int dist =  SearchListActivity.getDistance(location);
		if (dist == 0) {
			return "< 1 Mile Away";
		}else if (dist == 1) {
			return "1 Mile Away";
		}else{
			return dist + " Miles Away";
		}
	}
}
