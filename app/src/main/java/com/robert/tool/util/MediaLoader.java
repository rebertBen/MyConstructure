package com.robert.tool.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;


/**
 * @author bobo
 * <p>
 * function：
 * <p>
 * create_time：2018/8/7 15:44
 * update_by：
 * update_time:
 */
public class MediaLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
//        RequestOptions option = new RequestOptions()
////                .error(R.drawable.error)
//                .placeholder(R.mipmap.ic_launcher);
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
