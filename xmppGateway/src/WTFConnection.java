import org.jivesoftware.smack.XMPPConnection;


public class WTFConnection extends XMPPConnection{

	public WTFConnection(){
		super("wtfismyip.com");
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return getUser();
	}

}
