package com.example.asus.sqldesign_test.beanset;

/**
 * Created by ASUS on 2017/5/24.
 */

public class week_plan_item {
    private String week_num;
    private String week_date;
    private String content_text;
    private String finish_id;

    public week_plan_item(String week_num,String week_date,String content_text,String finish_id){
        this.week_num = week_num;
        this.week_date = week_date;
        this.content_text = content_text;
        this.finish_id = finish_id;
    }

    public week_plan_item(String week_num,String week_date,String content_text){
        this.week_num = week_num;
        this.week_date = week_date;
        this.content_text = content_text;
    }

    public String getFinish_id() {
        return finish_id;
    }

    public void setFinish_id(String finish_id) {
        this.finish_id = finish_id;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getWeek_date() {
        return week_date;
    }

    public void setWeek_date(String week_date) {
        this.week_date = week_date;
    }

    public String getWeek_num() {
        return week_num;
    }

    public void setWeek_num(String week_num) {
        this.week_num = week_num;
    }
}
