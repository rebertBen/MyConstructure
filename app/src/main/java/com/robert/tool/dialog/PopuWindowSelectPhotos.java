package com.robert.tool.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.robert.tool.R;
import com.robert.tool.adapter.PopuListAdapter;
import com.robert.tool.view.image.SelectPhotoActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author bobo
 * <p>
 * function：弹窗
 * <p>
 * create_time：2018/8/9 17:21
 * update_by：
 * update_time:
 */
public class PopuWindowSelectPhotos extends PopupWindow {

    private static PopupWindow popupWindow;
    private View contentView;
    private ListView listView;
    private TextView tvTitle;

    private PopuListAdapter adapter;
    private List<String> list = new ArrayList<>();
    private String title;

    private static class Singleton{
        public static final PopuWindowSelectPhotos instance = new PopuWindowSelectPhotos();
    }

    public static PopuWindowSelectPhotos getInstance(){
        return Singleton.instance;
    }

    /**
     * 展示
     * @param context 上下文对象（Activity）
     * @param list 列表
     * @param title 标题
     * @param label 区别跳转
     */
    public void showWindow(final Activity context, List<String> list, String title, int label) {
        this.list = list;
        this.title = title;

        // 用于PopupWindow的View
        contentView = LayoutInflater.from(context)
                .inflate(R.layout.popu_window_center_view, null, false);

        popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.alpha_anim);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.3f;
//        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1.0f;
//                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(lp);
            }
        });

        initView(context, label);
    }

    private void initView(final Context context, final int label) {
        listView = contentView.findViewById(R.id.listview);
        tvTitle = contentView.findViewById(R.id.tv_title);

        adapter = new PopuListAdapter(context, list);
        listView.setAdapter(adapter);
        tvTitle.setText(title);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (label == 200){
                    ((SelectPhotoActivity) context).getFlag(position);
                }
                popupWindow.dismiss();
            }
        });
    }

}
