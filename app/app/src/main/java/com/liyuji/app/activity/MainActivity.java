package com.liyuji.app.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.liyuji.app.R;
import com.liyuji.app.fragment.AnonymousFragment;
import com.liyuji.app.fragment.ArticleFragment;
import com.liyuji.app.fragment.PersonalFragment;
import com.liyuji.app.fragment.ScheduleFragment;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;


    private static final String TAG = "MainActivity";

    //底部导航对象
    private BottomNavigationView bottomNavigationView;

    //存储页面对象
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断网络状态
        if (Util.isNetworkAvailable(MainActivity.this)) {
            deLogin();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        applyForRight();

        renderMainActivity();

        ActToFragment();
    }


    private void deLogin() {
        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(MainActivity.this);
        //判断share...里存的isLogin值   判断登录状态
        if (util.readBoolean("isLogin")) {
            Log.d(TAG, "onCreate: 已登录");
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        //设置View为登录界面
        setContentView(R.layout.activity_main);
    }

    private void applyForRight() {
        if (Build.VERSION.SDK_INT >= 24) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }

    private void ActToFragment() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        switch (id) {
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        //设置显示fragment
                        .replace(R.id.viewpager, new ArticleFragment())
                        .addToBackStack(null)
                        .commit();
                viewPager.setCurrentItem(0);
                System.out.println("跳转至ArticleFragment   id:" + id);
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        //设置显示fragment
                        .replace(R.id.viewpager, new ScheduleFragment())
                        .addToBackStack(null)
                        .commit();
                viewPager.setCurrentItem(1);
                System.out.println("跳转至ScheduleFragment   id:" + id);
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        //设置显示fragment
                        .replace(R.id.viewpager, new AnonymousFragment())
                        .addToBackStack(null)
                        .commit();
                viewPager.setCurrentItem(2);
                System.out.println("跳转至AnonymousFragment   id:" + id);
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        //设置显示fragment
                        .replace(R.id.viewpager, new PersonalFragment())
                        .addToBackStack(null)
                        .commit();
                viewPager.setCurrentItem(3);
                System.out.println("跳转至PersonalFragment   id:" + id);
                break;
            default:
                break;
        }
    }

    private void renderMainActivity() {
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉\\
        //        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.id_article:
                                bottomNavigationView.setBackgroundColor(Color.WHITE);
                                viewPager.setCurrentItem(0);
                                return true;
                            case R.id.id_schedule:
                                bottomNavigationView.setBackgroundColor(Color.WHITE);
                                viewPager.setCurrentItem(1);
                                return true;
                            case R.id.id_anonymous:
                                bottomNavigationView.setBackgroundColor(Color.BLACK);
                                viewPager.setCurrentItem(2);
                                return true;
                            case R.id.id_personal:
                                bottomNavigationView.setBackgroundColor(Color.WHITE);
                                viewPager.setCurrentItem(3);
                                return true;
                            default:
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//        禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        //向ViewPager添加各页面
        fragmentList = new ArrayList<>();
        fragmentList.add(new ArticleFragment());
        fragmentList.add(new ScheduleFragment());
        fragmentList.add(new AnonymousFragment());
        fragmentList.add(new PersonalFragment());
        MyFragAdapter myAdapter = new MyFragAdapter(getSupportFragmentManager(), this, fragmentList);
        viewPager.setAdapter(myAdapter);
    }

    class MyFragAdapter extends FragmentStatePagerAdapter {
        Context context;
        List<Fragment> listFragment;

        public MyFragAdapter(FragmentManager fm, Context context, List<Fragment> listFragment) {
            super(fm);
            this.context = context;
            this.listFragment = listFragment;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }
    }


}