package aashna.com.aashna.aashna_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import aashna.com.aashna.R;

/**
 * Created by Dell on 21-Feb-18.
 */

public class MyProfile extends AppCompatActivity {
    String str_getName, str_getPassword, str_getEmail, str_getMobile ,str_gen,str_dob,str_getAdderess;

    TextView name,uname,gender,dob,contact,email,paswd,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_activity);

        str_getName = Splash_Screen.sh.getString("Name", null);
        str_gen = Splash_Screen.sh.getString("Gender",null);
        str_dob = Splash_Screen.sh.getString("DOB",null);
        str_getPassword = Splash_Screen.sh.getString("password", null);
        str_getEmail = Splash_Screen.sh.getString("Email", null);
        str_getMobile = Splash_Screen.sh.getString("Mobile", null);
        str_getAdderess = Splash_Screen.sh.getString("Address",null);

        name = (TextView) findViewById(R.id.tname);
        gender = (TextView) findViewById(R.id.tgen);
        dob = (TextView) findViewById(R.id.tbday);
        contact = (TextView) findViewById(R.id.tmob);
        email = (TextView) findViewById(R.id.temail);
        paswd = (TextView) findViewById(R.id.tpswd);
        address = (TextView) findViewById(R.id.address);


        name.setText("Name : " + str_getName);
        gender.setText( "Gender : " + str_gen);
        dob.setText("DOB: " + str_dob );
        email.setText("Email : " + str_getEmail);
        paswd.setText( "Password : " + str_getPassword);
        contact.setText("Mobile : " + str_getMobile);
        address.setText("Address : "+str_getAdderess);



//        profile.setText("Name : " + str_getName + "\n"+ "\n" + "Gender : "
//                + str_gen + "\n" + "DOB: "
//                + str_dob  + "\n" + "Password : "
//                + str_getPassword + "\n" + "\n" + "Email : " + str_getEmail
//                + "\n" + "\n" + "Mobile : " + str_getMobile);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}

