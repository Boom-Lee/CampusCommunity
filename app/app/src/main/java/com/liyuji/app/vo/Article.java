package com.liyuji.app.vo;


public class Article {

    public Integer userId;

    public String articleContent;

    public String articleImg;

    public int communityId;

    @Override
    public String toString() {
        return "ArticleUpload{" +
                "userId=" + userId +
                ", articleContent='" + articleContent + '\'' +
                ", articleImg='" + articleImg + '\'' +
                ", communityId=" + communityId +
                '}';
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

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }
}

