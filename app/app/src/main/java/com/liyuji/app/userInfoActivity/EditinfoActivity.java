package com.liyuji.app.userInfoActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liyuji.app.activity.MainActivity;
import com.liyuji.app.R;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ServerResponse;
import com.liyuji.app.vo.UserVO;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;

import java.util.Date;

public class EditinfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mReBack;
    private LinearLayout mUserNicknameBtn;
    private TextView mUserNickname;
    private LinearLayout mUserMobileBtn;
    private TextView mUserMobile;
    private LinearLayout mUserIntroBtn;
    private TextView mUserIntro;
    private LinearLayout mUserRegisterTimeBtn;
    private TextView mUserRegisterTime;
    private LinearLayout mUserSexBtn;
    private TextView mUserSex;
    private LinearLayout mUserBirthdayBtn;
    private TextView mUserBirthday;
    private LinearLayout mUserEmailBtn;
    private TextView mUserEmail;
    private LinearLayout mUserCityBtn;
    private TextView mUserCity;
    private LinearLayout mUserSchoolBtn;
    private TextView mUserSchool;
    int userId;
    String userNickname;
    long userMobile;
    String userMobileS;
    String userSex;
    String userIntro;
    String userBirthday;
    String userEmail;
    String userCity;
    String userSchool;
    String userRegisterTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);

        mReBack = findViewById(R.id.reBack);
        mUserNicknameBtn = findViewById(R.id.user_nickname_btn);
        mUserNickname = findViewById(R.id.user_nickname);
        mUserMobileBtn = findViewById(R.id.user_mobile_btn);
        mUserMobile = findViewById(R.id.user_mobile);
        mUserIntroBtn = findViewById(R.id.user_intro_btn);
        mUserIntro = findViewById(R.id.user_intro);
        mUserRegisterTime = findViewById(R.id.user_registerTime);
        mUserSexBtn = findViewById(R.id.user_sex_btn);
        mUserSex = findViewById(R.id.user_sex);
        mUserBirthdayBtn = findViewById(R.id.user_birthday_btn);
        mUserBirthday = findViewById(R.id.user_birthday);
        mUserEmailBtn = findViewById(R.id.user_email_btn);
        mUserEmail = findViewById(R.id.user_email);
        mUserCityBtn = findViewById(R.id.user_city_btn);
        mUserCity = findViewById(R.id.user_city);
        mUserSchoolBtn = findViewById(R.id.user_school_btn);
        mUserSchool = findViewById(R.id.user_school);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        System.out.println("得到的用户编号：" + userId);

        setContent(userId);

        mReBack.setOnClickListener(this);
        mUserNicknameBtn.setOnClickListener(this);
        mUserMobileBtn.setOnClickListener(this);
        mUserIntroBtn.setOnClickListener(this);
        mUserSexBtn.setOnClickListener(this);
        mUserBirthdayBtn.setOnClickListener(this);
        mUserEmailBtn.setOnClickListener(this);
        mUserCityBtn.setOnClickListener(this);
        mUserSchoolBtn.setOnClickListener(this);
    }

    private void setContent(int userId) {

        OkHttpUtils.get(Util.SERVER_ADDR + "showInfo?userId=" + userId, new
                OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        ServerResponse<UserVO> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVO>>() {
                        }.getType());

                        userNickname = serverResponse.getData().getUserNickname();
                        userMobileS = serverResponse.getData().getUserMobile() + "";
                        userSex = serverResponse.getData().getUserSex();
                        userIntro = serverResponse.getData().getUserIntro();
                        if (serverResponse.getData().getUserBirthday() != null) {
                            userBirthday = MyTimeUtils.dateToYmd(serverResponse.getData().getUserBirthday());
                        }
                        userEmail = serverResponse.getData().getUserEmail();
                        userCity = serverResponse.getData().getUserCity();
                        userSchool = serverResponse.getData().getUserSchool();
                        userRegisterTime = MyTimeUtils.dateToYmd(serverResponse.getData().getUserRegisterTime());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userNickname != null) {
                                    mUserNickname.setText(userNickname);
                                }
                                if (userMobileS != null && !"0".equals(userMobileS)) {
                                    mUserMobile.setText(userMobileS);
                                }
                                if (userSex != null) {
                                    mUserSex.setText(userSex);
                                }
                                if (userIntro != null) {
                                    mUserIntro.setText(userIntro);
                                }
                                if (userBirthday != null) {

                                    mUserBirthday.setText(userBirthday);
                                }
                                if (userEmail != null) {

                                    mUserEmail.setText(userEmail);
                                }
                                if (userCity != null) {

                                    mUserCity.setText(userCity);
                                }
                                if (userSchool != null) {

                                    mUserSchool.setText(userSchool);
                                }
                                if (userRegisterTime != null) {

                                    mUserRegisterTime.setText(userRegisterTime);
                                }

                            }
                        });

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                Intent intent = new Intent(EditinfoActivity.this, MainActivity.class);
                intent.putExtra("id", Util.PERSONALFRAGMENT);
                startActivity(intent);
                break;
            case R.id.user_nickname_btn:
                Intent intentN = new Intent(this, Editusernickname.class);
                intentN.putExtra("userId", userId);
                intentN.putExtra("userNickname", userNickname);
                startActivity(intentN);
                break;
            case R.id.user_mobile_btn:
                Intent intentM = new Intent(this, Editusermobile.class);
                intentM.putExtra("userId", userId);
                intentM.putExtra("userMobile", Long.valueOf(userMobileS).longValue());
                startActivity(intentM);
                break;
            case R.id.user_intro_btn:
                Intent intentI = new Intent(this, Edituserintro.class);
                intentI.putExtra("userId", userId);
                intentI.putExtra("userIntro", userIntro);
                startActivity(intentI);
                break;
            case R.id.user_email_btn:
                Intent intentE = new Intent(this, Edituseremail.class);
                intentE.putExtra("userId", userId);
                intentE.putExtra("userEmail", userEmail);
                startActivity(intentE);
                break;
            case R.id.user_school_btn:
                Intent intentS = new Intent(this, Edituserschool.class);
                intentS.putExtra("userId", userId);
                intentS.putExtra("userEmail", userEmail);
                startActivity(intentS);
                break;
            case R.id.user_birthday_btn:
                TimePicker(mUserBirthday);
                break;
            case R.id.user_city_btn:
                CityPicker(mUserCity);
                break;
            case R.id.user_sex_btn:
                SexPicker(mUserSex);
                break;
            default:
                break;
        }
    }

    private void SexPicker(TextView mUserSex) {

        String[] sexArray = new String[]{"女", "男"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(sexArray, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserSex.setText(sexArray[which]);
                String EditUserSex = mUserSex.getText().toString();
                OkHttpUtils.get(Util.SERVER_ADDR + "updateUserSex?userId=" + userId + "&userSex=" + EditUserSex, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(EditinfoActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                dialog.dismiss();
            }
        });
        builder.show();// 让弹出框显示
    }

    private void CityPicker(TextView mUserCity) {
        JDCityPicker cityPicker = new JDCityPicker();
        JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();

        jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY_DIS);
        cityPicker.init(this);
        cityPicker.setConfig(jdCityConfig);
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                mUserCity.setText(province.getName() + city.getName() + district.getName());
                String EditUserCity = mUserCity.getText().toString();
                OkHttpUtils.get(Util.SERVER_ADDR + "updateUserCity?userId=" + userId + "&userCity=" + EditUserCity, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(EditinfoActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }

            @Override
            public void onCancel() {
            }
        });
        cityPicker.showCityPicker();
    }

    private void TimePicker(TextView mUserBirthday) {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mUserBirthday.setText(MyTimeUtils.dateToTimePickerYmd(date));
                String EditBirthDay = mUserBirthday.getText().toString();
                OkHttpUtils.get(Util.SERVER_ADDR + "updateUserBirthday?userId=" + userId + "&userBirthday=" + EditBirthDay, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(EditinfoActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        })
                // 默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                //取消按钮文字
                .setCancelText("取消")
                //确认按钮文字
                .setSubmitText("确定")
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setOutSideCancelable(true)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                .build();

        pvTime.show();
    }
}