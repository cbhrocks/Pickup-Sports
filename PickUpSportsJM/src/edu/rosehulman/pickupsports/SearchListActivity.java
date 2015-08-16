package edu.rosehulman.pickupsports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appspot.pickupsports_copy.pickupsports.Pickupsports;
import com.appspot.pickupsports_copy.pickupsports.model.Sport;
import com.appspot.pickupsports_copy.pickupsports.model.SportCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class SearchListActivity extends Activity {

	public static final double RADIUS = 6327.8;
	public static final double TO_MILES = 0.62137;
	public static double lat;
	public static double lon;
	public final static String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";
	List<Sport> events;
	ListAdapter adapter;
	ListView lv;
	Sport currentEvent;
	int radius = 30;
	List<String> filters = new ArrayList<String>();
	List<Boolean> enabledFilters = new ArrayList<Boolean>();
	private Pickupsports mService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);
		lv = (ListView) findViewById(R.id.list_view);
		events = new ArrayList<Sport>();
		loadFilters();
		double[] gps = getGPSLoc();
		lat=gps[0];
		lon=gps[1];
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(SearchListActivity.this,
						"Long Press detected",
						Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				
				SearchListActivity.this.showDetail(position);
				


			}
		});
		
		
		
		Pickupsports.Builder builder = new Pickupsports.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		mService = builder.build();
		
		loadList();
		
	}


	private void loadFilters() {

		
		String[] arr = getResources().getStringArray(R.array.sport_list);
		for (int i = 0; i < arr.length; i++) {
			this.filters.add(arr[i]);
			this.enabledFilters.add(true);
		}
		
	}


	@Override
	protected void onRestart(){
		super.onRestart();
		loadList();
	}


	private void loadList() {
		
		(new QueryForEventsTask()).execute();
	}


		private void reload() {
			adapter = new ListAdapter(this,(ArrayList<Sport>) events);
			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);
		} 

	//menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	
		switch (id) {
		case R.id.add_new_event_button:
			Intent intent = new Intent(this, CreateEventActivity.class);
			startActivity(intent);
			break;
		case R.id.set_filters:
			showSetFilters();
			break;
		case R.id.set_radius:
			showSetRadius();
			break;
		case R.id.change_location:
			showChangeLocation();
			break;
        case R.id.profile:
            Intent intentProfile = new Intent(this, ProfileActivity.class);
            startActivity(intentProfile);
            break;
		case R.id.help:
			showHelp();
			break;
		}
		
		
		
		return super.onOptionsItemSelected(item);
	}

	protected void showDetail(int position) {
		
		
		currentEvent = (Sport) adapter.getItem(position);
		
		DialogFragment df = new DialogFragment() {
			

			
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view = inflater.inflate(R.layout.event_detail, null);
				((TextView) view.findViewById(R.id.detail_sport)).setText(SearchListActivity.this.currentEvent.getName());
				((TextView) view.findViewById(R.id.detail_desc)).setText(SearchListActivity.this.currentEvent.getDescription());
				((TextView) view.findViewById(R.id.detail_avail)).setText(SearchListActivity.this.currentEvent.getAvailability());
				String dateTime =SearchListActivity.this.currentEvent.getDate();
				String date = dateTime.split("T")[0];
				String year = date.split("-")[0];
				int month = Integer.parseInt(date.split("-")[1]) + 1;
				String day = date.split("-")[2];
				date = year + "-" +  month + "-" + day;
				String time = dateTime.split("T")[1];
				time = time.split(":")[0] + ":" + time.split(":")[1];
				dateTime = date + " " + time;
				((TextView) view.findViewById(R.id.detail_date)).setText(dateTime);
				((TextView) view.findViewById(R.id.detail_loc)).setText(SearchListActivity.this.currentEvent.getLocation());
				builder.setView(view);
				builder.setNegativeButton(R.string.cancel_string,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						});
				builder.setNeutralButton("Get Directions!", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String destLat = currentEvent.getLatLon().split(":")[0];
								String destLon = currentEvent.getLatLon().split(":")[1];
								Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
									    Uri.parse("http://maps.google.com/maps?saddr=" + lat + "," + lon + "&daddr=" + destLat + "," + destLon));
									startActivity(intent);
								
							}
						});
				builder.setPositiveButton(R.string.event_accept,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(SearchListActivity.this,
										"Interesting!", Toast.LENGTH_LONG).show();
								
								SearchListActivity.this.toggleInterest();
								SearchListActivity.this.reload();
								dismiss();
							}
						});
				return builder.create();
			}
		};
		



		df.show(getFragmentManager(), "showDetail");
	}


	protected void toggleInterest() {
//		int index = this.events.indexOf(currentEvent);
//		events.get(index).setInterested(!currentEvent.isInterested());
		
		// add this back in next sprint
	}






	private void showSetFilters() {
		DialogFragment df = new DialogFragment(){
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle(R.string.set_filters_string);
				boolean[] array = new boolean[enabledFilters.size()];
				for (int i = 0; i < enabledFilters.size(); i++) {
					array[i] = enabledFilters.get(i);
				}
				builder.setMultiChoiceItems(R.array.sport_list,array, new OnMultiChoiceClickListener() {
					

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						enabledFilters.set(which, isChecked);
						
					}
				});
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(SearchListActivity.this,
										"Filters Set!", Toast.LENGTH_LONG).show();
								dismiss();
								loadList();
							}
						});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "");
		
	}



	private void showSetRadius() {
				DialogFragment df = new DialogFragment() {
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						LayoutInflater inflater = getActivity().getLayoutInflater();
						View view = inflater.inflate(R.layout.set_radius, null);
						builder.setView(view);
						final EditText text = (EditText)view.findViewById(R.id.set_rad_edit_text);
						builder.setNegativeButton(R.string.cancel_string,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dismiss();
									}
								});
						builder.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
										radius = Integer.parseInt(text.getText().toString());
										loadList();
										dismiss();
									}
								});
						return builder.create();
					}
				};
				df.show(getFragmentManager(), "setRadius");
	}



	private void showChangeLocation() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view = inflater.inflate(R.layout.change_location, null);
				final EditText address = (EditText) view.findViewById(R.id.set_loc_edit_text);
				builder.setView(view);
				builder.setNegativeButton(R.string.cancel_string,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						});
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								(new GenerateLatLon()).execute(address.getText().toString());
								dismiss();
							}
						});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "changeLocation");
	}



	private void showHelp() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle(R.string.about_title);
				builder.setMessage(R.string.about_message);
				builder.setIcon(android.R.drawable.ic_menu_help);
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "help");
		
	}
	
	
	
	class QueryForEventsTask extends AsyncTask<Void, Void, SportCollection>{
		SportCollection sports = null;
		@Override
		protected SportCollection doInBackground(Void... params) {
			try {
				com.appspot.pickupsports_copy.pickupsports.Pickupsports.Sport.List query = mService.sport().list();
				query.setLimit(50L);
				query.setOrder("name");//add filter to sort by distance as well. Next sprint.
				sports = query.execute();
			} catch (IOException e) {
				Log.e("PS", "Failed Loading " + e);
			}
			return sports;
		}
		@Override
		protected void onPostExecute(SportCollection result) {
			super.onPostExecute(result);
			if (result == null){
				Log.e("PS", "Result failed, null");
			}else{
				events.clear();
				List<Sport> allResults = result.getItems();
				for (Sport sport : allResults) {
					if(getDistance(sport.getLatLon()) <= radius && isEnabled(sport.getName()) && isNotExpired(sport.getDate())){
						events.add(sport);
					}
				}
				
				reload();
			}
		}
	}

	private double[] getGPSLoc(){
		LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = locMan.getProviders(true);
		Location loc = null;
		
		for (int i = providers.size()-1; i >= 0; i--) {
			loc = locMan.getLastKnownLocation(providers.get(i));
			if (loc != null) {
				break;
			}
		}
		double[] gps = new double[2];
		if (loc != null) {
			gps[0] = loc.getLatitude();
			gps[1] = loc.getLongitude();
		}
		return gps;
	}


	public boolean isNotExpired(String date) {
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


	public boolean isEnabled(String name) {
		return enabledFilters.get(filters.indexOf(name));
	}


	public static int getDistance(String location) {
		String[] latLon = location.split(":");
		double latitude = Double.parseDouble(latLon[0]);
		double longitude = Double.parseDouble(latLon[1]);
		
		return (int) haversine(lat, lon, latitude, longitude);
	}


	private static double haversine(double lat1, double lon1, double lat2,
			double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2* Math.asin(Math.sqrt(a));
		double finalResult = RADIUS * c * TO_MILES;
		Log.d("PS", "DISTANCE: " + finalResult);
		return finalResult;
	}
	
	 class GenerateLatLon extends AsyncTask<String, Void, String>{

			@Override
			protected String doInBackground(String... params) {
				try{
					String address = params[0];
					Log.d("PS", "address is: " + address);
		    		//Toast.makeText(CreateEventActivity.this, "Generating Lat Lon", Toast.LENGTH_SHORT).show();
		    		HttpGet getmethod = new HttpGet(SearchListActivity.BASE_URL + "?address=" + address.replace(' ', '+'));
		    		Log.d("PS", "created getmethod: ");
		    		org.apache.http.HttpResponse httpResponse = new DefaultHttpClient().execute(getmethod);
		    		Log.d("PS", "executed get method");
		    		int res = httpResponse.getStatusLine().getStatusCode();
		    		Log.d("PS", "status code: " + res);
		    		if (res == 200){
		    			StringBuilder build = new StringBuilder();
		    			BufferedReader read = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		    			for (String s = read.readLine(); s!= null; s = read.readLine()) {
		    				build.append(s);
		    			}
		    			
		    			String result = build.toString();
		    			// Generating the lat and long from the JSON result
		    		
		    			String returnResult = "";
		    			String lat = "";
		    			String lon = "";
		    			JSONObject obj = new JSONObject(result);
		    			try {

		    	            lat = ((JSONArray)obj.get("results")).getJSONObject(0)
		    	                .getJSONObject("geometry").getJSONObject("location")
		    	                .getDouble("lat") + "";

		    	            lon = ((JSONArray)obj.get("results")).getJSONObject(0)
		    	                .getJSONObject("geometry").getJSONObject("location")
		    	                .getDouble("lng") + "";

		    	            returnResult = lat + ":" + lon;
		    	        } catch (JSONException e) {
		    	            return "";

		    	        }
		    			
		    			
		    			Log.d("PS", "Final result is: " + returnResult);
		    			//Toast.makeText(CreateEventActivity.this, "Lat Long was: " + result, Toast.LENGTH_SHORT).show();
		    			return returnResult;
		    		} 
		    	}
		    	catch(Exception e){
		    		e.printStackTrace();
		    		Log.e("PS", "ERROR   " + e.getStackTrace().toString());
		    	}
		    	return "";
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result == null){
					Log.e("PS", "Change Loc GenLatLon, res is null");
				}else{
					SearchListActivity.lat = Double.parseDouble(result.split(":")[0]);
					SearchListActivity.lon = Double.parseDouble(result.split(":")[1]);
					loadList();
				}
			}
	    	
	    }
	
	
	
	
	
	
}
