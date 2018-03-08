package com.app.classdiary.model;

/**
 * Created by User1 on 19-02-2018.
 */

public class Announcements {
    String gid;
    String group_name;
    String created_date;
    String owner;
    String group_desc;

    public Announcements(String gid, String group_name, String created_date, String owner, String group_desc) {
        this.gid = gid;
        this.group_name = group_name;
        this.created_date = created_date;
        this.owner = owner;
        this.group_desc = group_desc;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }

    @Override
    public String toString() {
        return "Group{" +
                "gid='" + gid + '\'' +
                ", group_name='" + group_name + '\'' +
                ", created_date='" + created_date + '\'' +
                ", owner='" + owner + '\'' +
                ", group_desc='" + group_desc + '\'' +
                '}';
    }
}
