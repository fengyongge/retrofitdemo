package com.zzti.retrofitdemo.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzti.retrofitdemo.bean.WrapperRspEntity;
import com.zzti.retrofitdemo.bean.WrapperRspEntity1;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
    Observable<WrapperRspEntity> loginReq(@Field("mobile") String userName, @Field("password") String pwd, @Field("visitSource") String visitSource);


    /**
     * 查询标签
     * @param supplier_id
     * @param staff_id
     * @return
     */
    @GET("memberservice/queryStaffTag/suppliers/{supplier_id}/operator/{staff_id}")
    Observable<WrapperRspEntity> queryMemberTag(@Path("supplier_id") String supplier_id,
                                      @Path("staff_id") String staff_id);

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
                                  @Query("page") String page,@Query("per_page") String per_page);


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
    Observable<WrapperRspEntity> addMemberTag(@Path("supplier_id") String supplier_id,
                                    @Path("staff_id") String staff_id,
                                    @Field("name") String name,@Field("type") String type);

    /**
     * 删除标签
     * @param supplier_id
     * @param operator_id
     * @return
     */

    @HTTP(method = "DELETE", path = "memberservice/delMemberTag/suppliers/{supplier_id}/operator/{operator_id}", hasBody = true)
    Observable<WrapperRspEntity> deletMemberTag(@Path("supplier_id") String supplier_id,
                                      @Path("operator_id") String operator_id,
                                                @Body RequestBody tagids);


    /**
     * 修改标签
     * @param supplier_id
     * @param operator_id
     * @return
     */

    @FormUrlEncoded
    @PUT("memberservice/updateMemberTag/suppliers/{supplier_id}/operator/{operator_id}")
    Observable<WrapperRspEntity> updateMemberTag(@Path("supplier_id") String supplier_id,
                                       @Path("operator_id") String operator_id,
                                       @Field("supplier_id") String supplier_id1,
                                       @Field("operator_id") String operator_id1,
                                       @Field("name") String name,
                                       @Field("tagid") String tagid);

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


    @Multipart
    @POST("/upload")
    Observable<WrapperRspEntity> upload(
            @Part("portrait") RequestBody description,
            @Part MultipartBody.Part file);


    @Multipart
    @POST("file-storage/upload-avatar")
    Observable<WrapperRspEntity1> uplogo(
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
