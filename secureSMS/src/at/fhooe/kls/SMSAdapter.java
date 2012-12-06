package at.fhooe.kls;

import java.security.NoSuchAlgorithmException;

import info.guardianproject.database.SQLException;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSAdapter.
 */
public class SMSAdapter implements ISMSAdapter {

	/** The Constant PREFS_NAME. */
	public static final String PREFS_NAME = "smsadapter";

	/** The EMAIL. */
	static String EMAIL = "email";

	/** The KEY. */
	static String KEY = "key";

	/** The DATABAS e_ table. */
	static String DATABASE_TABLE = "Contacts";

	/** The CREAT e_ contacs. */
	static String CREATE_CONTACS = "CREATE TABLE " + DATABASE_TABLE + "("
			+ EMAIL + " TEXT," + KEY + " TEXT)";

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "data";

	/** The TAG. */
	static String TAG = "TAG";

	/** The instance. */
	private static SMSAdapter instance;

	/** The m ctx. */
	private Context mCtx;

	/** The m db helper. */
	private DatabaseHelper mDbHelper;

	/** The m db. */
	private SQLiteDatabase mDb;

	/** The m password. */
	private String mPassword;

	/** The m hashed password. */
	private String mHashedPassword;

	/**
	 * The Class DatabaseHelper.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		/**
		 * Instantiates a new database helper.
		 * 
		 * @param context
		 *            the context
		 */
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database
		 * .sqlite.SQLiteDatabase)
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_CONTACS);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database
		 * .sqlite.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Instantiates a new sMS adapter.
	 * 
	 * @param ctx
	 *            the ctx
	 */
	protected SMSAdapter(Context ctx) {

		this.mCtx = ctx;

	}

	/**
	 * Gets the single instance of SMSAdapter.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return single instance of SMSAdapter
	 */
	public static synchronized SMSAdapter getInstance(Context ctx) {

		if (instance == null) {
			instance = new SMSAdapter(ctx);

		}

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.fhooe.kls.ISMSAdapter#open(java.lang.String)
	 */
	public SMSAdapter open(String password) throws SQLException {

		SharedPreferences settings = mCtx.getSharedPreferences(PREFS_NAME, 0);

		String hashedPassword = settings.getString("hashedPassword", "");
		String tempHash = "";
		try {
			tempHash = Util.toHex(Hash.SHA256(password.getBytes()));
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (hashedPassword.isEmpty()) {
			SharedPreferences.Editor editor = settings.edit();
			mHashedPassword = tempHash;
			editor.putString("hashedPassword", mHashedPassword);
			editor.commit();
		} else {
			mHashedPassword = hashedPassword;
		}
		if (!tempHash.equals(mHashedPassword))
			throw new SQLException();

		mDbHelper = new DatabaseHelper(mCtx);
		mPassword = password;
		mDb = mDbHelper.getWritableDatabase();

		System.gc();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.fhooe.kls.ISMSAdapter#isOpen()
	 */
	public boolean isOpen() {
		if (mDb != null)
			return mDb.isOpen();
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.fhooe.kls.ISMSAdapter#close()
	 */
	public void close() {

		mDbHelper.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.fhooe.kls.ISMSAdapter#createNote(java.lang.String,
	 * java.lang.String)
	 */
	public long createNote(String email, String key) {

		// check existing

		ContentValues initialValues = new ContentValues();
		initialValues.put(EMAIL, email);
		try {
			key = Util.toHex(Util.encrypt(Hash.SHA256(mPassword.getBytes()),
					key.getBytes()));
			initialValues.put(KEY, key);
			if (mDb != null)
				if (getKey(email).isEmpty()) {
					return mDb.insertOrThrow(DATABASE_TABLE, null,
							initialValues);
				} else
					return mDb.update(DATABASE_TABLE, initialValues, EMAIL
							+ "=" + email, null);
			else
				return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			Log.d(TAG, e.getMessage() == null ? "Exception" : e.getMessage());
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, e.getMessage() == null ? "Exception" : e.getMessage());
			return -1;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.fhooe.kls.ISMSAdapter#getKey(java.lang.String)
	 */
	public String getKey(String email) {
		try {
			Cursor c = fetchNote(email);
			if (c.getCount() == 0)
				return "";
			String key = c.getString(0);
			return new String(Util.decrypt(Hash.SHA256(mPassword.getBytes()),
					Util.toByte(key)));

		} catch (SQLException s) {
			Log.d(TAG, s.getMessage());
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, e.getMessage());
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.fhooe.kls.ISMSAdapter#fetchNote(java.lang.String)
	 */
	public Cursor fetchNote(String email) throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE, new String[] { KEY }, EMAIL + "='"
				+ email + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}
}
