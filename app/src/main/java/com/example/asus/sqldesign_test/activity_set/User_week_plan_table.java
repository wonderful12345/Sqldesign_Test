package com.example.asus.sqldesign_test.activity_set;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.example.asus.sqldesign_test.SqlDataTable.DataBaseSQLHelp;
import com.example.asus.sqldesign_test.beanset.user_table_item;
import com.example.asus.sqldesign_test.beanset.week_plan_table_item;
import com.example.asus.sqldesign_test.adapter_set.week_plan_table_adapter;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.otherset.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class User_week_plan_table extends AppCompatActivity {

    private week_plan_table_adapter mAdapter;
    private List<week_plan_table_item> mData;
    private RecyclerView mRecyclerView_plan_table;
    private ImageView iv_return_plan_table;
    private DataBaseSQLHelp mDataBaseSQLHelp_add_plan;
    private Cursor cursor;
    private ContentValues values;
    private SQLiteDatabase db;
    private String user_name;
    private ImageView iv_return_user_table;
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
        setContentView(R.layout.activity_user_week_plan_table);
        initview();
        initRecycler();
    }

    private void initRecycler() {
        mData = new ArrayList<>();
        mRecyclerView_plan_table.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView_plan_table.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new week_plan_table_adapter(User_week_plan_table.this,mData);
        mRecyclerView_plan_table.setAdapter(mAdapter);
    }

    private void initview() {
        mRecyclerView_plan_table = (RecyclerView) findViewById(R.id.recycler_week_plan_table);
        mDataBaseSQLHelp_add_plan = new DataBaseSQLHelp(this,"Week_Plan_Add.db",null,1);
        values = new ContentValues();
        db = mDataBaseSQLHelp_add_plan.getWritableDatabase();
        cursor = db.query("Week_Plan_Add",null,null,null,null,null,null);
        mSharedPreferences_get_user = getSharedPreferences("user_name",MODE_PRIVATE);
        user_name = mSharedPreferences_get_user.getString("user","");
        iv_return_plan_table = (ImageView) findViewById(R.id.iv_return_week_plan_table);
        iv_return_plan_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    /**
     * 遍历数据库
     * @param
     */
    public void cursor_Data(String register_name){
        cursor = db.query("Week_Plan_Add",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String s1 = cursor.getString(cursor.getColumnIndex("register_name"));
                String s2 = cursor.getString(cursor.getColumnIndex("week_num"));
                String s3 = cursor.getString(cursor.getColumnIndex("week_day"));
                String s4 = cursor.getString(cursor.getColumnIndex("plan_context"));




                //mData.add(new week_plan_table_item(s1,s2));
                //int h = mData_user_table.size();
                //if (h>0){
                //    mAdapter.notifyDataSetChanged();
                //}
            }while (cursor.moveToNext());
        }
    }

}
