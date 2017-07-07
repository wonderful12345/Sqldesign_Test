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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.SqlDataTable.DataBaseSQLHelp;
import com.example.asus.sqldesign_test.otherset.SystemBarTintManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Add_Week_plan extends AppCompatActivity {
    private Spinner mSpinner_day;
    private DataBaseSQLHelp mDataBaseSQLHelp_add_plan;
    private Cursor cursor;
    private ContentValues values;
    private SQLiteDatabase db;
    private String user_name;
    private SharedPreferences mSharedPreferences_get_user;
    private Spinner mSpinner_week;
    private List<String> mList_day;
    private List<String> mList_week;
    private String week_data;
    private String week_num;
    private ImageButton mImageButton_back;
    private Button btn_change_sure;
    private Button btn_change_cancle;
    private Button btn_week_plan_save;
    private EditText et_week_plan_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.night_mode);//通知栏所需颜色
        }
        setContentView(R.layout.activity_add__week_plan);
        initview();
        setSpinnername();
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
        mDataBaseSQLHelp_add_plan = new DataBaseSQLHelp(this,"Week_Plan_Add.db",null,1);
        values = new ContentValues();
        db = mDataBaseSQLHelp_add_plan.getWritableDatabase();
        cursor = db.query("Week_Plan_Add",null,null,null,null,null,null);
        mSharedPreferences_get_user = getSharedPreferences("user_name",MODE_PRIVATE);
        user_name = mSharedPreferences_get_user.getString("user","");
        et_week_plan_context = (EditText) findViewById(R.id.et_add_week_plan_context);
        mSpinner_day = (Spinner) findViewById(R.id.spinner_day);
        mSpinner_week = (Spinner) findViewById(R.id.spinner_week);
        mImageButton_back = (ImageButton) findViewById(R.id.ib_Add_Plan_back);
        mImageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_week_plan_save = (Button) findViewById(R.id.btn_week_plan_save);
        btn_week_plan_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_week_plan_context.getText().toString();
                cursor_add_data(user_name,week_data,week_num,content);

            }
        });
    }
    private void setSpinnername() {
        mList_day = new ArrayList<>();
        mList_day.add("星期一");
        mList_day.add("星期二");
        mList_day.add("星期三");
        mList_day.add("星期四");
        mList_day.add("星期五");
        mList_day.add("星期六");
        mList_day.add("星期日");
        mList_week = new ArrayList<>();
        mList_week.add("第一周");
        mList_week.add("第二周");
        mList_week.add("第三周");
        mList_week.add("第四周");
        mList_week.add("第五周");
        mList_week.add("第六周");
        mList_week.add("第七周");
        ArrayAdapter<String> adapter_day_name = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mList_day);
        adapter_day_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner_day.setAdapter(adapter_day_name);
        ArrayAdapter<String> adapter_week_name = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mList_week);
        adapter_week_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner_week.setAdapter(adapter_week_name);
        mSpinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                week_data = mList_day.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                week_num = mList_week.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 遍历数据库
     * @param
     */
    public void cursor_add_data(final String register_name, final String week_day, final String week_num, final String content){
        Boolean data_exit = false;
        cursor = db.query("Week_Plan_Add",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String s1 = cursor.getString(cursor.getColumnIndex("register_name"));
                String s2 = cursor.getString(cursor.getColumnIndex("week_day"));
                String s3 = cursor.getString(cursor.getColumnIndex("week_num"));
                String s4 = cursor.getString(cursor.getColumnIndex("plan_context"));
                String s5 = cursor.getString(cursor.getColumnIndex("finish"));
                if (s1.equals(register_name)&&s2.equals(week_day)&&s3.equals(week_num)&&s5.equals("0")){
                    data_exit = true;
                    break;
                }
            }while (cursor.moveToNext());
        }
        if (data_exit){
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View view = layoutInflater.inflate(R.layout.alertdialog_chang_content,null);
            final AlertDialog alertDialog = new AlertDialog.Builder(Add_Week_plan.this).setView(view).setTitle("提示").setIcon(R.drawable.ic_account_balance_blue_500_24dp).show();
            btn_change_cancle = (Button) view.findViewById(R.id.btn_change_cancle);
            btn_change_sure = (Button) view.findViewById(R.id.btn_change_sure);
            btn_change_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    values = new ContentValues();
                    values.put("plan_context",content);
                    db.update("Week_Plan_Add",values,"register_name = ? AND week_day = ? AND week_num = ? AND finish = ?", new String[]{register_name,week_day,week_num,"0"});
                    alertDialog.dismiss();
                    Toast.makeText(Add_Week_plan.this,"数据更改成功",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            btn_change_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }else {
                    cursor = db.query("Week_Plan_Add",null,null,null,null,null,null);
                    values.put("register_name",register_name);
                    values.put("week_day",week_day);
                    values.put("week_num",week_num);
                    values.put("plan_context",content);
                    values.put("finish","0");
                    db.insert("Week_Plan_Add",null,values);
                    values.clear();
                    Toast.makeText(Add_Week_plan.this, "数据添加成功", Toast.LENGTH_LONG).show();
                    finish();
                }
        }

}
