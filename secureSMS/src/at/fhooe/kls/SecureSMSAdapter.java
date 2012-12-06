package at.fhooe.kls;

import info.guardianproject.database.SQLException;
import info.guardianproject.database.sqlcipher.*;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class SecureSMSAdapter.
 */
public class SecureSMSAdapter extends SMSAdapter{

	// static String CREATE_CONTACS =
	// "CREATE TABLE Preferences(email varchar(-1),PRIMARY KEY ())";
	/** The EMAIL. */
	static String EMAIL="email";
	
	/** The KEY. */
	static String KEY="key";
	
	/** The DATABAS e_ table. */
	static String DATABASE_TABLE = "Contacts";
	
	/** The CREAT e_ contacs. */
	static String CREATE_CONTACS = "CREATE TABLE "+DATABASE_TABLE+"("+EMAIL+" TEXT,"+KEY+" TEXT)";
	
	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "data";
	
	/** The TAG. */
	static String TAG = "TAG";
	
	/** The instance. */
	private static SecureSMSAdapter instance;
	
	/** The m ctx. */
	private Context mCtx;
	
	/** The m db helper. */
	private DatabaseHelper mDbHelper;
	
	/** The m db. */
	private SQLiteDatabase mDb;

	/**
	 * The Class DatabaseHelper.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		/**
		 * Instantiates a new database helper.
		 *
		 * @param context the context
		 */
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);

		}

		/* (non-Javadoc)
		 * @see info.guardianproject.database.sqlcipher.SQLiteOpenHelper#onCreate(info.guardianproject.database.sqlcipher.SQLiteDatabase)
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_CONTACS);
		}

		/* (non-Javadoc)
		 * @see info.guardianproject.database.sqlcipher.SQLiteOpenHelper#onUpgrade(info.guardianproject.database.sqlcipher.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Instantiates a new secure sms adapter.
	 *
	 * @param ctx the ctx
	 */
	private SecureSMSAdapter(Context ctx) {
		super(ctx);
		this.mCtx = ctx;

	}

	/**
	 * Gets the single instance of SecureSMSAdapter.
	 *
	 * @param ctx the ctx
	 * @return single instance of SecureSMSAdapter
	 */
	public static synchronized SecureSMSAdapter getInstance(Context ctx) {

		if (instance == null) {
			instance = new SecureSMSAdapter(ctx);
			
		}

		return instance;
	}

	/* (non-Javadoc)
	 * @see at.fhooe.kls.SMSAdapter#open(java.lang.String)
	 */
	public SecureSMSAdapter open(String password) throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		
		mDb = mDbHelper.getWritableDatabase(password);

		System.gc();
		return this;
	}

	/* (non-Javadoc)
	 * @see at.fhooe.kls.SMSAdapter#isOpen()
	 */
	public boolean isOpen() {
		if (mDb != null)
			return mDb.isOpen();
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see at.fhooe.kls.SMSAdapter#close()
	 */
	public void close() {

		mDbHelper.close();
	}

	/* (non-Javadoc)
	 * @see at.fhooe.kls.SMSAdapter#createNote(java.lang.String, java.lang.String)
	 */
	public long createNote(String email, String key) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(EMAIL, email);
		initialValues.put(KEY, key);

		if (mDb != null)
			return mDb.insert(DATABASE_TABLE, null, initialValues);
		else
			return -1;
	}
	  
  	/* (non-Javadoc)
  	 * @see at.fhooe.kls.SMSAdapter#fetchNote(java.lang.String)
  	 */
  	public Cursor fetchNote(String email) throws SQLException {

	        Cursor mCursor =

	                mDb.query(true, DATABASE_TABLE, new String[] {KEY}, EMAIL + "='" + email+"'", null,
	                        null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;

	    }

	/* (non-Javadoc)
	 * @see at.fhooe.kls.SMSAdapter#getKey(java.lang.String)
	 */
	@Override
	public String getKey(String email){
		try{
		Cursor c=fetchNote(email);
		return c.getString(0);
		}
		catch(SQLException s){
			Log.d(TAG,s.getMessage());
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG,e.getMessage());
			return "";
		}
	}

}
