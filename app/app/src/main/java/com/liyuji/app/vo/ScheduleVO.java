package com.liyuji.app.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 日程
 *
 * @author L
 */
public class ScheduleVO {

    public int scheduleId;
    public int userId;
    public String scheduleTitle;
    public String scheduleContent;
    public String scheduleLocation;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date scheduleStarttime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date scheduleEndtime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date scheduleCreattime;

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
                '}';
    }

    public int getScheduleId() {
        return scheduleId;
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
}

