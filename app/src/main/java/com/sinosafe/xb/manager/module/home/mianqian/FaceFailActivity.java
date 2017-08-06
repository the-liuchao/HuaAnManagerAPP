package com.sinosafe.xb.manager.module.home.mianqian;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;

import com.megvii.livenesslib.LivenessActivity;
import com.sinosafe.xb.manager.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 人脸识别 结果
 * Created by cnmobi_01 on 2017/4/26.
 */

public class FaceFailActivity extends BaseFragmentActivity {

    @BindView(R.id.btn_next)
    Button mBtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_result);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {

        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {

            EasyPermissions.requestPermissions(this ,"", 0x03, Manifest.permission.CAMERA);
            return;
        }
        IntentUtil.gotoActivityAndFinish(this, LivenessActivity.class);
    }
}
