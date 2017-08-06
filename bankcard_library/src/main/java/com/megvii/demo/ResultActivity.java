package com.megvii.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.megvii.demo.util.MoreEditView;
import com.megvii.demo.util.SystemBarTintManager;
import com.megvii.demo.util.Util;

/**
 * Created by binghezhouke on 15-8-12.
 */
public class ResultActivity extends Activity implements OnClickListener {
	private ImageView mBankCardImage;
	private TextView mBankCardText, confidenceText;
	private MoreEditView moreEditView;
	private String filePath, num, confidence;
	private Button sureBtn;
	private boolean isDebuge;
	private String titleStr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarTransparent(this, getResources().getColor(R.color.statusBar2));
		setContentView(R.layout.activity_resutl);
		filePath = getIntent().getStringExtra("filePath");
		num = getIntent().getStringExtra("bankNum");
		confidence = getIntent().getStringExtra("confidence");
		isDebuge = getIntent().getBooleanExtra(Util.KEY_ISDEBUGE, false);
		init();
	}

	private void init() {
		titleStr = getIntent().getStringExtra(Util.KEY_TITLE);
		if (titleStr == null)
			titleStr = "核对卡号";
		TextView title = (TextView) findViewById(R.id.resutl_layout_title);
		title.setText(titleStr);
		findViewById(R.id.title_layout_returnRel).setOnClickListener(this);
		findViewById(R.id.resutl_layout_rootRel).setOnClickListener(this);
		mBankCardImage = (ImageView) findViewById(R.id.result_bankcard_image);
		mBankCardText = (TextView) findViewById(R.id.result_bankcard_text);
		confidenceText = (TextView) findViewById(R.id.result_bankcard_confidencetext);
		confidenceText.setText("confidence:  " + confidence);
		if (isDebuge)
			confidenceText.setVisibility(View.VISIBLE);
		else
			confidenceText.setVisibility(View.GONE);
		moreEditView = (MoreEditView) findViewById(R.id.result_bankcard_editText);
		moreEditView.setStr(num);
		sureBtn = (Button) findViewById(R.id.result_layout_sureBtn);
		sureBtn.setOnClickListener(this);
		mBankCardImage.setImageBitmap(BitmapFactory.decodeFile(filePath));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.result_layout_sureBtn) {
			clickSureBtn();
		} else if (v.getId() == R.id.title_layout_returnRel) {
			finish();
		} else if (v.getId() == R.id.resutl_layout_rootRel) {
			Util.isGoneKeyBoard(this);
		}
	}

	private void clickSureBtn() {
		String num = moreEditView.getNumText();
		//mBankCardText.setText(num);
		Intent intent = new Intent();
		intent.putExtra("filePath", filePath);
		intent.putExtra("bankNum", num);
		intent.putExtra("confidence", confidence);
		setResult(RESULT_OK, intent);
		finish();
	}


	/**
	 * 沉浸状态栏
	 * @param context
	 * @param tintColor
	 */
	public static void setStatusBarTransparent(Activity context, int tintColor) {
		// create our manager instance after the content view is set
		SystemBarTintManager tintManager = new SystemBarTintManager(context);
		// enable status bar tint
		tintManager.setStatusBarTintEnabled(true);
		// enable navigation bar tint
		tintManager.setNavigationBarTintEnabled(true);
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			context.getWindow().setStatusBarColor(tintColor);
		} else {
			tintManager.setTintColor(tintColor);
		}
	}
}