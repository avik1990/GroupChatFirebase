package com.app.classdiary.announcement.announceadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.classdiary.dashboard.dashadapter.NotiHolder;
import com.app.classdiary.announcement.AnnouncementDetails;
import com.app.classdiary.R;
import com.app.classdiary.model.Announcements;

import java.util.List;

/**
 * Created by User1 on 19-02-2018.
 */

public class Announcementsadapter extends RecyclerView.Adapter<NotiHolder> {
    private List<Announcements> moviesList;
    Context mContext;

    public Announcementsadapter(Context mContext, List<Announcements> moviesList) {
        this.moviesList = moviesList;
        this.mContext = mContext;
    }

    @Override
    public NotiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        return new NotiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotiHolder holder, final int position) {
        Announcements movie = moviesList.get(position);
        holder.tv_header.setText(movie.getGroup_name());
        holder.tv_details.setText(movie.getGroup_desc());
        holder.tv_date.setText(movie.getCreated_date());
        holder.tv_postedby.setText("created by " + movie.getOwner());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, AnnouncementDetails.class);
                i.putExtra("announcement_id", moviesList.get(position).getGid());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}