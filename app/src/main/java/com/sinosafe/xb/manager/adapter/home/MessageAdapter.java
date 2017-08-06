package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.bean.MessageBean;


/**
 * 消息适配器
 */
public class MessageAdapter extends BaseRecyclerViewAdapter<MessageBean, BaseRecyclerViewHolder> {


    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, MessageBean item) {

        TextView tvOperator = baseRecyclerViewHolder.getView(R.id.tvOperator);
        TextView tvTitle = baseRecyclerViewHolder.getView(R.id.tvTitle);
        TextView tvMsgTime = baseRecyclerViewHolder.getView(R.id.tvMsgTime);
        TextView tvContent = baseRecyclerViewHolder.getView(R.id.tvContent);
        ImageView ivRead = baseRecyclerViewHolder.getView(R.id.ivRead);

        tvTitle.setText(item.getMess_title());
        tvContent.setText(item.getMess_content());
        tvMsgTime.setText(item.getCreate_time());
        tvOperator.setText("查看更多");

        //未读
        if(item.getIs_read()==0){
            ivRead.setImageResource(R.drawable.shape_red_oval360);
        }else{
            ivRead.setImageResource(R.drawable.shape_gray_oval2);
        }

    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.message_item));
    }




    public class ItemType{

        public final static int XIAO_FEI_DAI = 0; //消费贷

        public final static int MIAN_QIAN = 1;    //面签

        public final static int WEI_DAI = 2;      //微贷

    }
}