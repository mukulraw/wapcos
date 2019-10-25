package com.nsez.wapcos;

import com.nsez.wapcos.feedbackPOJO.feedbackBean;
import com.nsez.wapcos.getConmplainPOJO.getComplainBean;
import com.nsez.wapcos.loginPOJO.loginBean;
import com.nsez.wapcos.searchPOJO.searchBean;
import com.nsez.wapcos.singleComplaintPOJO.singleComplaintBean;

import java.util.List;

import okhttp3.MultipartBody;
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
            @Part("user_id") String user_id,
            @Part("date") String date
    );

    @Multipart
    @POST("api/getComplain1.php")
    Call<getComplainBean> getComplain1(
            @Part("user_id") String user_id,
            @Part("date") String date
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

    @Multipart
    @POST("api/close.php")
    Call<String> close(
            @Part("status") String status,
            @Part("id") String id,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("api/addComplain.php")
    Call<String> addComplain(
            @Part("user_id") String user_id,
            @Part("category") String category,
            @Part("name") String name,
            @Part("email") String email,
            @Part("phone") String phone,
            @Part("designation") String designation,
            @Part("complain") String complain,
            @Part("company") String company,
            @Part("address") String address,
            @Part("tag") String tag,
            @Part List<MultipartBody.Part> files
    );

    @Multipart
    @POST("api/search.php")
    Call<searchBean> search(
            @Part("user_id") String user_id,
            @Part("data") String data
    );

    @Multipart
    @POST("api/search1.php")
    Call<searchBean> search1(
            @Part("user_id") String user_id,
            @Part("data") String data
    );

    @Multipart
    @POST("api/addClosure.php")
    Call<String> addClosure(
            @Part("cldate") String cldate,
            @Part("id") String id
    );

    @Multipart
    @POST("api/vendor_noti.php")
    Call<getComplainBean> user_noti(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/user_noti.php")
    Call<getComplainBean> vendor_noti(
            @Part("user_id") String user_id
    );

}
