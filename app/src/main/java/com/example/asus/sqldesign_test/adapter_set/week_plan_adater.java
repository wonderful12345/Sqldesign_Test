package com.example.asus.sqldesign_test.adapter_set;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.beanset.week_plan_item;

import java.util.List;

/**
 * Created by ASUS on 2017/5/24.
 */

public class week_plan_adater extends RecyclerView.Adapter<week_plan_adater.Plan_item_holder> {

    private List<week_plan_item> mData_plan_item;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public OnItemClickListener itemClickListener;

    public week_plan_adater(Context context,List<week_plan_item> list){
        this.mContext = context;
        this.mData_plan_item = list;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public Plan_item_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.adapter_plan_item,parent,false);
        Plan_item_holder plan_item_holder = new Plan_item_holder(view);
        return plan_item_holder;
    }

    @Override
    public void onBindViewHolder(Plan_item_holder holder, int position) {
        holder.mTextView_week_day.setText(mData_plan_item.get(position).getWeek_date());
        holder.mTextView_week_num.setText(mData_plan_item.get(position).getWeek_num());
        String content = mData_plan_item.get(position).getContent_text();
        int content_length = content.length();
        if (content_length>8){
            content = content.substring(0,9)+"...";
        }
        holder.mTextView_plan_content.setText(content);
    }

    @Override
    public int getItemCount() {
        return mData_plan_item.size();
    }



    class Plan_item_holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView_week_num;
        public TextView mTextView_week_day;
        public TextView mTextView_plan_content;
        public CardView mCardView_plan;

        public Plan_item_holder(View itemView) {
            super(itemView);
            mTextView_plan_content = (TextView) itemView.findViewById(R.id.tv_plan_content);
            mTextView_week_day = (TextView) itemView.findViewById(R.id.tv_week_day);
            mTextView_week_num = (TextView) itemView.findViewById(R.id.tv_week_num);
            mCardView_plan = (CardView) itemView.findViewById(R.id.cv_week_plan);
            mCardView_plan.setOnClickListener(this);
        }
        // 通过接口回调来实现RecyclerView的点击事件
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
