package com.zzti.retrofitdemo.net.api;

import com.zzti.retrofitdemo.base.BaseResponse;
import com.zzti.retrofitdemo.bean.LoginBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * @author fengyonggge
 * @date 2017/2/7
 */

public interface Api {

    /**
     * 登陆
     * @param userName
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("staffservice/login")
    Observable<BaseResponse<LoginBean>> loginReq(@Field("mobile") String userName, @Field("password") String pwd,
                                                 @Field("visitSource") String visitSource, @Field("timestamp") String timestamp);


    /**
     * 添加标签
     * @param supplier_id
     * @param staff_id
     * @param name
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("memberservice/addMemberTag/suppliers/{supplier_id}/operator/{staff_id}")
    Observable<BaseResponse> addMemberTag(@Path("supplier_id") String supplier_id,
                                              @Path("staff_id") String staff_id,
                                              @Field("name") String name, @Field("type") String type,@Field("timestamp") String timestamp);

    /**
     * 删除标签
     * @param supplier_id
     * @param operator_id
     * @return
     */

    @HTTP(method = "DELETE", path = "memberservice/delMemberTag/suppliers/{supplier_id}/operator/{operator_id}", hasBody = true)
    Observable<BaseResponse> deletMemberTag(@Path("supplier_id") String supplier_id,
                                                @Path("operator_id") String operator_id,
                                                @Body RequestBody tagids,@Path("timestamp") String timestamp);


    /**
     * 修改标签
     * @param supplier_id
     * @param operator_id
     * @return
     */

    @FormUrlEncoded
    @PUT("memberservice/updateMemberTag/suppliers/{supplier_id}/operator/{operator_id}")
    Observable<BaseResponse> updateMemberTag(@Path("supplier_id") String supplier_id,
                                                 @Path("operator_id") String operator_id,
                                                 @Field("supplier_id") String supplier_id1,
                                                 @Field("operator_id") String operator_id1,
                                                 @Field("name") String name,
                                                 @Field("tagid") String tagid,@Field("timestamp") String timestamp);


//    /**
//     * 修改标签
//     * @param supplier_id
//     * @param operator_id
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("memberservice/updateMemberTag/suppliers/{supplier_id}/operator/{operator_id}")
//    Observable<WrapperRspEntity> updateMemberTag(@Path("supplier_id") String supplier_id,
//                                                 @Path("operator_id") String operator_id,
//                                                 @FieldMap Map<String, String> map);


    /**
     * 查询标签
     * @param supplier_id
     * @param staff_id
     * @return
     */
    @GET("memberservice/queryStaffTag/suppliers/{supplier_id}/operator/{staff_id}/timestamp/{timestamp}")
    Observable<BaseResponse> queryMemberTag(@Path("supplier_id") String supplier_id,
                                                @Path("staff_id") String staff_id ,@Path("timestamp") String timestamp);



    /**
     * 获取会员列表
     * @param supplier_id
     * @param staff_id
     * @param page
     * @param per_page
     * @return
     */
    @GET("memberservice/suppliers/{supplier_id}/staffs/{staff_id}")
    Call<ResponseBody> getMembers(@Path("supplier_id") String supplier_id,
                                  @Path("staff_id") String staff_id,
                                  @Query("page") String page, @Query("per_page") String per_page);

    @Multipart
    @POST("/upload")
    Observable<BaseResponse> upload(
            @Part("portrait") RequestBody description,
            @Part MultipartBody.Part file);


    @Multipart
    @POST("file-storage/upload-avatar")
    Observable<BaseResponse> uplogo(
            @Part("avatar_url") RequestBody description,
            @Part MultipartBody.Part file);


//    public UrlBean useredit(final Context context, String token,
//                            String merchant_id) {
//        String noncestr = getNoncestr();
//        String time = getTime();
//        Map<String, String> map = new TreeMap<String, String>();
//
//
//
//        String sortString = sort(token, map);
//        String sign = toMD5(sortString);
//
//        final String apiUri = NET_DOMAIN + "?c=useredit" + "&sign=" + sign;
//        return new UrlBean(apiUri, noncestr, time, version);
//    }

}
