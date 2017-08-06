package com.sinosafe.xb.manager.module.home.fragment;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import com.sinosafe.xb.manager.module.yewu.ContentFragment;

import java.lang.reflect.Field;

import luo.library.base.base.BaseFragment;

/**
 * 类名称：   com.cnmobi.huaan.manager.fragment
 * 内容摘要： //业务片。
 * 修改备注：
 * 创建时间： 2017/6/3 0003
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class YeWuBaseFragment extends BaseFragment{


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


    class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
        // TabLayout title
        private String tabTitles[] = new String[]{"申请中", "审批中", "待放款","还款中","已拒绝"};

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ContentFragment.newInstance(position);
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
    }
}
