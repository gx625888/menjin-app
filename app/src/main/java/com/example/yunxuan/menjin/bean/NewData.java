package com.example.yunxuan.menjin.bean;

/**
 * Created by daiMaGe on 2019/2/22.
 */

public class NewData {

    /**
     * personId : 1558
     * personName : 高  翔
     * phone : 13851556167
     * houseId : 175
     * houseName : 801
     * levelId : 174
     * levelName : 8
     * unitId : 150
     * unitName : 一单元
     * deviceId : 10:a4:be:68:39:b0
     * deviceTel : 5002
     * devicePhone : jkjfDpprjdpaaf
     * buildId : 149
     * buildName : 孵鹰大厦A座
     * residentName : 南京晶谷鼎
     */

    private int personId;
    private String personName;
    private String phone;
    private int houseId;
    private String houseName;
    private int levelId;
    private String levelName;
    private int unitId;
    private String unitName;
    private String deviceId;
    private String deviceTel;
    private String devicePhone;
    private int buildId;
    private String buildName;
    private String residentName;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceTel() {
        return deviceTel;
    }

    public void setDeviceTel(String deviceTel) {
        this.deviceTel = deviceTel;
    }

    public String getDevicePhone() {
        return devicePhone;
    }

    public void setDevicePhone(String devicePhone) {
        this.devicePhone = devicePhone;
    }

    public int getBuildId() {
        return buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    @Override
    public String toString() {
        return "NewData{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                ", phone='" + phone + '\'' +
                ", houseId=" + houseId +
                ", houseName='" + houseName + '\'' +
                ", levelId=" + levelId +
                ", levelName='" + levelName + '\'' +
                ", unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceTel='" + deviceTel + '\'' +
                ", devicePhone='" + devicePhone + '\'' +
                ", buildId=" + buildId +
                ", buildName='" + buildName + '\'' +
                ", residentName='" + residentName + '\'' +
                '}';
    }
}
