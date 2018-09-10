package com.robert.tool.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.robert.tool.R;

import java.util.List;


/**
 * @author bobo
 * <p>
 * function：popuWindow适配器
 * <p>
 * create_time：2018/8/21 15:14
 * update_by：
 * update_time:
 */
public class PopuListAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public PopuListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.popu_window_list_item, null);
            holder.tvText = convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvText.setText(list.get(position));

        return convertView;
    }

    class ViewHolder{
        TextView tvText;
    }
}
