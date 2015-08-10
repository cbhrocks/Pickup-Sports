package edu.rosehulman.pickupsports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.appspot.pickupsports_copy.pickupsports.Pickupsports;
import com.appspot.pickupsports_copy.pickupsports.model.Sport;
import com.appspot.pickupsports_copy.pickupsports.model.SportCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
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
	
	List<Sport> events;
	ListAdapter adapter;
	ListView lv;
	Sport currentEvent;
	int radius = 30;
	
	private Pickupsports mService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);
		lv = (ListView) findViewById(R.id.list_view);
		events = new ArrayList<Sport>();
		
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


	@Override
	protected void onRestart(){
		super.onRestart();
		loadList();
	}


	//demo method
	private void loadList() {
		
		
		//Demo Purposes Only
		
//		String sampleDescription = "This is a string that will house the description of the event, stating extra information that the user wants to share, such as certain rules, variations, etc.";
//		SportEvent s1 = new SportEvent(1, "Soccer", sampleDescription, null, null, "Anyone");
//		SportEvent s2 = new SportEvent(2, "FootBall", sampleDescription, null, null, "Only cool KidZ");
//		SportEvent s3 = new SportEvent(3, "Frisbee", sampleDescription, null, null, "Anyone");
//		SportEvent s4 = new SportEvent(4, "Soccer", sampleDescription, null, null, "Anyone");
//		SportEvent s5 = new SportEvent(5, "Tennis", sampleDescription, null, null, "Residents of cherry street");
//		SportEvent s6 = new SportEvent(6, "Rocket League", sampleDescription, null, null, "Anyone who is awesome");
//		
//		events.add(s1);
//		events.add(s2);
//		events.add(s3);
//		events.add(s4);
//		events.add(s5);
//		events.add(s6);
//		adapter = new ListAdapter(this,events);
//		lv.setAdapter(adapter);
		
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
			Toast.makeText(this,
					"Link to Create new Event Here", Toast.LENGTH_LONG).show();
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
				((TextView) view.findViewById(R.id.detail_date)).setText(SearchListActivity.this.currentEvent.getDate());
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
				
//				if (SearchListActivity.this.currentEvent.isInterested()) {
//					builder.setPositiveButton(R.string.event_decline,
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									Toast.makeText(SearchListActivity.this,
//											"Not Interesting!", Toast.LENGTH_LONG).show();
//									
//									SearchListActivity.this.toggleInterest();
//									SearchListActivity.this.reload();
//									dismiss();
//								}
//							});
//					return builder.create();
//				}else{
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
//			}
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
				builder.setMultiChoiceItems(R.array.sport_list,new boolean[]{true, true, true, true, true, true, true, true}, new OnMultiChoiceClickListener() {
					

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						String s = getResources().getStringArray(R.array.sport_list)[which];
						Toast.makeText(getApplicationContext(), (isChecked ? "Yeah for " : "Boo for ") + s + "!", Toast.LENGTH_LONG).show();
						
					}
				});
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
								Toast.makeText(SearchListActivity.this,
										"You are good at setting filters", Toast.LENGTH_LONG).show();
								dismiss();
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
								Toast.makeText(SearchListActivity.this,
										"You are good at setting locations", Toast.LENGTH_LONG).show();
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
					if(getDistance(sport.getLocation()) <= radius){
						events.add(sport);
					}
				}
				
				reload();
			}
		}
	}



	public int getDistance(String location) {
		// TODO Make this a real thing. Duplicate code in ListAdapter needs to be resolved.
		return 15;
	}
	
}
