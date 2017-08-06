package com.sinosafe.xb.manager.module.home.loanmanager;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.home.RepaymentDetailAdapter;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragment;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.EmptyLayout;


/**
 * 还款明细
 */
public class RepaymentDetailsFragment extends BaseFragment {

    private static RepaymentDetailsFragment detailFragment;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mLayoutEmpty;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    private TextView mTvRepayMode;
    RepaymentDetailAdapter repaymentDetailAdapter;
    protected boolean isViewInitiated;
    //我的还款记录
    private List<MyRepayDetail> myRepayDetails = new ArrayList<>();

    public static RepaymentDetailsFragment newInstance() {
        if (detailFragment == null)
            detailFragment = new RepaymentDetailsFragment();
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repayment_history_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewInitiated = true;

        initView();
    }

    /**
     * 初始化
     */
    public void initView() {

        repaymentDetailAdapter = new RepaymentDetailAdapter(getActivity());
        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(repaymentDetailAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        View view = getActivity().getLayoutInflater().inflate(R.layout.repayment_mode,null);
        mTvRepayMode = (TextView) view.findViewById(R.id.tvRepayMode);
        mTvRepayMode.setText(MyUtils.getRepaymentModel(((PaymentHistoryActivity)getActivity()).getPayType()));
        mHeaderAndFooterRecyclerViewAdapter.addHeaderView(view);

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mLayoutEmpty.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading) {
                    return;
                }
                mLayoutEmpty.setErrorType(EmptyLayout.NETWORK_LOADING);
                mRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        MyLog.e("onHiddenChanged  RepaymentDetailsFragment =="+hidden);
        if (!hidden && isViewInitiated && repaymentDetailAdapter.getDatas().size()==0) {
            mRefreshLayout.setRefreshing(true);
            getData();
        }
    }

    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mRefreshLayout.setRefreshing(true);
            getData();
        }
    };


    private void getData(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/queryRepayDetail");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("serno",((PaymentHistoryActivity)getActivity()).getSerno());//JRZX2017010923117450
        params.addQueryStringParameter("prd_id",((PaymentHistoryActivity)getActivity()).getPrd_id());
        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.get(params, onResponseListener);
    }

    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            mRefreshLayout.setRefreshing(false);
            if(repaymentDetailAdapter.getDatas().size()==0)
                mLayoutEmpty.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public void onCommonRequestCallback(String result) {
            repaymentDetailAdapter.getDatas().clear();
            BaseEntity entity = GsonUtil.GsonToBean(result,BaseEntity.class);
            try {
                RepaymentDetails repaymentDetails = GsonUtil.GsonToBean(entity.getResult().toString(),RepaymentDetails.class);
                if(repaymentDetails!=null&&repaymentDetails.getAccMtdPlan()!=null){
                    handleRepaymentData(repaymentDetails);
                    String repayment_mode = repaymentDetails.getAccMtdPlan().get(0).getREPAYMENT_MODE();
                    mTvRepayMode.setText(MyUtils.getRepaymentModel(repayment_mode));
                }
            }catch (Exception e){
                MyLog.e("还款记录明细====="+e.getMessage()+"     "+entity.getMsg());
            }
            if(repaymentDetailAdapter.getDatas().size()==0){
                mLayoutEmpty.setErrorType(EmptyLayout.NODATA);
            }else{
                mLayoutEmpty.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
            mRefreshLayout.setRefreshing(false);
        }
    };


    /**
     * 重新封装还款记录数据
     * @param repaymentDetails
     */
    private void handleRepaymentData(RepaymentDetails repaymentDetails){

        myRepayDetails.clear();
        List<RepaymentDetails.RepaymentPlan> repaymentPlens = repaymentDetails.getAccMtdPlan();
        for(int i= 0;i<repaymentPlens.size();i++){


            RepaymentDetails.RepaymentPlan repaymentPlan = repaymentPlens.get(i);
            MyRepayDetail myRepayDetail = new MyRepayDetail();

            myRepayDetail.setOpenFlag(false);
            myRepayDetail.setIssueNumber(repaymentPlan.getPS_PERD_NO());
            myRepayDetail.setRepaymentTime(repaymentPlan.getPS_DUE_DT());

            //期号
            int perdNo = repaymentPlan.getPS_PERD_NO();
            myRepayDetail.setIssueNumber(perdNo);
            //对应的还款明细
            List<RepaymentDetails.RepaymentNote> repaymentNotes = getRepaymentNoteList(perdNo,repaymentDetails.getAccMtdPsNote());

            //时间早到晚排序
            sortClass sort = new sortClass();
            Collections.sort(repaymentNotes,sort);

            //是否逾期
            boolean hasPassTime = false;
            //应还金额(（本金+利息+应还罚息+应还复利）-（实还本金+实还利息+实还罚息+实还复利）】【实还的数据为明细表的实还记录)
            //实还金额（实还本金+实还利息+实还罚息+实还复利）
            float repayments = 0;
            for(int j=0;j<repaymentNotes.size();j++){
                RepaymentDetails.RepaymentNote repaymentNote = repaymentNotes.get(j);
                if("20".equals(repaymentNote.getABSTRACT())){
                    hasPassTime = true;
                }
                if("10".equals(repaymentNote.getABSTRACT())||"21".equals(repaymentNote.getABSTRACT()))
                    repayments += repaymentNote.getPS_REAL_PRCP_AMT()+repaymentNote.getPS_REAL_INT_AMT()+repaymentNote.getSETL_OD_INC_TAKEN()+repaymentNote.getSETL_COMM_OD_INT();
            }

            if(repaymentNotes!=null&&repaymentNotes.size()>0){
                myRepayDetail.setCanOpen(true);
            }else{
                myRepayDetail.setCanOpen(false);
            }

            //已还款金额
            String hasRepayStr = "已还款 "+ MyUtils.keepTwoDecimal(getRepayments(repaymentNotes));
            myRepayDetail.setHasRepayment(hasRepayStr);

            //未还金额
            float dueAmount = repaymentPlan.getOVER_AMOUNT();
            //dueAmount = repaymentPlan.getPS_PRCP_AMT()+repaymentPlan.getPS_NORM_INT_AMT()+repaymentPlan.getPS_OD_INT_AMT()+repaymentPlan.getPS_COMM_OD_INT();
            myRepayDetail.setDueAmount(MyUtils.keepTwoDecimal(dueAmount));//(MyUtils.keepTwoDecimal(dueAmount-repayments));

            //如果未还金额为0，则不显示
            /*if("0.00".equals(myRepayDetail.getDueAmount()))
                continue;*/

            //是否上报征信
            myRepayDetail.setIS_CREDIT_REPORT(repaymentPlan.getIS_CREDIT_REPORT());

            //明细   逾期  (("20".equals(repaymentPlan.getREPAY_FLAG())&&repaymentNotes.size()==0)||hasPassTime)
            if(dueAmount>0){
                String bottomStr = "本金 "+MyUtils.keepTwoDecimal(repaymentPlan.getPS_PRCP_AMT())+
                        " | 利息 "+MyUtils.keepTwoDecimal(repaymentPlan.getPS_NORM_INT_AMT())+
                        " | 罚息 "+MyUtils.keepTwoDecimal(repaymentPlan.getPS_OD_INT_AMT());

                myRepayDetail.setSurvey(bottomStr);

                String detailStr = "应还金额："+MyUtils.keepTwoDecimal(dueAmount)+"  "+
                        "实还金额: "+MyUtils.keepTwoDecimal(repayments)+"  \n"+
                        //"未还金额: "+MyUtils.keepTwoDecimal(dueAmount-repayments)+"\n"+
                        //"应还时间："+repaymentPlan.getPS_DUE_DT()+"\n"+
                        getExactTime(repaymentNotes,repaymentNotes.size()>1?1:0);

                myRepayDetail.setDetailed(detailStr);
                myRepayDetail.setRepayStatus("20");
            }
            else {
                String bottomStr = "本金 "+MyUtils.keepTwoDecimal(repaymentPlan.getPS_PRCP_AMT())+
                        " | 利息 "+MyUtils.keepTwoDecimal(repaymentPlan.getPS_NORM_INT_AMT());

                myRepayDetail.setSurvey(bottomStr);

                String detailStr = "应还金额: "+MyUtils.keepTwoDecimal(dueAmount)+"  "+
                        "实还金额: "+MyUtils.keepTwoDecimal(repayments)+"\n"+
                        //"应还时间："+repaymentPlan.getPS_DUE_DT()+"\n"+
                        getExactTime(repaymentNotes,repaymentNotes.size()>1?1:0);

                myRepayDetail.setDetailed(detailStr);
                myRepayDetail.setRepayStatus(repaymentPlan.getREPAY_FLAG()+"");
            }

            myRepayDetails.add(myRepayDetail);
        }
        repaymentDetailAdapter.update(myRepayDetails);
    }


    //通过期号获取对应的明细列表
    private List<RepaymentDetails.RepaymentNote> getRepaymentNoteList(int perdNo ,List<RepaymentDetails.RepaymentNote> noteList){
        List<RepaymentDetails.RepaymentNote> repaymentNotes = new ArrayList<>();
        for(int i=0;i<noteList.size();i++){
            if(noteList.get(i).getPS_PERD_NO()==perdNo){
                repaymentNotes.add(noteList.get(i));
            }
        }
        return repaymentNotes;
    }


    //已还款金额
    private float getRepayments(List<RepaymentDetails.RepaymentNote> repaymentNotes){
        float repayments = 0;
        for(int i=0;i<repaymentNotes.size();i++){
            RepaymentDetails.RepaymentNote repaymentNote = repaymentNotes.get(i);
            repayments += repaymentNote.getPS_REAL_PRCP_AMT()+repaymentNote.getPS_REAL_INT_AMT()+repaymentNote.getSETL_OD_INC_TAKEN()+repaymentNote.getSETL_COMM_OD_INT();
        }
        return repayments;
    }


    //实还时间明细
    private String getExactTime(List<RepaymentDetails.RepaymentNote> repaymentNotes,int type){
        String exactTimeStr ="";
        for(int i=0;i<repaymentNotes.size();i++){
            RepaymentDetails.RepaymentNote repaymentNote = repaymentNotes.get(i);
            //if(type==1) {
                if(i==repaymentNotes.size()-1)
                    exactTimeStr += "实还时间：" + repaymentNote.getPS_REAL_DT() +
                            " (" + MyUtils.keepTwoDecimal(getRepayments(repaymentNote)) + ") "+getABSTRACT(repaymentNote.getABSTRACT());
                else{
                    exactTimeStr += "实还时间：" + repaymentNote.getPS_REAL_DT() +
                            " (" + MyUtils.keepTwoDecimal(getRepayments(repaymentNote)) + ") "+getABSTRACT(repaymentNote.getABSTRACT())+"\n";
                }

           /* }else{
                if(i==repaymentNotes.size()-1)
                    exactTimeStr += "实还时间："+repaymentNote.getPS_REAL_DT()+"";
                else{
                    exactTimeStr += "实还时间："+repaymentNote.getPS_REAL_DT()+"\n";
                }
            }*/
        }
        return exactTimeStr;
    }

    //明细：已还款金额
    private float getRepayments(RepaymentDetails.RepaymentNote repaymentNote){
        float repayments = 0;
        repayments += repaymentNote.getPS_REAL_PRCP_AMT()+repaymentNote.getPS_REAL_INT_AMT()+repaymentNote.getSETL_OD_INC_TAKEN()+repaymentNote.getSETL_COMM_OD_INT();
        return repayments;
    }

    //明细摘要状态
    private String getABSTRACT(String abStract){

        /**
         * 10	正常
         20	理赔
         21	超期
         30	代垫
         40	买断
         */
        if(abStract.equals("10"))
            return "正常";
        else if(abStract.equals("20"))
            return "理赔";
        else if(abStract.equals("21")){
            return "超期";
        }
        else if(abStract.equals("30")){
            return "代垫";
        }else{
            return "买断";
        }
    }

    public class sortClass implements Comparator {
        public int compare(Object arg0,Object arg1){
            RepaymentDetails.RepaymentNote user0 = (RepaymentDetails.RepaymentNote)arg0;
            RepaymentDetails.RepaymentNote user1 = (RepaymentDetails.RepaymentNote)arg1;
            int flag = user0.getPS_REAL_DT().compareTo(user1.getPS_REAL_DT());
            return flag;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detailFragment = null;
    }

}
