package com.vwwsapp.model;

public class MeterReaderAssignments {

    private int id;
    private String meter_reader_id;
    private String meter_reader_no;
    private String meter_reader_name;
    private String customer_id;
    private String customer_no;
    private String customer_name;
    private String barangay;
    private String  barangay_id;
    private String  purok;
    private String  purok_id;
    private String date_added;
    private String date_updated;
    private String added_by_id;
    private String update_by_id;
    private int status;
    private String occupancy_id;
    private String occupancy;
    private String occupancy_type_id;
    private String occupancy_type;
    private String occupancy_type_code;
    private String city;
    private String city_id;
    private String sitio;
    private String sitio_id;
    private String meter_no;
    private String pipe_size;

    public MeterReaderAssignments() {
    }

    public MeterReaderAssignments(int id, String meter_reader_id, String meter_reader_no, String meter_reader_name, String customer_id, String customer_no, String customer_name, String barangay, String barangay_id, String purok, String purok_id, String date_added, String date_updated, String added_by_id, String update_by_id, int status, String occupancy_id, String occupancy, String occupancy_type_id, String occupancy_type, String occupancy_type_code, String city, String city_id, String sitio, String sitio_id, String meter_no, String pipe_size) {
        this.id = id;
        this.meter_reader_id = meter_reader_id;
        this.meter_reader_no = meter_reader_no;
        this.meter_reader_name = meter_reader_name;
        this.customer_id = customer_id;
        this.customer_no = customer_no;
        this.customer_name = customer_name;
        this.barangay = barangay;
        this.barangay_id = barangay_id;
        this.purok = purok;
        this.purok_id = purok_id;
        this.date_added = date_added;
        this.date_updated = date_updated;
        this.added_by_id = added_by_id;
        this.update_by_id = update_by_id;
        this.status = status;
        this.occupancy_id = occupancy_id;
        this.occupancy = occupancy;
        this.occupancy_type_id = occupancy_type_id;
        this.occupancy_type = occupancy_type;
        this.occupancy_type_code = occupancy_type_code;
        this.city = city;
        this.city_id = city_id;
        this.sitio = sitio;
        this.sitio_id = sitio_id;
        this.meter_no = meter_no;
        this.pipe_size = pipe_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeter_reader_id() {
        return meter_reader_id;
    }

    public void setMeter_reader_id(String meter_reader_id) {
        this.meter_reader_id = meter_reader_id;
    }

    public String getMeter_reader_no() {
        return meter_reader_no;
    }

    public void setMeter_reader_no(String meter_reader_no) {
        this.meter_reader_no = meter_reader_no;
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

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getAdded_by_id() {
        return added_by_id;
    }

    public void setAdded_by_id(String added_by_id) {
        this.added_by_id = added_by_id;
    }

    public String getUpdate_by_id() {
        return update_by_id;
    }

    public void setUpdate_by_id(String update_by_id) {
        this.update_by_id = update_by_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public String getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public String getPipe_size() {
        return pipe_size;
    }

    public void setPipe_size(String pipe_size) {
        this.pipe_size = pipe_size;
    }
}
