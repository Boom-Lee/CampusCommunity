package com.liyuji.app.Activity;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author L
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;

    //底部导航对象
    private BottomNavigationView bottomNavigationView;

    //存储页面对象
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderMainActivity();
    }

    private void renderMainActivity() {
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉\
        //        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.id_article:
                                viewPager.setCurrentItem(0);
                                return true;
                            case R.id.id_schedule:
                                viewPager.setCurrentItem(1);
                                return true;

                            case R.id.id_anonymous:
                                viewPager.setCurrentItem(2);
                                return true;

                            case R.id.id_personal:
                                viewPager.setCurrentItem(3);
                                return true;
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

        //禁止ViewPager滑动
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