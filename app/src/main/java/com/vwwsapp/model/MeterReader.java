package com.vwwsapp.model;

public class MeterReader {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeter_reader_no() {
        return meter_reader_no;
    }

    public void setMeter_reader_no(String meter_reader_no) {
        this.meter_reader_no = meter_reader_no;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getBarangay_id() {
        return barangay_id;
    }

    public void setBarangay_id(String barangay_id) {
        this.barangay_id = barangay_id;
    }

    public String getPurok() {
        return purok;
    }

    public void setPurok(String purok) {
        this.purok = purok;
    }

    public String getPurok_id() {
        return purok_id;
    }

    public void setPurok_id(String purok_id) {
        this.purok_id = purok_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private  int id;
    private String meter_reader_no;
    private String fname;
    private String mi;
    private String lname;
    private String city;
    private String city_id;
    private String barangay;
    private String barangay_id;
    private String purok;
    private String purok_id;
    private String address;
    private String user_name;
    private String password;

    public MeterReader() {

    }

    public MeterReader(int id, String meter_reader_no, String fname, String mi, String lname, String city, String city_id, String barangay, String barangay_id, String purok, String purok_id, String address, String user_name, String password) {
        this.id = id;
        this.meter_reader_no = meter_reader_no;
        this.fname = fname;
        this.mi = mi;
        this.lname = lname;
        this.city = city;
        this.city_id = city_id;
        this.barangay = barangay;
        this.barangay_id = barangay_id;
        this.purok = purok;
        this.purok_id = purok_id;
        this.address = address;
        this.user_name = user_name;
        this.password = password;
    }
}
