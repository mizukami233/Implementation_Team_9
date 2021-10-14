package Model;

import java.util.Date;

//AppId,ReasonId,AppDateTime,PatId,BranchId,GpId
public class Appointment {
    private int appId;
    private int patId;
    private int branchId;
    private int gpId;
    private int reasonId;
    private Date appDate;
    private String appBeginTime;
    private String appEndTime;
    private Date checkInTime;
    private Date appTime;

    public Appointment(){

    }

    public Appointment(int appId, int patId, int branchId, int gpId, int reasonId, Date appDate, String appBeginTime, String appEndTime) {
        this.appId = appId;
        this.patId = patId;
        this.branchId = branchId;
        this.gpId = gpId;
        this.reasonId = reasonId;
        this.appDate = appDate;
        this.appBeginTime = appBeginTime;
        this.appEndTime = appEndTime;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public int getPatId() {
        return patId;
    }

    public void setPatId(int patId) {
        this.patId = patId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getGpId() {
        return gpId;
    }

    public void setGpId(int gpId) {
        this.gpId = gpId;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getAppBeginTime() {
        return appBeginTime;
    }

    public void setAppBeginTime(String date) {
        this.appBeginTime = date;
    }

    public String getAppEndTime() {
        return appEndTime;
    }

    public void setAppEndTime(String appEndTime) {
        this.appEndTime = appEndTime;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getAppTime() {
        return appTime;
    }

    public void setAppTime(Date appTime) {
        this.appTime = appTime;
    }
}

