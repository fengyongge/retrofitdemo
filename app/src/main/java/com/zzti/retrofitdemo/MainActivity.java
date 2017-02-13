package com.zzti.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.zzti.retrofitdemo.api.Api;
import com.zzti.retrofitdemo.bean.BodyBean;
import com.zzti.retrofitdemo.bean.LoginBean;
import com.zzti.retrofitdemo.bean.WrapperRspEntity;
import com.zzti.retrofitdemo.net.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class MainActivity extends AppCompatActivity {

    TextView tv_login,tv_addtag,tv_deletag,tv_query,tv_updatetag,tv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_addtag = (TextView) findViewById(R.id.tv_addtag);
        tv_deletag = (TextView) findViewById(R.id.tv_deletag);
        tv_query = (TextView) findViewById(R.id.tv_query);
        tv_updatetag = (TextView) findViewById(R.id.tv_updatetag);
        tv_logo = (TextView) findViewById(R.id.tv_logo);



        tv_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Api loginApi = RetrofitManager.getInstance().createReq(Api.class);
//                Call<ResponseBody> call = loginApi.queryMemberTag("1", "53");
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()) {
//                            String str = null;
//                            try {
//                                str = response.body().string();
//
//
//                                Log.i("fyg", "后台返回："+str);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                    }
//                });

                RetrofitManager.getInstance().createReq(Api.class).queryMemberTag("1","53").subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<WrapperRspEntity>() {
                            @Override
                            public void call(WrapperRspEntity wrapperRspEntity) {

                                if(Integer.parseInt(wrapperRspEntity.getCode().toString())>=200&&
                                        Integer.parseInt(wrapperRspEntity.getCode().toString())<300){

                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getData().toString(), Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getMsg().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        tv_addtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetrofitManager.getInstance().createReq(Api.class).addMemberTag("1", "53","6666","2").subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<WrapperRspEntity>() {
                            @Override
                            public void call(WrapperRspEntity wrapperRspEntity) {

                                if(Integer.parseInt(wrapperRspEntity.getCode().toString())>=200&&
                                        Integer.parseInt(wrapperRspEntity.getCode().toString())<300){

                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getData().toString(), Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getMsg().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        tv_deletag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Map<String,String> map = new HashMap<String,String>();
//                map.put("tagids","234");

                BodyBean bodyBean = new BodyBean();
                bodyBean.setSupplier_id("1");
                bodyBean.setOperator_id("53");
                bodyBean.setTagids("240");

                Log.i("fyg","---"+bodyBean.toString());

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),bodyBean.toString());

                RetrofitManager.getInstance().createReq(Api.class).deletMemberTag("1","53", body).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<WrapperRspEntity>() {
                            @Override
                            public void call(WrapperRspEntity wrapperRspEntity) {

                                if(Integer.parseInt(wrapperRspEntity.getCode().toString())>=200&&
                                        Integer.parseInt(wrapperRspEntity.getCode().toString())<300){

                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getData().toString(), Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getMsg().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        tv_updatetag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,String> map = new HashMap<String,String>();
//                map.put("supplier_id","1");
//                map.put("operator_id","53");
//                map.put("name","888");
//                map.put("tagid","240");
                RetrofitManager.getInstance().createReq(Api.class).updateMemberTag("1","53","1","53","888","240").subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<WrapperRspEntity>() {
                            @Override
                            public void call(WrapperRspEntity wrapperRspEntity) {

                                if(Integer.parseInt(wrapperRspEntity.getCode().toString())>=200&&
                                        Integer.parseInt(wrapperRspEntity.getCode().toString())<300){

                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getData().toString(), Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getMsg().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetrofitManager.getInstance().createReq(Api.class).loginReq("15201649365", "123456a", "1").subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<WrapperRspEntity>() {
                            @Override
                            public void call(WrapperRspEntity wrapperRspEntity) {

                                if(Integer.parseInt(wrapperRspEntity.getCode().toString())>=200&&
                                        Integer.parseInt(wrapperRspEntity.getCode().toString())<300){

//                                    LoginBean loginBean =JSON.parseObject(wrapperRspEntity.getData().toString(), LoginBean.class);
                                    Gson gson = new Gson();
                                    LoginBean loginBean = gson.fromJson(wrapperRspEntity.getData().toString(),LoginBean.class);

                                    Toast.makeText(MainActivity.this,loginBean.getStaff_image(), Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(MainActivity.this, wrapperRspEntity.getMsg().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        tv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,TestActivity.class);
                startActivity(intent);

            }
        });

    }


 }
