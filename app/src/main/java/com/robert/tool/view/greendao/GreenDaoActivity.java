package com.robert.tool.view.greendao;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.robert.tool.R;
import com.robert.tool.adapter.RecyclerViewAdapter;
import com.robert.tool.base.BaseActivity;
import com.robert.tool.constant.ArouterPathConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bobo
 * <p>
 * function：数据库操作
 * <p>
 * create_time：2018/10/31 15:39
 * update_by：
 * update_time:
 */
@Route(path = ArouterPathConstant.PATH_TO_DATABASE)
public class GreenDaoActivity extends BaseActivity implements RecyclerViewAdapter.GreenDAOInterface {
    private List<String> list = new ArrayList<>();

    private RecyclerViewAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int layoutId() {
        return R.layout.activity_greendao_layout;
    }

    @Override
    protected void setViews() {
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.data_base));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initList();
        adapter = new RecyclerViewAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    private void initList() {
        if (list.size() != 0)
            list.clear();
        for (int i = 0; i < 10; i++){
            list.add("数据 " + i);
        }
    }


    @Override
    public void add(int pos) {

    }

    @Override
    public void update(int pos) {

    }

    @Override
    public void delete(int pos) {

    }
}
