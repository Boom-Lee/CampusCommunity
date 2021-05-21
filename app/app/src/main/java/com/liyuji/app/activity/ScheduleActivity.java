package com.liyuji.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.liyuji.app.R;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ServerResponse;

/**
 * @author L
 */
public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    int scheduleId = 0;
    TextView schedule_return, userSchedule_title, userSchedule_content, userSchedule_location;
    TextView schedule_starttime, schedule_endtime;
    Button schedule_del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Intent intent = getIntent();
        scheduleId = intent.getIntExtra("scheduleId", 0);
        System.out.println("当前日程点击的编号：" + scheduleId);

        schedule_return = findViewById(R.id.schedule_return);
        userSchedule_title = findViewById(R.id.userSchedule_title);
        userSchedule_content = findViewById(R.id.userSchedule_content);
        userSchedule_location = findViewById(R.id.userSchedule_location);
        schedule_starttime = findViewById(R.id.userSchedule_starttime);
        schedule_endtime = findViewById(R.id.userSchedule_endtime);
        schedule_del = findViewById(R.id.schedule_del);

        OkHttpUtils.get(Util.SERVER_ADDR + "scheduleShow?scheduleId=" + scheduleId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);

//                将字符转换成JSONOBJECT对象
                JSONObject response = JSONObject.parseObject(msg);
                //得到里面data的值
                JSONObject jsonObject = response.getJSONObject("data");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                                userSchedule_title.setText(jsonObject.getString("scheduleTitle"));
                                userSchedule_content.setText(jsonObject.getString("scheduleContent"));
                                userSchedule_location.setText(jsonObject.getString("scheduleLocation"));
                                schedule_starttime.setText(MyTimeUtils.dateToYmdHm(jsonObject.getDate("scheduleStarttime")));
                                schedule_endtime.setText(MyTimeUtils.dateToYmdHm(jsonObject.getDate("scheduleEndtime")));
                    }
                });

            }
        });

        schedule_return.setOnClickListener(this);
        schedule_del.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_return:
                finish();
                break;
            case R.id.schedule_del:
                //删除
                OkHttpUtils.get(Util.SERVER_ADDR + "scheduleDel?scheduleId=" + scheduleId, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(ScheduleActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
                intent.putExtra("id",Util.SCHEDULERAGMENT);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}