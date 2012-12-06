package at.fhooe.kls;

import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordPopUp.
 */
public class PasswordPopUp extends Activity implements OnClickListener {
	
	/** The starting intent. */
	private Intent startingIntent;
	
	/** The m adapter. */
	private SMSAdapter mAdapter;
	
	/** The current pass attempts. */
	private int currentPassAttempts=0;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passwordpopup);
		Button okButton = (Button) findViewById(R.id.popup_OKButton);
		okButton.setOnClickListener(this);
		if (Build.VERSION.SDK_INT < 10) {
			mAdapter = SecureSMSAdapter.getInstance(this);
		} else
			mAdapter = SMSAdapter.getInstance(this);

		startingIntent = getIntent();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	

	/**
	 * Unlock database.
	 *
	 * @param password the password
	 * @return true, if successful
	 */
	private boolean unlockDatabase(String password) {

		try {
			mAdapter.open(password);
			// reset the pass attempts
			currentPassAttempts = 0;
			return true;
		} catch (Exception e) {
			currentPassAttempts++;
			Toast.makeText(this, "Falsches Passwort!", Toast.LENGTH_LONG)
					.show();
			return false;

		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.popup_OKButton:
			String password = ((EditText) findViewById(R.id.popup_passwordEdit))
					.getText().toString();
			
			password = "muster1234";
			
			if (unlockDatabase(password)) {
				setResult(RESULT_OK);
			} else {
				setResult(RESULT_CANCELED);
			}

			finish();
			break;

		default:
			break;
		}
	}

}
