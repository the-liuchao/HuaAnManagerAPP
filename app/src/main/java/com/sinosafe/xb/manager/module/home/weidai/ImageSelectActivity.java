package com.sinosafe.xb.manager.module.home.weidai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.home.UpLoadGridAdapter;
import com.sinosafe.xb.manager.module.imagePreview.MyImagePreviewDelActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.NoScrollGridView;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;
import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;

/**
 * 图片选择
 */
public class ImageSelectActivity extends BaseFragmentActivity {


    @BindView(R.id.gvImage)
    NoScrollGridView mGvImage;
    /**
     * 工牌照
     */
    private List<ImageItem> images = new ArrayList<>();
    private UpLoadGridAdapter imagesAdapter;
    private final static int REQUEST_CODE_SELECT = 110;
    private static int MAX_IMAGE_NUM = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        String titleStr = getIntent().getStringExtra("title");
        setTitleText(titleStr);
        int type = getIntent().getIntExtra("type",0);
        if(type==1){
            MAX_IMAGE_NUM = 20;
        }
        initListener();
        setImageSelectLimit(9);

        setRightButtonText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveImages();
            }
        });
        setRightButtonTextColor(R.color.main_title_color);
    }

    /**
     * 保存到本地
     */
    private void saveImages() {

        String pathStr = "";
        for(int i=0;i<images.size();i++){
            pathStr += images.get(i).path+",";
        }

        if(!"".equals(pathStr)){
            pathStr = pathStr.substring(0,pathStr.length()-1);
        }
        Intent intent = new Intent();
        intent.putExtra("path",pathStr);
        setResult(RESULT_OK,intent);
        finish();
    }

    /**
     * 监听
     */
    private void initListener() {

        String pathStr = getIntent().getStringExtra("path");
        if(!"".equals(pathStr)){
            String pathArr[] = pathStr.split(",");
            for(int i=0;i<pathArr.length;i++){
                ImageItem imageItem = new ImageItem();
                imageItem.path = pathArr[i];
                images.add(imageItem);
            }
        }
        imagesAdapter = new UpLoadGridAdapter(this,3);
        mGvImage.setAdapter(imagesAdapter);

        imagesAdapter.update(images);

        mGvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= images.size() - 1) {
                    openImagePreviewActivity(position, images);
                    return;
                }
                setImageSelectLimit(MAX_IMAGE_NUM - images.size());
                openImageGridActivity();
            }
        });
    }


    /**
     * 设置还可以选择几张图片
     *
     * @param num
     */
    private void setImageSelectLimit(int num) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        //选中数量限制
        imagePicker.setSelectLimit(num);
    }

    /**
     * 打开相册
     */
    private void openImageGridActivity() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        // 是否是直接打开相机
        //intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 预览选中的相册
     */
    private void openImagePreviewActivity(int position, List<ImageItem> images) {
        //打开预览
        Intent intentPreview = new Intent(this, MyImagePreviewDelActivity.class);
        // 但采用弱引用会导致预览弱引用直接返回空指针
        //DataHolder.getInstance().save(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS, images);
        //据说这样会导致大量图片崩溃
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) images);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intentPreview.putExtra("needDel",1);
        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //选择回来
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for(int j=0;j<images2.size();j++)
                    MyLog.e("返回前图片==="+images2.get(j).path);
                images.addAll(images2);
                imagesAdapter.update(images);
            }
        }

        //预览回来
        else if (requestCode == REQUEST_CODE_PREVIEW && resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data != null) {
                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                images.clear();
                if (images2 != null)
                    images.addAll(images2);
                imagesAdapter.update(images);
            }
        }
    }
}
