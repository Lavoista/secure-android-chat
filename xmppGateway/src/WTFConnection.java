
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

public class WTFConnection extends XMPPConnection{

	public WTFConnection(ConnectionConfiguration config) {
		super(config);
		// TODO Auto-generated constructor stub
	}
	public WTFConnection(String server){
		//super("wtfismyip.com");
		super(server);
		//super("blah.at");
		//super("jabber.blah.at");
	}
	public WTFConnection(){
		//super("wtfismyip.com");
		super("jabber.org");
		//super("blah.at");
		//super("jabber.blah.at");
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return getUser();
	}

}
