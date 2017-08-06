package com.sinosafe.xb.manager.module.home.xiaofeidai;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.widget.ElectronicSignatureView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.utils.SystemBarTintManager;


/**
 * 横屏电子签名
 */
public class ElectronicSignatureActivity extends FragmentActivity {

    @BindView(R.id.view)
    ElectronicSignatureView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarTransparent(this, getResources().getColor(R.color.color_494953));
        setContentView(R.layout.activity_electronic_signature);
        ButterKnife.bind(this);

        view.setPaintWidth(6);
    }

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

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.iv_back, R.id.btn_clear_screen, R.id.btn_confirm})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_clear_screen:
                view.clear();
                break;
            case R.id.btn_confirm:
                if (view.getTouched()) {
                    try {
                        view.save(false, 10);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ElectronicSignatureActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
