package com.zzti.retrofitdemo.util;

import android.graphics.Bitmap;
import android.os.Environment;


import com.zzti.retrofitdemo.app.MyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by fengyongge on 2016/11/24 0024.
 */

public class FileUtil {

    public static String SDCARD_PAHT ;// SD卡路径
    public static String CURRENT_PATH = "";// 当前的路径,如果有SD卡的时候当前路径为SD卡，如果没有的话则为程序的私有目录

    public static String LOCAL_PATH ;
    static
    {
        init();
    }

    public static void init()
    {
        SDCARD_PAHT = Environment.getExternalStorageDirectory().getPath();// SD卡路径
        LOCAL_PATH = MyApp.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();// 本地路径,即/data/data/目录下的程序私有目录

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            CURRENT_PATH = SDCARD_PAHT;
        }
        else
        {
            CURRENT_PATH = LOCAL_PATH;
        }
    }

    public static void writeImage(Bitmap bitmap, String destPath, int quality)
    {
        try {
            FileUtil.deleteFile(destPath);
            if (FileUtil.createFile(destPath))
            {
                FileOutputStream out = new FileOutputStream(destPath);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG,quality, out))
                {
                    out.flush();
                    out.close();
                    out = null;
                }

                bitmap.recycle();
                bitmap=null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 删除一个文件
     *
     * @param filePath
     *            要删除的文件路径名
     * @return true if this file was deleted, false otherwise
     */
    public static boolean deleteFile(String filePath)
    {
        try {
            File file = new File(filePath);
            if (file.exists())
            {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建一个文件，创建成功返回true
     *
     * @param filePath
     * @return
     */
    public static boolean createFile(String filePath)
    {
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                if (!file.getParentFile().exists())
                {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
