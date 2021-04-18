//package com.liyuji.app.fragment;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.liyuji.app.R;
//
//import java.util.ArrayList;
//
///**
// * @author L
// */
//public class BaseListViewArticleAdapter extends BaseAdapter {
//
//
//    /**
//     * 动态列表
//     */
//    public ArrayList<ArticleVO> articleArrayList;
//
//    public void setArticleArrayList(ArrayList<ArticleVO> articleArrayList) {
//        this.articleArrayList = articleArrayList;
//    }
//
//
//    /**
//     * 确认行数
//     */
//    @Override
//    public int getCount() {
//        return articleArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return articleArrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View view = View.inflate(R.layout.listitem_article,R.layout.listitem_article,null);
//        return view;
//    }
//}