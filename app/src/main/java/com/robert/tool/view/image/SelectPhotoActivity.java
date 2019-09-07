package com.robert.tool.view.image;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.robert.tool.R;
import com.robert.tool.adapter.SelectPhotoRecyclerAdapter;
import com.robert.tool.base.BaseActivity;
import com.robert.tool.constant.ArouterPathConstant;
import com.robert.tool.dialog.PopuWindowShowPhoto;
import com.robert.tool.util.AlbumUtil;
import com.robert.tool.util.SelectPhotoDialog;
import com.robert.tool.util.ToastUtil;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private static final int IMG_SIZE = 9;
    public boolean dismissFlag = false; // 是否预览图片状态

    RecyclerView recyclerView;

    private SelectPhotoRecyclerAdapter adapter;
    private List<String> list = new ArrayList<>();

    private ArrayList<AlbumFile> pathList = new ArrayList<>(); // 记录选择图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_select_photos;
    }

    @Override
    protected void setViews() {
        setTitle(getResources().getString(R.string.select_photo));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new SelectPhotoRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SelectPhotoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ArrayList<String> urls = new ArrayList<>();
                urls.addAll(list);
                PopuWindowShowPhoto.getInstance().showWindow(SelectPhotoActivity.this, urls, pos);
                dismissFlag = true;
            }

            @Override
            public void imgDelete(int pos) {
                ivDelete(pos);
            }
        });
    }

    @OnClick(R.id.img_btn)
    public void onViewClicked() {
        SelectPhotoDialog.getInstance()
                .showDialog(this, getResources().getString(R.string.select_photo), 200);
    }

    /**
     * 相册选择
     *
     * @param item 0表示拍照，1表示相册
     */
    public void getFlag(int item) {
        if (item == 0) {
            AlbumUtil.getInstance().getCameraType(this, false, 200, 9);
        } else if (item == 1) {
            AlbumUtil.getInstance().getCameraType(this, true, 200, 9);
        }
    }

    /**
     * 相册数据返回
     * @param tag true表示相册，false表示拍照
     * @param pathList
     * @param takePhotoUrl
     */
    public void getImgUrl(boolean tag, ArrayList<AlbumFile> pathList, String takePhotoUrl) {
        this.pathList = pathList;
        List<String> template = new ArrayList<>();
        template.addAll(list);
        if (null != pathList) {
            if (tag){
                for (int i = 0, len = this.pathList.size(); i < len; i++) {
                    String url = "file:///" + pathList.get(i).getPath();
                    if (!list.contains(url))
                        list.add(url);
                }
            } else {
                list.add(takePhotoUrl);
            }
        }
        if (list.size() > IMG_SIZE){
            ToastUtil.showShort(this, "最多选择" + IMG_SIZE + "张图片");
            list.clear();
            list.addAll(template);
            return;
        }
        adapter.notifyDataSetChanged();
    }

    public void ivDelete(int pos) {
        for (int i = 0, len = pathList.size(); i < len; i++){
            String url = list.get(pos).replace("file:///", "");
            if (pathList.get(i).getPath().equals(url)){
                pathList.remove(i);
                break;
            }
        }
        list.remove(pos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (dismissFlag) {
                PopuWindowShowPhoto.getInstance().onDismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
