package com.liyuji.app.vo;

public class ReplyVO {

    public int replyId;

    public int commentId;

    public String replyContent;

    public int fromUid;

    public String userNickname;

    public String userHeadImg;

    @Override
    public String toString() {
        return "ReplyVO{" +
                "replyId=" + replyId +
                ", commentId=" + commentId +
                ", replyContent='" + replyContent + '\'' +
                ", fromUid=" + fromUid +
                ", userNickname='" + userNickname + '\'' +
                ", userHeadImg='" + userHeadImg + '\'' +
                '}';
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
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
