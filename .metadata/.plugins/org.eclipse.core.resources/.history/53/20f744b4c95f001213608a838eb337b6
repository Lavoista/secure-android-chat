package at.fhooe.kls;

import info.guardianproject.database.SQLException;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class StartUp.
 */
public class StartUp extends Activity {

	/**
	 * The Enum States.
	 */
	enum States {

		/** The LOGIN. */
		LOGIN,
		/** The START. */
		START
	};

	static boolean DEBUG = true;

	/** The state. */
	States state = States.LOGIN;

	/** The current pass attempts. */
	private int currentPassAttempts;

	/** The TAG. */
	static String TAG = "SMS";

	/** The Constant PREFS_NAME. */
	public static final String PREFS_NAME = "MyPrefsFile";

	/** The m adapter. */
	//SMSAdapter mAdapter;

	String PASSWORD;
	String USERNAME;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < 10) {
			//SQLiteDatabase.loadLibs(this);
			//mAdapter = SecureSMSAdapter.getInstance(this);
		} else
			//mAdapter = SMSAdapter.getInstance(this);

		if (DEBUG) {
			final TelephonyManager tm = (TelephonyManager) getBaseContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm.getDeviceId().equals("351554050051631")) {
				USERNAME = "sip_gateway1";
				PASSWORD = "sip_gateway1";
			} else {
				USERNAME = "sip_gateway2";
				PASSWORD = "sip_gateway2";
			}

		}

		final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final String email = settings.getString("email", "empty");
		if (email.equals("empty")) {
			state = States.LOGIN;
			setContentView(R.layout.login);
			Button login = (Button) findViewById(R.id.btLogin);
			login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					String password = ((EditText) findViewById(R.id.passText))
							.getText().toString();
					password = password.isEmpty() ? PASSWORD : password;
					try {
						// mAdapter.open(password);
						SecureSharedPrefAdapter.open(StartUp.this, password);
						Editor editor = settings.edit();
						String username = ((EditText) findViewById(R.id.userText))
								.getText().toString();
						username = username.isEmpty() ? USERNAME : username;
						editor.putString("email", username);
						editor.commit();

						checkLogin(username, password);

					} catch (SQLException s) {
						Toast.makeText(StartUp.this,
								"Datenbank konnte nicht erstellt werden",
								Toast.LENGTH_LONG).show();
						Log.d(TAG, s.getMessage());
					}

				}
			});

		} else {
			state = States.START;
			setContentView(R.layout.start);
			Button go = (Button) findViewById(R.id.btGo);
			go.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String password = ((EditText) findViewById(R.id.edPassword))
							.getText().toString();
					password = password.isEmpty() ? PASSWORD : password;
					if (unlockDatabase(password)) {

						checkLogin(email, password);

					}
				}
			});
		}
		/*
		 * SecureSMSAdapter sms = SecureSMSAdapter.getInstance(this);
		 * 
		 * Log.d(TAG, "try create note"); sms.createNote("test@gmail.com",
		 * "fasdsa"); Log.d(TAG, "note created");
		 * 
		 * sms.createNote("test2@gmail.com", "fasdsa");
		 * sms.createNote("test3@gmail.com", "fasdsa");
		 * sms.createNote("test4@gmail.com", "fasdsa");
		 * 
		 * Cursor cur = sms.fetchNote("test@gmail.com"); String test =
		 * cur.getString(0); Toast.makeText(this, "test: " + test,
		 * 10000).show(); sms.close();
		 */
	}

	/**
	 * Check login.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	protected void checkLogin(String username, String password) {
		// TODO Auto-generated method stub
		new CheckLoginInBackground()
				.execute(new String[] { username, password });// .doInBackground(new
																// String[]{username,password});
	}

	/**
	 * The Class CheckLoginInBackground.
	 */
	private class CheckLoginInBackground extends
			AsyncTask<String, Integer, Boolean> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		protected Boolean doInBackground(String... params) {
			if (params.length == 2) {
				XMPPClient client = new XMPPClient().getInstance();
				return client.login(params[0], params[1]);
			} else
				return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
		protected void onProgressUpdate(Integer... progress) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(Boolean result) {
			if (result) {

				Intent intent = new Intent(StartUp.this,
						SecureSMSActivity.class);
				// intent.putExtra("password", password);
				startActivity(intent);
			} else {
				Toast.makeText(StartUp.this, "Probleme beim Anmelden",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * Unlock database.
	 * 
	 * @param password
	 *            the password
	 * @return true, if successful
	 */
	private boolean unlockDatabase(String password) {
		return SecureSharedPrefAdapter.open(StartUp.this, password);
		/*
		 * try {
		 * 
		 * // mAdapter.open(password);
		 * 
		 * // reset the pass attempts currentPassAttempts = 0; return true; }
		 * catch (Exception e) { currentPassAttempts++; Toast.makeText(this,
		 * "Falsches Passwort!", Toast.LENGTH_LONG) .show(); return false;
		 * 
		 * }
		 */
	}
}
