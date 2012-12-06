package at.fhooe.kls;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatListAdapter.
 */
public class ChatListAdapter extends ArrayAdapter<ChatMessage> {

    /** The items. */
    private ArrayList<ChatMessage> items;
	
	/** The context. */
	private Context context;
	
	/** The colors. */
	private int[] colors = new int[] { 0xFFFF5335, 0xFF306E73 };

    /**
     * Instantiates a new chat list adapter.
     *
     * @param context the context
     * @param textViewResourceId the text view resource id
     * @param items the items
     */
    public ChatListAdapter(Context context, int textViewResourceId, ArrayList<ChatMessage> items) {
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
                v = vi.inflate(R.layout.row, null);
            }
            
            ChatMessage chatMessage = items.get(position);
           
            
            if (chatMessage != null) {
            	 int gravity;
            	 
                 if(chatMessage.isMine()){
                 	v.setBackgroundColor(colors[0]);
                 	gravity=Gravity.LEFT;
                 }
                 else{
                 	gravity=Gravity.RIGHT;
                 	v.setBackgroundColor(colors[1]);
                 }
                    TextView tt = (TextView) v.findViewById(R.id.tMessage);
                    TextView bt = (TextView) v.findViewById(R.id.tTime);
                    
                    
                    if (tt != null) {
                          tt.setText(chatMessage.getSender().split("@")[0]+": "+chatMessage.getMessage());                            }
                    if(bt != null){
                          bt.setText(chatMessage.shortTime());
                    }
                  tt.setGravity(gravity);
                  bt.setGravity(gravity);
            }
            return v;
    }
}
