package com.robert.tool.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author bobo
 * <p>
 * function：吐司弹窗
 * <p>
 * create_time：2018/6/4 11:52
 * update_by：
 * update_time:
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 短吐司
     * @param context
     * @param content
     */
    public static void showShort(Context context, String content){
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 长吐司
     * @param context
     * @param content
     */
    public static void showLong(Context context, String content){
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 自定义吐司时间
     * @param context
     * @param content
     * @param duration 弹出时间
     */
    public static void showToast(Context context, String content, int duration) {
        toast = null;
        toast = Toast.makeText(context, content, duration);
        toast.show();
    }


}
