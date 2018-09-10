package com.robert.tool.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.robert.tool.R;
import com.robert.tool.adapter.MyViewPagerAdapter;

import java.util.ArrayList;


/**
 * @author bobo
 * <p>
 * function：图片展示
 * <p>
 * create_time：2018/8/9 17:21
 * update_by：
 * update_time:
 */
public class PopuWindowShowPhoto extends PopupWindow implements PopupWindow.OnDismissListener, MyViewPagerAdapter.ViewPagerInter {
    private static PopupWindow popupWindow;
    private View contentView;
    private MyViewPagerAdapter adapter;
    private ViewPager viewPager;
    private Activity context;

    /**
     * 图片展示
     * @param context 上下文对象
     * @param urls 图片集合
     * @param pos 当前图片位置
     */
    public void showWindow(final Activity context, ArrayList<String> urls, int pos) {
        this.context = context;

        // 用于PopupWindow的View
        contentView = LayoutInflater.from(context)
                .inflate(R.layout.popu_window_show_photo, null, false);

        popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.alpha_anim);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.3f;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);

        viewPager = contentView.findViewById(R.id.view_pager);
        adapter = new MyViewPagerAdapter(context, urls, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
    }

    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 1.0f;
        context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
        popupWindow.dismiss();
    }

    @Override
    public void viewPagerClick() {
        onDismiss();
    }
}
