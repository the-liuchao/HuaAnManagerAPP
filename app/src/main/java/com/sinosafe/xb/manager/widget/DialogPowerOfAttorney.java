package com.sinosafe.xb.manager.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * 授权委托书 对话框
 * Created by cnmobi_01 on 2017/5/15.
 */

public class DialogPowerOfAttorney extends AlertDialog  implements OnPageChangeListener, OnLoadCompleteListener {

    private Context context;
    private TextView tv_content, tv_prompt,tv_title;
    //private WebView webContent;
    private ImageView iv_signature;
    public static final String SIGNATURE_NAME = "signature.png";
    private OnConfirmListener onConfirmListener;
    private OnSignatureListener onSignatureListener;
    private OnRefuseListener onRefuseListener;
    private VerificationCodeButton mBtnGetCode;
    private ClearEditText clearEditText;

    PDFView pdfView;
    Integer pageNumber = 0;
    private static String SAMPLE_FILE = "auth.pdf";
    private int type = 0;

    public interface OnConfirmListener {
        void onConfirm();
    }

    public interface OnSignatureListener {
        void onSignature();
    }

    public interface OnRefuseListener {
        void refuse();
    }

    public DialogPowerOfAttorney(Context context, OnSignatureListener onSignatureListener, OnConfirmListener onConfirmListener,OnRefuseListener onRefuseListener) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onSignatureListener = onSignatureListener;
        this.onConfirmListener = onConfirmListener;
        this.onRefuseListener = onRefuseListener;
        initView();
    }


    public DialogPowerOfAttorney(Context context, OnSignatureListener onSignatureListener, OnConfirmListener onConfirmListener,OnRefuseListener onRefuseListener,int type) {
        super(context, R.style.AlertDialogTheme);
        this.context = context;
        this.onSignatureListener = onSignatureListener;
        this.onConfirmListener = onConfirmListener;
        this.onRefuseListener = onRefuseListener;
        this.type = type;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_powerofattorney, null);
        setView(view);
        setCanceledOnTouchOutside(false);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_prompt = (TextView) view.findViewById(R.id.tv_prompt);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        iv_signature = (ImageView) view.findViewById(R.id.iv_signature);
        mBtnGetCode = (VerificationCodeButton)view.findViewById(R.id.btn_get_code);
        clearEditText = (ClearEditText)view.findViewById(R.id.et_code);

        pdfView = (PDFView) view.findViewById(R.id.pdfView);
        /*webContent = (WebView) view.findViewById(R.id.wv_content);
        ViewGroup.LayoutParams params = webContent.getLayoutParams();
        params.width = (int) (DensityUtil.widthPixels(context)*0.8);
        webContent.setLayoutParams(params);
        setWebView();*/

        mBtnGetCode.setSendType(1);
        mBtnGetCode.setEtPhone(clearEditText);
        /*try {
            InputStream is = context.getAssets().open("powerofattorney.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer, "GB2312");
            tv_content.setText(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        if(type==1) {
            tv_title.setText("投保单");
            SAMPLE_FILE = "apply_form.pdf";
        }
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                displayFromAsset(SAMPLE_FILE);
            }
        });

        iv_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///dismiss();
                onSignatureListener.onSignature();
            }
        });
        tv_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignatureListener.onSignature();
            }
        });
        view.findViewById(R.id.btn_agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /*if(FileUtil.getBitmap(context,SIGNATURE_NAME)==null){
                    T.showShortBottom("请先进行电子签署");
                    return;
                }*/
               /*if(TextUtils.isEmpty(mBtnGetCode.getSmsCode())){
                    T.showShortBottom("请先获取验证码");
                    return;
                }*/
                if(TextUtils.isEmpty(getUserInputSmsCode())){
                    T.showShortBottom("请输入验证码");
                    return;
                }
                onConfirmListener.onConfirm();
            }
        });
        view.findViewById(R.id.btn_disagree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onRefuseListener.refuse();
            }
        });
    }

    private void setWebView() {
        /*WebSettings settings = webContent.getSettings();
        settings.setSupportZoom(true);
        settings.setTextSize(WebSettings.TextSize.SMALLER);
        webContent.loadUrl("file:///android_asset/credit_auth.html");
        webContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容大小
        webContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });*/
    }

    public void destroyWebView(){
        /*if (webContent != null) {
            webContent.clearHistory();
            webContent.loadUrl("about:blank");
            webContent.destroy();
            webContent = null;
        }*/
    }

    /**
     * 发送短信验证码类型
     * @param type
     */
    public void setSendType(int type){
        mBtnGetCode.setSendType(type);
    }

    /**
     * 用户输入的验证码
     * @return
     */
    public String getUserInputSmsCode(){

        return clearEditText.getText().toString().trim();
    }


    /**
     * 显示签名
     */
    public void showSignatureImage() {

        Bitmap bm = getBitmap(context, SIGNATURE_NAME);
        tv_prompt.setVisibility(bm==null ? View.VISIBLE : View.GONE);
        iv_signature.setImageBitmap(bm);
    }

    /**
     * 获取bitmap
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getBitmap(Context context, String fileName) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }



    private void displayFromAsset(String assetFileName) {

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(context))
                .spacing(10) // in dp
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        //setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
        //pdfView.jumpTo(0,true);
        if(type==1)
            pdfView.zoomWithAnimation(0,0,1.6f);
        else{
            pdfView.zoomWithAnimation(0,0,1.8f);
        }
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}
