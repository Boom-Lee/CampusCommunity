package com.liyuji.app.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 动态VO
 *
 * @author L
 */
public class ArticleVO {
    public int articleId;

    public Integer userId;

    public String articleContent;

    public String articleImg;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date articleDate;

    public String articleStatus;

    public int communityId;

    public String userNickname;

    public Date getArticleDate() {
        return articleDate;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public int browseCount;

    @Override
    public String toString() {
        return "ArticleVO{" +
                "articleId=" + articleId +
                ", userId=" + userId +
                ", articleContent='" + articleContent + '\'' +
                ", articleImg='" + articleImg + '\'' +
                ", articleDate=" + articleDate +
                ", articleStatus='" + articleStatus + '\'' +
                ", communityId=" + communityId +
                ", userNickname='" + userNickname + '\'' +
                ", browseCount=" + browseCount +
                ", userHeadImg='" + userHeadImg + '\'' +
                '}';
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(String articleStatus) {
        this.articleStatus = articleStatus;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
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

    public String userHeadImg;
}
