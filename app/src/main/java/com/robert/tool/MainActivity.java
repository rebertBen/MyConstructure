package com.robert.tool;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.robert.tool.base.BaseActivity;
import com.robert.tool.constant.ArouterPathConstant;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setViews() {
        setTitle(getResources().getString(R.string.main_page));
        ivBack.setVisibility(View.GONE);
    }

    @OnClick({R.id.img_btn, R.id.wechat_btn, R.id.js_btn, R.id.refresh_btn, R.id.database_btn,
            R.id.zxing_btn, R.id.locate_btn, R.id.banner_btn, R.id.video_btn, R.id.vertify_btn,
            R.id.popu_btn, R.id.snap_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_btn:
                ARouter.getInstance().build(ArouterPathConstant.PATH_TO_PHOTO).navigation();
                break;
            case R.id.wechat_btn:
                break;
            case R.id.js_btn:
                ARouter.getInstance().build(ArouterPathConstant.PATH_TO_WEBVIEW).navigation();
                break;
            case R.id.refresh_btn:
                ARouter.getInstance().build(ArouterPathConstant.PATH_TO_REFRESH).navigation();
            case R.id.database_btn:
                ARouter.getInstance().build(ArouterPathConstant.PATH_TO_DATABASE).navigation();
                break;
            case R.id.zxing_btn:
                ARouter.getInstance().build(ArouterPathConstant.PATH_TO_QR_CODE).navigation();
                break;
            case R.id.locate_btn:
                break;
            case R.id.banner_btn:
                break;
            case R.id.video_btn:
                break;
            case R.id.vertify_btn:
                break;
            case R.id.popu_btn:
                break;
            case R.id.snap_btn:
                ARouter.getInstance().build(ArouterPathConstant.PATH_TO_SNAP).navigation();
                break;
        }
    }

}
