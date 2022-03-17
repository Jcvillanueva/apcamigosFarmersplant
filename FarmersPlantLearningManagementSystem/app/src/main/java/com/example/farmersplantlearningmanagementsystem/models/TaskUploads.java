package com.example.farmersplantlearningmanagementsystem.models;

public class TaskUploads {
    int id;
    int user_id;
    int task_id;
    String task_path;
    String task_name;
    String created_at;
    String updated_at;

    public TaskUploads(int id, int user_id, int task_id, String task_path, String task_name, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.task_id = task_id;
        this.task_path = task_path;
        this.task_name = task_name;
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

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_path() {
        return task_path;
    }

    public void setTask_path(String task_path) {
        this.task_path = task_path;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
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


