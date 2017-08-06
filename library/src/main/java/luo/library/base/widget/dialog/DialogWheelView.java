package luo.library.base.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.wheelview.OnWheelChangedListener;
import com.wheelview.WheelView;
import com.wheelview.adapters.ArrayWheelAdapter;

import java.util.List;

import luo.library.R;
import luo.library.base.bean.WheelViewBean;

/**
 * 带有WheelView 的Dialog
 * Created by john lee on 2017/5/20.
 */

public class DialogWheelView extends AlertDialog {

    private Context context;
    private int position = 0;

    private List<WheelViewBean> items;
    private OnConfirmListener onConfirmListener;
    private ArrayWheelAdapter arrayWheelAdapter;
    private WheelView wheelview;

    public interface OnConfirmListener {
        void onConfirm(WheelViewBean item);
    }

    public DialogWheelView(Context context, List<WheelViewBean> items, OnConfirmListener onConfirmListener) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.items = items;
        this.onConfirmListener = onConfirmListener;
        initView();
        setCanceledOnTouchOutside(false);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_wheel_view, null);
        setView(view);

        wheelview = (WheelView) view.findViewById(R.id.wheelview);
        ArrayWheelAdapter adapter = new ArrayWheelAdapter<WheelViewBean>(context, items);
        wheelview.setViewAdapter(adapter);// 设置显示数据
        wheelview.setVisibleItems(8);
        wheelview.setCurrentItem(0);// 初始化时显示的数据
        wheelview.setCyclic(false);
        wheelview.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                position = newValue;
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
            onConfirmListener.onConfirm(items.get(position));
        dismiss();
    }


    public void update(List<WheelViewBean> items){
        arrayWheelAdapter = new ArrayWheelAdapter<WheelViewBean>(context,items);
        wheelview.setViewAdapter(arrayWheelAdapter);// 设置显示数据
    }

}
