package edu.rosehulman.pickupsports;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.appspot.pickupsports_copy.pickupsports.Pickupsports;
import com.appspot.pickupsports_copy.pickupsports.model.Sport;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;

import edu.rosehulman.pickupsports.SearchListActivity.QueryForEventsTask;


public class CreateEventActivity extends FragmentActivity implements View.OnClickListener, OnDateSetListener, OnTimeSetListener{
    private Button mButtonDate;
    private Button mButtonTime;
    private Button mButtonPost;
    private Button mButtonCancel;
    private Spinner mSportName;
    private Spinner mSpinnerState;
    private Sport sport = new Sport();
    private EditText mEditTextAddress;
    private EditText mEditTextCity;
    private EditText mEditTextZip;
    private Button mButtonSetAvail;
    private Button mButtonSetDesc;

    private Calendar c = Calendar.getInstance();

    Date date = new Date(System.currentTimeMillis());
    int year = date.getYear();
    int month = date.getMonth();
    int day = date.getDay();
    int hour = date.getHours();
    int minute = date.getMinutes();
    
    
    
    private Pickupsports mService;
    private boolean readyToPost = false;
	private DateTime mDate;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        mButtonPost = (Button) findViewById(R.id.buttonPost);
        mButtonCancel = (Button) findViewById(R.id.buttonCancel);
        mButtonDate = (Button) findViewById(R.id.buttonDate);
        mButtonTime = (Button) findViewById(R.id.buttonTime);
        mButtonSetAvail = (Button) findViewById(R.id.buttonSetAvail);
        mButtonSetDesc = (Button) findViewById(R.id.buttonSetDesc);
        mEditTextAddress = (EditText) findViewById(R.id.editTextAddress);
        mEditTextCity = (EditText) findViewById(R.id.editTextCity);
        mEditTextZip = (EditText) findViewById(R.id.editTextZip);
        mSportName = (Spinner) findViewById(R.id.spinner);
        mSpinnerState = (Spinner) findViewById(R.id.spinnerState);
        mButtonPost.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mButtonDate.setOnClickListener(this);
        mButtonTime.setOnClickListener(this);
        mButtonSetAvail.setOnClickListener(this);
        mButtonSetDesc.setOnClickListener(this);
        Pickupsports.Builder builder = new Pickupsports.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		mService = builder.build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    
    
    
    
    private void showConfirm() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Confirm Posting of New Event");
				builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

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
								if (!CreateEventActivity.this.readyToPost) {
									Toast.makeText(
											CreateEventActivity.this,
											"Generating coordinates, try again later!",
											Toast.LENGTH_LONG).show();
								} else {
									dismiss();
									(new InsertEventTask()).execute(sport);
								}
							}
						});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "help");
		
	}
    
    
    
    
    @Override
    public void onClick(View v) {

    	if(v == mButtonPost){
    		sport.setName(mSportName.getSelectedItem().toString());
    		String address = mEditTextAddress.getText().toString() + " " +mEditTextCity.getText().toString() + ", " + mSpinnerState.getSelectedItem().toString() + " " + mEditTextZip.getText().toString();

    		String date = year + "-" + month + "-" + day + "T" +  hour + ":" + minute;
    		sport.setDate(date);
    		sport.setLocation(address);
    		(new GenerateLatLon()).execute(address);
    		showConfirm();
    	}else if (v == mButtonDate){
    		DialogFragment newFragment = new DatePickerFragment();
    		newFragment.show(getFragmentManager(), "datePicker");
    	}else if (v == mButtonTime) {
    		DialogFragment newFragment = new TimePickerFragment();
    		newFragment.show(getFragmentManager(), "timePicker");
		}else if (v == mButtonSetAvail){
    		showAvail();
    	}else if (v == mButtonSetDesc) {
    		showDesc();
		}
    }
    
    
    
    
    
    private void showDesc() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view = inflater.inflate(R.layout.set_desc, null);
				builder.setView(view);
				final EditText text = (EditText)view.findViewById(R.id.set_desc_edit_text);
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
								
								CreateEventActivity.this.sport.setDescription(text.getText().toString());
								dismiss();
							}
						});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "setDesc");
    }
    
    
    
    private void showAvail() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view = inflater.inflate(R.layout.set_avail, null);
				builder.setView(view);
				final EditText text = (EditText)view.findViewById(R.id.set_avail_edit_text);
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
								
								CreateEventActivity.this.sport.setAvailability(text.getText().toString());
								dismiss();
							}
						});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "setAvail");
    }
    

    protected void setDate(int year, int month, int day){
//        this.mDate.set(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    protected void setTime(int hour, int minute){
//        this.mDate.set(Calendar.HOUR_OF_DAY, hour);
//        this.mDate.set(Calendar.MINUTE, minute);
        this.hour = hour;
        this.minute = minute;
    }

    public static class TimePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), (CreateEventActivity) getActivity(), hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (CreateEventActivity) getActivity(), year, month, day);
        }


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
	    	//Toast.makeText(CreateEventActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
	    	return "";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null){
				Log.e("PS", "Insert GenLatLon, res is null");
			}else{
				//Toast and Go back
				//Toast.makeText(CreateEventActivity.this, "Created new Event!", Toast.LENGTH_LONG).show();
				CreateEventActivity.this.readyToPost = true;
				CreateEventActivity.this.sport.setLatLon(result);
			}
		}
    	
    }
    
    
	class InsertEventTask extends AsyncTask<Sport, Void, Sport>{
		@Override
		protected void onPreExecute(){
			Toast.makeText(CreateEventActivity.this, "Creating new Event!", Toast.LENGTH_LONG).show();
		}
		@Override
		protected Sport doInBackground(Sport... params) {
			Sport toReturn = null;
			try {
				toReturn = mService.sport().insert(params[0]).execute();
			} catch (IOException e) {
				Log.e("PS", "Failed Inserting " + e);
			}
			return toReturn;
		}
		
		@Override
		protected void onPostExecute(Sport result) {
			super.onPostExecute(result);
			if (result == null){
				Log.e("PS", "Insert failed, res is null");
			}else{
				//Toast and Go back
				Toast.makeText(CreateEventActivity.this, "Created new Event!", Toast.LENGTH_LONG).show();
				CreateEventActivity.this.finish();
			}
		}
		
	}
	@Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

 
        int fixedMonth = month+1;
        
        CharSequence text = "The date chosen is: " + year + "/" + fixedMonth + "/" + day;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(CreateEventActivity.this, text, duration);
        toast.show();
        
        this.year = year;
        this.month = month;
        this.day = day;

    }
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
       
        CharSequence text = "The time chosen is: " + hourOfDay + ":"+ minute;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(CreateEventActivity.this, text, duration);
        toast.show();
        
        this.hour = hourOfDay;
        this.minute = minute;
    }
	
}
