package com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sinosafe.xb.manager.MainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;


/**
 * 消费贷申请 结果
 * Created by cnmobi_01 on 2017/4/26.
 */

public class LoadApplyResultActivity extends BaseFragmentActivity {


    @BindView(R.id.btnToHome)
    Button mBtnToHome;
    @BindView(R.id.btnToProgress)
    Button mBtnToProgress;

    private String serno = "WXSQ20170614017527";
    private String prdId = "051402";
    private String prdType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumloan_apply_result);
        ButterKnife.bind(this);

        setTitleText("操作结果");
        LinearLayout backTv = (LinearLayout) findViewById(R.id.ly_base_back);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                IntentUtil.gotoActivityAndFinish(LoadApplyResultActivity.this, MainActivity.class);
            }
        });
        serno = getIntent().getStringExtra("serno");
        prdId = getIntent().getStringExtra("prdId");
        prdType = getIntent().getStringExtra("prdType");
    }


    @OnClick({R.id.btnToHome, R.id.btnToProgress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回首页
            case R.id.btnToHome:
                IntentUtil.gotoActivityAndFinish(this, MainActivity.class);
                break;

            //查看进度
            case R.id.btnToProgress:
                IntentUtil.gotoActivityAndFinish(this,MainActivity.class);
                Intent intent = new Intent(Constant.LOAD_APPLY_PROGRESS);
                intent.putExtra("serno",serno);
                intent.putExtra("prdId",prdId);
                intent.putExtra("prdType",prdType);
                sendBroadcast(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            IntentUtil.gotoActivityAndFinish(this, MainActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
