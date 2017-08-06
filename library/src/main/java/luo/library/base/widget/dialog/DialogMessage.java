package luo.library.base.widget.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import luo.library.R;

/**
 * 消息对话框
 * Created by cnmobi_01 on 2017/5/15.
 */

public class DialogMessage extends AlertDialog {

    private Context context;

    private TextView tv_title,tv_message;
    private Button btnCancel,btnConfirm;
    private LinearLayout cancelLayout,confirmLayout;
    private OnConfirmListener onConfirmListener;
    private OnOtherConfirmListener onOtherConfirmListener;
    private int btnNum = 2;


    public interface OnConfirmListener {
        void onConfirm();
    }
    public interface OnOtherConfirmListener {
        void onOtherConfirm();
    }

    public DialogMessage(Context context, OnConfirmListener onConfirmListener,int btnNum) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onConfirmListener = onConfirmListener;
        this.btnNum = btnNum;
        initView();
    }

    public DialogMessage(Context context) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        initView();
    }


    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_message, null);
        setView(view);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_message = (TextView) view.findViewById(R.id.tv_message);

        cancelLayout = (LinearLayout) view.findViewById(R.id.cancelLayout);
        confirmLayout = (LinearLayout) view.findViewById(R.id.confirmLayout);

        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);

        if(btnNum==1){
            cancelLayout.setVisibility(View.GONE);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onOtherConfirmListener!=null){
                    otherConfirm();
                }
            }
        });

        setCanceledOnTouchOutside(false);
    }

    /**
     * 设置内容
     * @param mess
     * @return
     */
    public DialogMessage setMess(String mess){

        tv_message.setText(mess);
        return this;
    }

    /**
     * 设置确认按钮文字
     * @param tips
     * @return
     */
    public DialogMessage setConfirmTips(String tips){

        btnConfirm.setText(tips);
        return this;
    }


    /**
     * 设置其他确认按钮文字
     * @param tips
     * @return
     */
    public DialogMessage setOtherConfirmTips(String tips){

        btnCancel.setText(tips);
        return this;
    }

    /**
     * 设置TextView 底下图标
     * @param resId
     * @return
     */
    public DialogMessage setBottomImage(int resId){
        tv_title.setText("");
        Drawable dwBottom = context.getResources().getDrawable(resId);
        dwBottom.setBounds(0, 0, dwBottom.getMinimumWidth(), dwBottom.getMinimumHeight());
        tv_title.setCompoundDrawables(null, null, null, dwBottom);
        return this;
    }


    private void confirm() {
        if (onConfirmListener != null)
            onConfirmListener.onConfirm();
        dismiss();
    }

    private void otherConfirm() {
        if (onOtherConfirmListener != null)
            onOtherConfirmListener.onOtherConfirm();
        dismiss();
    }


    public DialogMessage setConfirmListener(OnConfirmListener onConfirmListener){

        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public DialogMessage setOtherConfirmListener(OnOtherConfirmListener onOtherConfirmListener){

        this.onOtherConfirmListener = onOtherConfirmListener;
        return this;
    }

    public void setTitle(int resId) {
        setTitle(context.getString(resId));
    }

    public DialogMessage setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public void setMessage(int resId) {
        setMessage(context.getString(resId));
    }
    public void setMessage(String message) {
        tv_message.setText(message);
    }
}
