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
import com.zzti.retrofitdemo.util.StringUtils;
import com.zzti.retrofitdemo.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.SafeSubscriber;
import rx.schedulers.Schedulers;

import static com.zzti.retrofitdemo.net.interceptor.RspCheckInterceptor.toMD5;


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
    private boolean isClear;

    private  String publicKey="DGAIC6F0SW5BTV56";
    static String appSecret="D$GAS@WQK8QD19$I";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);

        staff_id = PreferencesUtils.getString(QueryActivity.this,"staff_id");
        supply_id = PreferencesUtils.getString(QueryActivity.this,"supply_id");
        loadMore();
    }


    public void loadMore(){
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

                        if(isClear){
                            list.clear();
                        }

                        if(dialog!=null&&dialog.isShowing()){
                            dialog.dismiss();
                        }

                        list.addAll( baseResponse.data.getList());
                        Adapter adapter = new Adapter(list);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        isClear=true;
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

//                            Map<String, String> map = new TreeMap<String, String>();
//                            map.put("timestamp", getTime());
//                            map.put("supplier_id", supply_id);
//                            map.put("operator_id", staff_id);
//                            map.put("tagids", list.get(position).getId());
//                            String sortString = sort(appSecret, map);
//                            Logger.i("签名之前："+sortString);
//                            String sign = toMD5(sortString);
//                            HashMap<String, String> hashMap = new HashMap();
//                            hashMap.put("timestamp",getTime());
//                            hashMap.put("tagids",list.get(position).getId());
//                            String content =  getpParameter(map,sign);
//
//                            RequestBody  body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),content);


                            Map<String, String> map = new TreeMap<String, String>();
                            map.put("timestamp", getTime());
                            map.put("supplier_id", supply_id);
                            map.put("operator_id", staff_id);
                            map.put("tagids", list.get(position).getId());
                            String content =  getpParameter2(map);

                            MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
                            RequestBody  body = FormBody.create(CONTENT_TYPE,content);

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

                                                       loadMore();

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



    public String getpParameter(Map<String,String> map,String sign){
        StringBuilder buffer = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            buffer.append("&").append(entry.getKey()).append("=").append(
                    entry.getValue()!= null && entry.getValue().length()
                            > 0 ? entry.getValue() : "");
        }
        String parameterString = buffer.toString();
        String s2="";
        if(parameterString.length()>0){
            s2 = parameterString.substring(1,parameterString.length());
            s2+= "&sign="+ sign +"&publicKey="+publicKey;
        }
        return s2;
    }


    public String getpParameter2(Map<String,String> map){
        StringBuilder buffer = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            buffer.append("&").append(entry.getKey()).append("=").append(
                    entry.getValue()!= null && entry.getValue().length()
                            > 0 ? entry.getValue() : "");
        }
        String parameterString = buffer.toString();
        String s2="";
        if(parameterString.length()>0){
            s2 = parameterString.substring(1,parameterString.length());
        }
        return s2;
    }


    public static class Param {
        String key;
        String value;
        public Param() {
        }
        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Param{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }


    public final static String sort(String token, Map<String, String> map) {

        Iterator<String> iter = map.keySet().iterator();
        String s = token;
        while (iter.hasNext()) {
            Object key = iter.next();
            StringBuffer sb = new StringBuffer();
            s += sb.append(key + map.get(key));
        }
        String ss = s + token;
        Logger.i("签名之前" + ss);
        return ss;
    }




}
