package com.example.asus.sqldesign_test.activity_set;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.asus.sqldesign_test.R;
import com.example.asus.sqldesign_test.otherset.SystemBarTintManager;

public class SqlLite_query extends AppCompatActivity implements View.OnClickListener {

    private Button btn_user_table;
    private Button btn_user_plan_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.night_mode);//通知栏所需颜色
        }
        setContentView(R.layout.activity_sql_lite_query);
        initview();
    }

    private void initview() {
        btn_user_plan_table = (Button) findViewById(R.id.btn_user_week_plan_table);
        btn_user_table = (Button) findViewById(R.id.btn_user_table);
        btn_user_plan_table.setOnClickListener(this);
        btn_user_table.setOnClickListener(this);
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
            case R.id.btn_user_table:
                Intent intent = new Intent();
                intent.setClass(SqlLite_query.this,User_table_extinct.class);
                startActivity(intent);
                break;
            case R.id.btn_user_week_plan_table:

                break;
            default:
                break;
        }
    }
}
