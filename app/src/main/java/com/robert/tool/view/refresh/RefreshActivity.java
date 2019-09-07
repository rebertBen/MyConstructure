package com.robert.tool.view.refresh;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.robert.tool.R;
import com.robert.tool.base.BaseActivity;
import com.robert.tool.constant.ArouterPathConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bobo
 * <p>
 * function：下拉刷新
 * <p>
 * create_time：2018/9/10 16:44
 * update_by：
 * update_time:
 */
@Route(path = ArouterPathConstant.PATH_TO_REFRESH)
public class RefreshActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_refresh_layout;
    }

    @Override
    protected void setViews() {
        refreshLayout = findViewById(R.id.refresh_layout);
        setTitle(getResources().getString(R.string.pull_refresh));
        initRefreshListener();
    }

    private void initRefreshListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore();
            }
        });
    }



}
