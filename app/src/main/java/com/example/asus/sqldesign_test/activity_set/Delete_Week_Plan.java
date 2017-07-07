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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.SqlDataTable.DataBaseSQLHelp;
import com.example.asus.sqldesign_test.adapter_set.week_plan_adater;
import com.example.asus.sqldesign_test.beanset.week_plan_item;
import com.example.asus.sqldesign_test.otherset.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class Delete_Week_Plan extends AppCompatActivity {


    private List<week_plan_item> mData_item;
    private week_plan_adater mAdater_plan;
    private Button btn_delete_sure;
    private Button btn_delete_cancle;
    private DataBaseSQLHelp mDataBaseSQLHelp_add_plan;
    private Cursor cursor;
    private ContentValues values;
    private SQLiteDatabase db;
    private String user_name;
    private SharedPreferences mSharedPreferences_get_user;
    private RecyclerView mRecyclerView_delete_plan;
    private ImageView iv_back_dele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.night_mode);//通知栏所需颜色
        }
        setContentView(R.layout.activity_delete__week__plan);
        initview();
        initRecycler();
        cursor_Data(user_name);
    }

    private void initview() {
        mDataBaseSQLHelp_add_plan = new DataBaseSQLHelp(this,"Week_Plan_Add.db",null,1);
        values = new ContentValues();
        db = mDataBaseSQLHelp_add_plan.getWritableDatabase();
        cursor = db.query("Week_Plan_Add",null,null,null,null,null,null);
        mSharedPreferences_get_user = getSharedPreferences("user_name",MODE_PRIVATE);
        user_name = mSharedPreferences_get_user.getString("user","");
        mRecyclerView_delete_plan = (RecyclerView) findViewById(R.id.recycler_delete_plan);
        iv_back_dele = (ImageView) findViewById(R.id.iv_return_delete);
        iv_back_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecycler() {
        mData_item = new ArrayList<>();
        mRecyclerView_delete_plan.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView_delete_plan.setItemAnimator(new DefaultItemAnimator());
        mAdater_plan = new week_plan_adater(this,mData_item);
        mRecyclerView_delete_plan.setAdapter(mAdater_plan);
        mAdater_plan.setOnItemClickListener(new week_plan_adater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                LayoutInflater layoutInflater = LayoutInflater.from(Delete_Week_Plan.this);
                View view_dialog = layoutInflater.inflate(R.layout.alertdialog_delete_content,null);
                final AlertDialog alertDialog = new AlertDialog.Builder(Delete_Week_Plan.this).setView(view_dialog).setTitle("提示").setIcon(R.drawable.ic_account_balance_blue_500_24dp).show();
                btn_delete_cancle = (Button) view_dialog.findViewById(R.id.btn_delete_cancle);
                btn_delete_sure = (Button) view_dialog.findViewById(R.id.btn_delete_sure);
                btn_delete_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_delete_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String week_day = mData_item.get(position).getWeek_date();
                        String week_num = mData_item.get(position).getWeek_num();
                        String content = mData_item.get(position).getContent_text();
                        db.delete("Week_Plan_Add","plan_context = ? ",new String[]{content});
                        //db.execSQL("delete from Week_Plan_Add where register_name='"+user_name+"' and week_day='"+week_day+"' and week_num='"+week_num+"'");
                        mData_item.clear();
                        cursor_Data(user_name);
                        alertDialog.dismiss();
                        finish();
                    }
                });
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
                String s2 = cursor.getString(cursor.getColumnIndex("week_day"));
                String s3 = cursor.getString(cursor.getColumnIndex("week_num"));
                String s4 = cursor.getString(cursor.getColumnIndex("plan_context"));
                String s5 = cursor.getString(cursor.getColumnIndex("finish"));
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
}
