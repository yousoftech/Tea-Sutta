
package com.tesuta.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AllCancel {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("Message")
    @Expose
    private String Message;




}
