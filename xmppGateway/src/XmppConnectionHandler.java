import java.util.Collection;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Packet;


public class XmppConnectionHandler implements ConnectionListener,PacketListener {
	Connection mConn;
	public XmppConnectionHandler(Connection _conn){
		System.out.println("Start Gateway");
		mConn=_conn;
		_conn.addConnectionListener(this);
		_conn.addPacketListener(this, new MessageTypeFilter(Type.chat));
	}

	

	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		System.out.println("connection closed "+mConn.getUser());
	}

	@Override
	public void connectionClosedOnError(Exception _e) {
		
		_e.printStackTrace();
	}

	@Override
	public void reconnectingIn(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnectionSuccessful() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processPacket(Packet _packet) {
		//System.out.println(_packet.toXML());
		printPacket(_packet);
	}
	public void printPacket(Packet _packet){
		if (!(_packet instanceof Message)){
			return;
		}
		Message msg=(Message)_packet;
	
		String p=String.format("from:%s to:%s message:%s",msg.getFrom(),msg.getTo(),msg.getBody());
		System.out.println(p);
	}

}
