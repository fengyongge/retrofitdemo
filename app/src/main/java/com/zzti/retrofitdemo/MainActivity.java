package com.zzti.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zzti.retrofitdemo.base.BaseResponse;
import com.zzti.retrofitdemo.bean.LoginBean;
import com.zzti.retrofitdemo.net.RetrofitManager;
import com.zzti.retrofitdemo.net.api.Api;
import com.zzti.retrofitdemo.ui.QueryActivity;
import com.zzti.retrofitdemo.ui.UploadPhotoActivity;
import com.zzti.retrofitdemo.util.PreferencesUtils;
import com.zzti.retrofitdemo.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvQuery)
    TextView tvQuery;
    @BindView(R.id.tvAddtag)
    TextView tvAddtag;

    @BindView(R.id.tvLogo)
    TextView tvLogo;

    private String supply_id="";
    private String staff_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.tvLogin, R.id.tvQuery, R.id.tvAddtag, R.id.tvLogo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:

                RetrofitManager.getInstance().createReq(Api.class).loginReq("13661390463", "123456", "1",getTime()).subscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<BaseResponse<LoginBean>>() {
                            @Override
                            public void onCompleted() {


                            }

                            @Override
                            public void onError(Throwable e) {


                            }

                            @Override
                            public void onNext(BaseResponse<LoginBean> loginBeanBaseResponse) {

                                if (loginBeanBaseResponse.getCode() >= 200 &&
                                        loginBeanBaseResponse.getCode() < 300) {

                                    ToastUtils.showToast(MainActivity.this,loginBeanBaseResponse.getData().getId()+"---"+loginBeanBaseResponse.getData().getSupplier_id());

                                    PreferencesUtils.putString(MainActivity.this,"staff_id",loginBeanBaseResponse.getData().getId());
                                    PreferencesUtils.putString(MainActivity.this,"supply_id",loginBeanBaseResponse.getData().getSupplier_id());

                                } else {
                                    Logger.i("报错了");

                                    Toast.makeText(MainActivity.this, loginBeanBaseResponse.getMsg().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                break;



            case R.id.tvAddtag:

                staff_id = PreferencesUtils.getString(MainActivity.this,"staff_id");
                supply_id = PreferencesUtils.getString(MainActivity.this,"supply_id");


                RetrofitManager.getInstance().createReq(Api.class).addMemberTag(supply_id, staff_id, "6666", "2",getTime()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<BaseResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {


                            }

                            @Override
                            public void onNext(BaseResponse baseResponse) {

                                if(baseResponse.getCode() >= 200 && baseResponse.getCode()<300){

                                    ToastUtils.showToast(MainActivity.this,baseResponse.getMsg());


                                }else{
                                    ToastUtils.showToast(MainActivity.this,baseResponse.getMsg());

                                }

                            }

                 });

                break;

            case R.id.tvQuery:

                startActivity(new Intent(MainActivity.this, QueryActivity.class));

                break;
            case R.id.tvLogo:

               Intent intent = new Intent(MainActivity.this, UploadPhotoActivity.class);
               startActivity(intent);
                break;
        }
    }

    private String getTime() {
        return System.currentTimeMillis() + "";
    }
}
