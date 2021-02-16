package edu.neu.madcourse.numsd21s_lauraworboys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
 * adapter binds the data to each view holder object &
 * connects (the app data) to the recycler view(ui )
 * provides the xml layout that is used to load the view holders
 * */
public class linkViewAdapter extends RecyclerView.Adapter<linkViewHolder> {
    // taking out url item card from that class and making an arraylist of them

    private final ArrayList<urlItemCard> urlList;
    private ItemClickListener listener;

    //constructor
    public linkViewAdapter(ArrayList<urlItemCard> urlList){
        this.urlList = urlList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    //inflate it
    public linkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //layout of the card itself
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_item_card, parent, false);
        //make a new viewHolder
        return new linkViewHolder(view, listener);
    }

    // setting everything (icon, text, checked or not)
    @Override
    public void onBindViewHolder(linkViewHolder holder, int position) {
        urlItemCard currentItem = urlList.get(position);

       holder.urlName.setText((CharSequence) currentItem.getItemName());
       holder.urlAddress.setText((CharSequence) currentItem.getItemURL());

    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }


}
