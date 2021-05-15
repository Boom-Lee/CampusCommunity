package com.liyuji.app.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class CommentVO {

    public int commentId;

    public int articleId;

    public String commentContent;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date commentDate;

    public int fromUid;

    public String userNickname;

    public String userHeadImg;

    @Override
    public String toString() {
        return "CommentVO{" +
                "commentId=" + commentId +
                ", articleId=" + articleId +
                ", commentContent='" + commentContent + '\'' +
                ", commentDate=" + commentDate +
                ", fromUid=" + fromUid +
                ", userNickname='" + userNickname + '\'' +
                ", userHeadImg='" + userHeadImg + '\'' +
                '}';
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
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
