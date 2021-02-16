package edu.neu.madcourse.numsd21s_lauraworboys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/*
 * Acting like our layout manager, controls how the view holders are displayed
 * */
public class LinkCollector extends AppCompatActivity {

    private ArrayList<urlItemCard> urlList = new ArrayList<>();
    private RecyclerView recyclerView;
    private linkViewAdapter viewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private FloatingActionButton addLinkFAB;
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the layout of the UI
        setContentView(R.layout.activity_link_collector);
        createRecyclerView();

        //get the floating action button and set a listener
        addLinkFAB = findViewById(R.id.addLinkFAB);
        addLinkFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            //FAB button onClick adding new url
            public void onClick(View view) {
                showURLAlert();
            }
        });

        //Specify what action a specific gesture performs, in this case swiping right or left
        // deletes the entry swipe direction is left or right
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //if someone tries to drag the items to a different order, dont allow -> false
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinkCollector.this, "Delete an item", Toast.LENGTH_SHORT).show();
                //which item is being swiped and remove it from the list
                int position = viewHolder.getLayoutPosition();
                urlList.remove(position);

                //notify that it has been removed, and that its only one thing
                viewAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showURLAlert(){
        // create new alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter URL Name and Actual URL");

        // create the input for the alert edittext so user can enter in input
        final EditText urlNameInput = new EditText(this);
        urlNameInput.setHint("Name of URL");
        final EditText urlAddressInput = new EditText(this);
        urlAddressInput.setHint("Actual URL");

        // Specify the type of input expected type variation
        urlNameInput.setInputType( InputType.TYPE_TEXT_VARIATION_NORMAL);
        urlAddressInput.setInputType( InputType.TYPE_TEXT_VARIATION_URI);

        //layout
        LinearLayout alertLayout = new LinearLayout(this);
        alertLayout.setOrientation(LinearLayout.VERTICAL);
        alertLayout.addView(urlNameInput);
        alertLayout.addView(urlAddressInput);
        builder.setView(alertLayout);

        // Set up add button
        builder.setPositiveButton("Add URL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //get user input
                String urlNameString = urlNameInput.getText().toString();
                String urlAddressString = urlAddressInput.getText().toString();

                //check if valid URL
                if(isUrlValid(urlAddressString)){
                    //send the input to addItem so that it can be added
                    addItem( urlNameString, urlAddressString);
                }
                else {
                    //invalid url so let user know and exit alert


                    Toast toast = Toast.makeText(LinkCollector.this, "URL is Invalid",
                            Toast.LENGTH_LONG);
                    View viewToast = toast.getView();
                    viewToast.setBackgroundColor(Color.YELLOW);
                    toast.show();
                    dialog.cancel();
                }
            }
        });

       // cancel button, if users wants to cancel and not add url
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private boolean isUrlValid(String urlInput){
            try{
                //allowing www as well as http and https
                if(urlInput.toLowerCase().startsWith("www")){
                    return true;
                }
                new URL(urlInput).toURI();
                return true;
            } catch(MalformedURLException | URISyntaxException e){
                return false;
            }
    }

    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int size = urlList == null ? 0 : urlList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item(which is the value)
        for (int i = 0; i < size; i++) {
            // put itemName information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", urlList.get(i).getItemName());
            // put itemDesc information into instance
            outState.putString(KEY_OF_INSTANCE + i + "2", urlList.get(i).getItemURL());
        }
        // save it, because a config change will make the activity be recreated so need to save whats
        //currently in it
        super.onSaveInstanceState(outState);
    }

     //the UI
    private void createRecyclerView() {
        rLayoutManger = new LinearLayoutManager(this);

        //get the recyclerView from the resource (activity_link_collector.xml)
        recyclerView = findViewById(R.id.UrlList);

        // tell it that it has a fixed size makes recalculations much faster
        recyclerView.setHasFixedSize(true);
        viewAdapter = new linkViewAdapter(urlList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
               //getting the position of item in list that has been clicked
                urlList.get(position).onItemClick(position);
                viewAdapter.notifyItemChanged(position);

               /*From textbook:a new intent object is created. Instead of specifying the class
               name of the intent, however, the code simply indicates the nature of the intent (to display
               something to the user) using the ACTION_VIEW option */

                try{
                    String urlAddress = urlList.get(position).getItemURL();
                    if (urlAddress.startsWith("www")){
                       urlAddress = "http://" + urlAddress;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
                    startActivity(intent);

                }catch (Exception e){
                    showURLAlert();
                }
            }
        };
        viewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    private void addItem( String urlName, String urlAddress) {
            //always adding new items to top of list like a stack
        urlList.add(0, new urlItemCard( urlName, urlAddress));
        Toast toast = Toast.makeText(LinkCollector.this, "URL Successfully Added!",
                Toast.LENGTH_LONG);
        View viewToast = toast.getView();
        viewToast.setBackgroundColor(Color.rgb(227, 28, 255));
        toast.show();
        viewAdapter.notifyItemInserted(0);
    }
}