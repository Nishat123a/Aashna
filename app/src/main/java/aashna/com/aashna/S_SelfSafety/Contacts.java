package aashna.com.aashna.S_SelfSafety;

/**
 * Created by dell on 1/12/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.Toast;

import aashna.com.aashna.R;

public class Contacts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int PICK_CONTACT = 1;

    EditText mText, mNum;
    Button mAdd,mPick,instruct;
    ListView mList;
    //Database Variables
    MyDbHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    SimpleCursorAdapter mAdapter;
    //Collapsing toolbar Variable
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayoutAndroid;
    CoordinatorLayout rootLayoutAndroid;


  NotificationManager mNotificationManager;

    Context context;
     Switch mservice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
//Initilizing toolbar variables
       /* toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initInstances();




        mText = (EditText) findViewById(R.id.name);
        mNum = (EditText) findViewById(R.id.number);
        mAdd = (Button) findViewById(R.id.add);
        mPick = (Button) findViewById(R.id.pick);
        instruct = (Button) findViewById(R.id.instruction);
        mservice = (Switch) findViewById(R.id.switch_btn);

        //focus on button

        mPick.setFocusable(true);
        mPick.setFocusableInTouchMode(true);
        mPick.requestFocus();
        //Onclick listner for adding contacts
        mList = (ListView) findViewById(R.id.list);
        //Onclick listner for list items
        mList.setOnItemClickListener(this);
        mHelper = new MyDbHelper(this);
        if (!Contact_permission())
            if (!SMS_permission())
                if (!Call_permission())
                    if (!Location_permission())
                        if(!Storage_permission())

            instruct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), HelpScreen.class);
                    startActivity(intent);
                }
            });

            //Picking Contacts from Phone
            mPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                         mText.setError(null);
                         mNum.setError(null);
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT);

                }

            });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // if user enter name and number manually then get then
                    String Name = mText.getText().toString();
                    String Number = mNum.getText().toString();
                    //if user tries to

                    if (mText.length() == 0) {
                        mText.setError("Enter Name");
                    } else if (mNum.length() == 0) {
                        mNum.setError("Enter Number");
                    } else {
                        mText.setText("");
                        mNum.setText("");
                        AddContact(Name,Number);
                        mCursor.requery();
                        mAdapter.notifyDataSetChanged();
                    }
            }
        });


        final boolean isServiceRunning = isServiceRunning(LockService_two.class.getName(),getApplicationContext());
        if(!isServiceRunning){
            mservice.setChecked(false);
        }else {
            Cursor data = mHelper.getListContents();
            if (data.getCount() !=0) {
                mservice.setChecked(true);
            }else {
                mservice.setChecked(false);
                stopService(new Intent(getApplicationContext(), LockService_two.class));

            }

        }


            mservice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        if (mList.getAdapter().getCount() == 0) {
                            Toast.makeText(getApplicationContext(), "Please add emergency contacts", Toast.LENGTH_SHORT).show();
                            mservice.setChecked(false);
                        } else {
                            startService(new Intent(getApplicationContext(), LockService_two.class));
                            Toast.makeText(getApplicationContext(), "Enabled", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        stopService(new Intent(getApplicationContext(), LockService_two.class));
                        Toast.makeText(getApplicationContext(), "Disabled", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    //method to add contacts to database
    private void AddContact(String mName, String mNumber) {
        //inserting into data base
        boolean insertData = mHelper.addContact(mName,mNumber);

        //if user unable to insert show toast
        if (insertData == false){
            Toast.makeText(Contacts.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
        }
    }

public boolean Contact_permission(){

    if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {

        requestPermissions(new String[]{
                Manifest.permission.READ_CONTACTS},10);

        return true;
    }
    return false;
}
public boolean SMS_permission(){
    if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ) {

        requestPermissions(new String[]{
                Manifest.permission.SEND_SMS},10);

        return true;
    }
    return false;
}
    public boolean Call_permission(){
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {

            requestPermissions(new String[]{
                    Manifest.permission.CALL_PHONE},10);

            return true;
        }
        return false;
    }
    public boolean Location_permission(){
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},10);

            return true;
        }
        return false;
    }
    public boolean Storage_permission(){
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},10);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                } else {
                    // Permission Denied
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }





    @Override
    public void onResume() {
        super.onResume();
        //Populating ListView
        mDb = mHelper.getWritableDatabase();
        String[] columns = new String[]{"_id", MyDbHelper.COL_NAME, MyDbHelper.COL_NUM};
        mCursor = mDb.query(MyDbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        String[] headers = new String[]{MyDbHelper.COL_NAME, MyDbHelper.COL_NUM};
        mAdapter = new SimpleCursorAdapter(this,R.layout.contacts_row,
                mCursor, headers, new int[]{R.id.tvname,R.id.tvnumber});
        mList.setAdapter(mAdapter);

    }

    @Override
    public void onPause() {
        super.onPause();
        mDb.close();
        mCursor.close();

    }


    //Deleting Contacts from List and Database
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        mCursor.moveToPosition(position);
        String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
        mDb.delete(MyDbHelper.TABLE_NAME, "_id = ?", new String[]{rowId});
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());
    }


    //Selecting Contacts
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            mNum.setText(cNumber);


                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        mText.setText(name);



                    }
                }
                break;
        }

    }
   /* @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    *//**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * *//*
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.myhelp:
                Intent intent = new Intent(this, HelpScreen.class);
                startActivity(intent);
                return true;

            case R.id.rateus:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                return true;

            case R.id.share_app:
                Intent intent8 = new Intent(Intent.ACTION_SEND);
                intent8.putExtra(Intent.EXTRA_TEXT, "Check out In Case of Emergency app for your smart phone.Safety is in your hands !!" + "\nlink : " + "http://play.google.com/store/apps/details?id="+getPackageName());
                intent8.setType("text/plain");
                startActivity(intent8);
                return true;

            case R.id.agree:
                Intent intent5 = new Intent(this, Agreement.class);
                startActivity(intent5);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }*/
    private void initInstances() {
        rootLayoutAndroid = (CoordinatorLayout) findViewById(R.id.android_coordinator_layout);
        collapsingToolbarLayoutAndroid = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_android_layout);
        collapsingToolbarLayoutAndroid.setTitle("Aashna");
    }


    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
