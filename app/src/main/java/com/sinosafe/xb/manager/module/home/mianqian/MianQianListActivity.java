package com.sinosafe.xb.manager.module.home.mianqian;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.megvii.livenesslib.LivenessActivity;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.OnItemClickListener;
import com.sinosafe.xb.manager.adapter.base.OnItemLongClickListener;
import com.sinosafe.xb.manager.adapter.yewu.YeWuAdapter;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.VerifyResultBean;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyFaceNetWorkWarranty;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyEasyPermissions;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;
import luo.library.base.widget.dialog.DialogMessage;
import pub.devrel.easypermissions.EasyPermissions;

import static luo.library.base.utils.GsonUtil.GsonToBean;

/**
 * 面签业务列表
 */
public class MianQianListActivity extends BaseFragmentActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;


    private YeWuAdapter yeWuAdapter;
    private MianQianListActivity instance;
    private int pageNum = 1;
    private static final int REQUEST_CODE = 1003;
    private YeWuBean yeWuBean;
    private MyFaceNetWorkWarranty myFaceNetWorkWarranty;
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mianqian_list);
        instance = this;

        ButterKnife.bind(this);
        initView();

        mRefreshLayout.setRefreshing(true);
        getData();
    }

    /**
     * 初始化
     */
    private void initView() {

        setTitleText("面签列表");
        myFaceNetWorkWarranty = new MyFaceNetWorkWarranty();
        yeWuAdapter = new YeWuAdapter(this,8);

        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(yeWuAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        yeWuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("yeWuBean",yeWuAdapter.getDatas().get(position));
                IntentUtil.gotoActivity(instance,MianQianDetailActivity.class,bundle);
            }
        });
        yeWuAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position, Object item) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(yeWuAdapter.getDatas().get(position).getSERNO());
                T.showShortBottom(yeWuAdapter.getDatas().get(position).getSERNO());
            }
        });

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading) {
                    return;
                }
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }


    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            pageNum = 1;
            getData();
        }
    };

    /**
     * RecycleView的滑动监听(加载更多)
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if(RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading) {
                return;
            }
            pageNum++;
            if(NetworkUtils.isNetAvailable(instance)){ //有网状态
                if(!RecyclerViewStateUtils.setFooterViewState(instance, mRecyclerView,
                        Constant.REQUEST_COUNT, LoadingFooter.State.Loading, null)){
                    pageNum --;
                    return;
                }
                getData();
            } else{
                RecyclerViewStateUtils.setFooterViewState(instance, mRecyclerView,
                        Constant.REQUEST_COUNT, LoadingFooter.State.NetWorkError, mFooterClick);
            }
        }
    };

    /**
     * 联网失败后点击重新加载事件
     */
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getData();
        }
    };



    /**
     * 查询待受理业务
     */
    protected void getData(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/checkTask");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
        params.addQueryStringParameter("task_type",  "20");
        params.addQueryStringParameter("curPage",  pageNum+"");
        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.get(params, onResponseListener);

    }


    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            if(pageNum==1){
                mRefreshLayout.setRefreshing(false);
            }
            if(yeWuAdapter.getDatas().size()==0)
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public void onCommonRequestCallback(String result) {
            if(pageNum==1){
                mRefreshLayout.setRefreshing(false);
                yeWuAdapter.getDatas().clear();
            }
            BaseEntity entity = GsonToBean(result,BaseEntity.class);
            List<YeWuBean> yeWuBeens = GsonUtil.GsonToList(entity.getResult().toString(),YeWuBean.class);
            if(yeWuBeens!=null){
                MyUtils.TheEndOrNomal(yeWuBeens,mRecyclerView);
                yeWuAdapter.update(yeWuBeens);
            }
            if(yeWuAdapter.getDatas().size()==0){
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            }else{
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }
    };


    /**
     * 点击办理面签
     */
    public void handleFacesSignedClicked(int currentPosition) {

        this.currentPosition = currentPosition;
        //判断是否授权成功
        if(!MyFaceNetWorkWarranty.isFaceNetWorkWarranty()){
            showFaceNetWorkWarrantyDialog();
            return;
        }

       /* if (!MyEasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            MyLog.e("没有读取文件权限，需要读取文件权限");
            EasyPermissions.requestPermissions(this ,"需要读取手机存储权限", 0x04, Manifest.permission.READ_EXTERNAL_STORAGE);
            return;
        }*/

        certCode = yeWuAdapter.getDatas().get(currentPosition).getCERT_CODE();
        cusName = yeWuAdapter.getDatas().get(currentPosition).getCUS_NAME();
        yeWuBean = yeWuAdapter.getDatas().get(currentPosition);
        if (!MyEasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            MyLog.e("没有相机权限，需要相机权限");
            EasyPermissions.requestPermissions(this ,"需要相机权限", 0x03, Manifest.permission.CAMERA);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("yeWuBean",yeWuBean);
        //IntentUtil.gotoActivityForResult(instance,BanLiMianQianActivity.class,bundle,REQUEST_CODE);
        IntentUtil.gotoActivityForResult(this, LivenessActivity.class,PAGE_INTO_LIVENESS);
    }

    /**
     * 授权失败提示
     */
    private void showFaceNetWorkWarrantyDialog(){
        new DialogMessage(this).setMess("人脸识别联网授权失败，请重新授权!")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        faceNetGrant();
                    }
                }).show();
    }

    /**
     * 联网授权
     */
    private void faceNetGrant(){
        startProgressDialog("授权中...");
        myFaceNetWorkWarranty.faceNetWorkWarranty(new MyFaceNetWorkWarranty.NetWorkWarrantyCallback() {
            @Override
            public void grantResult(boolean result) {
                stopProgressDialog();
                if(result){
                    handleFacesSignedClicked(currentPosition);
                }else{
                    showFaceNetWorkWarrantyDialog();
                }
            }
        });
    }


    private static final int PAGE_INTO_LIVENESS = 200;
    private String certCode;
    private String cusName;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAGE_INTO_LIVENESS && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            showWithStatus("请稍候");
            faceResult(result);
        }
    }


    /**
     * 人脸识别结果处理
     * @param jsonResult
     */
    private void faceResult(String jsonResult) {
        try {
            JSONObject result = new JSONObject(jsonResult);
            int resID = result.getInt("resultcode");
            String filePath = result.getString("filePath");
            String delta = result.getString("delta");

            if (resID == R.string.verify_success) {
                File file = new File(filePath);
                if (file == null && !file.exists()) {
                    closeSVProgressHUD();
                    T.showShortBottom("人脸识别失败");
                    return;
                }
                RequestParams params = new RequestParams("https://api.megvii.com/faceid/v2/verify");
                params.addBodyParameter("api_key", "InKdhB0QTKRbUXKDZgYBkyxs2TssltsN");
                params.addBodyParameter("api_secret", "7z4JdvTKRrj82oLRJ0ov_LFx1cWTS4kJ");
                params.addBodyParameter("idcard_name",  yeWuBean.getCUS_NAME());
                params.addBodyParameter("idcard_number",  yeWuBean.getCERT_CODE().trim());
                params.addBodyParameter("comparison_type",  "1");
                params.addBodyParameter("face_image_type",  "meglive");
                params.addBodyParameter("delta",  delta);
                params.addBodyParameter("image_best",  file);
                XutilsBaseHttp.requestType = 1;
                XutilsBaseHttp.post(params, onResponseListener2);
            } else if (resID == R.string.liveness_detection_failed_not_video ||
                    resID == R.string.liveness_detection_failed_timeout ||
                    resID == R.string.liveness_detection_failed) {
                T.showShortBottom(result.getString("result"));
            }

        } catch (Exception e) {
            T.showShortBottom("人脸识别失败");
            closeSVProgressHUD();
            e.printStackTrace();
        }
    }

    /**
     * 人脸识别请求
     */
    private OnResponseListener onResponseListener2 = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            closeSVProgressHUD();
            MyLog.e("人脸识别结果：===============var1"+var1);
            T.showShortBottom("人脸识别失败");
            /*Bundle bundle = new Bundle();
            bundle.putSerializable("yeWuBean",yeWuBean);
            IntentUtil.gotoActivityForResult(instance,BanLiMianQianActivity.class,bundle,REQUEST_CODE);*/
        }
        @Override
        public void onFaceSuccessListCallback(String result) {
            MyLog.e("人脸识别结果：===============result"+result);
            closeSVProgressHUD();
            VerifyResultBean verifyResultBean = GsonToBean(result, VerifyResultBean.class);
            double e_6 = 79.9;
            double confidence = verifyResultBean.getResult_faceid().getConfidence();
            if (confidence >= e_6) {
                T.showShortBottom("人脸识别成功");
                mHandler.sendEmptyMessageDelayed(0,250);
            } else{
                T.showShortBottom("人脸识别失败");
            }
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = new Bundle();
            bundle.putSerializable("yeWuBean",yeWuBean);
            IntentUtil.gotoActivityForResult(instance,BanLiMianQianActivity.class,bundle,REQUEST_CODE);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        myFaceNetWorkWarranty.myDestory();
        instance = null;
    }
}
