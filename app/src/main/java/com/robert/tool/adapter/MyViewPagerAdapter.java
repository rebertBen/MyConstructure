package com.robert.tool.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robert.tool.R;
import com.wingsofts.dragphotoview.DragPhotoView;

import java.util.List;

/**
 * @author bobo
 * <p>
 * function：图片适配器
 * <p>
 * create_time：2018/9/5 10:55
 * update_by：
 * update_time:
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private List<String> urls;
    private Context context;
    private ViewPagerInter inter;

    public MyViewPagerAdapter(Context context, List<String> urls, ViewPagerInter inter) {
        this.context = context;
        this.urls = urls;
        this.inter = inter;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.view_pager_item, null);
//        ImageView img = view.findViewById(R.id.iv_img);
        DragPhotoView img = view.findViewById(R.id.iv_img);
        TextView tvCount = view.findViewById(R.id.tv_count);
        Glide.with(context).load(urls.get(position)).into(img);
        tvCount.setText((position + 1) + "/" + urls.size());
        container.addView(view);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.viewPagerClick();
            }
        });

        img.setOnExitListener(new DragPhotoView.OnExitListener() {
            @Override
            public void onExit(DragPhotoView dragPhotoView, float v, float v1, float v2, float v3) {
                inter.viewPagerClick();
            }
        });

        img.setOnTapListener(new DragPhotoView.OnTapListener() {
            @Override
            public void onTap(DragPhotoView dragPhotoView) {
                inter.viewPagerClick();
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    public interface ViewPagerInter {
        void viewPagerClick();
    }
}
