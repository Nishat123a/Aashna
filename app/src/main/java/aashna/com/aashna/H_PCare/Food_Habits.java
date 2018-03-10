package aashna.com.aashna.H_PCare;

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

public class Food_Habits extends AppCompatActivity {

    //Declairing ArrayList for Storing Country name and price
    private ArrayList<Food_habits_pojo> foodlist;
    private RecyclerView recyclerView;
    private FoodAdapter foodadapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_habits);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new GetFoodDetails().execute();


    }

    class GetFoodDetails extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(Food_Habits.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();
        }



        @Override
        protected Void doInBackground(Void... arg0) {

            foodlist = new ArrayList<Food_habits_pojo>();

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

                Food_habits_pojo foodpojo;
                // Creating JSONObject from String
                JSONObject food = new JSONObject(myjsonstring);

                // Creating JSONArray from JSONObject
                JSONArray mag = food.getJSONArray("Food_Habits");

                for (int y = 0; y < mag.length(); y++) {

                    JSONObject jObj = mag.getJSONObject(y);

                    String Food_name = jObj.getString("Food");

                    foodpojo = new Food_habits_pojo(Food_name);
                    foodlist.add(foodpojo);
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
            foodadapter = new FoodAdapter(foodlist, Food_Habits.this);
            // Attach the adapter to the recyclerview to populate itemsce
            recyclerView.setAdapter(foodadapter);
            pDialog.dismiss();
        }
    }
}

