package com.robert.tool.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robert.tool.R;
import com.robert.tool.util.ToastUtil;

/**
 * @author bobo
 * <p>
 * function：基类
 * <p>
 * create_time：2018/9/3 9:46
 * update_by：
 * update_time:
 */
public abstract class BaseActivity extends AppCompatActivity {
    private RelativeLayout contentView;
    public ImageView ivBack;
    private TextView tvTitle;
    public View contentLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });

        contentView = findViewById(R.id.rl_content_layout);
        contentLayout = LayoutInflater.from(this).inflate(layoutId(), null, false);
        contentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        contentView.addView(contentLayout);
        setViews();
    }

    /**
     * 布局文件id
     */
    protected abstract int layoutId();

    /**
     * 布局初始化
     */
    protected abstract void setViews();

    protected void setTitle(String title){
        if (null != title && !"".equals(title.trim().toString()))
            tvTitle.setText(title);
        else
            ToastUtil.showShort(this, getResources().getString(R.string.title_error));
    }

    @Override
    public void finish() {
        super.finish();
    }

}
