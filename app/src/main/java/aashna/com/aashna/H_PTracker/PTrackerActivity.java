package aashna.com.aashna.H_PTracker;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import aashna.com.aashna.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dell on 17-Feb-18.
 */

public class PTrackerActivity  extends AppCompatActivity {

    TextView sdate,ndate;
    Button submit;
    EditText cycle;
    CharSequence strDate = null;
    DatePickerDialog datePickerDialog;
    int userInput=0;
    String sd,nd,cl,newSelectedDate;

    // Create object of SharedPreferences.
    SharedPreferences sharedPref;
    //now get Editor
    SharedPreferences.Editor editor;
    private ScheduleClient scheduleClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* //This is to show logo
       getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        setContentView(R.layout.ptracker_activity);
        sharedPref= getSharedPreferences("mypref", 0);

        editor= sharedPref.edit();

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        sdate = (TextView) findViewById(R.id.tv_sdate);
        ndate = (TextView) findViewById(R.id.tv_ndate);
        submit = (Button) findViewById(R.id.btn_date);
        cycle = (EditText) findViewById(R.id.ed_clength);


        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(PTrackerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Time chosenDate = new Time();
                                chosenDate.set(dayOfMonth, monthOfYear, year);
                                long dtDob = chosenDate.toMillis(true);

                                strDate = DateFormat.format("dd/MM/yyyy", dtDob);
                                // set day of month , month and year value in the edit text
                                sdate.setText(/*dayOfMonth + "/" + (monthOfYear + 1) + "/" + year*/strDate);

                                Log.e("sd",">>>>>>>>>>>"+sd);
                                Log.e("sdate.getText()",">>>>>>>>>>>>>>>>>>"+sdate.getText());

                                if(sd != sdate.getText()){
                                    editor.remove("clength");
                                    editor.remove("ndate");
                                    ndate.setText("");
                                    cycle.setText("");
                                    editor.commit();
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        cycle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Convert the editable to int.
                String val = cycle.getText().toString();
                if (cycle.getText().toString().trim().length() < 0) {

                    Toast.makeText(getApplicationContext(), "Please enter your cycle length",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        userInput = Integer.parseInt(val);
                    } catch (NumberFormatException e) {
                        val = " ";// this will cause the parameter check for quantity ordered to fail and pop toast
                    }
                }
                //userInput = Integer.parseInt(cycle.getText().toString());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("User Input Length",">>>>>>>>>>"+userInput);
                getIncrementedDate(String.valueOf(strDate));
                //put your value
                editor.putString("sdate", String.valueOf(strDate));
                editor.putString("ndate", String.valueOf(newSelectedDate));
                editor.putString("clength", String.valueOf(userInput));
                //commits your edits
                editor.commit();

            }
        });

        sd = sharedPref.getString("sdate", "");
        nd = sharedPref.getString("ndate", "");
        cl = sharedPref.getString("clength", "");

        sdate.setText(sd);
        ndate.setText(nd);
        cycle.setText(cl);
    }

    public String getIncrementedDate(String selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(String.valueOf(strDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, userInput);  // number of days to add
        selectedDate = sdf.format(c.getTime());  // selectedDate is now the new date

        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c);
        // Notify the user what they just did
        //Toast.makeText(this, "Notification set for: "+c, Toast.LENGTH_SHORT).show();
        ndate.setText(selectedDate);
        newSelectedDate = selectedDate;
        return selectedDate;
    }

}