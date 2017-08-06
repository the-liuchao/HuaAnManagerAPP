package luo.library.base.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import luo.library.R;


/**
 * 输入对话框
 * Created by cnmobi_01 on 2017/5/15.
 */

public class DialogInput extends AlertDialog {

    private Context context;
    private TextView tv_title;
    private EditText et_name;
    private OnTextBackListener onTextBackListener;

    public interface OnTextBackListener {
        void onTextBack(String text);
    }

    public DialogInput(Context context, OnTextBackListener onTextBackListener) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onTextBackListener = onTextBackListener;
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_input, null);
        setView(view);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        et_name = (EditText) view.findViewById(R.id.et_name);

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
        if (onTextBackListener != null)
            onTextBackListener.onTextBack(et_name.getEditableText().toString());
    }

    public DialogInput setTitleWithId(int resId) {

        tv_title.setText(context.getString(resId));
        return this;
    }

    public DialogInput setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public DialogInput setTextInputType() {
        et_name.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        return this;
    }

    public DialogInput setHint(int resId) {
        setHint(context.getString(resId));

        return this;
    }

    public DialogInput setHint(String title) {
        et_name.setHint(title);
        return this;
    }

    public DialogInput setEditText(String text) {
        et_name.setText(text);
        return this;
    }
}
