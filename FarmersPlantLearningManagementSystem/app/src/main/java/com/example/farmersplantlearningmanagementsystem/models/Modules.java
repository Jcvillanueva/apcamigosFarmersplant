package com.example.farmersplantlearningmanagementsystem.models;

public class Modules {
    int id;
    int user_id;
    String file_path;
    String file_name;
    String created_at;
    String updated_at;

    public Modules(int id, int user_id, String file_path, String file_name, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.file_path = file_path;
        this.file_name = file_name;
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

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
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

