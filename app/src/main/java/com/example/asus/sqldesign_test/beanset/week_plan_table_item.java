package com.example.asus.sqldesign_test.beanset;

/**
 * Created by ASUS on 2017/5/26.
 */

public class week_plan_table_item {

    private String week_num;
    private String monday_content;
    private String tuesday_content;
    private String Wed_content;
    private String Thus_content;
    private String Firday_content;
    private String Saturday_content;
    private String Sunday_content;
    public week_plan_table_item(String week_num,String monday_content,String tuesday_content,String wed_content,String thus_content,String firday_content,String saturday_content,String sunday_content){
        this.week_num = week_num;
        this.monday_content = monday_content;
        this.tuesday_content = tuesday_content;
        this.Wed_content = wed_content;
        this.Thus_content = thus_content;
        this.Firday_content = firday_content;
        this.Saturday_content = saturday_content;
        this.Sunday_content = sunday_content;
    }

    public String getWeek_num() {
        return week_num;
    }

    public void setWeek_num(String week_num) {
        this.week_num = week_num;
    }

    public String getFirday_content() {
        return Firday_content;
    }

    public void setFirday_content(String firday_content) {
        Firday_content = firday_content;
    }

    public String getMonday_content() {
        return monday_content;
    }

    public String getSaturday_content() {
        return Saturday_content;
    }

    public void setMonday_content(String monday_content) {
        this.monday_content = monday_content;
    }

    public void setSaturday_content(String saturday_content) {
        Saturday_content = saturday_content;
    }

    public String getSunday_content() {
        return Sunday_content;
    }

    public void setSunday_content(String sunday_content) {
        Sunday_content = sunday_content;
    }

    public String getThus_content() {
        return Thus_content;
    }

    public void setThus_content(String thus_content) {
        Thus_content = thus_content;
    }

    public String getTuesday_content() {
        return tuesday_content;
    }

    public void setTuesday_content(String tuesday_content) {
        this.tuesday_content = tuesday_content;
    }

    public String getWed_content() {
        return Wed_content;
    }

    public void setWed_content(String wed_content) {
        Wed_content = wed_content;
    }
}
