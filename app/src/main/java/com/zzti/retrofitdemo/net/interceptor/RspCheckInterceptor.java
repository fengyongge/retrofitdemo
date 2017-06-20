package com.zzti.retrofitdemo.net.interceptor;


import android.util.Log;

import encrypt.Sha1;

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

/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class RspCheckInterceptor implements Interceptor{


    private  String publicKey="DGAIC6F0SW5BTV56";
    static String appSecret="D$GAS@WQK8QD19$I";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
            try {

                Request request = chain.request();

                if (request.method().equals("GET")) {
                    request = addGetParams(request);
                } else if (request.method().equals("POST")) {
                    request = addPostParams(request);
                } else if (request.method().equals("DELETE")) {
                    request = addDeleteParams(request);
                } else if (request.method().equals("PUT")) {
                    request = addPutParams(request);
                }
                response = chain.proceed(request);

//                JSONObject jsonObject = new JSONObject(InterceptorUtils.getRspData(response.body()));
//                Log.i("fyg","jsonObject:"+jsonObject);

            }catch (Exception e){
                if (e instanceof IOException){
                    throw (IOException)e;
                }
            }

        return response;
    }


    private Request addDeleteParams(Request request) throws UnsupportedEncodingException {

        if (request.body() instanceof FormBody) {

            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
            formBody = bodyBuilder
//                    .addEncoded("ctype", String.valueOf(NetConstants.CLIENT_TYPE_ANDROID))
//                    .addEncoded("ver","" )
//                    .addEncoded("time", String.valueOf(System.currentTimeMillis()))
                    .build();

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
            request = request.newBuilder().delete(formBody).build();
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
            formBody = bodyBuilder
//                    .addEncoded("ctype", String.valueOf(NetConstants.CLIENT_TYPE_ANDROID))
//                    .addEncoded("ver","" )
//                    .addEncoded("time", String.valueOf(System.currentTimeMillis()))
                    .build();

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



    private Request addGetParams(Request request) {
        HttpUrl httpUrl = request.url()
                .newBuilder()
//                .addQueryParameter("clienttype", String.valueOf(NetConstants.CLIENT_TYPE_ANDROID))
//                .addQueryParameter("version","" )
                .addQueryParameter("timestamp", String.valueOf(System.currentTimeMillis()))
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
                    httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");
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

            formBody = bodyBuilder
//                    .addEncoded("ctype", String.valueOf(NetConstants.CLIENT_TYPE_ANDROID))
//                    .addEncoded("ver","" )
//                    .addEncoded("time", String.valueOf(System.currentTimeMillis()))
                    .build();

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
