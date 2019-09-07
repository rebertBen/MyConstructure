package com.robert.tool.view.snap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.robert.tool.R;

import java.util.List;

/**
 * @author RyanLee
 * @date 2017/12/7
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private Context mContext;
    private List<String> list;


    public RecyclerAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_gallery_item, parent, false);
        MyHolder viewHolder = new MyHolder(itemView);
        int width = parent.getWidth();
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.width =  (width / 5);
        itemView.setLayoutParams(layoutParams);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        if (position < 2 || position > list.size() - 3){
            holder.tvText.setVisibility(View.INVISIBLE);
        } else {
            holder.tvText.setVisibility(View.VISIBLE);
        }
        holder.tvText.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        MyHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

}
