package aashna.com.aashna.H_DoctorAppointment;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.FragmentActivity;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.ArrayList;

        import aashna.com.aashna.R;

public class DoctorAppointmentActivity  extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        private ArrayList<Doctor_details> doctor_details;
        Double lat1,lng1;
        String name1,add1,em1,ct1,tm1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.doctor_appoint_activity);
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
        }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                doctor_details = new ArrayList<Doctor_details>();

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
*/
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
                        Doctor_details doctorDetails;
                        // Creating JSONObject from String
                        JSONObject maploc = new JSONObject(myjsonstring);

                        // Creating JSONArray from JSONObject
                        final JSONArray loc = maploc.getJSONArray("Doctors_locations");

                        for (int y = 0; y < loc.length(); y++) {
                                JSONObject jObj = loc.getJSONObject(y);

                                final String name = jObj.getString("doc_name");
                                String lat = String.valueOf(jObj.get("Latitude"));
                                String longi = String.valueOf(jObj.get("Longitude"));
                                final String email = String.valueOf(jObj.get("Email"));
                                final String contact = String.valueOf(jObj.get("Contact"));
                                final String address = String.valueOf(jObj.get("Address"));
                                final String timings = String.valueOf(jObj.get("timing"));

                                Log.d("latitude",lat);
                                Log.d("longitude",longi);

                                doctorDetails = new Doctor_details(name,Double.valueOf(lat),Double.valueOf(longi),email,contact,address,timings);
                                doctor_details.add(doctorDetails);

                                lat1 = Double.valueOf(lat);
                                lng1 = Double.valueOf(longi);

                                addLocationsToMap();
                        }


                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }


        private final void addLocationsToMap() {
                int i = 0;

                for (Doctor_details doc : doctor_details) {
                        LatLng l = new LatLng(doc.getLat(), doc.getLongi());

                        MarkerOptions marker = new MarkerOptions()
                                .position(l)
                                .title(doc.getDoc_name())
                                .snippet("" + i)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(marker);
                        ++i;
                }

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker marker) {

                                try {
                                        Doctor_details details = doctor_details.get(Integer.parseInt(marker
                                                .getSnippet()));

                                        // set details
                                        name1= details.getDoc_name();
                                        em1 = details.getEmail();
                                        ct1 = details.getContact();
                                        tm1 = details.getTiming();
                                        add1 = details.getAddress();

                                        Intent intent = new Intent(DoctorAppointmentActivity.this,Doctor_detail_activity.class);
                                        intent.putExtra("name",name1);
                                        intent.putExtra("address",add1);
                                        intent.putExtra("email",em1);
                                        intent.putExtra("contact",ct1);
                                        intent.putExtra("timing",tm1);
                                        startActivity(intent);
                                       // Toast.makeText(DoctorAppointmentActivity.this, name1+em1+ct1+add1+tm1, Toast.LENGTH_SHORT).show();

                                } catch (ArrayIndexOutOfBoundsException e) {
                                        Log.e("AIOutOfBound", " Occured");
                                }

                        }
                });

        }
}
