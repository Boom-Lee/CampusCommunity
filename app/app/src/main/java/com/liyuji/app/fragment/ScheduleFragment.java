package com.liyuji.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.liyuji.app.Activity.DeliverscheduleActivity;
import com.liyuji.app.R;

public class ScheduleFragment extends Fragment {

    private ListView listView;
    ImageView schedule_deliver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        //加载ListView
//        listView = root.findViewById(R.id.article_list);
//        BaseListViewArticleAdapter adapter = new BaseListViewArticleAdapter(getContext());
//        listView.setAdapter(adapter);

        //发布
        root.findViewById(R.id.schedule_deliver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeliverscheduleActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}