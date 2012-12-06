package at.fhooe.kls;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactItem.
 */
public class ContactItem implements Parcelable {
	
	/** The user. */
	private String user;
	
	/** The available. */
	private boolean available;
	
	/** The key. */
	private String key;
	
	/** The status. */
	private String status;
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}


	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}


	/**
	 * Instantiates a new contact item.
	 *
	 * @param user the user
	 */
	public ContactItem( String user) {
		
		this.user = user;
		
	}
	
	
	/**
	 * Checks if is available.
	 *
	 * @return true, if is available
	 */
	public boolean isAvailable() {
		return available;
	}


	/**
	 * Sets the available.
	 *
	 * @param available the new available
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}


	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(String user) {
		this.user = user;
	}


	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ContactItem> CREATOR = new Parcelable.Creator<ContactItem>() {
        public ContactItem createFromParcel(Parcel in) {
            return new ContactItem(in);
        }

        public ContactItem[] newArray(int size) {
            return new ContactItem[size];
        }
    };
	
	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(user);
		dest.writeString(key);
		dest.writeInt(available?1:0);
	}
	
	/**
	 * Instantiates a new contact item.
	 *
	 * @param in the in
	 */
	private ContactItem(Parcel in) {
        user = in.readString();
        key=in.readString();
        available=in.readInt()==1?true:false;
    }
	
}
