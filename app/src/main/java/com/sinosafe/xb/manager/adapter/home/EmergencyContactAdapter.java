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
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.bean.WheelViewBean;
import luo.library.base.widget.dialog.DialogAddContact;
import luo.library.base.widget.dialog.DialogMessage;
import luo.library.base.widget.dialog.DialogWheelView;

import static com.sinosafe.xb.manager.utils.T.showShortBottom;

/**
 * 紧急联系人
 * Created by john lee on 2017/5/6.
 */

public class EmergencyContactAdapter extends BaseRecyclerViewAdapter<LoanUserInfo.UserContactor, BaseRecyclerViewHolder>
        implements View.OnClickListener{

    private Context context;
    private DialogMessage dialogMessage;
    private int currentPosition;
    private List<WheelViewBean> relationShips;
    private DialogAddContact dialogAddContact;
    private DialogWheelView dialogWheelView;
    private List<LoanUserInfo.UserContactor> contacts;
    private String cus_id;

    LoanUserInfo.UserContactor contactBean;
    private String con_relation;
    /**
     * @param context
     */
    public EmergencyContactAdapter(Context context,List<LoanUserInfo.UserContactor> contacts,String cus_id) {
        super(context);
        this.contacts = contacts;
        this.context = context;
        this.cus_id = cus_id;
        relationShips = new ArrayList<>();
        initRelationShips();
    }

    public List<WheelViewBean> getRelationShips() {
        return relationShips;
    }


    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, LoanUserInfo.UserContactor item) {

        //添加
        if (item != null && item.getCon_id()==0){
            baseRecyclerViewHolder.getView(R.id.addContactLayout).setOnClickListener(this);
        }
        //其他
        else{
            //删除紧急联系人
            ImageView ivDelete = baseRecyclerViewHolder.getView(R.id.iv_delete);
            ivDelete.setTag(position);
            ivDelete.setOnClickListener(this);

            //修改关系
            View relationship = baseRecyclerViewHolder.getView(R.id.ll_relationship);
            relationship.setTag(position);
            relationship.setOnClickListener(this);

            TextView tv_name = baseRecyclerViewHolder.getView(R.id.tv_name);
            TextView tv_phone = baseRecyclerViewHolder.getView(R.id.tv_phone);
            TextView tv_relationship = baseRecyclerViewHolder.getView(R.id.tv_relationship);

            tv_name.setText(item.getCon_name());
            tv_phone.setText(item.getCon_mobile());
            tv_relationship.setText(getRelationName(item.getCon_relation()));
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ItemType.ADD)
            return new BaseRecyclerViewHolder(inflateItemView(parent,R.layout.item_emergency_add_contact));
        else if (viewType == ItemType.NORMAL)
            return new BaseRecyclerViewHolder(inflateItemView(parent,R.layout.item_emergency_contact));
        return null;
    }


    @Override
    public int getItemViewType(int position) {

        LoanUserInfo.UserContactor  contact = getItem(position);
        if (contact != null && contact.getCon_id()==0)
            return ItemType.ADD;
        else
            return ItemType.NORMAL;
    }

    /**
     * 添加紧急联系人
     */
    public void showDialogAddContact(String name,String phone){
        if(dialogAddContact==null){
            dialogAddContact = new DialogAddContact(context, new DialogAddContact.OnTextBackListener() {
                @Override
                public void onTextBack(String name, String phone) {
                    if("".equals(name)||name==null){
                        showShortBottom("请输入姓名");
                        return;
                    }
                    if (!MyUtils.isRealName(name)) {
                        T.showShortBottom("请正确填写联系人中文姓名！");
                        return;
                    }else{
                        //判断是否重复
                        if(isExitContact(name,0)){
                            showShortBottom("该联系人已存在");
                            return;
                        }
                    }
                    if("".equals(phone)||phone==null){
                        showShortBottom("请输入电话");
                        return;
                    }else{
                        if(!MyUtils.isPhone(phone)){
                            showShortBottom("电话格式错误");
                            return;
                        }
                        //判断是否重复
                        if(isExitContact(phone,1)){
                            showShortBottom("该联系方式已存在");
                            return;
                        }

                    }
                    dialogAddContact.dismiss();
                    contactBean = new  LoanUserInfo.UserContactor ();
                    contactBean.setCon_name(name);
                    contactBean.setCon_mobile(phone);
                    setContactorInfoMap(contactBean);
                }
            });
        }

        dialogAddContact.setContent(name,phone);
        dialogAddContact.show();
    }

    /**
     * 删除提示窗口
     */
    private void showDeleContactDialog(){
        if(dialogMessage==null){
            dialogMessage = new DialogMessage(context);
            dialogMessage.setTitle("提示");
            dialogMessage.setMess("确定要删除此联系人吗?");
            dialogMessage.setConfirmListener(new DialogMessage.OnConfirmListener() {
                @Override
                public void onConfirm() {
                    deleteEmergencyContact(contacts.get(currentPosition).getCon_id()+"");
                }
            });
        }
        dialogMessage.show();
    }

    /**
     * 联系人是否重复
     * @param phoneOrName
     * @return
     */
    private boolean isExitContact(String phoneOrName,int type){

        for(int i=0;i<contacts.size();i++){
            LoanUserInfo.UserContactor contact = contacts.get(i);
            if(type==0){
                if(contact.getCon_id()!=0&&(contact.getCon_name().equals(phoneOrName)))
                    return true;
            }
            else if(type==1){
                if(contact.getCon_id()!=0&&(contact.getCon_mobile().equals(phoneOrName)))
                    return true;
            }
        }
        return false;
    }


    /**
     * 修改与紧急联系人关系
     */
    private void showDialogWheelView(){

        if(dialogWheelView==null){
            dialogWheelView = new DialogWheelView(context, relationShips, new DialogWheelView.OnConfirmListener() {
                @Override
                public void onConfirm(WheelViewBean item) {
                    contactBean = contacts.get(currentPosition);
                    con_relation = item.getType();
                    setContactorInfoMap(contactBean);
                }
            });
        }
        dialogWheelView.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //添加紧急联系人
            case R.id.addContactLayout:
                //((BaseFragmentActivity)context).readContacts();
                showDialogAddContact("","");
                break;
            //删除紧急联系人
            case R.id.iv_delete:
                currentPosition = Integer.valueOf(v.getTag().toString());
                showDeleContactDialog();
                break;

            case R.id.ll_relationship:
                currentPosition = Integer.valueOf(v.getTag().toString());
                showDialogWheelView();
                break;
        }
    }


    public class ItemType {
        public final static int NORMAL = 0;
        public final static int ADD = 1;
    }

    /**
     * 关系列表
     *
     * @return
     */
    public void initRelationShips() {
        relationShips.add(new WheelViewBean("1", "配偶"));
        relationShips.add(new WheelViewBean("2", "父母"));
        relationShips.add(new WheelViewBean("3", "子女"));
        relationShips.add(new WheelViewBean("7", "合伙人"));
        relationShips.add(new WheelViewBean("8", "其他关系"));
        relationShips.add(new WheelViewBean("9", "联系人"));
    }

    /**
     * 关系名称
     *
     * @param relation
     * @return
     */
    private String getRelationName(String relation) {
        String name = "";
        for (WheelViewBean relationShip : relationShips) {
            if (relationShip.getType().equals(relation))
                name = relationShip.getName();
        }
        return name;
    }


    private void setContactorInfoMap(LoanUserInfo.UserContactor contactBean){

        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        if(contactBean.getCon_id()!=0) {
            map.put("con_id", contactBean.getCon_id()+"");
            map.put("con_relation",con_relation);
            ((BaseFragmentActivity)context).showWithStatus("修改中...");
        }else {
            map.put("con_relation","2");
            ((BaseFragmentActivity)context).showWithStatus("添加中...");
        }
        map.put("con_name",contactBean.getCon_name());
        map.put("con_mobile",contactBean.getCon_mobile());
        map.put("cus_id",cus_id);
        saveOrEditContactor(map);
    }

    /**
     * 添加、修改紧急联系人
     */
    private void saveOrEditContactor(Map<String,String> map ){
        ClientModel.saveOrEditContactor(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    ((BaseFragmentActivity)context).closeSVProgressHUD();
                    synLocalCustomerInfo(baseEntity.getResult().toString());
                }
                @Override
                public void _onError(String msg) {
                    ((BaseFragmentActivity)context).closeSVProgressHUD();
                    showShortBottom("操作失败");
                }});
    }

    //同步数据内容
    private void synLocalCustomerInfo(String baseInfo){
        //修改
        if(contactBean.getCon_id()!=0) {
            showShortBottom("修改成功");
            contactBean.setCon_relation(con_relation);
            getDatas().clear();
            update(contacts);
        }
        //添加
        else {
            try {
                JSONObject object = new JSONObject(baseInfo);
                contactBean.setCon_id(object.getInt("con_id"));
                contactBean.setCon_relation("2");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showShortBottom("添加成功");
            contacts.add(contacts.size()-1,contactBean);
            controlContactsNum();
            //清除
            getDatas().clear();
            update(contacts);
        }
    }

    /**
     * 删除紧急联系人
     *
     * @param id
     */
    public void deleteEmergencyContact(String id) {
        ((BaseFragmentActivity)context).showWithStatus("删除中...");
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("con_id",id);
        map.put("cus_id",cus_id);
        ClientModel.delUserContacto(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    showShortBottom("删除成功");
                    ((BaseFragmentActivity)context).closeSVProgressHUD();
                    contacts.remove(currentPosition);
                    getDatas().clear();
                    controlContactsNum();
                    update(contacts);
                }
                @Override
                public void _onError(String msg) {
                    ((BaseFragmentActivity)context).closeSVProgressHUD();
                    showShortBottom("操作失败");
                }});
    }

    /**
     * 控制联系人个数，少于5个有添加按钮，
     */
    private void controlContactsNum(){

        for(int i=0;i<contacts.size();i++){
            LoanUserInfo.UserContactor contactor = contacts.get(i);
            if(contactor.getCon_id()==0){
                contacts.remove(contactor);
            }
        }
        if(contacts.size()<5)
            contacts.add(new LoanUserInfo.UserContactor());
    }
}
