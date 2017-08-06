package com.sinosafe.xb.manager.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luo.library.base.base.BaseImage;

/**
 * this is a BaseAdapter for RecyclerView
 * author
 * version 1.0
 * date 2015/6/3.
 */
public abstract class BaseRecyclerViewAdapter<T, VH extends BaseRecyclerViewAdapter.SparseArrayViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     * click listener
     */
    protected OnItemClickListener mOnItemClickListener;
    /**
     * long click listener
     */
    protected OnItemLongClickListener mOnItemLongClickListener;
    /**
     * data
     */
    protected List<T> mList = new ArrayList<T>();

    private static Context mContext;

    /**
     */
    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

   /* public void addAll(ArrayList<T> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }*/

    public void addAll(List<T> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void update(List<T> mDatas) {
        this.mList.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void updateData(List<T> mDatas) {
        this.mList=mDatas;
        notifyDataSetChanged();
    }

    public void clear() {
        this.mList.clear();
        notifyDataSetChanged();
    }
    public void clearList() {
        this.mList.clear();
    }


    /**
     * get a item by index
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<T> getDatas() {
        return mList;
    }

    /**
     * set a long click listener
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * set a click listener
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * inflate a view by viewgroup ,id ,etc
     *
     * @param viewGroup
     * @param layoutId
     * @return
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return inflateItemView(viewGroup, layoutId, false);
    }

    /**
     * inflate a view by viewgroup ,id ,etc
     *
     * @param viewGroup
     * @param layoutId
     * @param attach
     * @return
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId, boolean attach) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, attach);
    }


    /**
     * a final function to avoid you override
     * use template design pattern
     *
     * @param vh
     * @param position
     */
    @Override
    public final void onBindViewHolder(VH vh, int position) {
        final T item = getItem(position);
        bindDataToItemView(vh, position, item);
        bindItemViewClickListener(vh, position, item);
    }


    /**
     * bind data to itemview
     *
     * @param vh   viewholder
     * @param item item
     */
    protected abstract void bindDataToItemView(VH vh, int position, T item);


    /**
     * bind click listner to itemview
     *
     * @param vh   viewholder
     * @param item item
     */
    protected final void bindItemViewClickListener(VH vh, final int position, final T item) {
        if (mOnItemClickListener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(view, position, item);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onLongClick(v, position, item);
                    return true;
                }
            });
        }
    }


    /**
     * BaseViewHolder
     * using bindViewById(View view,int id) function to handle the relations between view and viewId
     */
    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            findView();
        }

        /**
         * you need to override this method to bind view in the viewholder
         * you'd better use bindViewById(View view,int id)
         */
        protected abstract void findView();

        /**
         * generic function to findViewById
         *
         * @param id viewId
         * @return the view found
         */
        protected <T extends View> T findViewById(int id) {
            return (T) itemView.findViewById(id);
        }

    }

    public static class SparseArrayViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> views;

        public SparseArrayViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<View>();
        }

        public <T extends View> T getView(int id) {
            View view = views.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return (T) view;
        }

        public SparseArrayViewHolder setText(int viewId, CharSequence value) {
            TextView view = getView(viewId);
            view.setText(value);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setSpannableText(int viewId, Spannable value) {
            TextView view = getView(viewId);
            view.setText(value);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setImageResource(int viewId, int imageResId) {
            ImageView view = getView(viewId);
            view.setImageResource(imageResId);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBitmap(int viewId, Bitmap bm) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bm);
            return SparseArrayViewHolder.this;
        }

      /*  public SparseArrayViewHolder setImageUrl(int viewId, Uri url) {
            SimpleDraweeView view = getView(viewId);
            view.setImageURI(url);
            return SparseArrayViewHolder.this;
        }*/

        public SparseArrayViewHolder setImageURL(int viewId, String imageUrl) {
            ImageView view = getView(viewId);
            BaseImage.getInstance().displayCricleImage(mContext,imageUrl, view);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundResource(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
            View view = getView(viewId);
            view.setOnTouchListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
            View view = getView(viewId);
            view.setOnLongClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return SparseArrayViewHolder.this;
        }


    }
}
