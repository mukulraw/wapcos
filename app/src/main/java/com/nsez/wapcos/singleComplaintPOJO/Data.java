package com.nsez.wapcos.singleComplaintPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
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
    @SerializedName("handled")
    @Expose
    private String handled;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("closing")
    @Expose
    private String closing;
    @SerializedName("vname")
    @Expose
    private String vname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("alternateemail")
    @Expose
    private String alternateemail;
    @SerializedName("alternatephone")
    @Expose
    private String alternatephone;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("vid")
    @Expose
    private String vid;


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

    public String getHandled() {
        return handled;
    }

    public void setHandled(String handled) {
        this.handled = handled;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlternateemail() {
        return alternateemail;
    }

    public void setAlternateemail(String alternateemail) {
        this.alternateemail = alternateemail;
    }

    public String getAlternatephone() {
        return alternatephone;
    }

    public void setAlternatephone(String alternatephone) {
        this.alternatephone = alternatephone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
