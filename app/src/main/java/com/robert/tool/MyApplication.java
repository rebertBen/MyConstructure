package com.robert.tool;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author bobo
 * <p>
 * function：初始化数据(插件初始化)
 * <p>
 * create_time：2018/7/20 11:30
 * update_by：
 * update_time:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (true) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

//        registerToWX();
    }

//    private void registerToWX() {
//        //第二个参数是指你应用在微信开放平台上的AppID
//        mWxApi = WXAPIFactory.createWXAPI(this, "", false);
//        // 将该app注册到微信
//        mWxApi.registerApp("");
//    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy(); // 解除绑定
    }
}
