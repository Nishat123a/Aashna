package aashna.com.aashna.aashna_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import aashna.com.aashna.R;

public class MainActivity extends AppCompatActivity {

    Button safetybtn,healthbtn;
   /* private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        safetybtn =(Button) findViewById(R.id.safetybtn);
        healthbtn = (Button) findViewById(R.id.healthbtn);
       /* fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener( this);
        fab2.setOnClickListener(this);*/

        safetybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Safety_Mainscreen.class);
                startActivity(intent);
            }
        });

        healthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Health_Mainscreen.class);
                startActivity(intent);
            }
        });
    }

  /*  @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                Log.d("App", "plus");
                break;
            case R.id.fab1:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Dollar Conversion for your smart phone to perform currency conversion with respect to USD."
                        +"\n Link: " + "http://play.google.com/store/apps/details?id=com.innovatum.dollarconversion";
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Sharing Options"));

                Log.d("App", "rate");
                break;
            case R.id.fab2:
                Intent intent = new Intent(MainActivity.this,MyProfile.class);
                startActivity(intent);
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.innovatum.dollarconversion")));
                Log.d("App", "profile");
                break;
        }
    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
*/
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_actions,menu);

        menu.findItem(R.id.profile).setTitle(Html.fromHtml("<font color='#212121'>Profile</font>"));
        menu.findItem(R.id.rateus).setTitle(Html.fromHtml("<font color='#212121'>Rate us</font>"));
        menu.findItem(R.id.share_app).setTitle(Html.fromHtml("<font color='#212121'>Share</font>"));
        menu.findItem(R.id.logout).setTitle(Html.fromHtml("<font color='#212121'>Logout</font>"));
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this,MyProfile.class);
                startActivity(intent);
                return true;

            case R.id.rateus:
               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                return true;

            case R.id.share_app:
              /*  Intent intent8 = new Intent(Intent.ACTION_SEND);
                intent8.putExtra(Intent.EXTRA_TEXT, "Check out In Case of Emergency app for your smart phone.Safety is in your hands !!" + "\nlink : " + "http://play.google.com/store/apps/details?id="+getPackageName());
                intent8.setType("text/plain");
                startActivity(intent8);*/
                return true;
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "You have successfully logout",
                        Toast.LENGTH_LONG).show();
                Splash_Screen.editor.remove("loginTest");

                Splash_Screen.editor.commit();

                Intent moveToMain = new Intent(getApplicationContext(), Login_Activity.class);
                moveToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                moveToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                moveToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(moveToMain);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                MainActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
