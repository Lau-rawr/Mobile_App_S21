package edu.neu.madcourse.numsd21s_lauraworboys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClickyClicky extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);

    }

    public void onClick(View view){
        TextView alphaButton = (TextView)findViewById(R.id.alphaButton);

        switch(view.getId()){
            //code for each button
            case R.id.button_A:
              alphaButton.setText(R.string.A);
                break;
            case R.id.button_B:
                alphaButton.setText(R.string.B);
                break;
            case R.id.button_C:
                alphaButton.setText(R.string.C);
                break;
            case R.id.button_D:
                alphaButton.setText(R.string.D);
                break;
            case R.id.button_E:
                alphaButton.setText(R.string.E);
                break;
            case R.id.button_F:
                alphaButton.setText(R.string.F);
                break;
        }
    }
}