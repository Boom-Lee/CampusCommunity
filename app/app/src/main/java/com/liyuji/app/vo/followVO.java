package com.liyuji.app.vo;

/**
 * @author L
 */
public class followVO {


    public int follow_count;

    @Override
    public String toString() {
        return "followVO{" +
                "follow_count=" + follow_count +
                '}';
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }
}
