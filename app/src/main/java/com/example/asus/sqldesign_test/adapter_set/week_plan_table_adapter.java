package com.example.asus.sqldesign_test.adapter_set;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.beanset.week_plan_table_item;
import java.util.List;

/**
 * Created by ASUS on 2017/5/26.
 */

public class week_plan_table_adapter extends RecyclerView.Adapter<week_plan_table_adapter.Week_plan_table_holder> {

    private Context mContext;
    private List<week_plan_table_item> mData_week_plan_table;
    private LayoutInflater mLayoutInflater;

    public week_plan_table_adapter(Context context,List<week_plan_table_item> list){
        this.mContext = context;
        this.mData_week_plan_table = list;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public Week_plan_table_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.week_plan_table_item,parent,false);
        Week_plan_table_holder week_plan_holder = new Week_plan_table_holder(view);
        return week_plan_holder;
    }
    @Override
    public void onBindViewHolder(Week_plan_table_holder holder, int position) {
        holder.tv_week_num.setText(mData_week_plan_table.get(position).getWeek_num());
        holder.tv_monday_content.setText(mData_week_plan_table.get(position).getMonday_content());
        holder.tv_tuesday_content.setText(mData_week_plan_table.get(position).getTuesday_content());
        holder.tv_wed_content.setText(mData_week_plan_table.get(position).getWed_content());
        holder.tv_thus_content.setText(mData_week_plan_table.get(position).getThus_content());
        holder.tv_friday_content.setText(mData_week_plan_table.get(position).getFirday_content());
        holder.tv_sat_content.setText(mData_week_plan_table.get(position).getSaturday_content());
        holder.tv_sunday_content.setText(mData_week_plan_table.get(position).getSunday_content());
    }

    @Override
    public int getItemCount() {
        return mData_week_plan_table.size();
    }

    class Week_plan_table_holder extends RecyclerView.ViewHolder{
        public TextView tv_week_num;
        public TextView tv_monday_content;
        public TextView tv_tuesday_content;
        public TextView tv_wed_content;
        public TextView tv_thus_content;
        public TextView tv_friday_content;
        public TextView tv_sat_content;
        public TextView tv_sunday_content;
        public Week_plan_table_holder(View itemView) {
            super(itemView);
            tv_week_num = (TextView) itemView.findViewById(R.id.tv_plan_table_week_num);
            tv_monday_content= (TextView) itemView.findViewById(R.id.tv_plan_monday_content);
            tv_tuesday_content = (TextView) itemView.findViewById(R.id.tv_plan_second_content);
            tv_wed_content = (TextView) itemView.findViewById(R.id.tv_plan_third_content);
            tv_thus_content = (TextView) itemView.findViewById(R.id.tv_plan_four_content);
            tv_friday_content = (TextView) itemView.findViewById(R.id.tv_plan_five_content);
            tv_sat_content = (TextView) itemView.findViewById(R.id.tv_plan_six_content);
            tv_sunday_content = (TextView) itemView.findViewById(R.id.tv_plan_seven_content);
        }
    }
}
