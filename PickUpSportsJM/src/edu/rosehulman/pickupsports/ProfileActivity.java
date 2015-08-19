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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import edu.rosehulman.pickupsports.SearchListActivity.UpdateProfileTask;

public class ProfileActivity extends Activity {
	private TabHost mTabHost;
	private TabWidget mTabWidget;
	private FrameLayout mFrameLayoutTabs;
	private ListView mFriendListView;
	private ListView mEventListView;
	
	private TextView mName;
	private TextView mPhoneNumber;

	private List<Profile> mFriends;
	private List<Sport> mEvents;

	public static double lat;
	public static double lon;
	
	Profile mProfile;
	Sport currentEvent;

	FriendListAdapter fAdapter;
	ListAdapter eAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mName = (TextView) findViewById(R.id.textViewName);
		mPhoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);

		mTabHost = (TabHost) findViewById(R.id.tabHost);
		mTabHost.setup();

		mTabWidget = mTabHost.getTabWidget();
		mFrameLayoutTabs = mTabHost.getTabContentView();
		
		mEvents = new ArrayList<Sport>();
		mFriends = new ArrayList<Profile>();
		mProfile = new Profile();
		
		mEventListView = (ListView) findViewById(R.id.listViewEvents);
		mEventListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ProfileActivity.this.showDetail(position);

			}
		});

		loadProfile();
		loadEventsList();
		
//		if (mProfile == null){
//			Intent intent = new Intent(this, CreateProfileActivity.class);
//			startActivity(intent);
//		}

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
		eAdapter = new ListAdapter(this,(ArrayList<Sport>) mEvents);
		eAdapter.notifyDataSetChanged();
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
				Get query = SearchListActivity.mService.profile().get();
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
				if (allResults != null){
					mProfile = allResults.get(0);
					Log.d(SearchListActivity.PS, "current profile = " + mProfile);
					
					mName.setText(mProfile.getFirstName()+ " " + mProfile.getLastName());
					mPhoneNumber.setText(mProfile.getPhoneNumber());

					mFriends = new ArrayList<Profile>();
					mEvents = new ArrayList<Sport>();

//					mFriendListView = (ListView) findViewById(R.id.listViewFriends);
					mEventListView = (ListView) findViewById(R.id.listViewEvents);
				} else{
					Intent intent = new Intent(getBaseContext(), CreateProfileActivity.class);
					startActivity(intent);
				}
			}
		}
	}

	class QueryForFriendsTask extends AsyncTask<Void, Void, ProfileCollection> {
		ProfileCollection profiles = null;

		@Override
		protected ProfileCollection doInBackground(Void... params) {
			try {
				com.appspot.horton_mcnelly_pickup_sports.pickupsports.Pickupsports.Profile.List query = 
						SearchListActivity.mService.profile().list();
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
				if (mFriends != null){
					mFriends.clear();
				}
				mFriends.clear();
				List<Profile> allResults = result.getItems();
				for (Profile profile : allResults) {
					if ((mProfile.getFriends() != null) && mProfile.getFriends().contains(profile.getEntityKey())) {
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
				com.appspot.horton_mcnelly_pickup_sports.pickupsports.Pickupsports.Sport.List query = SearchListActivity.mService.sport().list();
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
				if (mEvents != null){
					mEvents.clear();
				}
				List<Sport> allResults = result.getItems();
				for (Sport sport : allResults) {
					if ((mProfile.getEvents() != null) && mProfile.getEvents().contains(sport.getEntityKey()) && isNotExpired(sport.getDate())) {
						mEvents.add(sport);
					}
				}

				reloadEList();
			}
		}
	}
	
	protected void showDetail(int position) {

		currentEvent = (Sport) eAdapter.getItem(position);

		DialogFragment df = new DialogFragment() {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view = inflater.inflate(R.layout.event_detail, null);
				((TextView) view.findViewById(R.id.detail_sport))
						.setText(ProfileActivity.this.currentEvent.getName());
				((TextView) view.findViewById(R.id.detail_desc))
						.setText(ProfileActivity.this.currentEvent.getDescription());
				((TextView) view.findViewById(R.id.detail_avail))
						.setText(ProfileActivity.this.currentEvent.getAvailability());
				String dateTime = ProfileActivity.this.currentEvent.getDate();
				String date = dateTime.split("T")[0];
				String year = date.split("-")[0];
				int month = Integer.parseInt(date.split("-")[1]) + 1;
				String day = date.split("-")[2];
				date = year + "-" + month + "-" + day;
				String time = dateTime.split("T")[1];
				time = time.split(":")[0] + ":" + time.split(":")[1];
				dateTime = date + " " + time;
				((TextView) view.findViewById(R.id.detail_date)).setText(dateTime);
				((TextView) view.findViewById(R.id.detail_loc))
						.setText(ProfileActivity.this.currentEvent.getLocation());
				builder.setView(view);
				builder.setNegativeButton(R.string.cancel_string, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
					}
				});
				builder.setNeutralButton(R.string.remove, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(ProfileActivity.this, "removing event!", Toast.LENGTH_LONG).show();
						
						List<String> PEList = mProfile.getEvents();

						if (PEList.contains(currentEvent.getEntityKey())){
							PEList.remove(currentEvent.getEntityKey());
						}
						mProfile.setEvents(PEList);
						
						(new UpdateProfileTask()).execute(mProfile);
						reloadEList();

						dismiss();
					}
				});
				builder.setPositiveButton("Get Directions!", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String destLat = currentEvent.getLatLon().split(":")[0];
						String destLon = currentEvent.getLatLon().split(":")[1];
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
								Uri.parse("http://maps.google.com/maps?saddr=" + lat + "," + lon + "&daddr=" + destLat
										+ "," + destLon));
						startActivity(intent);

					}
				});
				return builder.create();
			}
		};

		df.show(getFragmentManager(), "showDetail");
	}
	
	class UpdateProfileTask extends AsyncTask<Profile, Void, Profile> {
		@Override
		protected void onPreExecute() {
			Toast.makeText(ProfileActivity.this, "updating Profile!", Toast.LENGTH_LONG).show();
		}

		@Override
		protected Profile doInBackground(Profile... params) {
			Profile toReturn = null;
			try {
				toReturn = SearchListActivity.mService.profile().insert(params[0]).execute();
			} catch (IOException e) {
				Log.e("PS", "Failed removing " + e);
			}
			return toReturn;
		}

		@Override
		protected void onPostExecute(Profile result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.e("PS", "Remove failed, res is null");
			} else {
				// Toast and Go back
				Toast.makeText(ProfileActivity.this, "Removed Event to Your Profile!", Toast.LENGTH_LONG).show();
				loadEventsList();
			}
		}

	}
}
