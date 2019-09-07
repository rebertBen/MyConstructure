package com.robert.tool.view.snap;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.robert.tool.R;
import com.robert.tool.constant.ArouterPathConstant;
import com.robert.tool.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : bobo
 * <p>
 * description: snap
 * <p>
 * date: 2019/9/7 16:25
 * update by:
 * update time:
 * version: 1.0
 */
@Route(path = ArouterPathConstant.PATH_TO_SNAP)
public class SnapHorizontalActivity extends AppCompatActivity implements GalleryRecyclerView.OnItemClickListener {

    @BindView(R.id.recycler_view)
    GalleryRecyclerView recyclerView;

    private List<String> list;
    private int index;
    private int itemWidth; // 一个item的宽度
    private int distance; // 移动距离


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        initData();
        initScreen();
        RecyclerAdapter adapter = new RecyclerAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        setScreenPosition();
    }

    private void initScreen() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        itemWidth = width / 5;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                distance += dx;
                index = distance / itemWidth + 2;
            }
        });
    }

    private void initData() {
        if (null == list)
            list = new ArrayList<>();
        if (list.size() > 0)
            list.clear();

        for (int i = 0; i < 10; i++){
            list.add("第 " + i + "个");
        }
    }

    private void setScreenPosition() {
        int size = list.size();
        index = 0;
        if (size == 5 || size == 6){ // 1, 2个
            index = 0;
        } else if (list.size() == 7){
            index = 1;
        } else { // 大于3
            index = 2;
        }
        recyclerView.setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                .setOnItemClickListener(this)
                .initPosition(index)
                .setUp();
    }



    @Override
    public void onItemClick(View view, int position) {
        ToastUtil.showShort(this, "选中第" + position + " item");
        int dis = getDistance(position) * itemWidth;
        recyclerView.smoothScrollBy(dis, 0);
        index = position;
    }

    private int getDistance(int pos){
        if (index > pos) {
            return pos - index;
        } else if (index == pos) {
            return 0;
        } else {
            return pos - index;
        }
    }

}
