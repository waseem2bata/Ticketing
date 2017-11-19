package com.waseem.ticketing.activity.UserApp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waseem.ticketing.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by waseem on 2/19/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{


    private List<listItem> listItems;

    public Context context;
    public MyAdapter activity;

    public MyAdapter(List<listItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card , parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final listItem listitem = listItems.get(position);
        holder.namet.setText(listitem.getName());
        holder.emailt.setText(listitem.getEmail());
        activity = this;

        holder.buttontask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myactivity = new Intent(context.getApplicationContext(), AddTask.class);
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                Bundle b = new Bundle();
                String email = listitem.getEmail();
                b.putString("text", email);
                myactivity.putExtras(b);
                context.getApplicationContext().startActivity(myactivity);
            }
        });


    }






    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public Context getApplicationContext() {
        return context;
    }

    public  class  ViewHolder extends  RecyclerView.ViewHolder{

        public TextView namet,emailt;
        public EditText taskname,taskdetails;
        public Button buttontask;
        public LinearLayout layout;
        public CardView card;
        public CardView createtask;




        public ViewHolder(View itemView) {

            super(itemView);

            namet = (TextView) itemView.findViewById(R.id.tasktext);
            emailt = (TextView) itemView.findViewById(R.id.emailtext);

            buttontask = (Button) itemView.findViewById(R.id.createtask);
            card = (CardView) itemView.findViewById(R.id.card_view);
            layout = (LinearLayout) itemView.findViewById(R.id.lin);



        }

    }

}
