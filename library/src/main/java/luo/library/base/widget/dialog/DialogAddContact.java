package luo.library.base.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import luo.library.R;


/**
 * 添加紧急联系人
 * Created by cnmobi_01 on 2017/5/15.
 */

public class DialogAddContact extends AlertDialog {

    private Context context;
    private TextView tv_title;
    private EditText et_name;
    private EditText et_phone;
    private OnTextBackListener onTextBackListener;

    public interface OnTextBackListener {
        void onTextBack(String name,String phone);
    }

    public DialogAddContact(Context context, OnTextBackListener onTextBackListener) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onTextBackListener = onTextBackListener;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_contact, null);
        setView(view);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_phone = (EditText) view.findViewById(R.id.et_phone);

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

    public void setContent(String name,String phone){
        et_name.setText(name);
        et_phone.setText(phone);
    }

    private void confirm() {
        if (onTextBackListener != null)
            onTextBackListener.onTextBack(et_name.getEditableText().toString(),et_phone.getEditableText().toString());
    }

    public DialogAddContact setTitleWithId(int resId) {

        tv_title.setText(context.getString(resId));
        return this;
    }

    public DialogAddContact setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public DialogAddContact setHint(int resId) {
        setHint(context.getString(resId));

        return this;
    }

    public DialogAddContact setHint(String title) {
        et_name.setHint(title);
        return this;
    }

    public DialogAddContact setEditText(String text) {
        et_name.setText(text);
        return this;
    }
}
