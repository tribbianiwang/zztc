package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/11/14.
 */

public class AliNotifyValueBean {


    /**
     * config_str :
     * engine_num : RU5469
     * issue_date : 20090318
     * model : 三菱L300
     * owner : 广西壮族自治区汀滨医院
     * plate_num : 桂AA0002
     * register_date : 20050925
     * request_id : 20171114162143_c69d11e925d72fe28cc407c52eca441e
     * success : true
     * use_character : 非营运
     * vehicle_type : 中型普通客车
     * vin : 00846
     */

    private String config_str;
    private String engine_num;
    private String issue_date;
    private String model;
    private String owner;
    private String plate_num;
    private String register_date;
    private String request_id;
    private boolean success;
    private String use_character;
    private String vehicle_type;
    private String vin;

    public String getConfig_str() {
        return config_str;
    }

    public void setConfig_str(String config_str) {
        this.config_str = config_str;
    }

    public String getEngine_num() {
        return engine_num;
    }

    public void setEngine_num(String engine_num) {
        this.engine_num = engine_num;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlate_num() {
        return plate_num;
    }

    public void setPlate_num(String plate_num) {
        this.plate_num = plate_num;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUse_character() {
        return use_character;
    }

    public void setUse_character(String use_character) {
        this.use_character = use_character;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
