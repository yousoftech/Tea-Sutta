package com.tesuta.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllOrderDelivery {


    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AllExpandOrderDeliverList> getUserorder() {
        return userorder;
    }

    public void setUserorder(List<AllExpandOrderDeliverList> userorder) {
        this.userorder = userorder;
    }

    @SerializedName("userorder")
    @Expose
    private List<AllExpandOrderDeliverList> userorder = new ArrayList<AllExpandOrderDeliverList>();


}
