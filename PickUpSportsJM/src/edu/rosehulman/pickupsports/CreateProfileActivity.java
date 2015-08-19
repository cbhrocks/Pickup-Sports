package edu.rosehulman.pickupsports;

import java.io.IOException;

import com.appspot.horton_mcnelly_pickup_sports.pickupsports.Pickupsports;
import com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.Profile;
import com.appspot.horton_mcnelly_pickup_sports.pickupsports.model.Sport;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateProfileActivity extends Activity implements OnClickListener{
	EditText mEditTextFirstName;
	EditText mEditTextLastName;
	EditText mEditTextPhoneNumber;
	Button mButtonAccept;
	Button mButtonCancel;
	Profile mProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_profile);
		mEditTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
		mEditTextLastName = (EditText) findViewById(R.id.editTextLastName);
		mEditTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
		mButtonAccept = (Button) findViewById(R.id.buttonAccept);
		mButtonCancel = (Button) findViewById(R.id.buttonCancel);
		mButtonAccept.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
		mProfile = new Profile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == mButtonAccept){
			mProfile.setFirstName(mEditTextFirstName.getText().toString());
			mProfile.setLastName(mEditTextLastName.getText().toString());
			mProfile.setPhoneNumber(mEditTextPhoneNumber.getText().toString());
//			Log.d("PS", "Using account name (in createProfile) = " + SearchListActivity.mCredential.getSelectedAccountName());
			(new InsertProfileTask()).execute(mProfile);
			CreateProfileActivity.this.finish();
		} else if (v == mButtonCancel){
			CreateProfileActivity.this.finish();
		}
	}
	
	class InsertProfileTask extends AsyncTask<Profile, Void, Profile>{
		@Override
		protected void onPreExecute(){
			Toast.makeText(CreateProfileActivity.this, "Creating new Profile!", Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected Profile doInBackground(Profile... params) {
			Profile toReturn = null;
			try {
				toReturn = SearchListActivity.mService.profile().insert(params[0]).execute();
			} catch (IOException e) {
				Log.e("PS", "Failed Inserting " + e);
			}
			return toReturn;
		}
		
		@Override
		protected void onPostExecute(Profile result) {
			super.onPostExecute(result);
			if (result == null){
				Log.e("PS", "Insert failed, res is null");
			}else{
				//Toast and Go back
				Toast.makeText(CreateProfileActivity.this, "Created Your Profile!", Toast.LENGTH_LONG).show();
				CreateProfileActivity.this.finish();
			}
		}
		
	}
}
