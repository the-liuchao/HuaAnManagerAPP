package luo.library.base.widget.banner;

import android.view.View;

/**
 * 类名称：   luo.library.base.widget.banner
 * 内容摘要： //轮播控件的监听事件
 * 修改备注：
 * 创建时间： 2017/6/3 0003
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public interface ImageCycleViewListener {

    /**
     * 单击图片事件
     * @param imageView
     */
    void onImageClick(CycleVpEntity info, int postion, View imageView);
}
