package at.fhooe.kls;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatMessage.
 */
public class ChatMessage implements Parcelable {
	
	/** The sender. */
	private String sender;
	
	/** The time. */
	private Date time;
	
	/** The message. */
	private String message;
	
	/** The mine. */
	private boolean mine;

	/**
	 * Instantiates a new chat message.
	 *
	 * @param sender the sender
	 * @param time the time
	 * @param message the message
	 */
	public ChatMessage(String sender, Date time, String message) {
		this.sender = sender;
		this.time = time;
		this.message = message;
		this.mine = false;
	}

	/**
	 * Instantiates a new chat message.
	 *
	 * @param sender the sender
	 * @param time the time
	 * @param message the message
	 * @param mine the mine
	 */
	public ChatMessage(String sender, Date time, String message, boolean mine) {
		this(sender, time, message);
		this.mine = mine;
	}

	/**
	 * Gets the sender.
	 *
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the sender.
	 *
	 * @param sender the new sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * Short time.
	 *
	 * @return the string
	 */
	public String shortTime() {

		StringBuilder sb = new StringBuilder();
		sb.append(time.getHours());
		sb.append(":");
		sb.append(time.getMinutes());
		return sb.toString();
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * Checks if is mine.
	 *
	 * @return true, if is mine
	 */
	public boolean isMine() {
		return mine;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(sender);
		dest.writeLong(time.getTime());
		dest.writeString(message);
		dest.writeByte((byte) (mine ? 1 : 0));

	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ChatMessage> CREATOR = new Parcelable.Creator<ChatMessage>() {
		public ChatMessage createFromParcel(Parcel in) {
			return new ChatMessage(in);
		}

		public ChatMessage[] newArray(int size) {
			return new ChatMessage[size];
		}
	};

	/**
	 * Instantiates a new chat message.
	 *
	 * @param in the in
	 */
	public ChatMessage(Parcel in) {
		sender=in.readString();
		time= new Date(in.readLong());
		message=in.readString();
		mine=in.readByte()==1;
	}
}
