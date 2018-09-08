package com.robert.tool.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * @author bobo
 * <p>
 * function：动态权限的获取处理
 * <p>
 * create_time：2018/6/15 11:39
 * update_by：
 * update_time:
 */
public class PerimissionsUtil {
    private static final int REQUEST_PERMISSION_CODE = 321;
    private AlertDialog mPermissionDialog;

    private static boolean isAutoRequest(){
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    /**
     * 开始提交请求权限
     * @param mActivity 上下文对象
     */
    public static void startRequestPermission(Activity mActivity, String[] permissions) {
        if (!isAutoRequest())
            ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_PERMISSION_CODE);
    }

    /**
     * 判断是否获取权限
     * @return true已授权，false未授权
     */
    public static boolean isPermissionGranted(Activity mActivity, String permission){
        if (!isAutoRequest()) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(mActivity, permission);
            if (i != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public static void showUserRequestPermission(final Activity mActivity, String message){
        new AlertDialog.Builder(mActivity)
                .setTitle("存储权限不可用")
                .setMessage(message)
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(mActivity,"用户取消授权", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
    }

    /**
     * 不再提示权限时的展示对话框
     */
    public static void showPermissionDialog(final Context context) {
            new AlertDialog.Builder(context)
                    .setMessage("已禁用权限，请在设置中手动授予，以免影响功能使用")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri packageURI = Uri.parse("package:" + context.getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO 取消处理
                        }
                    }).setCancelable(true).show();
    }



}
