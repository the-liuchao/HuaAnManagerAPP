package com.sinosafe.xb.manager.module.yewu.weidai;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yewu.ProcessAdapter;
import com.sinosafe.xb.manager.module.yewu.xiaofeidai.LoanDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import luo.library.base.base.BaseFragment;


/**
 * 微贷审核进度片
 */
public class MicroCreditProgressFragment extends BaseFragment {


    private MicroCreditProgressFragment progressFragment;
    @BindView(R.id.rv_approval_process)
    RecyclerView mRvApprovalProcess;
    Unbinder unbinder;


    public MicroCreditProgressFragment newInstance() {

        if (progressFragment == null)
            progressFragment = new MicroCreditProgressFragment();
        return progressFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_progress, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    /**
     * 初始化
     */
    public void initView(List<LoanDetail.LoadSchedual> mDataSet) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ProcessAdapter adapter = new ProcessAdapter(mDataSet);
        mRvApprovalProcess.setLayoutManager(layoutManager);
        mRvApprovalProcess.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressFragment = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
