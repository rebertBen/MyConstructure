package com.robert.tool.view.image;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.robert.tool.R;
import com.robert.tool.base.BaseActivity;
import com.robert.tool.constant.ArouterPathConstant;

/**
 * @author bobo
 * <p>
 * function：图片选择
 * <p>
 * create_time：2018/9/8 18:42
 * update_by：
 * update_time:
 */
@Route(path = ArouterPathConstant.PATH_TO_PHOTO)
public class SelectPhotoActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_select_photos;
    }

    @Override
    protected void setViews() {
        setTitle(getResources().getString(R.string.select_photo));
    }
}
