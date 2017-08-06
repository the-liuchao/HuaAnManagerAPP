package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lzy.imagepicker.bean.ImageItem;
import com.sinosafe.xb.manager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import luo.library.base.base.BaseImage;
import luo.library.base.utils.DensityUtil;

/**
 * 待上传图片预览适配器
 */
public class UpLoadGridAdapter extends BaseAdapter {
	private LayoutInflater inflater; // 视图容器
	private int selectedPosition = -1;// 选中的位置
	private Context context;
	private List<ImageItem> images = new ArrayList<>();
	//一行有几列，默认4列
	private int numColumns = 4;
	private int wh;
	private int imageNum = 9;

	/**
	 * 图片的最大数量
	 */
	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}

	public UpLoadGridAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.wh=(DensityUtil.widthPixels(context)-DensityUtil.dp2px(context, 32+(numColumns-1)*5+10))/numColumns;
	}

	public UpLoadGridAdapter(Context context ,int numColumns) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.numColumns = numColumns;
		this.wh=(DensityUtil.widthPixels(context)-DensityUtil.dp2px(context, 32+(numColumns-1)*5+10))/numColumns;
	}


	public void update(List<ImageItem> images) {
		// loading();
		this.images.clear();
		this.images.addAll(images);
		notifyDataSetChanged();
	}

	public int getCount() {
		return (images.size() + 1);
	}

	public Object getItem(int arg0) {

		return null;
	}

	public long getItemId(int arg0) {

		return 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		final int coord = position;
		ViewHolder holder = null;
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.activity_image_item,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_image);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(wh,wh);
			holder.image.setLayoutParams(param);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//最后一个。
		if (position == images.size()) {

			BaseImage.getInstance().displayImage(context,R.mipmap.icon_dianjitianjia,holder.image);

			if (position == imageNum) {
				holder.image.setVisibility(View.GONE);

			}else{
				holder.image.setVisibility(View.VISIBLE);
			}
		} else {

			if(images.get(position).path.startsWith("http"))
				BaseImage.getInstance().displayImage(context,images.get(position).path,holder.image,R.mipmap.default_image);
			else{
				BaseImage.getInstance().displayImage(context,new File(images.get(position).path),holder.image);
			}
		}

		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
	}

}
