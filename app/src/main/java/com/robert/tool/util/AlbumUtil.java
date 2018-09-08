package com.robert.tool.util;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * @author bobo
 * <p>
 * function：相册选择
 * <p>
 * create_time：2018/9/4 10:22
 * update_by：
 * update_time:
 */
public class AlbumUtil {
    private static final int IMG_SIZE = 9;
    private ArrayList<AlbumFile> pathList = new ArrayList<>(); // 记录选择图片
    private String takePhotoUrl;
    private int label; // 用于标记从哪个activity跳转过来

    private static class Singleton {
        public static final AlbumUtil instance = new AlbumUtil();
    }

    public static AlbumUtil getInstance() {
        return Singleton.instance;
    }

    private void initAlbum(Activity mActivity, int label) {
        this.label = label;
        Album.initialize(AlbumConfig.newBuilder(mActivity)
                .setAlbumLoader(new MediaLoader()).build());
    }

    /**
     * 选择相册还是拍照
     * @param mActivity
     * @param flag true表示相册，false表示相机拍照
     * @param label 标记从哪个Activity跳转而来
     * @param count 图片可选数量
     */
    public void getCameraType(final Activity mActivity, boolean flag, int label, int count) {
        initAlbum(mActivity, label);
        if (flag){ // 相册
            Album.image(mActivity)
                    .multipleChoice()
                    .camera(true)
                    .selectCount(count)
                    .checkedList(pathList)
                    .afterFilterVisibility(true)
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            pathList = result;
                            dealData(mActivity, true);
                        }
                    }).onCancel(new Action<String>() {
                @Override
                public void onAction(@NonNull String result) {

                }
            }).start();
        } else { // 拍照
            Album.camera(mActivity) // Camera function.
                    .image() // Take Picture.
//                    .filePath() // File save path, not required.
                    .onResult(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {
                            takePhotoUrl = result;
                            dealData(mActivity, false);
                        }
                    })
                    .onCancel(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {
                        }
                    })
                    .start();
        }
    }

    /**
     * 相册数据返回处理
     */
    private void dealData(Activity mActivity, boolean tag){
        if (label == 100){ // PhotoSelectActivity
//            ((PhotoSelectActivity) mActivity).getImgUrl(tag, pathList, takePhotoUrl);
        }
    }


    /**
     * 清除相册已经勾选的图片
     */
    public void clearList(){
        if (null != pathList && pathList.size() > 0)
            pathList.clear();
    }
}
