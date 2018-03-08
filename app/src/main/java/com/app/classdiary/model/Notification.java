package com.app.classdiary.model;

/**
 * Created by User1 on 19-02-2018.
 */

public class Notification {
    String id;
    String txt_header;
    String txt_desc;
    String date;
    String posted_by;
    String is_read;
    String type;

    public Notification(String id, String txt_header, String txt_desc, String date, String posted_by, String is_read, String type) {
        this.id = id;
        this.txt_header = txt_header;
        this.txt_desc = txt_desc;
        this.date = date;
        this.posted_by = posted_by;
        this.is_read = is_read;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxt_header() {
        return txt_header;
    }

    public void setTxt_header(String txt_header) {
        this.txt_header = txt_header;
    }

    public String getTxt_desc() {
        return txt_desc;
    }

    public void setTxt_desc(String txt_desc) {
        this.txt_desc = txt_desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", txt_header='" + txt_header + '\'' +
                ", txt_desc='" + txt_desc + '\'' +
                ", date='" + date + '\'' +
                ", posted_by='" + posted_by + '\'' +
                ", is_read='" + is_read + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
