package com.robert.tool.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.robert.tool.util.NetUtil;
import com.robert.tool.util.ToastUtil;

/**
 * @author bobo
 * <p>
 * function：基类
 * <p>
 * create_time：2018/11/3 11:10
 * update_by：
 * update_time:
 */
public abstract class BaseActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        initViews();
        initResource();
    }

    /**
     * 布局文件
     * @return 布局文件id
     */
    public abstract int setLayout();

    /**
     * 布局初始化
     */
    public abstract void initViews();

    /**
     * 初始化数据
     */
    protected abstract void initResource();


    /**
     * 加载对话
     */
    public void showLoadingDialg(){

    }

    /**
     * 关闭加载框
     */
    public void closeLoadingDialog(){

    }

    /**
     * 是否联网
     * @return
     */
    public boolean getNetworkStatus(){
        return NetUtil.isConnected(this);
    }

    /**
     * 吐司
     * @param msg 内容
     */
    public void toastMsg(String msg){
        ToastUtil.showShort(this, msg);
    }

}
