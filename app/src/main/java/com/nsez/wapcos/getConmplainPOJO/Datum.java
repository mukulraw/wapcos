package com.nsez.wapcos.getConmplainPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("complain")
    @Expose
    private String complain;
    @SerializedName("akd_date")
    @Expose
    private String akdDate;
    @SerializedName("exp_cl_date")
    @Expose
    private String expClDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getAkdDate() {
        return akdDate;
    }

    public void setAkdDate(String akdDate) {
        this.akdDate = akdDate;
    }

    public String getExpClDate() {
        return expClDate;
    }

    public void setExpClDate(String expClDate) {
        this.expClDate = expClDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
