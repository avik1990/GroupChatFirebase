package com.app.classdiary.group.groupadapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.classdiary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User1 on 19-02-2018.
 */

public class GroupHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.tv_header)
    TextView tv_header;

    @BindView(R.id.tv_details)
    TextView tv_details;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_postedby)
    TextView tv_postedby;

    @BindView(R.id.card_view)
    CardView card_view;


    GroupHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
