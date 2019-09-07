package com.robert.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robert.tool.R;

import java.util.List;

/**
 * @author bobo
 * <p>
 * function：
 * <p>
 * create_time：2018/10/31 15:56
 * update_by：
 * update_time:
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {
    private List<String> list;
    private Context context;
    private GreenDAOInterface inter;

    public RecyclerViewAdapter (Context context, List<String> list, GreenDAOInterface inter){
        this.list = list;
        this.context = context;
        this.inter = inter;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.adapter_greendao_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.tvText.setText(list.get(position));

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.add(position);
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.update(position);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tvText;
        private Button addBtn, editBtn, deleteBtn;

        public Holder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
            addBtn = itemView.findViewById(R.id.add_btn);
            editBtn = itemView.findViewById(R.id.edit_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }

    public interface GreenDAOInterface {
        void add(int pos);
        void update(int pos);
        void delete(int pos);
    }
}
