package luo.library.base.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.wheelview.OnWheelChangedListener;
import com.wheelview.WheelView;
import com.wheelview.adapters.ArrayWheelAdapter;
import com.wheelview.bean.NameTypeBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import luo.library.R;

/**
 * 年月选择器
 * Created by cnmobi_01 on 2017/5/15.
 */

public class DialogYearMonthWheel extends AlertDialog {

    private Context context;
    private WheelView options1, options2;
    private OnDateSelectListener onDateSelectListener;
    private int startYear = 2000;
    private int endYear = 0;
    private int currentYear;
    private int currentMonth;

    public interface OnDateSelectListener {
        void onDateSelect(String year, String month);
    }

    public DialogYearMonthWheel(Context context, OnDateSelectListener onDateSelectListener) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onDateSelectListener = onDateSelectListener;
        initView();
        setCanceledOnTouchOutside(false);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_year_month, null);
        setView(view);

        options1 = (WheelView) view.findViewById(R.id.options1);
        options2 = (WheelView) view.findViewById(R.id.options2);

        endYear = Calendar.getInstance().get(Calendar.YEAR);
        List<NameTypeBean> nameTypesYear = new ArrayList<>();
        for (int i = 0; i < endYear - startYear + 1; i++) {
            NameTypeBean nameType = new NameTypeBean(startYear + i + "", startYear + i + "");
            nameTypesYear.add(nameType);
        }

        options1.setViewAdapter(new ArrayWheelAdapter(context, nameTypesYear));// 设置"年"的显示数据
        options1.setCurrentItem(endYear - startYear);// 初始化时显示的数据
        options1.setVisibleItems(8);
        options1.setCyclic(false);
        currentYear = endYear;
        options1.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentYear = options1.getCurrentItem() + startYear;
            }
        });


        List<NameTypeBean> nameTypesMonth = new ArrayList<>();
        for (int i = 0; i < 12 ; i++) {
            NameTypeBean nameType = new NameTypeBean(1 + i + "", 1 + i + "");
            nameTypesMonth.add(nameType);
        }
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        options2.setViewAdapter(new ArrayWheelAdapter(context,nameTypesMonth));// 设置"月"的显示数据
        options2.setCurrentItem(currentMonth-1);// 初始化时显示的数据
        options2.setVisibleItems(8);
        options2.setCyclic(false);
        options2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentMonth = options2.getCurrentItem() + 1;
            }
        });


        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateSelectListener.onDateSelect(currentYear+"", currentMonth+"");
                dismiss();
            }
        });
        //取消按钮
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
