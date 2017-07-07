package com.example.asus.sqldesign_test.adapter_set;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.beanset.finish_data_week_num_item;
import java.util.List;

/**
 * Created by ASUS on 2017/6/7.
 */

public class finish_data_week_num_adapter extends RecyclerView.Adapter<finish_data_week_num_adapter.Finish_week_num_holder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<finish_data_week_num_item> mData;
    public OnItemClickListener itemClickListener;

    public finish_data_week_num_adapter(Context context,List<finish_data_week_num_item> mdata){
        this.mContext = context;
        this.mData = mdata;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public Finish_week_num_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.finish_recycler_week_num_item,parent,false);
        Finish_week_num_holder holder = new Finish_week_num_holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Finish_week_num_holder holder, int position) {
        holder.tv_week_num_item.setText(mData.get(position).getWeek_num());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Finish_week_num_holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_week_num_item;
        public Finish_week_num_holder(View itemView) {
            super(itemView);
            tv_week_num_item = (TextView) itemView.findViewById(R.id.tv_finish_week_num_item);
            tv_week_num_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v,getPosition());
            }
        }
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
