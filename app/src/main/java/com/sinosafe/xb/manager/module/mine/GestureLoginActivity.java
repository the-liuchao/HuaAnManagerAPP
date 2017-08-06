package com.sinosafe.xb.manager.module.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.MainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.module.login.LoginActivity;
import com.star.lockpattern.util.ACache;
import com.star.lockpattern.util.LockPatternUtil;
import com.star.lockpattern.widget.LockPatternView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseDb;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.DialogHelper;

import static com.sinosafe.xb.manager.R.id.lockPatternView;
import static com.sinosafe.xb.manager.R.id.messageTv;


/**
 * 手势密码登录
 * Created by john lee on 2017/5/31.
 */
public class GestureLoginActivity extends BaseFragmentActivity {

    //手势输入错误次数
    public static final String GESTURE_NUM = "GESTURE_NUM";

    @BindView(messageTv)
    TextView mMessageTv;
    @BindView(lockPatternView)
    LockPatternView mLockPatternView;
    @BindView(R.id.forgetGestureBtn)
    Button mForgetGestureBtn;
    private ACache aCache;
    private static final long DELAYTIME = 600l;
    private byte[] gesturePassword;

    private int num = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_login);
        ButterKnife.bind(this);

        initView();
    }

    protected void initView() {

        aCache = ACache.get(GestureLoginActivity.this);
        if (aCache.getAsObject(GESTURE_NUM) != null)
            num = (Integer) aCache.getAsObject(GESTURE_NUM);

        //得到当前用户的手势密码
        gesturePassword = aCache.getAsBinary(CreateGestureActivity.GESTURE_PASSWORD);
        mLockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);
    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            mLockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                if (LockPatternUtil.checkPattern(pattern, gesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERRORNUM);
                    if (num == 1) {
                        clearGestureData();
                        gestureFail();
                    } else {
                        num--;
                        aCache.put(GESTURE_NUM, num);
                    }
                }
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        //mMessageTv.setText(status.strId);
        mMessageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                mLockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERRORNUM:
                mLockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                mLockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CORRECT:
//                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                loginGestureSuccess();
                break;
        }
        if (status == Status.ERRORNUM) {
            if (num > 0)
                mMessageTv.setText("密码错误，还可以输入" + num + "次");
        } else
            mMessageTv.setText(status.strId);
    }

    /**
     * 手势登录成功（去首页）
     */
    private void loginGestureSuccess() {
        aCache.put(GESTURE_NUM, 5);
        Intent intent = new Intent(GestureLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 忘记手势密码（去账号登录界面）
     */
    @OnClick(R.id.forgetGestureBtn)
    void forgetGesturePasswrod() {
        DialogHelper.getConfirmDialog(this, "忘记手势密码，可以使用账号密码登录，登录后可以重新绘制手势图案", false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearGestureData();
                goLogin();
            }
        }).show();
    }

    /**
     * 手势密码输入失败次数达上限
     */
    private void gestureFail() {
        DialogHelper.getMessageDialog(this, "手势密码已失效，请重新登录", false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goLogin();
            }
        }).show();
    }

    /**
     * 登录界面
     */
    private void goLogin() {
        //跳转到登录界面
        IntentUtil.gotoActivityAndFinish(this, LoginActivity.class);
    }


    /**
     * 清空手势密码数据
     */
    private void clearGestureData() {
        //清除手势密码
        aCache.remove(CreateGestureActivity.GESTURE_PASSWORD);
        //清除手势密码输入错误次数
        aCache.remove(GESTURE_NUM);
        //清空登录信息
        /*APP.getApplication().getMainActivity().finish();
        APP.getApplication().setMainActivity(null);*/
        //删除登录用户信息
        BaseDb.delete(BaseDb.findOne(LoginUserBean.class));
        BaseMainActivity.loginUserBean = null;
    }

    @OnClick(R.id.forgetGestureBtn)
    public void onViewClicked() {
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default, R.color.grey_a5a5a5),
        //密码输入错误
        ERRORNUM(R.string.gesture_error, R.color.red_f4333c),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);
        //密码输入错误次数


        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }
}
