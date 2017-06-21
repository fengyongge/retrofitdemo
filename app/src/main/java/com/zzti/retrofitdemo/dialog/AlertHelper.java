package com.zzti.retrofitdemo.dialog;

import android.content.Context;
import android.view.View;

import com.zzti.retrofitdemo.myinterface.AlertCallback;
import com.zzti.retrofitdemo.myinterface.SweetAlertCallBack;


/**
 * Created by fengyongge on 2016/5/26 0026.
 */
public class AlertHelper {

    private static SweetAlertDialog alert;


    public static void createDialog(Context context, String confirm_name, String cancel_name, String message, final AlertCallback alertCallback) {

        new AlertDialog(context).builder()
                .setMsg(message)
                .setPositiveButton(confirm_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertCallback.onConfirm();
                    }
                }).setNegativeButton(cancel_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCallback.onCancel();
            }
        }).show();

    }



    public static SweetAlertDialog create2EditAlert(Context ctx, String confirm_name, String cancel_name, String hint, final SweetAlertCallBack alertCallback) {

        try {

            alert = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);
            alert.showCancelButton(true);
            alert.setConfirmText(confirm_name);
            alert.setCancelText(cancel_name);
            alert.setHintText(hint);

            alert.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    alertCallback.onCancle();
                    sDialog.dismiss();
                }
            })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            alertCallback.onConfirm(alert.getEditText());
                            sDialog.dismiss();
                        }
                    })
                    .show();
            alert.setCanceledOnTouchOutside(false);
            return alert;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }

}
