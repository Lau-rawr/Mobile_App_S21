package edu.neu.madcourse.numsd21s_lauraworboys;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/*
 *
 * view holders belong to the adaptor, they are for every item in the list
 * displays an element with data bound to the adapter obj. as well as keeps track of where it is
 * in the list
 *
 * */


public class linkViewHolder  extends RecyclerView.ViewHolder{


    public TextView urlName;
    public TextView urlAddress;


    // what the user inputs on the screen
    public linkViewHolder(View itemView, final ItemClickListener listener) {
        super(itemView);
        urlName = itemView.findViewById(R.id.urlName);
        urlAddress = itemView.findViewById(R.id.urlAddress);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    //which item are we in the list
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
