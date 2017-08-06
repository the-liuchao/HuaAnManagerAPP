package luo.library.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import luo.library.R;

/**
 * 类名称：ActionShareDialog.java <br>
 * 内容摘要： //分享界面。<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016年8月25日下午6:37:49<br>
 * 公司：深圳市华移科技股份有限公司<br>
 * @author Mr 张<br>
 */
public class ActionShareDialog {
	private Context context;
	private Dialog dialog;
	/** 朋友圈分享 **/
	private LinearLayout wxcircleLayout;
	/** QQ分享 **/
	private LinearLayout qqLayout;
	private Display display;

	private OnSheetItemClickListener sheetItemClickListener;

	/**
	 * @param sheetItemClickListener
	 *            the sheetItemClickListener to set
	 */
	public void setSheetItemClickListener(
			OnSheetItemClickListener sheetItemClickListener) {
		this.sheetItemClickListener = sheetItemClickListener;
	}

	public ActionShareDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public ActionShareDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.activity_share_menu_layout, null);

		wxcircleLayout = (LinearLayout) view.findViewById(R.id.wxcircleLayout);
		qqLayout = (LinearLayout) view.findViewById(R.id.qqLayout);
		wxcircleLayout.setOnClickListener(listener);
		qqLayout.setOnClickListener(listener);
		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);

		return this;
	}

	public ActionShareDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public ActionShareDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public void show() {
		dialog.show();
	}

	public void close() {
		dialog.dismiss();
	}

	/**
	 * 类名称：ActionShareDialog.java <br>
	 * 内容摘要： //分享点击监听。<br>
	 * 属性描述：<br>
	 * 方法描述：<br>
	 * 修改备注： <br>
	 * 创建时间： 2016年5月30日下午7:37:36<br>
	 * 公司：深圳市华移科技股份有限公司<br>
	 * 
	 * @author Mr 张<br>
	 */
	public interface OnSheetItemClickListener {
		void onClick(int which);
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if(v.getId()==R.id.wxcircleLayout){
				sheetItemClickListener.onClick(0);
			}
			else if(v.getId()==R.id.qqLayout){
				sheetItemClickListener.onClick(3);
			}
		}
	};
}
