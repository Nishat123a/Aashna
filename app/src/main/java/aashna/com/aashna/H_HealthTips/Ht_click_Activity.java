package aashna.com.aashna.H_HealthTips;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import aashna.com.aashna.R;
import aashna.com.aashna.aashna_main.Tips;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ht_click_Activity extends AppCompatActivity {

    //Declairing ArrayList for Storing Country name and price
    private ArrayList<Tips> htips;
    private RecyclerView recyclerView;
    private Ht_adapter Hadapter;
    private ProgressDialog pDialog;
    String get_title;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ht_click);

        Bundle bundle = getIntent().getExtras();
        get_title = bundle.getString("Title");

        title = (TextView) findViewById(R.id.click_title);
        title.setText(get_title);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new GetNewsFeed().execute();


    }

    class GetNewsFeed extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(Ht_click_Activity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();
        }



        @Override
        protected Void doInBackground(Void... arg0) {

            htips = new ArrayList<Tips>();

            // Reading json file from assets folder
            StringBuffer sb = new StringBuffer();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getApplicationContext().getAssets().open("listview.json")));
                String temp;
                while ((temp = br.readLine()) != null)
                    sb.append(temp);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close(); // stop reading
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String myjsonstring = sb.toString();
            // Try to parse JSON
            try {

                Tips tips_pojo;
                // Creating JSONObject from String
                JSONObject magazines = new JSONObject(myjsonstring);

                // Creating JSONArray from JSONObject
                JSONArray mag = magazines.getJSONArray("Health_tips");

                for (int y = 0; y < mag.length(); y++) {

                    JSONObject jObj = mag.getJSONObject(y);

                    String title = jObj.getString("Tip");
                    String desc = jObj.getString("Description");

                    tips_pojo = new Tips(title,desc);
                    htips.add(tips_pojo);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Create adapter passing in the sample user data
            Hadapter = new Ht_adapter(htips, Ht_click_Activity.this);
            // Attach the adapter to the recyclerview to populate itemsce
            recyclerView.setAdapter(Hadapter);
            pDialog.dismiss();
        }
    }
}
