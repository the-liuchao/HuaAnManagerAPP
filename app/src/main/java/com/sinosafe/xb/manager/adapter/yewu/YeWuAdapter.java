package com.sinosafe.xb.manager.adapter.yewu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.module.BaseWebViewActivity;
import com.sinosafe.xb.manager.module.home.mianqian.MianQianDetailActivity;
import com.sinosafe.xb.manager.module.home.mianqian.MianQianListActivity;
import com.sinosafe.xb.manager.module.home.supplyInfo.ImageSelectActivity2;
import com.sinosafe.xb.manager.module.home.supplyInfo.weidai.ApplyWeiDataActivity;
import com.sinosafe.xb.manager.module.home.supplyInfo.xiaofeidai.ApplyXiaoDataActivity;
import com.sinosafe.xb.manager.module.yewu.weidai.MicroCreditDetailActivity;
import com.sinosafe.xb.manager.module.yewu.xiaofeidai.ConsumeDetailActivity;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.widget.DialogPowerOfAttorney;

import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.MoneyView;

/**
 * 业务适配器
 */
public class YeWuAdapter extends BaseRecyclerViewAdapter<YeWuBean, BaseRecyclerViewHolder> implements View.OnClickListener{
    //5.补充资料             6.缴费     7.贷后管理
    //0:申请中；1:审批中；2:待放款;3:还款中；4:已拒绝；8:面签；9：不需要面签列表--在线签投保单
    private int type = -1;
    private Context mContext;
    private int currentPosition = -1;

    //电子签署
    private DialogPowerOfAttorney dialogPowerOfAttorney;
    private static final int ELECTRONIC_SIGNA = 400;
    YeWuBean currentYeWuBean;

    public int getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public YeWuAdapter(Context context, int type) {
        super(context);
        this.mContext = context;
        this.type = type;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, YeWuBean item) {

        MoneyView tvMoney = baseRecyclerViewHolder.getView(R.id.tvMoney);
        TextView tvStatus = baseRecyclerViewHolder.getView(R.id.tvStatus);
        TextView tvPhone = baseRecyclerViewHolder.getView(R.id.tvPhone);
        ImageView ivImage = baseRecyclerViewHolder.getView(R.id.ivImage);
        TextView tvName = baseRecyclerViewHolder.getView(R.id.tvName);
        TextView tvTime = baseRecyclerViewHolder.getView(R.id.tvTime);
        TextView tvType = baseRecyclerViewHolder.getView(R.id.tvType);
        TextView tvLoanStatus = baseRecyclerViewHolder.getView(R.id.tvLoanStatus);

        tvMoney.setMoneyText(MyUtils.keepTwoDecimal(item.getAMOUNT()));
        tvType.setText(item.getPRD_NAME());
        tvTime.setText(item.getAPPLY_DATE());
        tvName.setText(item.getCUS_NAME());
        YeWuBean.User user = item.getUser();
        tvPhone.setText(Html.fromHtml("<u>"+item.getPHONE()+"</u>"));
        if(user!=null) {
            BaseImage.getInstance().displayCricleImage(mContext,
                    user.getHead_photo(), ivImage, R.mipmap.icon_default_avatar);
        }
        else{
            BaseImage.getInstance().displayCricleImage(mContext, "http://123.jpg",
                    ivImage,R.mipmap.icon_default_avatar);
        }
        tvStatus.setTag(position);
        tvStatus.setOnClickListener(this);
        tvPhone.setTag(position);
        tvPhone.setOnClickListener(listener);
        //申请中
        if(type==0){
            tvStatus.setText("上传影像资料");
        }
        //审批中--查看详情，面签(打印投保单)，补充资料
        else if(type==1){

            //待面签
            if("112".equals(item.getNEW_APPROVE_STATUS())){
                tvStatus.setText("办理面签");
                tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                tvStatus.setTextColor(Color.parseColor("#FFFFFF"));
            }
            //补充资料
            else if("090".equals(item.getNEW_APPROVE_STATUS())){
                tvStatus.setText("补充资料");
                tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                tvStatus.setTextColor(Color.parseColor("#FFFFFF"));
            }
            //其他状态
            else{
                tvStatus.setBackgroundResource(R.drawable.shape_white_oval);
                tvStatus.setText("查看详情");
                tvStatus.setTextColor(Color.rgb(40,41,50));
            }
        }
        //待放款---查看详情，缴费，在线签署投保单
        else if(type==2){

            //待缴费
            if("995".equals(item.getNEW_APPROVE_STATUS())){
                tvStatus.setText("待缴费");
                tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                tvStatus.setTextColor(Color.parseColor("#FFFFFF"));
            }
            //在线签投保单
            else if("091".equals(item.getNEW_APPROVE_STATUS())){
                tvStatus.setText("在线签投保单");
                tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                tvStatus.setTextColor(Color.parseColor("#FFFFFF"));
            }
            //其他状态
            else{
                tvStatus.setBackgroundResource(R.drawable.shape_white_oval);
                tvStatus.setText("查看详情");
                tvStatus.setTextColor(Color.rgb(40,41,50));
            }

        }
        //还款中
        else if(type==3){

            if(item.getACCOUNT_STATUS()!=null&&"4".equals(item.getACCOUNT_STATUS())){
                tvLoanStatus.setVisibility(View.VISIBLE);
            }else{
                tvLoanStatus.setVisibility(View.GONE);
            }
            //待上传
            if("1".equals(item.getIS_LOAN_CHECK())){
                tvStatus.setText("收集贷后凭证");
                tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                tvStatus.setTextColor(Color.parseColor("#FFFFFF"));
            }else{
                tvStatus.setBackgroundResource(R.drawable.shape_white_oval);
                tvStatus.setText("查看详情");
                tvStatus.setTextColor(Color.rgb(40,41,50));
            }
        }
        //已拒绝
        else if(type==4){

            tvStatus.setBackgroundResource(R.drawable.shape_white_oval);
            tvStatus.setText("查看详情");
            tvStatus.setTextColor(Color.rgb(40,41,50));
        }
        //补充资料
       else if(type==5){
            tvStatus.setText("上传影像资料");
        }

        //缴费
        else if(type==6){

            tvStatus.setText("去缴费");

        }
        //贷后管理
        else if(type==7){
            tvStatus.setText("收集贷后凭证");

        }//面签
        else if(type==8){

            tvStatus.setText("办理面签");
        }
        else if(type==9){
            tvStatus.setText("在线签投保单");
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.yewu_content_item));
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentPosition = Integer.valueOf(v.getTag().toString());
            ((BaseFragmentActivity)mContext).callPhoneTips(getDatas().get(currentPosition).getPHONE());
        }
    };

    @Override
    public void onClick(View v) {
        currentPosition = Integer.valueOf(v.getTag().toString());
        Bundle bundle = new Bundle();
        YeWuBean yeWuBean = getDatas().get(currentPosition);
        switch (type){
            //上传影像资料
            case 0:
                break;
            //审批中
            case 1:
                //待面签
                if("112".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                    bundle.putSerializable("yeWuBean",yeWuBean);
                    IntentUtil.gotoActivity(mContext,MianQianDetailActivity.class,bundle);
                }
                //补充资料
                else if("090".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                    bundle.putInt("type",5);
                    bundle.putString("serno",yeWuBean.getSERNO());
                    bundle.putString("prdId",yeWuBean.getPRD_ID());
                    String prdType = yeWuBean.getPRD_TYPE();
                    if("2".equals(prdType))
                        IntentUtil.gotoActivityForResult(mContext,MicroCreditDetailActivity.class,bundle,10000);
                    else
                        IntentUtil.gotoActivityForResult(mContext,ConsumeDetailActivity.class,bundle,10000);
                    //IntentUtil.gotoActivityForResult(mContext,ConsumeDetailActivity.class,bundle,10000);
                }
                //其他状态
                else{
                    bundle.putInt("type",1);
                    bundle.putString("serno",yeWuBean.getSERNO());
                    bundle.putString("prdId",yeWuBean.getPRD_ID());
                    String prdType = yeWuBean.getPRD_TYPE();
                    if("2".equals(prdType))
                        IntentUtil.gotoActivity(mContext,MicroCreditDetailActivity.class,bundle);
                    else
                        IntentUtil.gotoActivity(mContext,ConsumeDetailActivity.class,bundle);
                    //IntentUtil.gotoActivity(mContext,ConsumeDetailActivity.class,bundle);
                }
                break;
            //打印投保单/待放款
            case 2:
                bundle.putString("serno",yeWuBean.getSERNO());
                bundle.putString("prdId",yeWuBean.getPRD_ID());
                bundle.putInt("type",2);
                String prdType = yeWuBean.getPRD_TYPE();
                if("2".equals(prdType))
                    IntentUtil.gotoActivityForResult(mContext,MicroCreditDetailActivity.class,bundle,10000);
                else
                    IntentUtil.gotoActivityForResult(mContext,ConsumeDetailActivity.class,bundle,10000);
                //IntentUtil.gotoActivityForResult(mContext,ConsumeDetailActivity.class,bundle,10000);
                break;
            //待缴费
            case 6:
                bundle.putString("serno",yeWuBean.getSERNO());
                IntentUtil.gotoActivityForResult(mContext, BaseWebViewActivity.class,bundle,10000);
                break;
            //还款中收集贷后凭证
            case 3:
                if("1".equals(yeWuBean.getIS_LOAN_CHECK())){
                    bundle.putInt("type",1);
                    bundle.putInt("serviceType",1);
                    bundle.putString("title","收集贷后凭证");
                    bundle.putString("prdType",getDatas().get(currentPosition).getPRD_TYPE());
                    bundle.putString("serno",getDatas().get(currentPosition).getSERNO());
                    IntentUtil.gotoActivityForResult(mContext,ImageSelectActivity2.class,bundle,10000);
                }else{
                    bundle.putString("serno",yeWuBean.getSERNO());
                    bundle.putString("prdId",yeWuBean.getPRD_ID());
                    bundle.putInt("type",type);
                    prdType = yeWuBean.getPRD_TYPE();
                    if("2".equals(prdType))
                        IntentUtil.gotoActivity(mContext,MicroCreditDetailActivity.class,bundle);
                    else
                        IntentUtil.gotoActivity(mContext,ConsumeDetailActivity.class,bundle);
                }
                break;
            //贷后管理收集贷后凭证
            case 7:
                bundle.putInt("type",1);
                bundle.putString("title","收集贷后凭证");
                bundle.putString("prdType",yeWuBean.getPRD_TYPE());
                bundle.putString("serno",yeWuBean.getSERNO());
                IntentUtil.gotoActivityForResult(mContext,ImageSelectActivity2.class,bundle,10000);
                break;
            //已拒绝
            case 4:
                bundle.putString("serno",yeWuBean.getSERNO());
                bundle.putString("prdId",yeWuBean.getPRD_ID());
                bundle.putInt("type",4);
                String prdType1 = yeWuBean.getPRD_TYPE();
                if("2".equals(prdType1))
                    IntentUtil.gotoActivity(mContext,MicroCreditDetailActivity.class,bundle);
                else
                    IntentUtil.gotoActivity(mContext,ConsumeDetailActivity.class,bundle);
                //IntentUtil.gotoActivity(mContext,ConsumeDetailActivity.class,bundle);
                break;
            //补充资料
            case 5:
                if(yeWuBean.getPRD_TYPE()==null){
                    bundle.putString("serno",yeWuBean.getSERNO());
                    IntentUtil.gotoActivityForResult(mContext,ApplyXiaoDataActivity.class,bundle,10000);
                    return;
                }
                if(yeWuBean.getPRD_TYPE().equals("0")){
                    bundle.putString("serno",yeWuBean.getSERNO());
                    IntentUtil.gotoActivityForResult(mContext,ApplyXiaoDataActivity.class,bundle,10000);
                }else {
                    bundle.putString("serno",yeWuBean.getSERNO());
                    IntentUtil.gotoActivityForResult(mContext,ApplyWeiDataActivity.class,bundle,10000);
                }
                break;

            //办理面签
            case 8:

                ((MianQianListActivity)mContext).handleFacesSignedClicked(currentPosition);
                break;

            //在线签投保单
            case 9:
                currentYeWuBean = getDatas().get(currentPosition);
                //showSignatureAuthorization();
                bundle.putString("serno",currentYeWuBean.getSERNO());
                bundle.putString("prdId",currentYeWuBean.getPRD_ID());
                bundle.putInt("type",9);
                String prdType2 = currentYeWuBean.getPRD_TYPE();
                if("2".equals(prdType2))
                    IntentUtil.gotoActivityForResult(mContext,MicroCreditDetailActivity.class,bundle,10000);
                else
                    IntentUtil.gotoActivityForResult(mContext,ConsumeDetailActivity.class,bundle,10000);
                //IntentUtil.gotoActivityForResult(mContext,ConsumeDetailActivity.class,bundle,10000);
                break;
        }
    }
}