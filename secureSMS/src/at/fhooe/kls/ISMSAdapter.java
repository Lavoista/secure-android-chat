package at.fhooe.kls;

import info.guardianproject.database.SQLException;
import android.content.Context;
import android.database.Cursor;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISMSAdapter.
 */
public interface ISMSAdapter {
	
	/**
	 * Checks if is open.
	 *
	 * @return true, if is open
	 */
	public boolean isOpen();
	
	/**
	 * Close.
	 */
	public void close();
	
	/**
	 * Creates the note.
	 *
	 * @param email the email
	 * @param key the key
	 * @return the long
	 */
	public long createNote(String email, String key);
	
	/**
	 * Gets the key.
	 *
	 * @param email the email
	 * @return the key
	 */
	public String getKey(String email);
	
	/**
	 * Fetch note.
	 *
	 * @param email the email
	 * @return the cursor
	 * @throws SQLException the sQL exception
	 */
	public Cursor fetchNote(String email) throws SQLException;
	
	/**
	 * Open.
	 *
	 * @param password the password
	 * @return the sMS adapter
	 * @throws SQLException the sQL exception
	 */
	public SMSAdapter open(String password) throws SQLException;
}
