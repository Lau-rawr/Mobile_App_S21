package edu.neu.madcourse.numsd21s_lauraworboys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
    }
    public void onClick(View view) {

        switch (view.getId()) {
            //code for each button
            case R.id.aboutPrevious:
                startActivity(new Intent(AboutMe.this, MainActivity.class));
                break;
        }
    }
}