package com.example.farmersplantlearningmanagementsystem.models;

public class TaskFarmers {
    int user_id;
    String full_name;
    String place_enrolled;
    String bdate;

    public TaskFarmers(int user_id, String full_name, String place_enrolled, String bdate) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.place_enrolled = place_enrolled;
        this.bdate = bdate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPlace_enrolled() {
        return place_enrolled;
    }

    public void setPlace_enrolled(String place_enrolled) {
        this.place_enrolled = place_enrolled;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }
}


