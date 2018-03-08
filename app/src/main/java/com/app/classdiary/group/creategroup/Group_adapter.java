package com.app.classdiary.group.creategroup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.classdiary.R;
import com.app.classdiary.utils.GroupMembers;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Group_adapter extends RecyclerView.Adapter<Group_adapter.VHItem> {
    Context mContext;
    int rowLayout;
    List<GroupMembers> dataMembers;
    String flag, ids;
    GroupMembers personaldata;

    public Group_adapter(Context mContext, int rowLayout, List<GroupMembers> ibsMembers, String flag) {
        this.mContext = mContext;
        this.rowLayout = rowLayout;
        this.flag = flag;
        this.dataMembers = ibsMembers;
    }

    @Override
    public VHItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new VHItem(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final VHItem viewHolder, final int position) {
        try {
            personaldata = dataMembers.get(position);
            if (!personaldata.getUser_name().equals("0")) {
                if (!personaldata.getUser_name().equals("") || !personaldata.getUser_name().isEmpty()) {
                    viewHolder.tv_name.setText(Html.fromHtml(personaldata.getUser_name()));
                }

                if (!personaldata.getUser_image().equals("") || !personaldata.getUser_image().isEmpty()) {
                    Picasso.with(mContext)
                            .load(personaldata.getUser_image())
                            .placeholder(R.mipmap.ic_launcher)
                            .into(viewHolder.iv_userimg);
                }

                viewHolder.tv_id.setText(personaldata.getUser_id());
                /*viewHolder.main_view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = null;
                        RelativeLayout rel = null;
                        FrameLayout frm = null;
                        View v11 = GroupMemberSel.lv_dirtectory.findViewWithTag(MemberClubAdapter.dataMembers_temp_id.indexOf(viewHolder.tv_id.getText().toString()));
                        if (v11 instanceof CheckBox) {
                            checkBox = (CheckBox) v11;
                            checkBox.setChecked(false);
                            v11.setTag(false);
                        }
                        GroupMemberSel.et_searchview.setText("");
                        LCHashMapUtils.m_group_members1.set(MemberClubAdapter.dataMembers_temp_id.indexOf(viewHolder.tv_id.getText().toString()), new GroupMembersDetails("0", "0", "0"));
                        dataMembers.remove(position);
                        if(dataMembers.isEmpty()){
                            GroupMemberSel.showResult(true);
                        }
                        try {
                            notifyDataSetChanged();
                        }catch (Exception e){
                        }

                        LCHashMapUtils.m_boolean.set(MemberClubAdapter.dataMembers_temp_id.indexOf(viewHolder.tv_id.getText().toString()), false);
                    }
                });*/
                /*if (dataMembers_temp.size() == 0) {
                    Toast.makeText(mContext,"Hello",Toast.LENGTH_SHORT).show();
                    GroupMemberSel.hideview();
                }*/
            } else {

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return dataMembers.size();
    }

    public class VHItem extends RecyclerView.ViewHolder {
        private ImageView iv_cancel;
        private ImageView iv_userimg;
        private TextView tv_name, tv_id;
        private FrameLayout main_view;

        public VHItem(View itemView) {
            super(itemView);
            iv_cancel = (ImageView) itemView.findViewById(R.id.iv_cancel);
            iv_userimg = (ImageView) itemView.findViewById(R.id.iv_userimg);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            main_view = (FrameLayout) itemView.findViewById(R.id.main_view);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            iv_cancel.setVisibility(View.GONE);
        }
    }
}