package edu.rosehulman.pickupsports;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendListItem extends LinearLayout {
	private TextView mName;

	public FriendListItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		((Activity)context).getLayoutInflater().inflate(R.layout.row_view, this);
		
		mName = (TextView) findViewById(R.id.textViewName);
	}
	
	public void setName(String name){
		mName.setText(name);
	}

}
