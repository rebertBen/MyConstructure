package com.robert.tool.util;

import android.app.Activity;

import com.robert.tool.dialog.PopuWindowSelectPhotos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bobo
 * <p>
 * function：拉起弹窗
 * <p>
 * create_time：2018/9/4 9:41
 * update_by：
 * update_time:
 */
public class SelectPhotoDialog {
    private List<String> strList = new ArrayList<>(); // 图片选择list

    private static class Singleton {
        public static final SelectPhotoDialog dialog = new SelectPhotoDialog();
    }
    public static SelectPhotoDialog getInstance(){
        return Singleton.dialog;
    }

    /**
     * 图片选择，list item初始化
     */
    private void initPhotoList(){
        if (strList.size() > 0)
            strList.clear();
        strList.add("拍照");
        strList.add("相册选择");
        strList.add("取消");
    }

    public void showDialog(Activity mActivity, String title, int label){
        initPhotoList();
        PopuWindowSelectPhotos.getInstance().showWindow(mActivity, strList, title, label);
    }

}
