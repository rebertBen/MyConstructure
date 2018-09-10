package com.robert.tool.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.wingsofts.dragphotoview.DragPhotoView;

/**
 * @author bobo
 * <p>
 * function：
 * <p>
 * create_time：2018/9/10 11:41
 * update_by：
 * update_time:
 */
public class MyDragPhotoView extends DragPhotoView implements View.OnClickListener {

    public MyDragPhotoView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public MyDragPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }


    @Override
    public void onClick(View v) {

    }
}
