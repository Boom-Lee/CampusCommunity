package com.liyuji.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liyuji.app.R;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.vo.ArticleLikeVO;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class LikeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    List<ArticleLikeVO> articleLikeVOList = new ArrayList<>();

    public LikeAdapter(LayoutInflater mInflater, List<ArticleLikeVO> articleLikeVOList) {
        this.mInflater = mInflater;
        this.articleLikeVOList = articleLikeVOList;
    }

    @Override
    public int getCount() {
        return articleLikeVOList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleLikeVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleLikeVO articleLikeVO = (ArticleLikeVO) getItem(position);
        View view = mInflater.inflate(R.layout.listitem_article, null);

        CircleImageView mHeadImg = view.findViewById(R.id.headImg);
        TextView mUserNickname = view.findViewById(R.id.userNickname);
        TextView mArticleDelivertime = view.findViewById(R.id.article_delivertime);
        TextView mContent = view.findViewById(R.id.content);
        ImageView mArticleImg = view.findViewById(R.id.article_img);

        String headImg = articleLikeVO.getUserHeadImg();
        String userNickname = articleLikeVO.getUserNickname();
        String articleDeliverTime = MyTimeUtils.dateToYmdHms(articleLikeVO.getArticleDate());
        String content = articleLikeVO.getArticleContent();
        String articleImg = articleLikeVO.getArticleImg();

        // 用户头像设置
        Glide.with(parent.getContext())
                .load(headImg)
                .into(mHeadImg);
        // 用户昵称设置
        mUserNickname.setText(userNickname);
        // 发布时间设置
        mArticleDelivertime.setText(articleDeliverTime);
        // 发布内容设置
        mContent.setText(content);
        // 内容图片设置
        if (articleImg != null) {
            Glide.with(parent.getContext())
                    .load(articleImg)
                    .into(mArticleImg);
        } else {
            mArticleImg.setVisibility(View.GONE);
        }

        return view;
    }
}