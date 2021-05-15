package com.liyuji.app.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author L
 */
public class FollowVO {

    public int userFollowId;

    public int userId;

    public int followedId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date followDate;

    public String userNickname;

    public String userHeadImg;


    @Override
    public String toString() {
        return "FollowVO{" +
                "userFollowId=" + userFollowId +
                ", userId=" + userId +
                ", followedId=" + followedId +
                ", followDate=" + followDate +
                ", userNickname='" + userNickname + '\'' +
                ", userHeadImg='" + userHeadImg + '\'' +
                '}';
    }

    public int getUserFollowId() {
        return userFollowId;
    }


    public void setUserFollowId(int userFollowId) {
        this.userFollowId = userFollowId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowedId() {
        return followedId;
    }

    public void setFollowedId(int followedId) {
        this.followedId = followedId;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }
}
