import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Message.Type;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class XmppPanel extends JPanel implements ConnectionListener,PacketListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5657339239939443076L;
	private HashMap<String, WTFConnection> m_connections = new HashMap<>();
	private HashMap<String, String> m_accounts = new HashMap<>();
	private JList m_connectionList;
	private JTextArea m_logArea;
	private JScrollPane scrollPane_2;
	private DefaultListModel<WTFConnection> m_listModel;
	private JLabel lblXmppConnections;
	private JLabel label;
	private JLabel label_1;

	public XmppPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblXmppConnections = new JLabel("XMPP Connections");
		add(lblXmppConnections);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		m_listModel=new DefaultListModel<>();
		m_connectionList = new JList(m_listModel);
		scrollPane.setViewportView(m_connectionList);
		
		scrollPane_2 = new JScrollPane();
		add(scrollPane_2);
		
		m_logArea = new JTextArea();
		scrollPane_2.setViewportView(m_logArea);
		m_logArea.setRows(10);
		m_logArea.setColumns(60);
		
		label = new JLabel("");
		add(label);
		
		label_1 = new JLabel("");
		add(label_1);
		m_accounts.put("sip_gateway1", "sip_gateway1");
		m_accounts.put("sip_gateway2", "sip_gateway2");
		
		connect();
	}

	public void connect() {
		WTFConnection conn;
		for (Entry<String, String> e : m_accounts.entrySet()) {
			try {
				conn = new WTFConnection();
				conn.connect();
				conn.login(e.getKey(), e.getValue());
				conn.addPacketListener(this,new MessageTypeFilter(Type.chat));
				conn.addConnectionListener(this);
				m_connections.put(e.getKey(), conn);
				m_listModel.addElement(conn);
			} catch (XMPPException _e) {
				System.err.println("connection exception");
				_e.printStackTrace();
			}
		}
		m_connectionList.updateUI();
	}
	public void disconnect(){
		for (Entry<String, WTFConnection> e : m_connections.entrySet()) {
			try {
				e.getValue().disconnect();
			} catch (Exception _e) {
				System.err.println("disconnect exception");
				_e.printStackTrace();
			}
		}
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
	
		String p=String.format("from:%s to:%s message:%s\n",msg.getFrom(),msg.getTo(),msg.getBody());
		m_logArea.append(p);
		m_logArea.notifyAll();
	}

	@Override
	public void connectionClosed() {
		System.out.println("connection closed");
	}

	@Override
	public void connectionClosedOnError(Exception e) {
		e.printStackTrace();
	}

	@Override
	public void reconnectingIn(int arg0) {
		System.out.println("reconnecting");
	}

	@Override
	public void reconnectionFailed(Exception arg0) {	
		System.out.println("reconnection failed");
	}

	@Override
	public void reconnectionSuccessful() {
		System.out.println("reconnection successful");
	}

}
