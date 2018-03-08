package com.app.classdiary.group.groupmodel;

import java.util.List;

public class Members {
    int id;
    String  member_id;
    String  group_id;
    String  designation_id;
    String  member_first_name;
    String  member_email;
    String  member_image;
    boolean checkedflag;
    public boolean box;
    public boolean box1;
    String member_check;

    public Members(int id, String member_id, String group_id, String designation_id, String member_first_name, String member_email, String member_image, boolean checkedflag, String member_check) {
        this.id = id;
        this.member_id = member_id;
        this.group_id = group_id;
        this.designation_id = designation_id;
        this.member_first_name = member_first_name;
        this.member_email = member_email;
        this.member_image = member_image;
        this.checkedflag = checkedflag;
        this.member_check = member_check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(String designation_id) {
        this.designation_id = designation_id;
    }

    public String getMember_first_name() {
        return member_first_name;
    }

    public void setMember_first_name(String member_first_name) {
        this.member_first_name = member_first_name;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_image() {
        return member_image;
    }

    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }

    public boolean isCheckedflag() {
        return checkedflag;
    }

    public void setCheckedflag(boolean checkedflag) {
        this.checkedflag = checkedflag;
    }

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }

    public String getMember_check() {
        return member_check;
    }

    public void setMember_check(String member_check) {
        this.member_check = member_check;
    }



    @Override
    public String toString() {
        return "Members{" +
                "id=" + id +
                ", member_id='" + member_id + '\'' +
                ", group_id='" + group_id + '\'' +
                ", designation_id='" + designation_id + '\'' +
                ", member_first_name='" + member_first_name + '\'' +
                ", member_email='" + member_email + '\'' +
                ", member_image='" + member_image + '\'' +
                ", checkedflag=" + checkedflag +
                ", box=" + box +
                ", box1=" + box1 +
                ", member_check='" + member_check + '\'' +
                '}';
    }
}
