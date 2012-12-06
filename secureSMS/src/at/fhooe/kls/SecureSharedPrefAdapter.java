package at.fhooe.kls;

import info.guardianproject.database.SQLException;

import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SecureSharedPrefAdapter {
	private static String mPassword;
	private static String PREFS_NAME="USERS";
	private static String mHashedPassword;
	private static Context mContext;
	public static boolean open(Context _ctx,String _password){
		mContext=_ctx;
		SharedPreferences settings = _ctx.getSharedPreferences(PREFS_NAME, 0);

		String hashedPassword = settings.getString("hashedPassword", "");
		String tempHash = "";
		try {
			tempHash = Util.toHex(Hash.SHA256(_password.getBytes()));
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
			return false;
		

		mPassword = _password;
		return true;

		
	}
	
	public static String getKey(String email){
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		String key= settings.getString(email, "");
		if(key.isEmpty())
			return "";
		try {
			return new String(Util.decrypt(Hash.SHA256(mPassword.getBytes()),
					Util.toByte(key)));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public static void insertKey(String email,String key){
		try {
			key = Util.toHex(Util.encrypt(Hash.SHA256(mPassword.getBytes()),
					key.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		Editor edit=settings.edit();
		edit.putString(email, key);
		edit.commit();
	}
}
