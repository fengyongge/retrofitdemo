package com.zzti.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.zzti.retrofitdemo.base.BaseResponse;
import com.zzti.retrofitdemo.bean.LoginBean;
import com.zzti.retrofitdemo.dialog.AlertHelper;
import com.zzti.retrofitdemo.myinterface.SweetAlertCallBack;
import com.zzti.retrofitdemo.net.RetrofitManager;
import com.zzti.retrofitdemo.net.api.Api;
import com.zzti.retrofitdemo.ui.QueryActivity;
import com.zzti.retrofitdemo.ui.UploadPhotoActivity;
import com.zzti.retrofitdemo.util.PreferencesUtils;
import com.zzti.retrofitdemo.util.StringUtils;
import com.zzti.retrofitdemo.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

                RetrofitManager.getInstance().createReq(Api.class).loginReq("13661390463", "123456", "1", getTime()).subscribeOn(Schedulers.io())
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

                //多图上传

//                File file1 = null;
//                RequestBody requestFile;
//                MultipartBody.Part filePart = null;
//                HashMap<String, RequestBody> map = new HashMap<>();
//                List<String> list = new ArrayList<>();
//                for (int i = 0; i <list.size() ; i++) {
//
//                    String token ="dsdsddadad244";
//                    requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
//
//                    // MultipartBody.Part is used to send also the actual file name
//                    filePart = MultipartBody.Part.createFormData("file", file1.getName(), requestFile);
//
//                    // create a map of data to pass along
//                    RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
//                    map.put("token", tokenBody);
//                }
//
//                RetrofitManager.getInstance().createReq(Api.class).uploadFileWithPartMap(map,filePart)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<ResponseBody>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onNext(ResponseBody responseBody) {
//
//                            }
//                        });

                Intent intent = new Intent(MainActivity.this, UploadPhotoActivity.class);
                startActivity(intent);
                break;

        }
    }

    private String getTime() {
        return System.currentTimeMillis() + "";
    }




}
