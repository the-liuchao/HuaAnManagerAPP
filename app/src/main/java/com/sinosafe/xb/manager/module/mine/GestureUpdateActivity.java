package com.sinosafe.xb.manager.module.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
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

/**
 * 手势密码修改
 * Created by john lee on 2017/5/31.
 */
public class GestureUpdateActivity extends BaseFragmentActivity {

    //手势输入错误次数
    private static final String GESTURE_NUM = "GESTURE_NUM";

    @BindView(R.id.lockPatternView)
    LockPatternView lockPatternView;
    @BindView(R.id.messageTv)
    TextView messageTv;

    private ACache aCache;
    private static final long DELAYTIME = 600l;
    private byte[] gesturePassword;

    private int num = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_update);
        ButterKnife.bind(this);

        initView();
    }


    protected void initView() {
        setTitleText("修改手势密码");
        aCache = ACache.get(this);
        if (aCache.getAsObject(GESTURE_NUM) != null)
            num = (Integer) aCache.getAsObject(GESTURE_NUM);
        //得到当前用户的手势密码
        gesturePassword = aCache.getAsBinary(CreateGestureActivity.GESTURE_PASSWORD);
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);

    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                if (LockPatternUtil.checkPattern(pattern, gesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    if (num == 1) {
                        clearGestureData();
                        gestureFail();
                    } else {
                        num--;
                        aCache.put(GESTURE_NUM, num);
                    }
                    updateStatus(Status.ERRORNUM);
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
        messageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERRORNUM:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CORRECT:
//                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                loginGestureSuccess();
                break;
        }
        if (status == Status.ERRORNUM) {
            if (num > 0)
                messageTv.setText("密码错误，还可以输入" + num + "次");
        } else
            messageTv.setText(status.strId);
    }

    /**
     * 手势登录成功（去首页）
     */
    private void loginGestureSuccess() {
        aCache.put(GESTURE_NUM, 5);
        Intent intent = new Intent(GestureUpdateActivity.this, CreateGestureActivity.class);
        startActivity(intent);
        finish();
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
     * 验证登录密码（去账号登录界面）
     */
    @OnClick(R.id.verify)
    void verify() {
        DialogHelper.getConfirmDialog(this, "忘记手势密码，可以使用账号密码登录，登录后可以重新绘制手势图案", false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearGestureData();
                goLogin();
            }
        }).show();
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
        APP.getApplication().getMainActivity().finish();
        APP.getApplication().setMainActivity(null);
        //删除登录用户信息
        BaseDb.delete(LoginUserBean.class, BaseMainActivity.loginUserBean.getId());
        BaseMainActivity.loginUserBean = null;
    }

    /**
     * 登录界面
     */
    private void goLogin() {
        //跳转到登录界面
        IntentUtil.gotoActivityAndFinish(this, LoginActivity.class);
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_primary_default, R.color.grey_a5a5a5),
        //密码输入错误次数
        ERRORNUM(R.string.gesture_error, R.color.red_f4333c),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }
}
