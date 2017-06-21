package com.zzti.retrofitdemo.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.zzti.retrofitdemo.R;
import com.zzti.retrofitdemo.base.BaseCommAdapter;
import com.zzti.retrofitdemo.base.BaseResponse;
import com.zzti.retrofitdemo.base.ViewHolder;
import com.zzti.retrofitdemo.bean.BodyBean;
import com.zzti.retrofitdemo.bean.TagsBean;
import com.zzti.retrofitdemo.dialog.AlertHelper;
import com.zzti.retrofitdemo.myinterface.AlertCallback;
import com.zzti.retrofitdemo.myinterface.SweetAlertCallBack;
import com.zzti.retrofitdemo.net.RetrofitManager;
import com.zzti.retrofitdemo.net.api.Api;
import com.zzti.retrofitdemo.util.PreferencesUtils;
import com.zzti.retrofitdemo.util.ProgressHelps;
import com.zzti.retrofitdemo.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
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

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);

        staff_id = PreferencesUtils.getString(QueryActivity.this,"staff_id");
        supply_id = PreferencesUtils.getString(QueryActivity.this,"supply_id");


        dialog = ProgressHelps.createWindowsBar(QueryActivity.this);

        RetrofitManager.getInstance().createReq(Api.class).queryMemberTag(supply_id, staff_id,getTime()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<TagsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        if(dialog!=null&&dialog.isShowing()){
                            dialog.dismiss();
                        }

                        ToastUtils.showToast(QueryActivity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(BaseResponse<TagsBean> baseResponse) {


                        if(dialog!=null&&dialog.isShowing()){
                            dialog.dismiss();
                        }

                        list.addAll( baseResponse.data.getList());
                        Adapter adapter = new Adapter(list);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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
        protected void setUI(ViewHolder holder, final int position, Context context) {
            ButterKnife.bind(this, holder.getConverView());

            tvTagName.setText(list.get(position).getName()==null?"null":list.get(position).getName());


            //修改
            tvTagName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertHelper.create2EditAlert(QueryActivity.this, "确定", "取消", "是否修改", new SweetAlertCallBack() {
                        @Override
                        public void onConfirm(final String data) {

                            RetrofitManager.getInstance().createReq(Api.class).updateMemberTag(supply_id, staff_id,supply_id,
                                    staff_id,data, list.get(position).getId(),getTime()).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<BaseResponse>() {
                                        @Override
                                        public void onCompleted() {


                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                            ToastUtils.showToast(QueryActivity.this,e.getMessage());

                                        }

                                        @Override
                                        public void onNext(BaseResponse baseResponse) {

                                            ToastUtils.showToast(QueryActivity.this,baseResponse.msg);

                                            tvTagName.setText(data);

                                        }

                                    });
                        }

                        @Override
                        public void onCancle() {

                        }
                    });


                }
            });


            tvTagName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertHelper.createDialog(QueryActivity.this, "确定", "取消",  "是否删除", new AlertCallback() {
                        @Override
                        public void onConfirm() {

                            dialog = ProgressHelps.createWindowsBar(QueryActivity.this);

                            BodyBean bodyBean = new BodyBean();
                            bodyBean.setTagids(list.get(position).getId());
                            bodyBean.setTimestamp(getTime());
                            bodyBean.setSupplier_id(supply_id);
                            bodyBean.setOperator_id(staff_id);
                            String content = JSON.toJSONString(bodyBean);
                            RequestBody  body = FormBody.create(MediaType.parse("application/json; charset=utf-8"),content);

//                            HashMap<String, String> hashMap = new HashMap();
//                            hashMap.put("supply_id",supply_id);
//                            hashMap.put("timestamp",getTime());
//                            hashMap.put("tagids",list.get(position).getId());

                            RetrofitManager.getInstance().createReq(Api.class).deletMemberTag(supply_id, staff_id, body).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<BaseResponse>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                            if(dialog!=null&&dialog.isShowing()){
                                                dialog.dismiss();
                                            }

                                            ToastUtils.showToast(QueryActivity.this,e.getMessage());

                                        }

                                        @Override
                                        public void onNext(BaseResponse baseResponse) {

                                            if(dialog!=null&&dialog.isShowing()){
                                                dialog.dismiss();
                                            }

                                            ToastUtils.showToast(QueryActivity.this,baseResponse.msg);


                                        }

                                    });


                        }

                        @Override
                        public void onCancel() {

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
//
//    public List<Param>  getParameter(Map<String,String> map, String sign){
//
//        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
//        List<Param> params = new ArrayList<>();
//        while (it.hasNext()) {
//            Map.Entry<String, String> entry = it.next();
//            params.add(new Param(entry.getKey(),entry.getValue()!= null && entry.getValue().length()
//                    > 0 ? entry.getValue() : ""));
//        }
//        params.add(new Param("sign", sign));
//        params.add(new Param("publicKey", publicKey));
//        return params;
//    }
//
//
//    public static class Param {
//        String key;
//        String value;
//        public Param() {
//        }
//        public Param(String key, String value) {
//            this.key = key;
//            this.value = value;
//        }
//
//        @Override
//        public String toString() {
//            return "Param{" +
//                    "key='" + key + '\'' +
//                    ", value='" + value + '\'' +
//                    '}';
//        }
//    }



}
