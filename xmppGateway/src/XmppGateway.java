import javax.swing.JFrame;
import javax.swing.plaf.SliderUI;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class XmppGateway extends java.awt.event.WindowAdapter {
	JFrame mFrame;
	XmppPanel mPanel;
	public XmppGateway() {
		super();
		initUI();
	}
	public void initUI() {
		mFrame = new JFrame();
		mFrame.setSize(800, 600);
		mFrame.addWindowListener(this);
		try{
		mPanel= new XmppPanel();
		}
		catch(Exception _e){
			_e.printStackTrace();
		}
		mFrame.add(mPanel);
		mFrame.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Connection conn1 = new XMPPConnection("jabber.org");
		// conn1.connect();
		
		XmppGateway xmpp = new XmppGateway();
		
		
	}

}
