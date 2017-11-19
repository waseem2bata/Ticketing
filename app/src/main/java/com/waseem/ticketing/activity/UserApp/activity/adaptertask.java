package com.waseem.ticketing.activity.UserApp.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waseem.ticketing.R;

import java.util.List;

/**
 * Created by waseem on 3/23/2017.
 */

public class adaptertask extends RecyclerView.Adapter<adaptertask.ViewHolder> {

    List<taskItem> taskitems;

    public adaptertask(List<taskItem> taskitems, Context context) {
        this.taskitems = taskitems;
        this.context = context;
    }

    private Context context;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskcard,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        taskItem taskitem = taskitems.get(position);

        holder.tasktext.setText(taskitem.getTask());
        holder.tasknametext.setText(taskitem.getTaskname());

    }

    @Override
    public int getItemCount() {
        return taskitems.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{

        public  TextView tasktext;
        public  TextView tasknametext;

        public ViewHolder(View itemView) {
            super(itemView);

            tasktext = (TextView)itemView.findViewById(R.id.tasktext);
            tasknametext = (TextView)itemView.findViewById(R.id.tasknametext);

        }
    }


}
