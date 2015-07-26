package edu.rosehulman.pickupsports;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RowView extends LinearLayout {

	private TextView mSport;
	private TextView mDist;
	private LinearLayout mRowView;
	public RowView(Context context) {
		super(context);
		
		((Activity)context).getLayoutInflater().inflate(R.layout.row_view, this);
		
		
		
		mSport = (TextView) findViewById(R.id.sport);
		mDist = (TextView) findViewById(R.id.dist);
		mRowView = (LinearLayout) findViewById(R.id.row);
	}

	public void setLeftText(String string) {
		mSport.setText(string);
	}

	public void setRightText(String string) {
		mDist.setText(string);
	}

	public void setBack(boolean interested) {
		if (interested) {
			mRowView.setBackgroundResource(R.drawable.paper_background_44_tall);
		}else {
			mRowView.setBackgroundResource(R.drawable.row_image);
		}
		
	}
}