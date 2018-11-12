
package com.tesuta.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AllContactCheck {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private String status;


}
