package com.example.asus.sqldesign_test.activity_set;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.SqlDataTable.DataBaseSQLHelp;
import com.example.asus.sqldesign_test.beanset.week_plan_item;
import com.example.asus.sqldesign_test.otherset.SystemBarTintManager;
import com.example.asus.sqldesign_test.beanset.finish_data_week_num_item;
import com.example.asus.sqldesign_test.adapter_set.finish_data_week_num_adapter;
import com.example.asus.sqldesign_test.adapter_set.week_finish_content_adater;
import java.util.ArrayList;
import java.util.List;

public class Finish_data_activity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mImageView_return;
    private RecyclerView mRecyclerView_week_num;
    private RecyclerView mRecyclerView_content;
    private List<finish_data_week_num_item> mdata_week_num;
    private List<week_plan_item> mData_finish_content_item;
    private week_finish_content_adater mWeek_finish_content_adater;
    private finish_data_week_num_adapter madapter_week_num;
    private DataBaseSQLHelp mDataBaseSQLHelp_add_plan;
    private Cursor cursor;
    private ContentValues values;
    private SQLiteDatabase db;
    private String user_name;
    private SharedPreferences mSharedPreferences_get_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.night_mode);//通知栏所需颜色
        }
        setContentView(R.layout.activity_finish_data_activity);
        initview();
        initRecycler_week_num();
        initRecycler_week_content();
        cursor_Data_week_num(user_name);
    }

    private void initRecycler_week_num() {
        mRecyclerView_week_num.setLayoutManager(new GridLayoutManager(this,5));
        mRecyclerView_week_num.setItemAnimator(new DefaultItemAnimator());
        mdata_week_num = new ArrayList<>();
        madapter_week_num = new finish_data_week_num_adapter(this,mdata_week_num);
        mRecyclerView_week_num.setAdapter(madapter_week_num);
        madapter_week_num.setOnItemClickListener(new finish_data_week_num_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String click_week_num = mdata_week_num.get(position).getWeek_num();
                mData_finish_content_item.clear();
                cursor_Data_content(user_name,click_week_num);
            }
        });

    }

    private void initRecycler_week_content() {
        mRecyclerView_content.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView_content.setItemAnimator(new DefaultItemAnimator());
        mData_finish_content_item = new ArrayList<>();
        mWeek_finish_content_adater = new week_finish_content_adater(this,mData_finish_content_item);
        mRecyclerView_content.setAdapter(mWeek_finish_content_adater);
        mWeek_finish_content_adater.setOnItemClickListener(new week_finish_content_adater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                View view1 = LayoutInflater.from(Finish_data_activity.this).inflate(R.layout.alertdialog_finish_plan_content,null);
                TextView textView_week_num = (TextView) view1.findViewById(R.id.tv_dialog_finish_week_num);
                TextView textView_week_day = (TextView) view1.findViewById(R.id.tv_dialog_finish_week_day);
                TextView textView_content = (TextView) view1.findViewById(R.id.tv_dialog_finish_week_plan_content);
                textView_week_num.setText(mData_finish_content_item.get(position).getWeek_num());
                textView_week_day.setText(mData_finish_content_item.get(position).getWeek_date());
                textView_content.setText("内容："+mData_finish_content_item.get(position).getContent_text());
                AlertDialog alertDialog = new AlertDialog.Builder(Finish_data_activity.this).setTitle("完成计划").setView(view1).show();

            }
        });
    }

    private void initview() {
        mDataBaseSQLHelp_add_plan = new DataBaseSQLHelp(this,"Plan_Punch_Card.db",null,1);
        values = new ContentValues();
        db = mDataBaseSQLHelp_add_plan.getWritableDatabase();
        cursor = db.query("Plan_Punch_Card",null,null,null,null,null,null);
        mSharedPreferences_get_user = getSharedPreferences("user_name",MODE_PRIVATE);
        user_name = mSharedPreferences_get_user.getString("user","");
        mImageView_return = (ImageView) findViewById(R.id.iv_finish_return);
        mImageView_return.setOnClickListener(this);
        mRecyclerView_content = (RecyclerView) findViewById(R.id.recycler_finish_data);
        mRecyclerView_week_num = (RecyclerView) findViewById(R.id.recycler_finish_data_week_num);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_finish_return:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 遍历数据库
     * @param
     */
    public void cursor_Data_week_num(String register_name){
        cursor = db.query("Plan_Punch_Card",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                Boolean data_exit = false;
                String s1 = cursor.getString(cursor.getColumnIndex("register_name"));
                String s3 = cursor.getString(cursor.getColumnIndex("week_num"));
                if (s1.equals(register_name)){
                    for (int i = 0;i<mdata_week_num.size();i++){
                        if (mdata_week_num.get(i).getWeek_num().equals(s3)){
                            data_exit = true;
                        }
                    }
                    if (data_exit){
                        continue;
                    }else {
                        mdata_week_num.add(new finish_data_week_num_item(s3));
                        Log.d("RRRRR",String.valueOf(mdata_week_num.size()));
                        int h = mdata_week_num.size();
                        if (h<0){
                            return;
                        }else {
                            madapter_week_num.notifyDataSetChanged();
                            Log.d("RRRR1",String.valueOf(mdata_week_num.size()));
                        }
                    }

                }
            }while (cursor.moveToNext());
        }
    }

    public void cursor_Data_content(String register_name,String week_num){
        cursor = db.query("Plan_Punch_Card",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String s1 = cursor.getString(cursor.getColumnIndex("register_name"));
                String s2 = cursor.getString(cursor.getColumnIndex("week_day"));
                String s3 = cursor.getString(cursor.getColumnIndex("week_num"));
                String s4 = cursor.getString(cursor.getColumnIndex("plan_context"));
                if (s1.equals(register_name)&&s3.equals(week_num)){
                    mData_finish_content_item.add(new week_plan_item(s3,s2,s4));
                    int h = mData_finish_content_item.size();
                    if (h<0){
                        return;
                    }else {
                        mWeek_finish_content_adater.notifyDataSetChanged();
                    }
                }
            }while (cursor.moveToNext());
        }
    }
}
