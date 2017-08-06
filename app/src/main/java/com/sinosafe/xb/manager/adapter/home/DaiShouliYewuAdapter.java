package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.module.home.shouliyewu.DaiShouLiYewuActivity;
import com.sinosafe.xb.manager.utils.MyUtils;

import java.util.HashMap;
import java.util.Map;

import luo.library.base.base.BaseImage;
import luo.library.base.widget.MoneyView;
import luo.library.base.widget.dialog.DialogMessage;


/**待受理业务适配器
 */
public class DaiShouliYewuAdapter extends BaseRecyclerViewAdapter<YeWuBean, BaseRecyclerViewHolder> {

    private Context mContext;
    private int currentPosition = -1;

    public DaiShouliYewuAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, YeWuBean item) {

        MoneyView tvMoney = baseRecyclerViewHolder.getView(R.id.tvMoney);
        TextView tvHandle = baseRecyclerViewHolder.getView(R.id.tvHandle);
        ImageView ivImage = baseRecyclerViewHolder.getView(R.id.ivImage);
        TextView tvName = baseRecyclerViewHolder.getView(R.id.tvName);
        TextView tvDistance = baseRecyclerViewHolder.getView(R.id.tvDistance);
        TextView tvType = baseRecyclerViewHolder.getView(R.id.tvType);

        tvMoney.setMoneyText(MyUtils.keepTwoDecimal(item.getAMOUNT()));
        tvType.setText(item.getPRD_NAME());

        //距离
        MyUtils.setDistance(tvDistance,item.getCREDIT_COORDINATE());
        tvName.setText(item.getCUS_NAME());
        YeWuBean.User user = item.getUser();
        if(user!=null) {
            BaseImage.getInstance().displayCricleImage(mContext,
                    user.getHead_photo(), ivImage, R.mipmap.icon_default_avatar);
        }
        else{
            BaseImage.getInstance().displayCricleImage(mContext, "http://123.jpg",
                    ivImage,R.mipmap.icon_default_avatar);
        }



        tvHandle.setTag(position);
        tvHandle.setOnClickListener(listener);

    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.daishouli_yewu_item));
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            currentPosition = Integer.valueOf(v.getTag().toString());
            showBussinessAcceptDialog();
        }
    };


    /**
     * 受理前提示
     */
    public void showBussinessAcceptDialog(){

        new DialogMessage(mContext).setMess("您确定要受理该贷款事项吗?")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                        Map<String,String> map = new HashMap<>();
                        map.put("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
                        map.put("token", BaseMainActivity.loginUserBean.getToken());
                        map.put("serno", getDatas().get(currentPosition).getSERNO());

                        ((DaiShouLiYewuActivity)mContext).bussinessAccept(map,currentPosition);
                    }
                }).show();
    }

}