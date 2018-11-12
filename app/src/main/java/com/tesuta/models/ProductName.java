package com.tesuta.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilay on 10/21/2017.
 */

public class ProductName {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<ProductNameList> data = new ArrayList<ProductNameList>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The data
     */
    public List<ProductNameList> getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(List<ProductNameList> data) {
        this.data = data;
    }

    /**
     *
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
