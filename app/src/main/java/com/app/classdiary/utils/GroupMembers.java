package com.app.classdiary.utils;

/**
 * Created by User1 on 21-02-2018.
 */

public class GroupMembers {
    public String user_id;
    public String user_image;
    public String user_name;
    public String user_img;

    public GroupMembers(String user_id, String user_image, String user_name) {
        this.user_id = user_id;
        this.user_image = user_image;
        this.user_name = user_name;
    }

    public GroupMembers() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
