package com.zzti.retrofitdemo.net;


import android.util.Log;

import com.zzti.retrofitdemo.util.InterceptorUtils;

import org.json.JSONObject;

import com.zzti.retrofitdemo.encrypt.Sha1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class RspCheckInterceptor implements Interceptor{

    private  String publicKey="DGAIC6F0SW5BTV56";
    static String appSecret="D$GAS@WQK8QD19$I";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response =null;

        //先根据接口不同请求方式，进行对参数加密，保证数据安全
            try {
                Request request = chain.request();

                if (request.method().equals("GET")) {
                    request = addGetParams(request);
                } else if (request.method().equals("POST")) {
                    request = addPostParams(request);
                }
                else if (request.method().equals("PUT")) {
                    request = addPutParams(request);
                }

                else if (request.method().equals("DELETE")) {
                    request = addDeleteParams(request);
                }
                response = chain.proceed(request);

                //在过滤器里面过滤的好处就是为了防止在每一个接口请求出的onNext里面都判断code
                ResponseBody rspBody = response.body();
                JSONObject jsonObject = new JSONObject(InterceptorUtils.getRspData(rspBody));
                int status = jsonObject.getInt("code");
                if (status < 200 || status >= 300){

                    throw new IOException(jsonObject.getString("msg"));

                }else{
                    return response;
                }


            }catch (Exception e){
                if (e instanceof IOException){
                    throw (IOException)e;
                }
            }

        return response;
    }




    private Request addDeleteParams(Request request) throws UnsupportedEncodingException {


        Log.i("fyg","11111");

            if (request.body() instanceof FormBody) {

                Log.i("fyg","222222");


            FormBody.Builder bodyBuilder = new FormBody.Builder();

            FormBody formBody = (FormBody) request.body();

            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }

            Map<String, String> bodyMap = new HashMap<>();
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < formBody.size(); i++) {
                nameList.add(formBody.encodedName(i));
                bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
            }
            Collections.sort(nameList);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nameList.size(); i++) {
                builder.append("").append(nameList.get(i)).append("")
                        .append(URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8"));
            }


            formBody = bodyBuilder.
                    addEncoded("sign", toMD5(builder.toString()))
                    .addEncoded("publicKey",publicKey)
                    .build();

                Log.i("fyg","33333");


                request = request.newBuilder().delete(formBody).build();

        }
        return request;
    }






    private Request addGetParams(Request request) {

        HttpUrl httpUrl = request.url()
                .newBuilder()
                .build();

        //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Collections.sort(nameList);

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            buffer.append("").append(nameList.get(i)).append("").append(
                    httpUrl.queryParameterValues(nameList.get(i)) != null &&
                    httpUrl.queryParameterValues(nameList.get(i)).size() > 0
                     ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");
        }

        httpUrl = httpUrl.newBuilder()
                .addQueryParameter("sign", toMD5(buffer.toString()))
                .addQueryParameter("publicKey",publicKey)
                .build();

        request = request.newBuilder().url(httpUrl).build();
        return request;
    }





    private Request addPostParams(Request request) throws UnsupportedEncodingException {
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }


            Map<String, String> bodyMap = new HashMap<>();
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < formBody.size(); i++) {
                nameList.add(formBody.encodedName(i));
                bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
            }
            Collections.sort(nameList);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nameList.size(); i++) {
                builder.append("").append(nameList.get(i)).append("")
                        .append(URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8"));
            }


            formBody = bodyBuilder.
                    addEncoded("sign", toMD5(builder.toString()))
                    .addEncoded("publicKey",publicKey)
                    .build();

            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }

    private Request addPutParams(Request request) throws UnsupportedEncodingException {

        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }


            Map<String, String> bodyMap = new HashMap<>();
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < formBody.size(); i++) {
                nameList.add(formBody.encodedName(i));
                bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));

            }
            Collections.sort(nameList);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nameList.size(); i++) {
                builder.append("").append(nameList.get(i)).append("")
                        .append(URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8"));
            }

            formBody = bodyBuilder.
                    addEncoded("sign", toMD5(builder.toString()))
                    .addEncoded("publicKey",publicKey)
                    .build();

            request = request.newBuilder().put(formBody).build();
        }
        return request;
    }



    public static String toMD5(String or_Sign) {
        String sign = null;
        or_Sign = appSecret+or_Sign+appSecret;
        Log.i("fyg","-加密前-"+or_Sign);
//        try {
////            sign = MD5.getMessageDigest(or_Sign.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        sign = Sha1.shaEncrypt(or_Sign);

        return sign;
    }


    private String getTime() {
        return System.currentTimeMillis() + "";
    }


}
