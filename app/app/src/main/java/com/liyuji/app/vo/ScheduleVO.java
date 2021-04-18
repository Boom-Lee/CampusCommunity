package com.liyuji.app.vo;

import java.util.Date;

public class ScheduleVO {

    public int scheduleId;
    public int userId;
    public String scheduleTitle;
    public String scheduleContent;
    public String scheduleLocation;
    public Date scheduleStarttime;
    public Date scheduleEndtime;
    public Date scheduleCreattime;
    public String userName;

    public int getScheduleId() {
        return scheduleId;
    }

    @Override
    public String toString() {
        return "ScheduleVO{" +
                "scheduleId=" + scheduleId +
                ", userId=" + userId +
                ", scheduleTitle='" + scheduleTitle + '\'' +
                ", scheduleContent='" + scheduleContent + '\'' +
                ", scheduleLocation='" + scheduleLocation + '\'' +
                ", scheduleStarttime=" + scheduleStarttime +
                ", scheduleEndtime=" + scheduleEndtime +
                ", scheduleCreattime=" + scheduleCreattime +
                ", userName='" + userName + '\'' +
                '}';
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    public String getScheduleLocation() {
        return scheduleLocation;
    }

    public void setScheduleLocation(String scheduleLocation) {
        this.scheduleLocation = scheduleLocation;
    }

    public Date getScheduleStarttime() {
        return scheduleStarttime;
    }

    public void setScheduleStarttime(Date scheduleStarttime) {
        this.scheduleStarttime = scheduleStarttime;
    }

    public Date getScheduleEndtime() {
        return scheduleEndtime;
    }

    public void setScheduleEndtime(Date scheduleEndtime) {
        this.scheduleEndtime = scheduleEndtime;
    }

    public Date getScheduleCreattime() {
        return scheduleCreattime;
    }

    public void setScheduleCreattime(Date scheduleCreattime) {
        this.scheduleCreattime = scheduleCreattime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

