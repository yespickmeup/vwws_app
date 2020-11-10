package com.vwwsapp.model;

public class MyUser {
    public static String id;
    public static String username;
    public static String screen_name;
    public static String meter_reader_no;
    public MyUser(String id, String username, String screen_name,String meter_reader_no) {
        this.id = id;
        this.username = username;
        this.screen_name = screen_name;
        this.meter_reader_no=meter_reader_no;
    }

    public static String getMeter_reader_no() {
        try{
            return meter_reader_no;
        }catch(Exception e){
            return "";
        }

    }

    public static void setMeter_reader_no(String meter_reader_no) {
        MyUser.meter_reader_no = meter_reader_no;
    }

    public static String getId() {
        try{
            return id;
        }catch(Exception e){
            return "";
        }

    }

    public static void setId(String id) {
        MyUser.id = id;
    }

    public static String getUsername() {
        try{
            return username;
        }catch(Exception e){
            return "";
        }

    }

    public static void setUsername(String username) {
        MyUser.username = username;
    }

    public static String getScreen_name() {
        try{
            return screen_name;
        }catch(Exception e){
            return "";
        }

    }

    public static void setScreen_name(String screen_name) {
        MyUser.screen_name = screen_name;
    }
}
