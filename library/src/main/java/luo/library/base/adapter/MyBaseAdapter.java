package luo.library.base.adapter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;
/**
 * 类名称：   luo.library.base.adapter
 * 内容摘要： //说明主要功能。
 * 修改备注：
 * 创建时间： 2017/2/22
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected List<T> items;

    public MyBaseAdapter() {

    }

    public MyBaseAdapter(List<T> items) {
        this.items = items;
    }
    public MyBaseAdapter(Context context , List<T> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 设置当前元素
     *
     * @param items
     */
    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    /**
     * 添加一个子项
     *
     * @param t
     */
    public void addItem(T t) {
        if (this.items == null) {
            this.items = new ArrayList<T>();
        }
        addItem(items.size(), t);
    }

    /**
     * 添加指定位置的子项
     *
     * @param position
     * @param t
     */
    public void addItem(int position, T t) {
        if (this.items == null) {
            this.items = new ArrayList<T>();
            this.items.add(t);
        } else {
            items.add(position, t);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加元素
     *
     * @param newItems
     */
    public void addMoreItems(List<T> newItems) {
        if (this.items != null) {
            addMoreItems(this.items.size(), newItems);
        } else {
            addMoreItems(0, newItems);
        }
    }

    /**
     * 指定位置添加元素
     *
     * @param location
     * @param newItems
     */
    public void addMoreItems(int location, List<T> newItems) {
        if (this.items != null) {
            this.items.addAll(location, newItems);
        } else {
            this.items = newItems;
        }
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void removeAllItems() {
        if (this.items != null) {
            this.items.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 获取元素
     *
     * @return
     */
    public List<T> getItems() {
        return this.items;
    }

}

