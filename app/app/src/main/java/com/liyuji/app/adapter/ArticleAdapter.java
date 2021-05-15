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
import com.liyuji.app.vo.ArticleVO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class ArticleAdapter extends BaseAdapter {


    /**
     * 动态列表
     */
    public List<ArticleVO> articleArrayList;
    private LayoutInflater mInflater;

    public ArticleAdapter(List<ArticleVO> articleArrayList, LayoutInflater mInflater) {
        this.articleArrayList = articleArrayList;
        this.mInflater = mInflater;
    }

    /**
     * 确认行数
     */
    @Override
    public int getCount() {
        return articleArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleVO articleVO = (ArticleVO) getItem(position);
        View view = mInflater.inflate(R.layout.listitem_article, null);

        CircleImageView mHeadImg = view.findViewById(R.id.headImg);
        TextView mUserNickname = view.findViewById(R.id.userNickname);
        TextView mArticleDelivertime = view.findViewById(R.id.article_delivertime);
        TextView mContent = view.findViewById(R.id.content);
        ImageView mArticleImg = view.findViewById(R.id.article_img);

        String headImg = articleVO.getUserHeadImg();
        String userNickname = articleVO.getUserNickname();
        String articleDeliverTime = MyTimeUtils.DateToYmdHms(articleVO.getArticleDate());
        String content = articleVO.getArticleContent();
        String articleImg = articleVO.getArticleImg();

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
