package com.robert.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.robert.tool.R;

import java.util.List;

/**
 * Created by Johnson on 2018/3/27.
 */

public class SelectPhotoRecyclerAdapter extends RecyclerView.Adapter<SelectPhotoRecyclerAdapter.Holder> {

    private OnItemClickListener onItemClickListener;

    private Context context;
    private List<String> mDatas;

    public SelectPhotoRecyclerAdapter(Context context, List<String> datas) {
        this.context = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SelectPhotoRecyclerAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(context).inflate(R.layout.photo_grid_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectPhotoRecyclerAdapter.Holder holder, final int position) {
        if (mDatas.get(position).contains("android")){
            holder.ivDelete.setVisibility(View.GONE);
        } else {
            holder.ivDelete.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.imgDelete(position);
                }
            }
        });

        Glide.with(context).load(mDatas.get(position))
                .into(holder.imageView);


    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView imageView, ivDelete;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_img);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);

        void imgDelete(int pos);
    }
}
