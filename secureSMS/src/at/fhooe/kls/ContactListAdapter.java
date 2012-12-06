package at.fhooe.kls;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactListAdapter.
 */
public class ContactListAdapter extends ArrayAdapter<ContactItem>{
	
	/** The items. */
	private ArrayList<ContactItem> items;
	
	/** The context. */
	private Context context;
	
	/** The colors. */
	private int[] colors = new int[] { 0xFFFF5335, 0xFF306E73 };

    /**
     * Instantiates a new contact list adapter.
     *
     * @param context the context
     * @param textViewResourceId the text view resource id
     * @param items the items
     */
    public ContactListAdapter(Context context, int textViewResourceId, ArrayList<ContactItem> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
    }

    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.contactrow, null);
            }
           
           //v.setBackgroundColor(colors[position % colors.length]);
            ContactItem contact = items.get(position);
            int color = contact.isAvailable()?colors[1]:colors[0];
            v.setBackgroundColor(color);
            if (contact != null) {
                    
                    TextView bt = (TextView) v.findViewById(R.id.tUser);
                    TextView bs=(TextView)v.findViewById(R.id.tStatus);
                    if(bt != null){
                          bt.setText("User: "+ contact.getUser());
                         
                          
                    }
                    if(bs!=null){
                    	bs.setText("Status: "+contact.getStatus());
                    }
            }
            return v;
    }
}
