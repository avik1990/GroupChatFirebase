package com.app.classdiary.group.groupadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.classdiary.dashboard.dashadapter.NotiHolder;
import com.app.classdiary.announcement.AnnouncementsList;
import com.app.classdiary.group.groupmodel.Group;
import com.app.classdiary.R;

import java.util.List;

/**
 * Created by User1 on 19-02-2018.
 */

public class Groupadapter extends RecyclerView.Adapter<NotiHolder> {
    private List<Group> moviesList;
    Context mContext;

    public Groupadapter(Context mContext,List<Group> moviesList) {
        this.moviesList = moviesList;
        this.mContext=mContext;
    }

    @Override
    public NotiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        return new NotiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotiHolder holder, int position) {
        Group movie = moviesList.get(position);
        holder.tv_header.setText(movie.getGroup_name());
        holder.tv_details.setText(movie.getGroup_desc());
        holder.tv_date.setText(movie.getCreated_date());
        holder.tv_postedby.setText("created by " + movie.getOwner());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, AnnouncementsList.class);
                mContext.startActivity(i);
            }
        });


       /* holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, AnnouncementsList.class);
                mContext.startActivity(i);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}