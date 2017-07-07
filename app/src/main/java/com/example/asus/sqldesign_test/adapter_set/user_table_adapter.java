package com.example.asus.sqldesign_test.adapter_set;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.beanset.user_table_item;

import java.util.List;

/**
 * Created by ASUS on 2017/5/26.
 */

public class user_table_adapter extends RecyclerView.Adapter<user_table_adapter.User_table_holder> {

    private List<user_table_item> mData_user_item;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public user_table_adapter(Context context,List<user_table_item> list){
        this.mContext = context;
        this.mData_user_item = list;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public User_table_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.table_item,parent,false);
        User_table_holder user_table_holder = new User_table_holder(view);
        return user_table_holder;
    }

    @Override
    public void onBindViewHolder(User_table_holder holder, int position) {
        holder.mTextView_user_name.setText(mData_user_item.get(position).getUser_name());
        holder.mTextView_user_password.setText(mData_user_item.get(position).getUser_password());
    }

    @Override
    public int getItemCount() {
        return mData_user_item.size();
    }

    class User_table_holder extends RecyclerView.ViewHolder{

        public TextView mTextView_user_name;
        public TextView mTextView_user_password;

        public User_table_holder(View itemView) {
            super(itemView);
            mTextView_user_name = (TextView) itemView.findViewById(R.id.tv_user_table_user_name);
            mTextView_user_password = (TextView) itemView.findViewById(R.id.tv_user_table_user_password);
        }
    }
}
