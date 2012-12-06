package at.fhooe.kls;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class XMPPClient.
 */
public class XMPPClient {

	 /** The conn. */
 	private XMPPConnection conn;
	 
 	/** The instance. */
 	static XMPPClient instance;
	
	/**
	 * Login.
	 *
	 * @param _user the _user
	 * @param _pass the _pass
	 * @return true, if successful
	 */
	public boolean login(String _user, String _pass) {
		try {
			conn = new XMPPConnection("gmail.com");
			conn.connect();

			// We do not need user name in this case.
			conn.login(_user, _pass);
			XMPPLogic.getInstance().setConnection(conn);
			return true;
		} catch (XMPPException ex) {
			Log.e("WLM", ex.toString());
			conn = null;
			return false;
		}

	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public synchronized XMPPConnection getConnection()
	{
		return conn;
	}
	
	/**
	 * Gets the single instance of XMPPClient.
	 *
	 * @return single instance of XMPPClient
	 */
	public synchronized static XMPPClient getInstance() {
	    if(instance==null){
	      instance = new XMPPClient();
	    }
	    return instance;
	  }
	
	/**
	 * Send message.
	 *
	 * @param to the to
	 * @param text the text
	 */
	public void sendMessage(String to, String text) {
		Message msg = new Message(to, Message.Type.chat);
		msg.setBody(text);
		conn.sendPacket(msg);
	}

	/**
	 * Gets the roster.
	 *
	 * @return the roster
	 */
	public Roster getRoster() {
		return conn.getRoster();
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {

		return conn.isConnected();
	}
}
