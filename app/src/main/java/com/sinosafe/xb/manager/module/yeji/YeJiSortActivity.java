package com.sinosafe.xb.manager.module.yeji;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sinosafe.xb.manager.R;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.widget.dialog.DialogYearMonthWheel;
import luo.library.base.widget.dialog.DialogYearsWheelView;

/**
 * 业绩排行榜
 */
public class YeJiSortActivity extends BaseFragmentActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private SimpleFragmentPagerAdapter pagerAdapter;
    //类型
    private int type = -1;
    private DialogYearsWheelView yearsWheelView;
    private DialogYearMonthWheel yearMonthWheel;
    private int currentPosition = -1;
    private YeJiSortBaseFragment currentFragment;
    private String currentYear,currentYearMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeji_sort);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        type = getIntent().getIntExtra("type",0);
        if(type==0)
            setTitleText("保费收入排行榜");
        else if(type==1){
            setTitleText("业绩提成排行榜");
        }else if(type==2){
            setTitleText("放款笔数排行榜");
        }else if(type==3){
            setTitleText("放款金额排行榜");
        }else if(type==4){
            setTitleText("回款率排行榜");
        }

        // 设置适配器
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout,16,16);
            }
        });
        // 为TabLayout设置ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                setRightButton(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
        // TabLayout title
        private String tabTitles[] = new String[]{"日", "周", "月","年"};

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return YeJiSortFragment.newInstance(position,type);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // 返回纯文字
            return tabTitles[position];
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentFragment = (YeJiSortBaseFragment) object;
            super.setPrimaryItem(container, position, object);
        }
    }


    /**
     * 设置右按钮功能
     * @param position
     */
    private void setRightButton(int position){
        if(position==0||position==1){
            hideRightButtonText();
        }else if(position==2){
            String title = "历史排名";
            if(currentYearMonth!=null)
                title = currentYearMonth;
            setRightButtonText(title, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectYearMonthDialog();
                }
            });
        }else if(position==3){
            String title = "历史排名";
            if(currentYear!=null)
                title = currentYear;
            setRightButtonText(title, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectYearDialog();
                }
            });
        }
    }

    /**
     * 选择年份
     */
    private void showSelectYearDialog(){
        if(yearsWheelView==null){
            yearsWheelView = new DialogYearsWheelView(this, new DialogYearsWheelView.OnConfirmListener() {
                @Override
                public void onConfirm(String year) {
                    currentYear = year;
                    setRightButtonText(currentYear);
                    currentFragment.setQueryHistoryReportRankingMap(year);
                }
            },2000);
        }
        yearsWheelView.show();
    }

    /**
     * 选择年份、月份
     */
    private void showSelectYearMonthDialog(){
        if(yearMonthWheel==null){
            yearMonthWheel = new DialogYearMonthWheel(this, new DialogYearMonthWheel.OnDateSelectListener() {
                @Override
                public void onDateSelect(String year, String month) {
                    if(month.length()==1)
                        month = "0"+month;
                    currentYearMonth = year+"-"+month;
                    setRightButtonText(currentYearMonth);
                    currentFragment.setQueryHistoryReportRankingMap(year+"-"+month);
                }
            });
        }
        yearMonthWheel.show();
    }

    /**
     * 改变 TabLayout 下划线的长度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip){
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
