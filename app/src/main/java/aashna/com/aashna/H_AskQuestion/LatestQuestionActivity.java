package aashna.com.aashna.H_AskQuestion;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import aashna.com.aashna.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Dell on 21-Feb-18.
 */

public class LatestQuestionActivity extends AppCompatActivity {

    //Declairing ArrayList for Storing Country name and price
    private ArrayList<FAQ_pojo> faqlist;
    private RecyclerView recyclerView;
    private FAQAdapter faqadapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_question);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new GetFAQ().execute();


    }

    class GetFAQ extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(LatestQuestionActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();
        }



        @Override
        protected Void doInBackground(Void... arg0) {

            faqlist = new ArrayList<FAQ_pojo>();

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

                FAQ_pojo faqpojo;
                // Creating JSONObject from String
                JSONObject faq = new JSONObject(myjsonstring);

                // Creating JSONArray from JSONObject
                JSONArray mag = faq.getJSONArray("FAQ");

                for (int y = 0; y < mag.length(); y++) {

                    JSONObject jObj = mag.getJSONObject(y);

                    String name = jObj.getString("PersonName");
                    String category = jObj.getString("Heath_category");
                    String question = jObj.getString("Question");

                    faqpojo = new FAQ_pojo(name,category,question);
                    faqlist.add(faqpojo);
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
            faqadapter = new FAQAdapter(faqlist, LatestQuestionActivity.this);
            // Attach the adapter to the recyclerview to populate itemsce
            recyclerView.setAdapter(faqadapter);
            pDialog.dismiss();
        }
    }
}

