package com.example.asus.sqldesign_test.activity_set;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.SqlDataTable.DataBaseSQLHelp;
import com.example.asus.sqldesign_test.beanset.week_plan_item;
import com.example.asus.sqldesign_test.otherset.SystemBarTintManager;

public class Content_of_Plan extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_content_plan_title;
    private TextView tv_week_num;
    private TextView tv_week_day;
    private TextView tv_week_content;
    private Button btn_punch_card;
    private DataBaseSQLHelp mDataBaseSQLHelp_add_plan;
    private Cursor cursor;
    private ContentValues values;
    private ContentValues values1;
    private SQLiteDatabase db;
    private String user_name;
    private String week_num;
    private String week_day;
    private String content;
    private SharedPreferences mSharedPreferences_get_user;
    private SQLiteDatabase db_add_plan;
    private DataBaseSQLHelp mDataBaseSQLHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.night_mode);//通知栏所需颜色
        }
        setContentView(R.layout.activity_content_of__plan);
        initview();
        Intent intent = getIntent();
        week_num = intent.getStringExtra("week_num");
        week_day = intent.getStringExtra("week_day");
        content = intent.getStringExtra("content");
        settext(week_num,week_day,content);
    }

    private void settext(String week_num,String week_day,String week_content) {
        tv_week_content.setText(week_content);
        tv_week_num.setText(week_num);
        tv_week_day.setText(week_day);
    }

    private void initview() {
        mDataBaseSQLHelp_add_plan = new DataBaseSQLHelp(this,"Plan_Punch_Card.db",null,1);
        mDataBaseSQLHelp = new DataBaseSQLHelp(this,"Week_Plan_Add.db",null,1);
        values1 = new ContentValues();
        db_add_plan = mDataBaseSQLHelp.getWritableDatabase();
        values = new ContentValues();
        db = mDataBaseSQLHelp_add_plan.getWritableDatabase();
        cursor = db.query("Plan_Punch_Card",null,null,null,null,null,null);
        mSharedPreferences_get_user = getSharedPreferences("user_name",MODE_PRIVATE);
        user_name = mSharedPreferences_get_user.getString("user","");
        iv_content_plan_title = (ImageView) findViewById(R.id.iv_return_content_plan);
        iv_content_plan_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_week_content = (TextView) findViewById(R.id.tv_content_plan_week_content);
        tv_week_day = (TextView) findViewById(R.id.tv_content_plan_week_day);
        tv_week_num = (TextView) findViewById(R.id.tv_content_plan_week_num);
        btn_punch_card = (Button) findViewById(R.id.btn_punch_card);
        btn_punch_card.setOnClickListener(this);
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
            case R.id.btn_punch_card:
                cursor_add_data(user_name,week_day,week_num,content);
                break;
            default:
                break;
        }
    }

    public void cursor_add_data(final String register_name, final String week_day, final String week_num, final String content){
        cursor = db.query("Plan_Punch_Card",null,null,null,null,null,null);
        Boolean exist = false;
        if (cursor.moveToFirst()){
            do{
                String s1 = cursor.getString(cursor.getColumnIndex("register_name"));
                String s2 = cursor.getString(cursor.getColumnIndex("week_day"));
                String s3 = cursor.getString(cursor.getColumnIndex("week_num"));
                String s4 = cursor.getString(cursor.getColumnIndex("plan_context"));
                if (s1.equals(register_name)&&s2.equals(week_day)&&s3.equals(week_num)){
                    exist = true;
                    break;
                }
            }while (cursor.moveToNext());
        }
        if (exist){
            values.put("plan_context",content);
            db.update("Plan_Punch_Card",values,"register_name = ? AND week_day = ? AND week_num = ?", new String[]{register_name,week_day,week_num});
            Toast.makeText(this, "打卡成功", Toast.LENGTH_LONG).show();
            cursor = db_add_plan.query("Week_Plan_Add",null,null,null,null,null,null);
            values1 = new ContentValues();
            String finish = "1";
            values1.put("finish",finish);
            db_add_plan.update("Week_Plan_Add",values1,"register_name = ? AND week_day = ? AND week_num = ?", new String[]{register_name,week_day,week_num});
            finish();
        }else {
            values.put("register_name",register_name);
            values.put("week_day",week_day);
            values.put("week_num",week_num);
            values.put("plan_context",content);
            db.insert("Plan_Punch_Card",null,values);
            values.clear();
            Toast.makeText(this, "打卡成功", Toast.LENGTH_LONG).show();
            cursor = db_add_plan.query("Week_Plan_Add",null,null,null,null,null,null);
            values1 = new ContentValues();
            String finish = "1";
            values1.put("finish",finish);
            db_add_plan.update("Week_Plan_Add",values1,"register_name = ? AND week_day = ? AND week_num = ?", new String[]{register_name,week_day,week_num});
            //db.execSQL("delete from Week_Plan_Add where register_name='"+user_name+"' and week_day='"+week_day+"' and week_num='"+week_num+"'");
            finish();
        }

    }
}