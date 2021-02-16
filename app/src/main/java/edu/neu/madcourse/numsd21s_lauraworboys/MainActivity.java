package edu.neu.madcourse.numsd21s_lauraworboys;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view){
        switch(view.getId()){
            //code for each button on the main page
            case R.id.aboutMain:
                startActivity(new Intent(MainActivity.this, AboutMe.class));
                break;
            case R.id.ClickyButton:
                startActivity(new Intent(MainActivity.this, ClickyClicky.class));
                break;
            case R.id.LinkButton:
                startActivity(new Intent(MainActivity.this, LinkCollector.class));
                break;
        }
    }
}