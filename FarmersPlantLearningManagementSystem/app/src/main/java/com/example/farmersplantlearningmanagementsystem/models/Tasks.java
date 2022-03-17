package com.example.farmersplantlearningmanagementsystem.models;

public class Tasks {
    int id;
    int user_id;
    String name;
    Boolean is_submitted;
    String submitted_at;
    String created_at;
    String updated_at;

    public Tasks(int id, int user_id, String name, Boolean is_submitted, String submitted_at, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.is_submitted = is_submitted;
        this.submitted_at = submitted_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_submitted() {
        return is_submitted;
    }

    public void setIs_submitted(Boolean is_submitted) {
        this.is_submitted = is_submitted;
    }

    public String getSubmitted_at() {
        return submitted_at;
    }

    public void setSubmitted_at(String submitted_at) {
        this.submitted_at = submitted_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}


