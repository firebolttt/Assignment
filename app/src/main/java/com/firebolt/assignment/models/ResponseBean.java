package com.firebolt.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseBean {
    @SerializedName("page")
    private int page;
    @SerializedName("data")
    private ArrayList<UserDetails>users;
    @SerializedName("total")
    private int total;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("per_page")
    private int perPage;

    @Override
    public String toString() {
        return "ResponseBean{" +
                "page=" + page +
                ", users=" + users +
                ", total=" + total +
                ", totalPages=" + totalPages +
                ", perPage=" + perPage +
                '}';
    }

    public int getPage() {
        return page;
    }

    public ArrayList<UserDetails> getUsers() {
        return users;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPerPage() {
        return perPage;
    }
}
