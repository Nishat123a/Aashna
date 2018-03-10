package aashna.com.aashna.H_AskQuestion;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

import aashna.com.aashna.R;

/**
 * Created by Dell on 21-Feb-18.
 */

public class AskQuestion extends TabActivity {
    TabHost tabHost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        tabHost = getTabHost();


        // Tab for Latest
        TabHost.TabSpec latest = tabHost.newTabSpec("Latest");
        // setting Title and Icon for the Tab
        latest.setIndicator("Latest");
        Intent photosIntent = new Intent(this, LatestQuestionActivity.class);
        latest.setContent(photosIntent);

        // Tab for Myquestion
        TabHost.TabSpec myqn = tabHost.newTabSpec("My Question");
        myqn.setIndicator("My Question");
        Intent songsIntent = new Intent(this, MyQuestionActivity.class);
        myqn.setContent(songsIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(latest); // Adding latest tab
        tabHost.addTab(myqn); // Adding myqn tab


    }

}