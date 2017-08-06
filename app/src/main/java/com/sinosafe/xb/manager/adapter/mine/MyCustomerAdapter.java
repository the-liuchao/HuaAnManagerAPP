package com.sinosafe.xb.manager.adapter.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.MyCustomerBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.mine.MyCustomerActivity;
import com.sinosafe.xb.manager.utils.T;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import luo.library.base.base.BaseImage;
import luo.library.base.widget.dialog.DialogMessage;


/**我的客户适配器
 */
public class MyCustomerAdapter extends BaseRecyclerViewAdapter<MyCustomerBean, BaseRecyclerViewHolder> {

    private Context mContext;
    private int currentPosition = -1;

    public MyCustomerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, MyCustomerBean item) {

        TextView tvName = baseRecyclerViewHolder.getView(R.id.tvName);
        TextView tvPhone = baseRecyclerViewHolder.getView(R.id.tvPhone);
        TextView tvHandle = baseRecyclerViewHolder.getView(R.id.tvHandle);
        ImageView ivImage = baseRecyclerViewHolder.getView(R.id.ivImage);

        tvName.setText(item.getCus_name());
        tvPhone.setText(item.getCus_phone());

        //未激活
        if(item.getCus_status().equals("0")){
            tvHandle.setTag(position);
            tvHandle.setOnClickListener(listener);
            tvHandle.setText("未激活");
            tvHandle.setTextColor(Color.parseColor("#FFFFFF"));
            tvHandle.setBackgroundResource(R.drawable.shape_blue_oval);
        }else{

            tvHandle.setOnClickListener(null);
            tvHandle.setText("已激活");
            tvHandle.setTextColor(Color.parseColor("#818394"));
            tvHandle.setBackgroundResource(R.drawable.shape_white_oval);
        }
        BaseImage.getInstance().displayCricleImage(mContext,item.getCus_photo(),ivImage,
                R.mipmap.icon_default_avatar);

    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.my_customer_item));
    }


    private View.OnClickListener listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            currentPosition = Integer.valueOf(v.getTag().toString());
            showActiviteCustomerDialog();

        }
    };


    /**
     * 激活提示
     */
    private void showActiviteCustomerDialog(){

        new DialogMessage(mContext).setMess("您确定要激活该客户吗?")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
            @Override
            public void onConfirm() {
                MyCustomerBean myCustomerBean = getDatas().get(currentPosition);
                Map<String,String> map = new HashMap<>();
                map.put("token", BaseMainActivity.loginUserBean.getToken());
                map.put("type", 1+"");
                map.put("cus_id", myCustomerBean.getCus_id()+"");
                map.put("cus_status", 1+"");
                ((MyCustomerActivity)mContext).addAndEditCustomerById(map);
            }
        }).show();
    }

    /**
     * 激活后，同步数据
     */
    public void synCustomerBeans(){
        getDatas().get(currentPosition).setCus_status(1+"");
        notifyDataSetChanged();
    }
}