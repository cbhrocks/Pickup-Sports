package edu.rosehulman.pickupsports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.Profile;
import com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.ProfileCollection;
import com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.Sport;
import com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.SportCollection;
import com.appspot.horton_mcnelly_pickup_sports.pickupsports.*;
import com.appspot.horton_mcnelly_pickup_sports.pickupsports.Pickupsports.Profile.Get;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import edu.rosehulman.pickupsports.SearchListActivity.QueryForEventsTask;

public class ProfileActivity extends Activity {
	private TabHost mTabHost;
	private TabWidget mTabWidget;
	private FrameLayout mFrameLayoutTabs;
	private ListView mFriendListView;
	private ListView mEventListView;

	private List<Profile> mFriends;
	private List<Sport> mEvents;

	com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.Profile mProfile;

	FriendListAdapter fAdapter;
	ListAdapter eAdapter;

	private Pickupsports mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		mTabHost = (TabHost) findViewById(R.id.tabHost);
		mTabHost.setup();

		mTabWidget = mTabHost.getTabWidget();
		mFrameLayoutTabs = mTabHost.getTabContentView();

		mProfile = new Profile();

		mFriends = new ArrayList<Profile>();
		mEvents = new ArrayList<Sport>();

		mFriendListView = (ListView) findViewById(R.id.listViewFriends);
		mEventListView = (ListView) findViewById(R.id.listViewEvents);

		TextView[] originalTextViews = new TextView[mTabWidget.getTabCount()];
		for (int i = 0; i < mTabWidget.getTabCount(); i++) {
			originalTextViews[i] = (TextView) mTabWidget.getChildTabViewAt(i);
		}
		mTabWidget.removeAllViews();

		for (int i = 0; i < mFrameLayoutTabs.getChildCount(); i++) {
			mFrameLayoutTabs.getChildAt(i).setVisibility(View.GONE);
		}

		for (int i = 0; i < originalTextViews.length; i++) {
			final TextView tabWidgetTextView = originalTextViews[i];
			final View tabContentView = mFrameLayoutTabs.getChildAt(i);
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec((String) tabWidgetTextView.getTag());
			tabSpec.setContent(new TabHost.TabContentFactory() {
				@Override
				public View createTabContent(String tag) {
					return tabContentView;
				}
			});
			if (tabWidgetTextView.getBackground() == null) {
				tabSpec.setIndicator(tabWidgetTextView.getText());
			} else {
				tabSpec.setIndicator(tabWidgetTextView.getText(), tabWidgetTextView.getBackground());
			}
			mTabHost.addTab(tabSpec);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		loadProfile();
		loadFriendsList();
		loadEventsList();
	}

	private void loadProfile() {
		(new QueryForProfileTask()).execute();
	}

	private void loadFriendsList() {
		(new QueryForFriendsTask()).execute();
	}

	private void loadEventsList() {
		(new QueryForEventsTask()).execute();
	}
	
	private void reloadEList() {
		eAdapter = new ListAdapter(this,(ArrayList<Sport>) events);
		eAapter.notifyDataSetChanged();
		mEventListView.setAdapter(eAdapter);
	} 

	private void reloadFList() {
		fAdapter = new FriendListAdapter(this, (ArrayList<Profile>) mFriends);
		fAdapter.notifyDataSetChanged();
		mFriendListView.setAdapter(fAdapter);
	}

	private boolean isNotExpired(String date) {
		Date currentDate = new Date(System.currentTimeMillis());
		int year = Integer.parseInt(date.split("T")[0].split("-")[0]);
		int month = Integer.parseInt(date.split("T")[0].split("-")[1]);
		int day = Integer.parseInt(date.split("T")[0].split("-")[2]);
		int hour = Integer.parseInt(date.split("T")[1].split(":")[0]);
		int minute = Integer.parseInt(date.split("T")[1].split(":")[1]);
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, minute);

		Date checkDate = c.getTime();

		if (currentDate.getTime() > checkDate.getTime() + 360000) {
			return false;
		}
		return true;
	}

	class QueryForProfileTask extends AsyncTask<Void, Void, ProfileCollection> {
		ProfileCollection profile = null;

		@Override
		protected ProfileCollection doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Get query = mService.profile().get();
				profile = query.execute();
			} catch (IOException e) {
				Log.e("PS", "Failed Loading " + e);
			}
			return profile;
		}

		@Override
		protected void onPostExecute(ProfileCollection result) {
			if (result == null) {
				Log.e("PS", "Result failed, null");
			} else {
				List<com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.Profile> allResults = result
						.getItems();
				mProfile = allResults.get(0);
			}
		}
	}

	class QueryForFriendsTask extends AsyncTask<Void, Void, ProfileCollection> {
		ProfileCollection profiles = null;

		@Override
		protected ProfileCollection doInBackground(Void... params) {
			try {
				com.appspot.horton_mcnelly_pickup_sports.pickupsports.Pickupsports.Profile.List query = mService
						.profile().list();
				query.setOrder("first_name");// add filter to sort by distance
												// as well. Next sprint.
				profiles = query.execute();
			} catch (IOException e) {
				Log.e("PS", "Failed Loading " + e);
			}
			return profiles;
		}

		@Override
		protected void onPostExecute(ProfileCollection result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.e("PS", "Result failed, null");
			} else {
				mFriends.clear();
				List<Profile> allResults = result.getItems();
				for (Profile profile : allResults) {
					if (mProfile.getFriends().contains(profile.getEntityKey())) {
						mFriends.add(profile);
					}
				}

				reloadFList();
			}
		}
	}

	class QueryForEventsTask extends AsyncTask<Void, Void, SportCollection> {
		SportCollection sports = null;

		@Override
		protected SportCollection doInBackground(Void... params) {
			try {
				com.appspot.horton_mcnelly_pickup_sports.pickupsports.Pickupsports.Sport.List query = mService.sport().list();
				query.setLimit(50L);
				query.setOrder("name");// add filter to sort by distance as
										// well. Next sprint.
				sports = query.execute();
			} catch (IOException e) {
				Log.e("PS", "Failed Loading " + e);
			}
			return sports;
		}

		@Override
		protected void onPostExecute(SportCollection result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.e("PS", "Result failed, null");
			} else {
				mEvents.clear();
				List<Sport> allResults = result.getItems();
				for (Sport sport : allResults) {
					if (mProfile.getEvents().contains(sport.getEntityKey()) && isNotExpired(sport.getDate())) {
						mEvents.add(sport);
					}
				}

				reloadEList();
			}
		}
	}
}
