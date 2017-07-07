package com.example.asus.sqldesign_test.beanset;

/**
 * Created by ASUS on 2017/5/26.
 */

public class user_table_item {

    String user_name;
    String user_password;

    public user_table_item(String user_name,String user_password){
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
