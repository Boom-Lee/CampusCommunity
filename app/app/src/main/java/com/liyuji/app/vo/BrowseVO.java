package com.liyuji.app.vo;

import java.util.Date;

/**
 * 浏览
 *
 * @author L
 */
public class BrowseVO {

    public int userBrowseId;

    public int userId;

    public int articleId;

    public Date userBrowseDate;

    public String userNickName;

    public String userHeadImg;

    public String articleContent;

    public String articleImg;

    public Date articleDate;

    @Override
    public String toString() {
        return "BrowseVO{" +
                "userBrowseId=" + userBrowseId +
                ", userId=" + userId +
                ", articleId=" + articleId +
                ", userBrowseDate=" + userBrowseDate +
                ", userNickName='" + userNickName + '\'' +
                ", userHeadImg='" + userHeadImg + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articleImg='" + articleImg + '\'' +
                ", articleDate=" + articleDate +
                '}';
    }

    public int getUserBrowseId() {
        return userBrowseId;
    }

    public void setUserBrowseId(int userBrowseId) {
        this.userBrowseId = userBrowseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Date getUserBrowseDate() {
        return userBrowseDate;
    }

    public void setUserBrowseDate(Date userBrowseDate) {
        this.userBrowseDate = userBrowseDate;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
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

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }
}
