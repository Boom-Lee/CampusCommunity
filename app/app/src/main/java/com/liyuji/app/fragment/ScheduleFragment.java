package com.liyuji.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyuji.app.activity.DeliverscheduleActivity;
import com.liyuji.app.activity.ScheduleActivity;
import com.liyuji.app.R;
import com.liyuji.app.adapter.ScheduleAdapter;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ScheduleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L
 */
public class ScheduleFragment extends Fragment {

    private ListView listView;
    List<ScheduleVO> scheduleVOList = new ArrayList<>();
    int scheduleId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        scheduleVOList.clear();
        //加载ListView
        listView = root.findViewById(R.id.schedule_list);

        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(getActivity());
        int userId = util.readInt("userId");

        //发布
        root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeliverscheduleActivity.class);
                startActivity(intent);
            }
        });

        OkHttpUtils.get(Util.SERVER_ADDR + "scheduleList?userId=" + userId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);
                //将字符转换成JSONOBJECT对象
                JSONObject response = JSONObject.parseObject(msg);
                //得到里面data的值
                JSONArray jsonArray = response.getJSONArray("data");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        // 放入 object
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ScheduleVO scheduleVO = new ScheduleVO();
                        scheduleVO.scheduleId = jsonObject.getInteger("scheduleId");
                        scheduleVO.scheduleTitle = jsonObject.getString("scheduleTitle");
                        scheduleVO.scheduleStarttime = jsonObject.getDate("scheduleStarttime");
                        scheduleVO.scheduleEndtime = jsonObject.getDate("scheduleEndtime");

                        scheduleVOList.add(scheduleVO);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ScheduleAdapter adapter = new ScheduleAdapter(scheduleVOList, inflater);
                            listView.setAdapter(adapter);
                        }
                    });
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int firstVisiblePosition = listView.getFirstVisiblePosition(); //屏幕内当前可以看见的第一条数据
                if (position - firstVisiblePosition >= 0) {
                    View itemView = listView.getChildAt(position - firstVisiblePosition);
                    Adapter itemAdapter = parent.getAdapter();
                    ScheduleVO map = (ScheduleVO) itemAdapter.getItem(position);
                    scheduleId = map.getScheduleId();
                    System.out.println("当前日程点击的编号：" + scheduleId);
                }
                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                intent.putExtra("scheduleId", scheduleId);
                startActivity(intent);
            }
        });

        return root;
    }
}