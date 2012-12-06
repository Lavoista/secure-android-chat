package at.fhooe.kls;

import org.jivesoftware.smack.XMPPConnection;


// TODO: Auto-generated Javadoc
/**
 * The Class XMPPLogic.
 */
public class XMPPLogic {

  /** The connection. */
  private XMPPConnection connection = null;

  /** The instance. */
  private static XMPPLogic instance = null;

  /**
   * Gets the single instance of XMPPLogic.
   *
   * @return single instance of XMPPLogic
   */
  public synchronized static XMPPLogic getInstance() {
    if(instance==null){
      instance = new XMPPLogic();
    }
    return instance;
  }

  /**
   * Sets the connection.
   *
   * @param connection the new connection
   */
  public void setConnection(XMPPConnection connection){
    this.connection = connection;
  }

  /**
   * Gets the connection.
   *
   * @return the connection
   */
  public XMPPConnection getConnection() {
    return this.connection;
  }

}