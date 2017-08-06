package com.sinosafe.xb.manager.module.home.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.module.home.message.MessageListActivity;
import com.sinosafe.xb.manager.module.mine.CreateGestureActivity;
import com.sinosafe.xb.manager.module.mine.GestureUpdateActivity;
import com.sinosafe.xb.manager.module.mine.MeCenterActivity;
import com.sinosafe.xb.manager.module.mine.MyCustomerActivity;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.star.lockpattern.util.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.UpdateService;
import luo.library.base.widget.dialog.ActionShareDialog;
import luo.library.base.widget.dialog.ActionShareDialog.OnSheetItemClickListener;

import static com.sinosafe.xb.manager.module.mine.GestureLoginActivity.GESTURE_NUM;


/**
 * 我的片
 */
public class MeFragment extends MeBaseFragment implements View.OnClickListener ,OnSheetItemClickListener {


    @BindView(R.id.ivImage)
    ImageView mIvImage;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.keHuLayout)
    RelativeLayout mKeHuLayout;
    @BindView(R.id.shouShiLayout)
    RelativeLayout mShouShiLayout;
    @BindView(R.id.banBenLayout)
    RelativeLayout mBanBenLayout;
    @BindView(R.id.fenXiangLayout)
    RelativeLayout mFenXiangLayout;
    @BindView(R.id.tbtnGesture)
    ToggleButton tbtnGesture;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    Unbinder unbinder;

    protected boolean isViewInitiated;
    private boolean isGesture = false;
    private ACache aCache;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarType = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the key for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        setTitleText("");
        hideBack();
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewInitiated) {
            showLoginUserInfo();
            setRightImage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoginUserInfo();
        setRightImage();
        if (aCache.getAsBinary(CreateGestureActivity.GESTURE_PASSWORD) != null) {
            isGesture = true;
            tbtnGesture.setVisibility(View.VISIBLE);
            tbtnGesture.setChecked(true);
        }else{
            tbtnGesture.setVisibility(View.GONE);
            isGesture = false;
        }
    }


    /**
     * 初始化
     */
    private void initView() {

        setRightImg(R.mipmap.icon_xiaoxi_no, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseMainActivity.hasNewMess = false;
                IntentUtil.gotoActivity(getActivity(),MessageListActivity.class);
                setRightImage();
            }
        });

        shareDialog = new ActionShareDialog(getActivity()).builder();
        shareDialog.setSheetItemClickListener(this);

        String token = BaseMainActivity.loginUserBean.getToken();
        String user_id = BaseMainActivity.loginUserBean.getActorno();
        shareUrl = APIManager.SINOSAFE_URL+"shareLogin?"+"type=1&token="+token+"&user_id="+user_id;
        aCache = ACache.get(getActivity());

        tvVersion.setText("V"+MyUtils.getVersionName(getActivity()));
        setRightImage();
        if (aCache.getAsBinary(CreateGestureActivity.GESTURE_PASSWORD) != null) {
            isGesture = true;
            tbtnGesture.setVisibility(View.VISIBLE);
            tbtnGesture.setChecked(true);
        }else{
            tbtnGesture.setVisibility(View.GONE);
            isGesture = false;
        }
    }

    /**
     * 显示登录用户信息
     */
    private void showLoginUserInfo(){
        MyLog.e("token===="+BaseMainActivity.loginUserBean.getToken());
        mTvName.setText(BaseMainActivity.loginUserBean.getActorname());
        mTvPhone.setText(BaseMainActivity.loginUserBean.getTelnum());
        BaseImage.getInstance().displayCricleImage(getActivity(),
                BaseMainActivity.loginUserBean.getAvatar(),mIvImage,R.mipmap.icon_user_image);
    }

    //消息图标
    public void setRightImage(){
        if(BaseMainActivity.hasNewMess)
            setRightImg(R.mipmap.icon_xiaoxi_has);
        else{
            setRightImg(R.mipmap.icon_xiaoxi_no);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @OnCheckedChanged(R.id.tbtnGesture)
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        if(!arg1){
            tbtnGesture.setVisibility(View.GONE);
            //清除手势密码
            aCache.remove(CreateGestureActivity.GESTURE_PASSWORD);
            //清除手势密码输入错误次数
            aCache.remove(GESTURE_NUM);
            isGesture = false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.keHuLayout, R.id.shouShiLayout, R.id.banBenLayout,
            R.id.fenXiangLayout,R.id.ivImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //我的客户
            case R.id.keHuLayout:
                IntentUtil.gotoActivity(getActivity(),MyCustomerActivity.class);
                break;
            //手势密码
            case R.id.shouShiLayout:
                if (isGesture)
                    IntentUtil.gotoActivity(getActivity(),GestureUpdateActivity.class);
                else
                    IntentUtil.gotoActivity(getActivity(),CreateGestureActivity.class);
                break;
            //版本更新
            case R.id.banBenLayout:
                if(UpdateService.isDownloading){
                    T.showShortBottom("版本升级中,请耐心等待.");
                    return;
                }
                checkVersion();
                break;
            //分享
            case R.id.fenXiangLayout:
                shareDialog.show();
                break;
            //我的资料
            case R.id.ivImage:
                IntentUtil.gotoActivity(getActivity(),MeCenterActivity.class);
                break;
        }
    }

    @Override
    public void onClick(int which) {

        switch (which) {
            // 微信
            case 0:
                wechatClick();
                shareDialog.close();
                break;

            // qq
            case 3:
                qqClick();
                shareDialog.close();
                break;
        }

    }
}
