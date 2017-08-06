package com.sinosafe.xb.manager.module.home.fragment;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.GongGaoBean;
import com.sinosafe.xb.manager.bean.WaitProcess;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.model.ClientModel;

import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import luo.library.base.base.BaseFragment;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.banner.CycleVpEntity;

/**
 * 类名称：   com.cnmobi.huaan.manager.fragment
 * 内容摘要： //首页片。
 * 修改备注：
 * 创建时间： 2017/6/3 0003
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public abstract class HomeBaseFragment extends BaseFragment {

    protected List<YeWuBean> yeWuBeens;

    //请求失败
    protected abstract void requestFail();
    //待受理业务请求成功回调
    protected abstract void checkTaskRequestSucess(List<YeWuBean> yeWuBeens);
    //待受理业务请求成功回调
    protected abstract void waitProcessRequestSucess(WaitProcess waitProcess);
    //banner
    protected abstract void bannerRequestSucess(List<CycleVpEntity> vpEntities);

    //公告
    protected abstract void gongGaoRequestSucess(List<GongGaoBean> gongGaoBeens);

    /**
     * 查询待受理业务
     */
    protected void checkTask(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/checkTask");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
        params.addQueryStringParameter("task_type",  "10");
        params.addQueryStringParameter("curPage",  "1");
        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.get(params, onResponseListener);

    }


    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            //closeSVProgressHUD();
            requestFail();
        }

        @Override
        public void onCommonRequestCallback(String result) {

            BaseEntity entity = GsonUtil.GsonToBean(result,BaseEntity.class);
            yeWuBeens = GsonUtil.GsonToList(entity.getResult().toString(),YeWuBean.class);
            checkTaskRequestSucess(yeWuBeens);
            setWaitProcessMap();
        }
    };


    /**
     * 设置查询代办事项总数的请求参数
     */
    protected void setWaitProcessMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
        map.put("belong_br_id", BaseMainActivity.loginUserBean.getOrgid());
        requestWaitProcess(map);
    }

    /**
     * 请求查询待办事项总数
     * @param filter
     */
    private void requestWaitProcess(Map<String,String> filter){

        ClientModel.waitProcess(filter)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<WaitProcess>>io_main())
            .map(new RxHttpResultFunc<WaitProcess>())
            .subscribe(new RxSubscriber<WaitProcess>() {
                @Override
                public void _onNext(WaitProcess waitProcess) {
                    requestFail();
                    waitProcessRequestSucess(waitProcess);
                }
                @Override
                public void _onError(String msg) {
                    //closeSVProgressHUD();
                    MyLog.e("msg===="+msg);
                    requestFail();
                }});
    }


    /**
     * 查询系统图片
     */
    protected void getSysPics(){

        ClientModel.getSysPics("4")
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<CycleVpEntity>>>io_main())
            .map(new RxHttpResultFunc<List<CycleVpEntity>>())
            .subscribe(new RxSubscriber<List<CycleVpEntity>>() {
                @Override
                public void _onNext(List<CycleVpEntity> vpEntities) {
                    getGongGaoList();
                    bannerRequestSucess(vpEntities);
                }
                @Override
                public void _onError(String msg) {
                    MyLog.e("getSysPics===="+msg);
                }});
    }


    /**
     * 获取公告列表
     */
    protected void getGongGaoList(){
        Map<String, String> maps = new HashMap<>();
        maps.put("type", "1");
        maps.put("curPage", "1");
        maps.put("pageSize", "10");
        ClientModel.getPostList(maps)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<GongGaoBean>>>io_main())
            .map(new RxHttpResultFunc<List<GongGaoBean>>())
            .subscribe(new RxSubscriber<List<GongGaoBean>>() {
                @Override
                public void _onNext(List<GongGaoBean> gongGaoBeens) {
                    gongGaoRequestSucess(gongGaoBeens);
                }
                @Override
                public void _onError(String msg) {
                    MyLog.e("获取公告异常：===="+msg);
                }});
    }
}
