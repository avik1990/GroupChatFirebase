package com.app.classdiary.group.creategroup;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.app.classdiary.R;
import com.app.classdiary.group.groupmodel.Members;
import com.app.classdiary.utils.GroupMembers;
import com.app.classdiary.utils.LCHashMapUtils;
import com.app.classdiary.utils.LCUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MemberClubAdapter extends ArrayAdapter<Members> implements Filterable {
    Context mContext;
    LayoutInflater inflater;
    int layoutResourceId;
    List<Members> dataMembers;
    String flag;
    private ArrayList<Members> arraylist;
    public static List<Boolean> m_positions;
    public static List<String> dataMembers_temp_id = new ArrayList<>();


    public MemberClubAdapter(Context context, int layoutResourceId, List<Members> members, String flag) {
        super(context, layoutResourceId, members);
        this.dataMembers = members;
        this.flag = flag;
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.arraylist = new ArrayList<Members>();
        this.arraylist.addAll(members);
        m_positions = new ArrayList<>(members.size());
        dataMembers_temp_id.clear();
        for (int i = 0; i < dataMembers.size(); i++) {
            dataMembers_temp_id.add(dataMembers.get(i).getMember_id());
        }
    }

    @Override
    public int getCount() {
        return dataMembers.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(dataMembers.get(position).getId());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = null;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = null;
            inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.tv_club = (TextView) row.findViewById(R.id.textView_club);
            holder.textView_member_name = (TextView) row.findViewById(R.id.textView_member_name);
            holder.tv_position = (TextView) row.findViewById(R.id.tv_position);
            holder.textView_designation = (TextView) row.findViewById(R.id.textView_designation);
            holder.textView_contact = (TextView) row.findViewById(R.id.textView_contact);
            holder.textView_email = (TextView) row.findViewById(R.id.textView_email);
            holder.prof_pic = (ImageView) row.findViewById(R.id.prof_pic);
            holder.chk_members = (CheckBox) row.findViewById(R.id.chk_members);
            holder.framemain_body = (FrameLayout) row.findViewById(R.id.framemain_body);
            holder.rl_mainchildview = (RelativeLayout) row.findViewById(R.id.rl_mainchildview);
            holder.tv_id = (TextView) row.findViewById(R.id.tv_id);
            row.setTag(holder);
        } else {
            holder = new ViewHolder();
            holder = (ViewHolder) row.getTag();
        }

        final Members membersData = dataMembers.get(position);
        holder.id = membersData.getMember_id();
        holder.chk_members.setTag(position);
        //holder.framemain_body.setTag(position);
        //holder.rl_mainchildview.setTag(true);
        holder.tv_position.setText(String.valueOf(position));
        holder.tv_id.setText(membersData.getMember_id());

        final ViewHolder finalHolder = holder;
        holder.chk_members.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    getProduct((Integer) buttonView.getTag()).box = isChecked;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (finalHolder.chk_members.isPressed()) {
                    if (isChecked) {
                        LCHashMapUtils.m_boolean.set(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString()), true);
                        LCHashMapUtils.m_group_members1.set(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString()),
                                new GroupMembers(GroupMemberSel.membersData_1.get(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString())).getMember_id(), GroupMemberSel.membersData_1.get(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString())).getMember_image(), GroupMemberSel.membersData_1.get(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString())).getMember_first_name()));
                    } else {
                        LCHashMapUtils.m_boolean.set(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString()), false);
                        LCHashMapUtils.m_group_members1.set(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString()), new GroupMembers("0", "0", "0"));
                    }
                    /*if (finalHolder.chk_members.isPressed()) {
                        GroupMemberSel.et_searchview.setText("");
                    }*/
                    GroupMemberSel.showResult(true);
                }

            }
        });

        if (LCHashMapUtils.m_boolean.get(dataMembers_temp_id.indexOf(finalHolder.tv_id.getText().toString()))) {
            holder.chk_members.setChecked(membersData.box);
        } else {
            holder.chk_members.setChecked(false);
        }

        String name = "";
        if (!LCUtils.isStringNullOrEmpty(membersData.getMember_first_name())) {
            name = membersData.getMember_first_name() + " ";
        }
        /*if (!LCUtils.isStringNullOrEmpty(membersData.getMember_middle_name())) {
            name = name + membersData.getMember_middle_name() + " ";
        }
        if (!LCUtils.isStringNullOrEmpty(membersData.getMember_last_name())) {
            name = name + membersData.getMember_last_name();
        }*/
        holder.textView_member_name.setText(name);
        String desig = "";

        if (!LCUtils.isStringNullOrEmpty(membersData.getDesignation_id())) {
            desig = membersData.getDesignation_id();
        } else {
            holder.textView_designation.setVisibility(View.GONE);
        }
        holder.textView_designation.setText(desig);

        String contact = "";
        if (!LCUtils.isStringNullOrEmpty(membersData.getDesignation_id())) {
            contact = "+" + membersData.getDesignation_id() + " ";
        }

        if (!LCUtils.isStringNullOrEmpty(membersData.getDesignation_id())) {
            contact = contact + membersData.getDesignation_id();
        } else {
            holder.textView_contact.setVisibility(View.GONE);
        }
        try {
            holder.tv_club.setText(membersData.getDesignation_id() + " - " + membersData.getDesignation_id());
        }catch (Exception e){
        }
        holder.textView_contact.setText(contact);
        String email = "";
        if (!LCUtils.isStringNullOrEmpty(membersData.getMember_email())) {
            email = membersData.getMember_email();
        } else {
            holder.textView_email.setVisibility(View.GONE);
        }
        holder.textView_email.setText(email);

        try {
            Picasso.with(mContext)
                    .load(membersData.getMember_image())
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(holder.prof_pic);
        } catch (Exception e) {
        }
        return row;
    }

    public Members getMembersByPosition(int position) {
        return dataMembers.get(position);
    }

    public Members getProduct(int position) {
        return ((Members) getItem(position));
    }


    public void filter(String charText) {
        charText = charText.toUpperCase();
        dataMembers.clear();
        if (charText.length() == 0) {
            dataMembers.addAll(arraylist);
        } else {
            for (Members contact : arraylist) {
                Log.d("charText", charText);
                String name = contact.getMember_first_name().trim();
                String name1 = contact.getMember_first_name().trim();
                if (contact.getMember_first_name().contains(charText)
                        || contact.getDesignation_id().contains(charText)
                        || contact.getMember_email().contains(charText)
                        || name.contains(charText)
                        || name1.contains(charText)) {
                    dataMembers.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }
}
