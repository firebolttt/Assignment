package com.firebolt.assignment.models;

import com.google.gson.annotations.SerializedName;

public class UserDetails {
    @SerializedName("first_name")
    private String fName="";
    @SerializedName("last_name")
    private String lName="";
    @SerializedName("avatar")
    private String imgUrl;
    @SerializedName("id")
    private int userId;

    @Override
    public String toString() {
        return "UserDetails{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", userId=" + userId +
                '}';
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
