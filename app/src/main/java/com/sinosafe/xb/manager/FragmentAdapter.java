package com.sinosafe.xb.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.sinosafe.xb.manager.module.home.fragment.HomeFragment;
import com.sinosafe.xb.manager.module.home.fragment.MeFragment;
import com.sinosafe.xb.manager.module.home.fragment.YeJiFragment;
import com.sinosafe.xb.manager.module.home.fragment.YeWuFragment;

/**
 * 类名称：   FragmentAdapter
 * 内容摘要： //底部导航栏适配器。
 * 修改备注：
 * 创建时间： 2017/4/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class FragmentAdapter extends FragmentPagerAdapter {
	public final static int TAB_COUNT = 4;
	//首页
	private HomeFragment homeFragment;
	//业务
	private YeWuFragment yeWuFragment;
	//业绩
	private YeJiFragment yeJiFragment;
	//我的
	private MeFragment meFragment;


	public HomeFragment getHomeFragment() {
		return homeFragment;
	}

	public YeWuFragment getYeWuFragment() {
		return yeWuFragment;
	}

	public YeJiFragment getYeJiFragment() {
		return yeJiFragment;
	}

	public MeFragment getMeFragment() {
		return meFragment;
	}

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		homeFragment = new HomeFragment();
		yeWuFragment = new YeWuFragment();
		yeJiFragment = new YeJiFragment();
		meFragment = new MeFragment();
	}

	@Override
	public Fragment getItem(int id) {
		switch (id) {
		case MainActivity.TAB_page1:
			return homeFragment;

		case MainActivity.TAB_page2:
			return yeWuFragment;

		case MainActivity.TAB_page3:
			return yeJiFragment;

		case MainActivity.TAB_page4:
			return meFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}
	
	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		//currentFragment = (Fragment_pro_type) object;
		super.setPrimaryItem(container, position, object);
	}

}

