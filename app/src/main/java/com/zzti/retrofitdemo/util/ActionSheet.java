package com.zzti.retrofitdemo.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzti.retrofitdemo.R;


/**
 * Created by fengyongge on 2017/2/11.
 */

public class ActionSheet {


    public interface OnActionSheetSelected {
        void onClick(int whichButton);
    }

    /**
     * 拍照或者从图库选择照片
     * @param context
     * @param actionSheetSelected
     * @param cancelListener
     * @return
     */




    public static Dialog showPhotoSheet(Context context, final OnActionSheetSelected actionSheetSelected,
                                        DialogInterface.OnCancelListener cancelListener) {
        final Dialog dlg = new Dialog(context, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.photoactionsheet, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);

        TextView take_pic = (TextView) layout.findViewById(R.id.take_pic);
        TextView album_pic = (TextView) layout.findViewById(R.id.album_pic);
        TextView mCancel = (TextView) layout.findViewById(R.id.cancel);



        take_pic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                actionSheetSelected.onClick(0);
                dlg.dismiss();
            }
        });

        album_pic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                actionSheetSelected.onClick(1);
                dlg.dismiss();
            }
        });


        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                actionSheetSelected.onClick(2);
                dlg.dismiss();
            }
        });


        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        if (cancelListener != null)
            dlg.setOnCancelListener(cancelListener);

        dlg.setContentView(layout);
        dlg.show();

        return dlg;
    }

}
