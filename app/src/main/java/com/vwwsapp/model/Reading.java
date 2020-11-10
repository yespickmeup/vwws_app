package com.vwwsapp.model;

public class Reading {

    private int id;
    private String reading_no;
    private String meter_reader_id;
    private String meter_reader_name;
    private String customer_id;
    private String customer_no;
    private String customer_name;
    private String customer_meter_no;
    private String previous_reading_date;
    private double previous_reading;
    private double current_reading;

    private String city;
    private String city_id;
    private String barangay;
    private String barangay_id;
    private String purok;
    private String purok_id;
    private String sitio;
    private String sitio_id;
    private String created_at;
    private String updated_at;

    private String created_by;
    private String updated_by;
    private String status;
    private String occupancy_id;
    private String occupancy;
    private String occupancy_type_id;
    private String occupancy_type;
    private String occupancy_type_code;

    private double actual_consumption;
    private double amount_due;
    private double mf;
    private double mr;
    private double interest;
    private double discount;
    private double net_due;
    private int is_paid;
    private String or_id;
    private String or_no;
    private String date_uploaded;
    private String is_uploaded;
    private String pipe_size;

    public Reading() {

    }

    public Reading(int id, String reading_no, String meter_reader_id, String meter_reader_name, String customer_id, String customer_no, String customer_name, String customer_meter_no, String previous_reading_date, double previous_reading, double current_reading, String city, String city_id, String barangay, String barangay_id, String purok, String purok_id, String sitio, String sitio_id, String created_at, String updated_at, String created_by, String updated_by, String status, String occupancy_id, String occupancy, String occupancy_type_id, String occupancy_type, String occupancy_type_code, double actual_consumption, double amount_due, double mf, double mr, double interest, double discount, double net_due, int is_paid, String or_id, String or_no, String date_uploaded, String is_uploaded, String pipe_size) {
        this.id = id;
        this.reading_no = reading_no;
        this.meter_reader_id = meter_reader_id;
        this.meter_reader_name = meter_reader_name;
        this.customer_id = customer_id;
        this.customer_no = customer_no;
        this.customer_name = customer_name;
        this.customer_meter_no = customer_meter_no;
        this.previous_reading_date = previous_reading_date;
        this.previous_reading = previous_reading;
        this.current_reading = current_reading;
        this.city = city;
        this.city_id = city_id;
        this.barangay = barangay;
        this.barangay_id = barangay_id;
        this.purok = purok;
        this.purok_id = purok_id;
        this.sitio = sitio;
        this.sitio_id = sitio_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.status = status;
        this.occupancy_id = occupancy_id;
        this.occupancy = occupancy;
        this.occupancy_type_id = occupancy_type_id;
        this.occupancy_type = occupancy_type;
        this.occupancy_type_code = occupancy_type_code;
        this.actual_consumption = actual_consumption;
        this.amount_due = amount_due;
        this.mf = mf;
        this.mr = mr;
        this.interest = interest;
        this.discount = discount;
        this.net_due = net_due;
        this.is_paid = is_paid;
        this.or_id = or_id;
        this.or_no = or_no;
        this.date_uploaded = date_uploaded;
        this.is_uploaded = is_uploaded;
        this.pipe_size = pipe_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReading_no() {
        return reading_no;
    }

    public void setReading_no(String reading_no) {
        this.reading_no = reading_no;
    }

    public String getMeter_reader_id() {
        return meter_reader_id;
    }

    public void setMeter_reader_id(String meter_reader_id) {
        this.meter_reader_id = meter_reader_id;
    }

    public String getMeter_reader_name() {
        return meter_reader_name;
    }

    public void setMeter_reader_name(String meter_reader_name) {
        this.meter_reader_name = meter_reader_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_meter_no() {
        return customer_meter_no;
    }

    public void setCustomer_meter_no(String customer_meter_no) {
        this.customer_meter_no = customer_meter_no;
    }

    public String getPrevious_reading_date() {
        return previous_reading_date;
    }

    public void setPrevious_reading_date(String previous_reading_date) {
        this.previous_reading_date = previous_reading_date;
    }

    public double getPrevious_reading() {
        return previous_reading;
    }

    public void setPrevious_reading(double previous_reading) {
        this.previous_reading = previous_reading;
    }

    public double getCurrent_reading() {
        return current_reading;
    }

    public void setCurrent_reading(double current_reading) {
        this.current_reading = current_reading;
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

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getSitio_id() {
        return sitio_id;
    }

    public void setSitio_id(String sitio_id) {
        this.sitio_id = sitio_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOccupancy_id() {
        return occupancy_id;
    }

    public void setOccupancy_id(String occupancy_id) {
        this.occupancy_id = occupancy_id;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public String getOccupancy_type_id() {
        return occupancy_type_id;
    }

    public void setOccupancy_type_id(String occupancy_type_id) {
        this.occupancy_type_id = occupancy_type_id;
    }

    public String getOccupancy_type() {
        return occupancy_type;
    }

    public void setOccupancy_type(String occupancy_type) {
        this.occupancy_type = occupancy_type;
    }

    public String getOccupancy_type_code() {
        return occupancy_type_code;
    }

    public void setOccupancy_type_code(String occupancy_type_code) {
        this.occupancy_type_code = occupancy_type_code;
    }

    public double getActual_consumption() {
        return actual_consumption;
    }

    public void setActual_consumption(double actual_consumption) {
        this.actual_consumption = actual_consumption;
    }

    public double getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(double amount_due) {
        this.amount_due = amount_due;
    }

    public double getMf() {
        return mf;
    }

    public void setMf(double mf) {
        this.mf = mf;
    }

    public double getMr() {
        return mr;
    }

    public void setMr(double mr) {
        this.mr = mr;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getNet_due() {
        return net_due;
    }

    public void setNet_due(double net_due) {
        this.net_due = net_due;
    }

    public int getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    public String getOr_id() {
        return or_id;
    }

    public void setOr_id(String or_id) {
        this.or_id = or_id;
    }

    public String getOr_no() {
        return or_no;
    }

    public void setOr_no(String or_no) {
        this.or_no = or_no;
    }

    public String getDate_uploaded() {
        return date_uploaded;
    }

    public void setDate_uploaded(String date_uploaded) {
        this.date_uploaded = date_uploaded;
    }

    public String getIs_uploaded() {
        return is_uploaded;
    }

    public void setIs_uploaded(String is_uploaded) {
        this.is_uploaded = is_uploaded;
    }

    public String getPipe_size() {
        return pipe_size;
    }

    public void setPipe_size(String pipe_size) {
        this.pipe_size = pipe_size;
    }
}
