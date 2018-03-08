package com.app.classdiary.dashboard.dashadapter;

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

public class NotiHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.tv_header)
    public
    TextView tv_header;

    @BindView(R.id.tv_details)
    public
    TextView tv_details;

    @BindView(R.id.tv_date)
    public
    TextView tv_date;

    @BindView(R.id.tv_postedby)
    public
    TextView tv_postedby;

    @BindView(R.id.card_view)
    public CardView card_view;


    public NotiHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
