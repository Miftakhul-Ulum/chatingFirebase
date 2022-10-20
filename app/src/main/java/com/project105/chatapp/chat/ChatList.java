package com.project105.chatapp.chat;

public class ChatList {
    private String mobile, name, messege, date, time;


    public ChatList(String mobile, String name, String messege, String date, String time) {
        this.mobile = mobile;
        this.name = name;
        this.messege = messege;
        this.date = date;
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
