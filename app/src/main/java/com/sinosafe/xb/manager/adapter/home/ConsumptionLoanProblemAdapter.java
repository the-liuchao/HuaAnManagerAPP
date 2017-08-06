package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.bean.LoanProblemBean;


/**
 * 常见问题适配器
 */
public class ConsumptionLoanProblemAdapter extends BaseRecyclerViewAdapter<LoanProblemBean, BaseRecyclerViewHolder> {


    public ConsumptionLoanProblemAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, LoanProblemBean problem) {

        TextView mProblemNumber = baseRecyclerViewHolder.getView(R.id.tv_problem_number);
        TextView mProblemTitle = baseRecyclerViewHolder.getView(R.id.tv_problem_title);;
        TextView mProblemContent = baseRecyclerViewHolder.getView(R.id.tv_problem_content);;

        mProblemNumber.setText(problem.getProblem_id());
        mProblemTitle.setText(problem.getProblem_title());
        mProblemContent.setText(problem.getProblem_text());

    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.item_loan_common_problem));
    }


}