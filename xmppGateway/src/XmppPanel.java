import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.net.SocketFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Packet;

public class XmppPanel extends JPanel implements ConnectionListener,
		PacketListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5657339239939443076L;
	private HashMap<String, WTFConnection> m_connections = new HashMap<>();
	private HashMap<String, String> m_accounts = new HashMap<>();
	private HashMap<String, String> m_relation = new HashMap<>();
	private JList m_connectionList;
	private JTextArea m_logArea;
	private JScrollPane scrollPane_2;
	private DefaultListModel<WTFConnection> m_listModel;
	private JLabel lblXmppConnections;
	private JLabel label;
	private JLabel label_1;
	private ConnectionConfiguration xmppConfig;
	private ConnectionConfiguration m_config;
	private static final int DELAY = 1000;

	public XmppPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblXmppConnections = new JLabel("XMPP Connections");
		add(lblXmppConnections);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		m_listModel = new DefaultListModel<>();
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
		// m_accounts.put("sip_gateway1", "sip_gateway1");
		// m_accounts.put("sip_gateway2", "sip_gateway2");

		// m_relation.put("sip_gateway1", "Berni");
		// m_relation.put("sip_gateway2", "Patrick");
		m_accounts.put("sip_berni_gw", "sip_berni_gw");
		m_accounts.put("sip_patrick_gw", "sip_patrick_gw");
		m_accounts.put("sip_berni", "sip_berni");
		m_accounts.put("sip_patrick", "sip_patrick");
		xmppConfig = new ConnectionConfiguration("jabber.org", 5222);
		xmppConfig
				.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		xmppConfig.setSocketFactory(SocketFactory.getDefault());
		m_config = new ConnectionConfiguration("twattle.net", 5222, "xmppgw");
		//m_config.setCompressionEnabled(false);
		//m_config.setSASLAuthenticationEnabled(false);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				connect();
				m_connectionList.updateUI();
				System.out.println("account: " + m_listModel.size());
			}
		}).start();

	}

	public void connect() {
		System.out.println("start connect");
		
		for (final Entry<String, String> e : m_accounts.entrySet()) {

			try {

				// Connection.DEBUG_ENABLED = true;
				// WTFConnection.DEBUG_ENABLED = true;
				WTFConnection conn = new WTFConnection(m_config);
				 conn = new WTFConnection("twattle.net");
				conn.connect();
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				WTFConnection
						.addConnectionCreationListener(new ConnectionCreationListener() {

							@Override
							public void connectionCreated(Connection conn) {
								// TODO Auto-generated method stub
								System.out.println(conn.getUser());

							}
						});
				conn.login(e.getKey(), e.getValue());
				conn.addPacketListener(this, new MessageTypeFilter(Type.chat));

				conn.addConnectionListener(this);
				m_connections.put(e.getKey(), conn);
				m_listModel.addElement(conn);
				System.out.println("connected" + e.getKey());
			} catch (XMPPException _e) {
				System.err.println("connection exception");
				_e.printStackTrace();
				// _e.printStackTrace();
			} catch (Exception _e) {
				System.err.println("generel exception");
			}
		}
		//m_connectionList.updateUI();
		System.out.println("connect finished");
	}

	public void disconnect() {
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
		// System.out.println(_packet.toXML());
		printPacket(_packet);
	}

	public void printPacket(Packet _packet) {
		if (!(_packet instanceof Message)) {
			return;
		}
		Message msg = (Message) _packet;
		if(msg.getBody()==null)
			return;
		String to = m_relation.get(msg.getTo());
		if (to == null) {
			m_logArea.append("No relation found for: " + msg.getTo()+"message: "+msg.getBody()+"\n");
			return;
		}
		String p = String.format("from:%s to:%s sip:(%s)\n\tmessage:%s\n",
				msg.getFrom(), msg.getTo(), to, msg.getBody());
		System.out.println(p);
		m_logArea.append(p);
		m_logArea.notifyAll();

		// Server.handleXMPPMessage(msg.getFrom(),m_relation.get(msg.getTo()),msg.getBody());

	}

	public void handleSIPMessage(String from, String to, String msg) {
		Connection conn = m_connections.get(to);
		try {
			Message message = new Message(to, Message.Type.chat);
			message.setBody(msg);
			conn.sendPacket(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
