package edu.rosehulman.pickupsports;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.appspot.pickupsports_copy.pickupsports.Pickupsports;
import com.appspot.pickupsports_copy.pickupsports.model.Sport;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import edu.rosehulman.pickupsports.SearchListActivity.QueryForEventsTask;


public class CreateEventActivity extends Activity implements View.OnClickListener{
    private Button mButtonDate;
    private Button mButtonTime;
    private Button mButtonPost;
    private Button mButtonCancel;
    private Spinner mSportName;
    private Spinner mSpinnerState;

    private EditText mEditTextAddress;
    private EditText mEditTextCity;
    private EditText mEditTextZip;

    private Calendar c = Calendar.getInstance();

    private Pickupsports mService;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        mButtonPost = (Button) findViewById(R.id.buttonPost);
        mEditTextAddress = (EditText) findViewById(R.id.editTextAddress);
        mSportName = (Spinner) findViewById(R.id.spinner);
        
        mButtonPost.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

    	if(v == mButtonPost){
    		//Kick off insert Async
    		//TODO: Add date, locatoin, and description here. Availability too.
    		Sport sport = new Sport();
    		sport.setName(mSportName.getSelectedItem().toString());
    		sport.setLocation(mEditTextAddress.getText().toString());
    		sport.setDescription("SAMPLE DESCRIPTION");
    		sport.setAvailability("SAMPLE AVAULABILITY");
       		(new InsertEventTask()).execute(sport);
    	}
    }

//    protected void setDate(int year, int month, int day){
//        this.mDate.set(year, month, day);
//    }
//
//    protected void setTime(int hour, int minute){
//        this.mDate.set(Calendar.HOUR_OF_DAY, hour);
//        this.mDate.set(Calendar.MINUTE, minute);
//    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Context context = getActivity().getApplicationContext();
            CharSequence text = "The time chosen is: " + hourOfDay + ":"+ minute;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            Context context = getActivity().getApplicationContext();
            CharSequence text = "The date chosen is: " + month + 1 + "/" + day + "/" + year;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


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
}
