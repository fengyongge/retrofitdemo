package com.zzti.retrofitdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zzti.retrofitdemo.MainActivity;
import com.zzti.retrofitdemo.R;
import com.zzti.retrofitdemo.base.BaseCommAdapter;
import com.zzti.retrofitdemo.base.BaseResponse;
import com.zzti.retrofitdemo.base.ViewHolder;
import com.zzti.retrofitdemo.bean.BodyBean;
import com.zzti.retrofitdemo.bean.TagsBean;
import com.zzti.retrofitdemo.net.RetrofitManager;
import com.zzti.retrofitdemo.net.api.Api;
import com.zzti.retrofitdemo.util.PreferencesUtils;
import com.zzti.retrofitdemo.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class QueryActivity extends AppCompatActivity {

    @BindView(R.id.lv)
    ListView lv;

    private String staff_id,supply_id;
    List<TagsBean.TagBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);

        staff_id = PreferencesUtils.getString(QueryActivity.this,"staff_id");
        supply_id = PreferencesUtils.getString(QueryActivity.this,"supply_id");

        RetrofitManager.getInstance().createReq(Api.class).queryMemberTag(supply_id, staff_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<TagsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<TagsBean> baseResponse) {

                        if(baseResponse.getCode() >= 200 && baseResponse.getCode()<300){

                            list.addAll( baseResponse.getData().getList());
                            Adapter adapter = new Adapter(list);

                            Logger.i(""+list.size());
                            lv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }else{

                        }

                    }

                });


    }


    public class Adapter extends BaseCommAdapter<TagsBean.TagBean> {


        @BindView(R.id.tvTagName)
        TextView tvTagName;

        public Adapter(List<TagsBean.TagBean> datas) {
            super(datas);
        }

        @Override
        protected void setUI(ViewHolder holder, int position, Context context) {
            ButterKnife.bind(this, holder.getConverView());


            tvTagName.setText(list.get(position).getName()==null?"null":list.get(position).getName());


            //修改
            tvTagName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RetrofitManager.getInstance().createReq(Api.class).updateMemberTag(supply_id, staff_id,supply_id,  staff_id,"53", "240",getTime()).subscribeOn(Schedulers.io())
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
                                        ToastUtils.showToast(QueryActivity.this,baseResponse.getMsg());
                                    }else{


                                        ToastUtils.showToast(QueryActivity.this,baseResponse.getMsg());
                                    }

                                }

                            });

                }
            });


            tvTagName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    BodyBean bodyBean = new BodyBean();
                    bodyBean.setSupplier_id("1");
                    bodyBean.setOperator_id("53");
                    bodyBean.setTagids("240");


                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyBean.toString());

                    RetrofitManager.getInstance().createReq(Api.class).deletMemberTag("1", "53", body,getTime()).subscribeOn(Schedulers.io())
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
                                        ToastUtils.showToast(QueryActivity.this,baseResponse.getMsg());
                                    }else{

                                    }

                                }

                            });



                    return false;
                }
            });

        }

        @Override
        protected int getLayoutId() {
            return R.layout.activity_query_item;
        }

    }


    private String getTime() {
        return System.currentTimeMillis() + "";
    }


}
