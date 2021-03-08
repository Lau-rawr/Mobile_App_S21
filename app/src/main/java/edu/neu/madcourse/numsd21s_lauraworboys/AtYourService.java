package edu.neu.madcourse.numsd21s_lauraworboys;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
/*
* "single_rider": false,
* "what_it_is":
* "height_restriction"
* "opened_on": "1975-01-15",
*  "wet": false,*/

public class AtYourService extends AppCompatActivity {
    private TextView rideDuration;
    private TextView rideOpened;
    private TextView rideHeight;
    private Spinner spinner;
    private TextView updates;
    private LinkedHashMap<String, String> rides2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        rideDuration = (TextView)findViewById(R.id.rideDuration);
        rideOpened = (TextView)findViewById(R.id.rideOpenDate);
        spinner = (Spinner) findViewById(R.id.rideNames);
        updates = (TextView)findViewById(R.id.rideProgess);
        rideHeight = (TextView)findViewById(R.id.heightRes);


        // https://demonuts.com/android-spinner/
        rides2 = new LinkedHashMap<>();
        rides2.put("Astro Orbiter", "astro-orbiter");
        rides2.put("Buzz Lightyear's Space Ranger Spin","buzz-lightyears-space-ranger-spin" );
        rides2.put("Dumbo the Flying Elephant","dumbo-the-flying-elephant");
        rides2.put("Hall of Presidents", "hall-of-presidents");
        rides2.put("The Haunted Mansion", "haunted-mansion");
        rides2.put("It's a Small World","its-a-small-world");
        rides2.put("Jungle Cruise", "jungle-cruise");
        rides2.put("Mad Tea Party","mad-tea-party");
        rides2.put("Magic Carpets","magic-carpets-of-aladdin");
        rides2.put("The Many Adventures of Winnie the Pooh","many-adventures-of-winnie-the-pooh");
        rides2.put("Mickey's PhilharMagic","mickeys-philharmagic");
        rides2.put("Monsters, Inc. Laugh Floor", "monsters-inc-laugh-floor");
        rides2.put("Peter Pan's Flight","peter-pans-flight");
        rides2.put("PeopleMover","tomorrowland-transit-authority-peoplemover");
        rides2.put("Pirate's Adventure","a-pirates-adventure-treasures-of-seven-seas");
        rides2.put("Pirates of the Caribbean","pirates-of-the-caribbean");
        rides2.put("Prince Charming Regal Carrousel","prince-charming-regal-carrousel");
        rides2.put("Seven Dwarfs Mine Train","seven-dwarfs-mine-train");
        rides2.put( "Space Mountain", "space-mountain");
        rides2.put("Splash Mountain","splash-mountain");
        rides2.put("Thunder Mountain","big-thunder-mountain-railroad");
        rides2.put("Tomorrowland Speedway","tomorrowland-speedway");
        rides2.put("Under the Sea","under-the-sea");
        rides2.put("Carousel of Progress","walt-disneys-carousel-of-progress");

        List<String> ridesKeyValues = new ArrayList<>(rides2.keySet());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ridesKeyValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
    }


/*To save the state information override onSaveInstanceState() method and add key-value pairs
to the Bundle object that is saved in the event that your activity is destroyed unexpectedly.
This method gets called before onStop().*/
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of item position
        CharSequence rideDur = rideDuration.getText();
        outState.putCharSequence("savedDuration", rideDur);

        CharSequence rideOpen = rideOpened.getText();
        outState.putCharSequence("savedDate", rideOpen);

        CharSequence updatesChar = updates.getText();
        outState.putCharSequence("savedUpdates", updatesChar);

        CharSequence rideHgt = rideHeight.getText();
        outState.putCharSequence("savedHeight", rideHgt);
    }


/*To recover your saved state from the Bundle override onRestoreInstanceState() method.
This is called after onStart() and before onResume().*/
    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Read the state of item position
        CharSequence rideDur = savedInstanceState.getCharSequence("savedDuration");
        rideDuration.setText(rideDur);

        CharSequence rideOpen = savedInstanceState.getCharSequence("savedDate");
        rideOpened.setText(rideOpen);

        CharSequence updatesChar = savedInstanceState.getCharSequence("savedUpdates");
        updates.setText(updatesChar);

        CharSequence rideHgt = savedInstanceState.getCharSequence("savedHeight");
        rideHeight.setText(rideHgt);
    }


    public void requestInfo (View view){
        InfoRetrieval info = new InfoRetrieval();
        try{
            String selection = spinner.getSelectedItem().toString();
            // getting the value of the key that was selected
            String selectionAddDash = rides2.get(selection);

            String serviceURL =
                    " https://touringplans.com/magic-kingdom/attractions/" + selectionAddDash +
                            ".json";
            info.execute(serviceURL);
        } catch (Exception e){
            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    private class InfoRetrieval extends AsyncTask<String, Integer, String[]> {
        /*The onPreExecute() method is called before the background tasks are initiated and can be
         * used to perform initialization steps.
         *
         * This method runs on the main thread so may be used to update the user interface. * */
        @Override
        protected void onPreExecute() {
        }

        /* The code to be performed in the background on a different thread from the main thread
         * resides in the doInBackground() method.
         *
         * This method does not have access to the main thread so cannot make user interface changes.*/
        @Override
        protected String[] doInBackground(String... rideUrl) {

            String[] rideInfo = new String[3];

            try {
                publishProgress(0);
                URL url = new URL(rideUrl[0]);

                //open connection and pass input
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                //tell the connection to connect
                conn.connect();

                // Read response.
                InputStream inputStream = conn.getInputStream();
                final String resp = convertStreamToString(inputStream);

                // string to json object from the response
                JSONObject rideObj = new JSONObject(resp);

                //getting data fields we need the name is the key part of the key value pair
                rideInfo[0] = Double.toString(rideObj.getDouble("duration"));
                rideInfo[1] = rideObj.getString("opened_on");

                // the way the API is written is either an int or null
                try {
                    String rideHgt = Integer.toString(rideObj.getInt("height_restriction"));
                    rideInfo[2]=rideHgt;
                }catch(JSONException e){
                    rideInfo[2]="0";
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return rideInfo;
        }


        /* The onProgressUpdate() method, however, is called each time a call is made to the
         * publishProgress() method from within the doInBackground() method and can be used
         * to update the user interface with progress information.*/
        @Override
        protected void onProgressUpdate(Integer... values) {
            updates.setText(R.string.getting_ride_info);
        }


        /* The onPostExecute() method is called when the tasks performed within the
         * doInBackground() method complete.
         *
         * This method is passed the value returned by the doInBackground() method and runs
         * within the main thread allowing user interface updates to be made.*/

        protected void onPostExecute(String[] rideInfo) {
            super.onPostExecute(rideInfo);
            //displays the text returned by the doInBackground() method
            rideDuration.setText(rideInfo[0]);
            rideOpened.setText(rideInfo[1]);
            rideHeight.setText(rideInfo[2]);
            updates.setText(R.string.done);

        }


        /**
         * Helper function to convert stream to a string
         */
        private String convertStreamToString(InputStream is) {
            Scanner s = new Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next().replace(",", ",\n") : "";
        }
    }
}