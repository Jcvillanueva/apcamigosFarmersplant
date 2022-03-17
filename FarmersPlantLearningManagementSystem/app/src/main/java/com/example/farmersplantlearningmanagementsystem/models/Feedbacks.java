package com.example.farmersplantlearningmanagementsystem.models;

public class Feedbacks {
    int user_id;
    int task_id;
    String task;
    String feedback;
    String created_at;

    public Feedbacks(int user_id, int task_id, String task, String feedback, String created_at) {
        this.user_id = user_id;
        this.task_id = task_id;
        this.task = task;
        this.feedback = feedback;
        this.created_at = created_at;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}


