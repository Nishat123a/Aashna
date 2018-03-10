package aashna.com.aashna.S_ContributeToNGO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import aashna.com.aashna.R;

public class C_NGO extends AppCompatActivity {

    //Declairing ArrayList for Storing Country name and price
    private ArrayList<C_ngo_pojo> c_ngo_list;
    private RecyclerView recyclerView;
    private C_NGO_Adapter Cadapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_ngo);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new Contribute().execute();


    }

    class Contribute extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(C_NGO.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();
        }



        @Override
        protected Void doInBackground(Void... arg0) {

            c_ngo_list = new ArrayList<C_ngo_pojo>();

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

                C_ngo_pojo cp;
                // Creating JSONObject from String
                JSONObject buy = new JSONObject(myjsonstring);

                // Creating JSONArray from JSONObject
                JSONArray mag = buy.getJSONArray("Contribute_NGO");

                for (int y = 0; y < mag.length(); y++) {

                    JSONObject jObj = mag.getJSONObject(y);

                    String NGO_name = jObj.getString("NGO_Name");
                    String account = jObj.getString("Account_number");

                    cp = new C_ngo_pojo(NGO_name,account);
                    c_ngo_list.add(cp);
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
            Cadapter = new C_NGO_Adapter(c_ngo_list, C_NGO.this);
            // Attach the adapter to the recyclerview to populate itemsce
            recyclerView.setAdapter(Cadapter);
            pDialog.dismiss();
        }
    }
}
