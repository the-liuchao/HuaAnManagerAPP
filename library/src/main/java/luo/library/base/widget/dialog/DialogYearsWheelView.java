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
 * 带有WheelView 的Dialog 选择年份
 * Created by john lee on 2017/5/20.
 */

public class DialogYearsWheelView extends AlertDialog {

    private static final int DEFAULT_START_YEAR = 1990;
    private static final int DEFAULT_END_YEAR = 2100;

    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private int currentYear;

    private Context context;

    private OnConfirmListener onConfirmListener;

    public interface OnConfirmListener {
        void onConfirm(String year);
    }

    public DialogYearsWheelView(Context context, OnConfirmListener onConfirmListener) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onConfirmListener = onConfirmListener;
        initView();
    }

    public DialogYearsWheelView(Context context, OnConfirmListener onConfirmListener,int startYear) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onConfirmListener = onConfirmListener;
        this.startYear = startYear;
        initView();
        setCanceledOnTouchOutside(false);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_wheel_view, null);
        setView(view);

        endYear = Calendar.getInstance().get(Calendar.YEAR);

        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);
        List<NameTypeBean> nameTypes = new ArrayList<>();
        for (int i = 0; i < endYear - startYear + 1; i++) {
            NameTypeBean nameType = new NameTypeBean(startYear + i + "", startYear + i + "");
            nameTypes.add(nameType);
        }

        wheelview.setViewAdapter(new ArrayWheelAdapter(context, nameTypes));// 设置"年"的显示数据
        wheelview.setCurrentItem(endYear - startYear);// 初始化时显示的数据
        wheelview.setVisibleItems(8);
        wheelview.setCyclic(false);
        currentYear = endYear;
        wheelview.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentYear = wheelview.getCurrentItem() + startYear;
            }
        });

        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void confirm() {
        if (onConfirmListener != null)
            onConfirmListener.onConfirm(currentYear + "");
        dismiss();
    }

}
