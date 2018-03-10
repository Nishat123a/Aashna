package aashna.com.aashna.S_SelfSafety;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import aashna.com.aashna.R;

import java.util.List;


/**
 * Created by srisai1 on 9/30/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    private Context mContext;
    int previousPosition = 0;
    MyDbHelper myDB;
    String dbRowID,dbName,dbNumber;
    int pos;
    String curr_lat,curr_lon,curr_address;


    /*Direct reference to each of the views within a data item
      Used to cache the views within the item layout for fast access*/
    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Number, address, date, copy, share;
        public Button button_call,button_sms,button_close;

        /* Constructor that accepts the entire item row
        and does the view lookups to find each subview*/
        public ViewHolder(final View itemView) {
            super(itemView);
             Name= (TextView) itemView.findViewById(R.id.text_name);
            Number = (TextView) itemView.findViewById(R.id.text_number);

            button_call = (Button) itemView.findViewById(R.id.b_call);
            button_sms = (Button) itemView.findViewById(R.id.b_sms);



        }


    }

    // Store a member variable for the Map
    private List<Map> mMap;

    // Pass in the Map array into the constructor
    public RecyclerViewAdapter(List<Map> maps, Context context) {
        mMap = maps;
        mContext = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Easy access to the context object in the recyclerview
        Context context = parent.getContext();
        // Usually involves inflating a layout from XML and returning the holder
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_layout, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;


    }


    @Override
    // Involves populating data into the item through holder
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Map map = mMap.get(position);

        // Set item views based on your views and data model
        final TextView mLatitude = viewHolder.Name;
        mLatitude.setText(map.getName());

        final TextView mLongitude = viewHolder.Number;
        mLongitude.setText(map.getNumber());

        final Button mCall = viewHolder.button_call;
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
                ((Activity)mContext).finish();
            }
        });

        final Button mSms = viewHolder.button_sms;
        mSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_sms();
                ((Activity)mContext).finish();
            }
        });


        if (position > previousPosition) { //we are scrolling DOWN

            AnimationUtil.animate(viewHolder, true);

        } else {  //we are scrolling UP

            AnimationUtil.animate(viewHolder, false);
        }
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpositio(position);
                pos = getpositio(position);
               call();
                ((Activity)mContext).finish();
            }
        });

        mSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpositio(position);
                pos = getpositio(position);
                send_sms();
                ((Activity)mContext).finish();

            }
        });


    }

    //getting address and rowid from database
    public void getContactsCount() {
        myDB = new MyDbHelper(mContext);
        //gets data from database
        Cursor data = myDB.getListContents();
        if (data.getCount() != 0) {
            //moving cursor object to fetch data from database
            data.moveToPosition(pos);
            Map maps = new Map(data.getString(0), data.getString(1), data.getString(2));
            dbRowID = data.getString(0);
            int row = Integer.parseInt(dbRowID);

        }
    }


    public void call() {

        myDB = new MyDbHelper(mContext);
        //gets data from database
        Cursor data = myDB.getListContents();
        if (data.getCount() != 0) {
            //moving cursor object to fetch data from database
            data.moveToPosition(pos);
            Map maps = new Map(data.getString(0), data.getString(1), data.getString(2));
            dbName = data.getString(1);
            dbNumber = data.getString(2);

            Intent callIntent = new Intent();
            callIntent.setAction(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + dbNumber));
            if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mContext.startActivity(callIntent);
        }

    }
    //Sending Emergency SMS to stored contacts
    public void send_sms() {

        curr_lat= View_Maps.sh.getString("current_lat", null);
        curr_lon = View_Maps.sh.getString("current_lon", null);
       myDB = new MyDbHelper(mContext);
        Cursor data = myDB.getListContents();
        if (data.getCount() != 0) {
            //moving cursor object to fetch data from database
            data.moveToPosition(pos);
            Map maps = new Map(data.getString(0), data.getString(1), data.getString(2));
            dbName = data.getString(1);
            dbNumber = data.getString(2);
            Log.d("Number", "----------" + dbNumber);

            String msg = "I'm in Emergency!Contact me" ;

            String msg_one = "I'm in Emergency!Contact me" + "\nLatitude : " + curr_lat +
                    "\nLongitude : " + curr_lon + "\nlink : " + "http://www.google.com/maps/place/" + curr_lat + "," + curr_lon;


            final String locationProviders = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (locationProviders == null || locationProviders.equals("")) {

                SmsManager.getDefault().sendTextMessage(dbNumber, null, msg, null, null);

            }else {
                SmsManager.getDefault().sendTextMessage(dbNumber, null, msg_one, null, null);

            }

        }

    }


// get position of clicked item
    public int getpositio(int position) {
        mMap.get(position);
        return position;

    }


    @Override
    // Returns the total count of items in the list
    public int getItemCount() {
        return mMap.size();
    }
}
