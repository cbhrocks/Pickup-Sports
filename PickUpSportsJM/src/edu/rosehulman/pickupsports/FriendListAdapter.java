package edu.rosehulman.pickupsports;

import java.util.ArrayList;

import com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.Profile;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FriendListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Profile> profiles;
	
	public FriendListAdapter(Context context, ArrayList<Profile> profiles) {
		mContext = context;
		this.profiles=profiles;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return profiles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return profiles.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		FriendListItem view;
		if (convertView == null) {
			view = new FriendListItem(mContext);
			
		} else {
			view = (FriendListItem) convertView;
		}
		
		view.setName(profiles.get(position).getFirstName() + profiles.get(position).getLastName());
		
		return null;
	}

}
