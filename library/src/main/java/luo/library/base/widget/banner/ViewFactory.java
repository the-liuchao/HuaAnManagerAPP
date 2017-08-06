package luo.library.base.widget.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import luo.library.R;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.WebViewActivity;


/**
 * ImageView创建工厂
 */
public class ViewFactory {
	/**
	 * 轮播广告图
	 */
	private static Context context;
	private static List<CycleVpEntity> cycentiy = new ArrayList<>();

	public static void initialize(Context context,final CycleViewPager cycleViewPager, List<CycleVpEntity> cycentiy) {

        ViewFactory.context = context;
		if(context==null){
			return;
		}

		ViewFactory.cycentiy.clear();
		ViewFactory.cycentiy.addAll(cycentiy);
		List<ImageView> views = new ArrayList<ImageView>();

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, cycentiy.get(cycentiy.size() - 1).getPic_url()));

		for (int i = 0; i < cycentiy.size(); i++) {
			views.add(ViewFactory.getImageView(context, cycentiy.get(i).getPic_url()));
		}
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, cycentiy.get(0).getPic_url()));


		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);
		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, cycentiy, new ImageCycleViewListener() {
			@Override
			public void onImageClick(CycleVpEntity info, int postion, View imageView) {
				//effect_type效果类型：0图片-无效果，1跳转产品，2跳转商品，3解析html
				if (cycleViewPager.isCycle())
					postion = postion - 1;
				CycleVpEntity cycleVpEntity = ViewFactory.cycentiy.get(postion);
				if("3".equals(cycleVpEntity.getEffect_type())){
					Bundle bundle = new Bundle();
					bundle.putString("html",cycleVpEntity.getEffect_bean());
					IntentUtil.gotoActivity(ViewFactory.context,WebViewActivity.class,bundle);
				}
				else if("1".equals(cycleVpEntity.getEffect_type())||"2".equals(cycleVpEntity.getEffect_type())){
					Intent intent = new Intent("COM_SINOSAFE_XB_BANNER_ACTION");
					intent.putExtra("id",cycleVpEntity.getEffect_obj_id());
					intent.putExtra("effectType",cycleVpEntity.getEffect_type());
					ViewFactory.context.sendBroadcast(intent);
				}
			}
		});
		//设置轮播,当只有一张图片时不轮播
		if(cycentiy.size()>1)
			cycleViewPager.setWheel(true);
		else{
			cycleViewPager.setWheel(false);
		}
		// 设置轮播时间，默认5000ms
		cycleViewPager.setTime(5000);
		//设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}
	
	/**
	 * 获取ImageView视图的同时加载显示url
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.view_banner, null);
		BaseImage.getInstance().displayImage(context,url, imageView,R.drawable.ic_default);
		//BaseImage.getInstance().displayRoundImage(context,url, imageView);
		return imageView;
	}
}
