package com.sinosafe.xb.manager.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.T;
import com.wheelview.OnWheelChangedListener;
import com.wheelview.WheelView;
import com.wheelview.adapters.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import luo.library.R;
import luo.library.base.bean.AreaListBean;

/**
 * 居住地址dialog
 * Created by cnmobi_01 on 2017/5/15.
 */

public class DialogResidentialAddress extends AlertDialog implements OnWheelChangedListener {

    private Context context;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private List<AreaListBean> options1Items = new ArrayList<>();
    private List<AreaListBean> options2Items = new ArrayList<>();
    private List<AreaListBean> options3Items = new ArrayList<>();

    protected AreaListBean mCurrentProvince;
    protected AreaListBean mCurrentCity;
    protected AreaListBean mCurrentDistrict;

    private OnAddressSelectListener onAddressSelectListener;

    public interface OnAddressSelectListener {
        void onAddressSelect(String areaCode, String area, String address);
    }

    public DialogResidentialAddress(Context context, OnAddressSelectListener onAddressSelectListener) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onAddressSelectListener = onAddressSelectListener;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_residential_address, null);
        setView(view);

        mViewProvince = (WheelView) view.findViewById(R.id.province);
        mViewCity = (WheelView) view.findViewById(R.id.city);
        mViewDistrict = (WheelView) view.findViewById(R.id.district);

        getAreaList("1", "all");

        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);

        // 设置可见条目数量
        mViewProvince.setVisibleItems(8);
        mViewCity.setVisibleItems(8);
        mViewDistrict.setVisibleItems(8);

        mViewProvince.setCyclic(false);
        mViewCity.setCyclic(false);
        mViewDistrict.setCyclic(false);


        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String areaCode = mCurrentDistrict.getENNAME();
                String area = mCurrentDistrict.getCNNAME();
                String address = mCurrentProvince.getCNNAME() + " " +
                        mCurrentCity.getCNNAME() + " " +
                        mCurrentDistrict.getCNNAME();
                onAddressSelectListener.onAddressSelect(areaCode, area, address);
                dismiss();
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });//取消按钮
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            getAreaList("2", options1Items.get(newValue).getENNAME());

            int pCurrent = mViewProvince.getCurrentItem();
            mCurrentProvince = options1Items.get(pCurrent);
        } else if (wheel == mViewCity) {
            mCurrentCity = options2Items.get(newValue);
            getAreaList("3", options2Items.get(newValue).getENNAME());

            int pCurrent = mViewCity.getCurrentItem();
            mCurrentCity = options2Items.get(pCurrent);
        } else if (wheel == mViewDistrict) {
            int pCurrent = mViewDistrict.getCurrentItem();
            mCurrentDistrict = options3Items.get(pCurrent);
        }
    }

    /**
     * 获取区域
     *
     * @param type      1省、2市、3县/区
     * @param parent_id 查询省则传all
     */
    private void getAreaList(final String type, String parent_id) {

        ClientModel.getAreaList(type,parent_id)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<AreaListBean>>>io_main())
            .map(new RxHttpResultFunc<List<AreaListBean>>())
            .subscribe(new RxSubscriber<List<AreaListBean>>() {
                @Override
                public void _onNext(List<AreaListBean> areaLists) {

                    if ("1".equals(type)) {
                        options1Items = areaLists;
                        ArrayWheelAdapter provinceAdapter = new ArrayWheelAdapter<AreaListBean>(context, areaLists);
                        mViewProvince.setViewAdapter(provinceAdapter);
                        mViewProvince.setCurrentItem(0);

                        int pCurrent = mViewProvince.getCurrentItem();
                        mCurrentProvince = options1Items.get(pCurrent);

                        getAreaList("2", options1Items.get(0).getENNAME());
                    } else if ("2".equals(type)) {
                        options2Items = areaLists;
                        ArrayWheelAdapter cityAdapter = new ArrayWheelAdapter<AreaListBean>(context, areaLists);
                        mViewCity.setViewAdapter(cityAdapter);
                        mViewCity.setCurrentItem(0);

                        int pCurrent = mViewCity.getCurrentItem();
                        mCurrentCity = options2Items.get(pCurrent);

                        getAreaList("3", options2Items.get(0).getENNAME());
                    } else if ("3".equals(type)) {
                        options3Items = areaLists;
                        ArrayWheelAdapter districtAdapter = new ArrayWheelAdapter<AreaListBean>(context, areaLists);
                        mViewDistrict.setViewAdapter(districtAdapter);
                        mViewDistrict.setCurrentItem(0);

                        int pCurrent = mViewDistrict.getCurrentItem();
                        mCurrentDistrict = options3Items.get(pCurrent);
                    }

                }
                @Override
                public void _onError(String msg) {

                    T.showShortBottom(msg);
                }});

    }

}
