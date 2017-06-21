package com.zzti.retrofitdemo.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zzti.retrofitdemo.ActionSheet;
import com.zzti.retrofitdemo.R;
import com.zzti.retrofitdemo.base.BaseResponse;
import com.zzti.retrofitdemo.net.RetrofitManager;
import com.zzti.retrofitdemo.net.api.Api;
import com.zzti.retrofitdemo.util.FileUtil;
import com.zzti.retrofitdemo.util.ImageUtils;
import com.zzti.retrofitdemo.util.ToastUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class UploadPhotoActivity extends AppCompatActivity {

    TextView tv_test;
    public static Uri cropImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
    }


    public void loadMore(String filepath){
        File file = new File(filepath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        String descriptionString = "";

        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        RetrofitManager.getInstance().createReq(Api.class).uplogo(description,body).subscribeOn(Schedulers.io())
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

                        if(baseResponse.code >= 200 && baseResponse.code<300){
                            ToastUtils.showToast(UploadPhotoActivity.this,baseResponse.msg);
                        }else{

                        }

                    }

                });
    }


    private void choosePhoto() {
        ActionSheet.showPhotoSheet(UploadPhotoActivity.this,
                new ActionSheet.OnActionSheetSelected() {

                    @Override
                    public void onClick(int whichButton) {
                        switch (whichButton) {
                            case 0:
                                showTakePicture();
                                break;

                            case 1:
                                showChoosePicture();
                                break;

                            case 2:
                                break;

                            default:
                                break;
                        }

                    }
                }, new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface arg0) {

                    }
                });
    }

    private void showTakePicture() {
        ImageUtils.imageUriFromCamera = ImageUtils.createImagePathUri(UploadPhotoActivity.this);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
        // 返回图片在onActivityResult中通过以下代码获取
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtils.imageUriFromCamera);
        startActivityForResult(intent, ImageUtils.GET_IMAGE_BY_CAMERA);

    }

    private void showChoosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data != null) {
                    cropImage(UploadPhotoActivity.this, data.getData(), false);
                }
                break;
            // 如果是调用相机拍照时
            case ImageUtils.GET_IMAGE_BY_CAMERA:
                if (ImageUtils.imageUriFromCamera != null && resultCode == -1) {
                    cropImage(UploadPhotoActivity.this, ImageUtils.imageUriFromCamera, false);
                }
                break;
            // 取得裁剪后的图片
            case 3:
                break;
            case 4:
                if (cropImageUri != null && data != null
                        && resultCode == -1) {
                    Log.i("fyg", "裁剪后的图片cropImageUri"+cropImageUri);

                    int degree = ImageUtils.getBitmapDegree(ImageUtils.getAbsoluteImagePath(
                            UploadPhotoActivity.this, cropImageUri));
                    String bitmap_url;
                    if (degree == 0) {
                        bitmap_url = cropMyImage(ImageUtils.rotateBitmapByDegree(
                                ImageUtils.getimage(ImageUtils.getAbsoluteImagePath(
                                        UploadPhotoActivity.this, cropImageUri)), degree));
                    } else {
                        bitmap_url = cropMyImage(ImageUtils.getimage(
                                ImageUtils.getAbsoluteImagePath(UploadPhotoActivity.this,
                                        cropImageUri)));
                    }

                    loadMore(bitmap_url);
                }
                break;
            default:
                break;
        }

    }




    /**
     * 截图
     *
     * @param context
     * @param srcUri
     * @param bgimg
     */
    public void cropImage(Context context, Uri srcUri, boolean bgimg) {
        cropImageUri = ImageUtils.createImagePathUri(context);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra("crop", "true");

        if (bgimg) {
            intent.putExtra("aspectX", 3);
            intent.putExtra("aspectY", 2);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, 4);
    }


    private String cropMyImage(Bitmap cropImage) {
        if (cropImage == null) {
            return null;
        } else {
            String path_name = FileUtil.LOCAL_PATH + "/" + System.currentTimeMillis() + ".jpg";
            FileUtil.writeImage(cropImage, path_name, 100);
            return path_name;
        }

    }

//    private String getTime() {
//
//        return System.currentTimeMillis() / 1000 + "";
//    }
//
//    private String getNoncestr() {
//        return UUID.randomUUID().toString().trim().replaceAll("-", "").trim();
//    }
}
