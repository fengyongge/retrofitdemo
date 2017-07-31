package com.zzti.retrofitdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzti.retrofitdemo.R;
import com.zzti.retrofitdemo.net.BaseResponse;
import com.zzti.retrofitdemo.bean.LoginBean;
import com.zzti.retrofitdemo.dialog.AlertHelper;
import com.zzti.retrofitdemo.myinterface.SweetAlertCallBack;
import com.zzti.retrofitdemo.net.RetrofitManager;
import com.zzti.retrofitdemo.net.api.Api;
import com.zzti.retrofitdemo.util.PreferencesUtils;
import com.zzti.retrofitdemo.util.StringUtils;
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
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.tvName)
    TextView tvName;

    private String supply_id = "";
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

                RetrofitManager.getInstance().createReq(Api.class).loginReq("13661390463", "123456", "1", getTime())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<BaseResponse<LoginBean>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                                ToastUtils.showToast(MainActivity.this,e.getMessage());

                            }

                            @Override
                            public void onNext(BaseResponse<LoginBean> loginBeanBaseResponse) {


                                PreferencesUtils.putString(MainActivity.this, "staff_id", loginBeanBaseResponse.data.getId());
                                PreferencesUtils.putString(MainActivity.this, "supply_id", loginBeanBaseResponse.data.getSupplier_id());

                                StringUtils.filtNull(tvName,loginBeanBaseResponse.data.getUsername());
                                ImageLoader.getInstance().displayImage(loginBeanBaseResponse.data.getStaff_image(),ivPhoto);
                            }

                        });

                break;


            case R.id.tvAddtag:

                staff_id = PreferencesUtils.getString(MainActivity.this, "staff_id");
                supply_id = PreferencesUtils.getString(MainActivity.this, "supply_id");


                AlertHelper.create2EditAlert(MainActivity.this, "确定", "取消", "是否创建", new SweetAlertCallBack() {
                    @Override
                    public void onConfirm(String data) {

                        RetrofitManager.getInstance().createReq(Api.class).addMemberTag(supply_id, staff_id, data, "2", getTime()).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Subscriber<BaseResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                        ToastUtils.showToast(MainActivity.this, e.getMessage());

                                    }

                                    @Override
                                    public void onNext(BaseResponse baseResponse) {

                                        ToastUtils.showToast(MainActivity.this, baseResponse.msg);

                                    }

                                });

                    }

                    @Override
                    public void onCancle() {

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
