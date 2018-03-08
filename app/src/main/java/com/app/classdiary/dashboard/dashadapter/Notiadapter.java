package com.app.classdiary.dashboard.dashadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.classdiary.model.Notification;
import com.app.classdiary.R;

import java.util.List;

/**
 * Created by User1 on 19-02-2018.
 */

public class Notiadapter extends RecyclerView.Adapter<NotiHolder> {
    private List<Notification> moviesList;
    public Notiadapter(List<Notification> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public NotiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        return new NotiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotiHolder holder, int position) {
        Notification movie = moviesList.get(position);
        holder.tv_header.setText(movie.getTxt_header());
        holder.tv_details.setText(movie.getTxt_desc());
        holder.tv_date.setText(movie.getDate());
        holder.tv_postedby.setText("posted by "+movie.getPosted_by());

        if(movie.getType().equals("1")){
            holder.icon.setBackgroundResource(R.drawable.ic_notification);
        }else{
            holder.icon.setBackgroundResource(R.drawable.ic_group);
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}