package com.example.asus.sqldesign_test;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.asus.sqldesign_test.activity_set.Content_of_Plan;
import com.example.asus.sqldesign_test.activity_set.Delete_Week_Plan;
import com.example.asus.sqldesign_test.activity_set.Finish_data_activity;
import com.example.asus.sqldesign_test.activity_set.SqlLite_query;
import com.example.asus.sqldesign_test.activity_set.User_table_extinct;
import com.example.asus.sqldesign_test.beanset.week_plan_item;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.asus.sqldesign_test.SqlDataTable.DataBaseSQLHelp;
import com.example.asus.sqldesign_test.activity_set.Add_Week_plan;
import com.example.asus.sqldesign_test.otherset.SystemBarTintManager;
import com.example.asus.sqldesign_test.adapter_set.week_plan_adater;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_add_week_plan;
    private Button btn_delete_week_plan;
    private Button btn_find_week;
    private List<week_plan_item> mData_item;
    private week_plan_adater mAdater_plan;
    private FloatingActionButton mFloatingActionButton;
    private DataBaseSQLHelp mDataBaseSQLHelp_add_plan;
    private Cursor cursor;
    private ContentValues values;
    private SQLiteDatabase db;
    private String user_name;
    private RecyclerView mRecyclerView_main;
    private SharedPreferences mSharedPreferences_get_user;
    private TextView tv_look_for_finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.night_mode);//通知栏所需颜色
        }
        setContentView(R.layout.activity_main);
        initview();
        initRecycler();
        cursor_Data(user_name);
    }

    private void initRecycler() {
        mData_item = new ArrayList<>();
        mRecyclerView_main.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView_main.setItemAnimator(new DefaultItemAnimator());
        mAdater_plan = new week_plan_adater(this,mData_item);
        mRecyclerView_main.setAdapter(mAdater_plan);
        mAdater_plan.setOnItemClickListener(new week_plan_adater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String week_num = mData_item.get(position).getWeek_num();
                String week_day = mData_item.get(position).getWeek_date();
                String content = mData_item.get(position).getContent_text();
                String finish = mData_item.get(position).getFinish_id();
                Intent intent = new Intent();
                intent.putExtra("week_num",week_num);
                intent.putExtra("week_day",week_day);
                intent.putExtra("content",content);
                intent.putExtra("finish_id",finish);
                intent.setClass(MainActivity.this, Content_of_Plan.class);
                startActivity(intent);


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

    private void initview() {
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_week_plan);
        mFloatingActionButton.setOnClickListener(this);
        mDataBaseSQLHelp_add_plan = new DataBaseSQLHelp(this,"Week_Plan_Add.db",null,1);
        values = new ContentValues();
        db = mDataBaseSQLHelp_add_plan.getWritableDatabase();
        cursor = db.query("Week_Plan_Add",null,null,null,null,null,null);
        mSharedPreferences_get_user = getSharedPreferences("user_name",MODE_PRIVATE);
        user_name = mSharedPreferences_get_user.getString("user","");
        mRecyclerView_main = (RecyclerView) findViewById(R.id.recycler_main);
        tv_look_for_finish = (TextView) findViewById(R.id.tv_look_for_finish);
        tv_look_for_finish.setOnClickListener(this);
    }

    private void ShowPopWindow(Context context, View parent){
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view_popWindow = inflater.inflate(R.layout.week_popupwindow,null,false);
        final PopupWindow popupWindow = new PopupWindow(view_popWindow,1000,1150,true);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
        btn_add_week_plan = (Button) view_popWindow.findViewById(R.id.btn_add_week_plan);
        btn_delete_week_plan = (Button) view_popWindow.findViewById(R.id.btn_delete_week_plan);
        btn_find_week = (Button) view_popWindow.findViewById(R.id.btn_find_week_plan);
        btn_add_week_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Add_Week_plan.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        btn_delete_week_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Delete_Week_Plan.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        btn_find_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SqlLite_query.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mData_item.clear();
        String finish = "1";
        db.delete("Week_Plan_Add","finish = ? ",new String[]{finish});
        cursor_Data(user_name);
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
                String s2 = cursor.getString(cursor.getColumnIndex("week_day"));
                String s3 = cursor.getString(cursor.getColumnIndex("week_num"));
                String s4 = cursor.getString(cursor.getColumnIndex("plan_context"));
                String s5 = cursor.getString(cursor.getColumnIndex("finish"));
                Log.d("LLLLL_finish",s5);
                if (s1.equals(register_name)&&s5.equals("0")){
                    mData_item.add(new week_plan_item(s3,s2,s4,s5));
                    int h = mData_item.size();
                    if (h<0){
                        return;
                    }else {
                        mAdater_plan.notifyDataSetChanged();
                    }
                }
            }while (cursor.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_week_plan:
                ShowPopWindow(MainActivity.this,v);
                break;
            case R.id.tv_look_for_finish:
                Intent intent = new Intent();
                intent.setClass(this, Finish_data_activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
