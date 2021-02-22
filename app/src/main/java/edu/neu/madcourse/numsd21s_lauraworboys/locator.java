package edu.neu.madcourse.numsd21s_lauraworboys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;


public class locator extends AppCompatActivity {
    //constant to act as the permission request
    private static final int LOCATION_REQUEST_CODE = 101;
    LocationManager locationManager;
    private static double usersLat;
    private static double usersLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);

        // on activity create after the view is set up call the the alert permission box
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);

        Button locateMeButton = findViewById(R.id.locateMe);
        locateMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //locate me button onClick get users location
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                getUserLocation();
            }
        });

        // for rotational purposes
        if (savedInstanceState != null) {
            /* both usersLat and usersLong are both private global class variables so they are
            initially set to 0.0 so if rotational changes were to happen it would display this and
            this is not how I want the app to function
            */
            if (usersLat != 0.0 && usersLong != 0.0) {
                usersLat = savedInstanceState.getDouble("userLat");
                TextView showLatUI = findViewById(R.id.latitudeValue);
                showLatUI.setText(String.valueOf(usersLat));

                usersLong = savedInstanceState.getDouble("userLong");
                TextView showLongUI = findViewById(R.id.LongitudeValue);
                showLongUI.setText(String.valueOf(usersLong));

            }
        }
    }


    private void getUserLocation() {

        //check the permission and ask again if they denied originally
        if (ActivityCompat.checkSelfPermission(locator.this,Manifest.permission.ACCESS_FINE_LOCATION)
           != PackageManager.PERMISSION_GRANTED &&
           ActivityCompat.checkSelfPermission(locator.this, Manifest.permission.ACCESS_COARSE_LOCATION)
           != PackageManager.PERMISSION_GRANTED) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed in order for you to see you coordinates")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(locator.this,
                                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(locator.this, "Location Denied", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }
        //try the network connection first
        else {
            boolean locationFound= false;
            if(locationManager != null) {
                Location networkLocation =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (networkLocation != null) {
                   usersLat = networkLocation.getLatitude();
                    TextView showLatUI = findViewById(R.id.latitudeValue);
                    showLatUI.setText(String.valueOf(usersLat));

                    usersLong = networkLocation.getLongitude();
                    TextView showLongUI = findViewById(R.id.LongitudeValue);
                    showLongUI.setText(String.valueOf(usersLong));
                    //set the boolean to used so we know we got the users location already
                    locationFound = true;
                }
            }
            //try the gps location if network location fails
            if(!locationFound){
                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //Taking location and loading to UI so viewer can see
                if (locationGPS != null) {
                    usersLat = locationGPS.getLatitude();
                    TextView showLatUI = findViewById(R.id.latitudeValue);
                    showLatUI.setText(String.valueOf(usersLat));

                     usersLong = locationGPS.getLongitude();
                    TextView showLongUI = findViewById(R.id.LongitudeValue);
                    showLongUI.setText(String.valueOf(usersLong));

                }
                // couldn't find location either through the network or the gps
                else {
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    @Override
//    protected void onRestoreInstanceState (Bundle savedInstanceState) {
//        super.onRestoreInstanceState (savedInstanceState);
//        // Restore our variable by key
//        usersLat = savedInstanceState.getDouble("userLat");
//        TextView showLatUI = findViewById(R.id.latitudeValue);
//        showLatUI.setText(String.valueOf(usersLat));
//
//        usersLong = savedInstanceState.getDouble("userLong");
//        TextView showLongUI = findViewById(R.id.LongitudeValue);
//        showLongUI.setText(String.valueOf(usersLong));
//    }

    // for rotational changes save the instance
    @Override
    protected void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState (outState);
        // Write the variable with the key in the Bundle
        outState.putDouble ("userLat", usersLat);
        outState.putDouble("userLong", usersLong);
    }

}