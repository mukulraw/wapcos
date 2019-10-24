package com.nsez.wapcos;

import com.nsez.wapcos.feedbackPOJO.feedbackBean;
import com.nsez.wapcos.getConmplainPOJO.getComplainBean;
import com.nsez.wapcos.loginPOJO.loginBean;
import com.nsez.wapcos.singleComplaintPOJO.singleComplaintBean;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllApiIneterface {

    @Multipart
    @POST("api/login.php")
    Call<loginBean> login(
            @Part("username") String username,
            @Part("password") String password
    );

    @Multipart
    @POST("api/login2.php")
    Call<loginBean> login2(
            @Part("username") String username,
            @Part("password") String password
    );

    @Multipart
    @POST("api/getComplain.php")
    Call<getComplainBean> getComplain(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getComplain1.php")
    Call<getComplainBean> getComplain1(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getComplainById.php")
    Call<singleComplaintBean> getComplainById(
            @Part("cid") String cid
    );

    @Multipart
    @POST("api/getComplainById1.php")
    Call<singleComplaintBean> getComplainById1(
            @Part("cid") String cid
    );

    @Multipart
    @POST("api/getFeedback.php")
    Call<feedbackBean> getFeedback(
            @Part("cid") String cid
    );

    @Multipart
    @POST("api/submitFeedback.php")
    Call<String> submitFeedback(
            @Part("feedback") String feedback,
            @Part("id") String id,
            @Part("user_id") String user_id,
            @Part("vendor_id") String vendor_id
    );

    @Multipart
    @POST("api/submitFeedback1.php")
    Call<String> submitFeedback1(
            @Part("feedback") String feedback,
            @Part("id") String id,
            @Part("user_id") String user_id,
            @Part("vendor_id") String vendor_id
    );

    @Multipart
    @POST("api/changeStatus.php")
    Call<String> changeStatus(
            @Part("status") String status,
            @Part("id") String id
    );

}
