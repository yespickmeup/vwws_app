package com.vwwsapp.model;

public class OccupancyTypes {

    private int id;
    private String occupancy;
    private String occupancy_type_id;
    private String occupancy_type_name;
    private String occupancy_type_code;
    private String pipe_size;
    private String cubic;
    private double mf;
    private double mr;
    private double charge;
    private String date_added;
    private String date_updated;
    private String added_by_id;
    private String update_by_id;
    private int status;
    private String remarks;

    public OccupancyTypes() {

    }

    public OccupancyTypes(int id, String occupancy, String occupancy_type_id, String occupancy_type_name, String occupancy_type_code, String pipe_size, String cubic, double mf, double mr, double charge, String date_added, String date_updated, String added_by_id, String update_by_id, int status, String remarks) {
        this.id = id;
        this.occupancy = occupancy;
        this.occupancy_type_id = occupancy_type_id;
        this.occupancy_type_name = occupancy_type_name;
        this.occupancy_type_code = occupancy_type_code;
        this.pipe_size = pipe_size;
        this.cubic = cubic;
        this.mf = mf;
        this.mr = mr;
        this.charge = charge;
        this.date_added = date_added;
        this.date_updated = date_updated;
        this.added_by_id = added_by_id;
        this.update_by_id = update_by_id;
        this.status = status;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOccupancy_type_name() {
        return occupancy_type_name;
    }

    public void setOccupancy_type_name(String occupancy_type_name) {
        this.occupancy_type_name = occupancy_type_name;
    }

    public String getOccupancy_type_code() {
        return occupancy_type_code;
    }

    public void setOccupancy_type_code(String occupancy_type_code) {
        this.occupancy_type_code = occupancy_type_code;
    }

    public String getPipe_size() {
        return pipe_size;
    }

    public void setPipe_size(String pipe_size) {
        this.pipe_size = pipe_size;
    }

    public String getCubic() {
        return cubic;
    }

    public void setCubic(String cubic) {
        this.cubic = cubic;
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

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
