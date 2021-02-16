package edu.neu.madcourse.numsd21s_lauraworboys;

import android.net.Uri;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
/*
 * kind of like a json object it tells what the object layout should include and do
 * */

public class urlItemCard implements ItemClickListener {


    // items stored in the object
    private final String urlName;
    private final String urlAddress;
   // public  TextView urlName;


    //constructor
    public urlItemCard(String urlName, String itemURL){
        this.urlName = urlName;
       // setUrlName(urlName);
        this.urlAddress = itemURL;
    }


    //getters
    public String getItemURL() {
        return urlAddress;
    }

    public String getItemName() {
        return urlName ;
    }

    //the itemClick means your clicking in the body of the item
    @Override
    public void onItemClick(int position) {

    }
}
