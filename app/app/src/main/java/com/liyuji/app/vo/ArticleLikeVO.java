package com.liyuji.app.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 喜欢
 * @author L
 */
public class ArticleLikeVO {

    public int articleLikeId;

    public int userId;

    public int articleId;

    public Date articleLikeDate;

    public String userNickname;

    public String userHeadImg;

    public String articleContent;

    public String articleImg;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date articleDate;

    @Override
    public String toString() {
        return "ArticleLikeVO{" +
                "articleLikeId=" + articleLikeId +
                ", userId=" + userId +
                ", articleId=" + articleId +
                ", articleLikeDate=" + articleLikeDate +
                ", userNickname='" + userNickname + '\'' +
                ", userHeadImg='" + userHeadImg + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articleImg='" + articleImg + '\'' +
                ", articleDate=" + articleDate +
                '}';
    }

    public int getArticleLikeId() {
        return articleLikeId;
    }

    public void setArticleLikeId(int articleLikeId) {
        this.articleLikeId = articleLikeId;
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

    public Date getArticleLikeDate() {
        return articleLikeDate;
    }

    public void setArticleLikeDate(Date articleLikeDate) {
        this.articleLikeDate = articleLikeDate;
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
