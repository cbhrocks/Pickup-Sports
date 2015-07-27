package edu.rosehulman.pickupsports;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class SearchListActivity extends Activity {
	
	ArrayList<SportEvent> events;
	ListAdapter adapter;
	ListView lv;
	SportEvent currentEvent;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);
		lv = (ListView) findViewById(R.id.list_view);
		events = new ArrayList<SportEvent>();
		
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
		
		
		loadList();
		
	}


	


	//demo method
	private void loadList() {
		
		
		//Demo Purposes Only
		
		String sampleDescription = "This is a string that will house the description of the event, stating extra information that the user wants to share, such as certain rules, variations, etc.";
		SportEvent s1 = new SportEvent(1, "Soccer", sampleDescription, null, null, "Anyone");
		SportEvent s2 = new SportEvent(2, "FootBall", sampleDescription, null, null, "Only cool KidZ");
		SportEvent s3 = new SportEvent(3, "Frisbee", sampleDescription, null, null, "Anyone");
		SportEvent s4 = new SportEvent(4, "Soccer", sampleDescription, null, null, "Anyone");
		SportEvent s5 = new SportEvent(5, "Tennis", sampleDescription, null, null, "Residents of cherry street");
		SportEvent s6 = new SportEvent(6, "Rocket League", sampleDescription, null, null, "Anyone who is awesome");
		
		events.add(s1);
		events.add(s2);
		events.add(s3);
		events.add(s4);
		events.add(s5);
		events.add(s6);
		adapter = new ListAdapter(this,events);
		lv.setAdapter(adapter);
	}


		private void reload() {
			adapter = new ListAdapter(this,events);
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
		
		
		currentEvent = (SportEvent) adapter.getItem(position);
		
		DialogFragment df = new DialogFragment() {
			

			
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view = inflater.inflate(R.layout.event_detail, null);
				((TextView) view.findViewById(R.id.detail_sport)).setText(SearchListActivity.this.currentEvent.getSport());
				((TextView) view.findViewById(R.id.detail_desc)).setText(SearchListActivity.this.currentEvent.getDescription());
				((TextView) view.findViewById(R.id.detail_avail)).setText(SearchListActivity.this.currentEvent.getAvailability());
				((TextView) view.findViewById(R.id.detail_date)).setText("Date not yet implemented");
				((TextView) view.findViewById(R.id.detail_loc)).setText("Location not yet implemented");
				builder.setView(view);
				builder.setNegativeButton(R.string.cancel_string,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						});
				
				if (SearchListActivity.this.currentEvent.isInterested()) {
					builder.setPositiveButton(R.string.event_decline,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(SearchListActivity.this,
											"Not Interesting!", Toast.LENGTH_LONG).show();
									
									SearchListActivity.this.toggleInterest();
									SearchListActivity.this.reload();
									dismiss();
								}
							});
					return builder.create();
				}else{
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
			}}
		};
		



		df.show(getFragmentManager(), "showDetail");
	}


	protected void toggleInterest() {
		int index = this.events.indexOf(currentEvent);
		events.get(index).setInterested(!currentEvent.isInterested());
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
												"You are good at setting redius", Toast.LENGTH_LONG).show();
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
}
