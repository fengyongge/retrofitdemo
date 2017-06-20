package com.zzti.retrofitdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.zzti.retrofitdemo.MainActivity;
import com.zzti.retrofitdemo.R;
import com.zzti.retrofitdemo.base.BaseCommAdapter;
import com.zzti.retrofitdemo.base.BaseResponse;
import com.zzti.retrofitdemo.base.ViewHolder;
import com.zzti.retrofitdemo.bean.TagsBean;
import com.zzti.retrofitdemo.net.RetrofitManager;
import com.zzti.retrofitdemo.net.api.Api;
import com.zzti.retrofitdemo.util.PreferencesUtils;
import com.zzti.retrofitdemo.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);

        staff_id = PreferencesUtils.getString(QueryActivity.this,"staff_id");
        supply_id = PreferencesUtils.getString(QueryActivity.this,"supply_id");

        RetrofitManager.getInstance().createReq(Api.class).queryMemberTag(supply_id, staff_id,getTime()).subscribeOn(Schedulers.io())
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




                        }else{

                        }

                    }

                });


    }


    public class Adapter extends BaseCommAdapter<TagsBean> {


        @BindView(R.id.tvTagName)
        TextView tvTagName;

        public Adapter(List<TagsBean> datas) {
            super(datas);
        }

        @Override
        protected void setUI(ViewHolder holder, int position, Context context) {
            ButterKnife.bind(this, holder.getConverView());

            tvTagName.setText("");


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
