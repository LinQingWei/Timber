package cn.way.sample.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Owner: way
 * 18-9-20
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected Context mContext;
    protected List<T> mDataList;

    public BaseAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    public void refresh(List<T> dataList) {
        try {
            mDataList = dataList;
            notifyDataSetChanged();
        } catch (Exception e) {
            android.util.Log.e("BaseAdapter", e.getMessage(), e);
        }
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList == null ? null : mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder holder;
        if (convertView == null) {
            v = newView(mContext, parent);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }

        setItem(holder, getItem(position), position);

        return v;
    }

    protected abstract View newView(Context context, ViewGroup parent);

    protected abstract void setItem(ViewHolder holder, T data, int position);
}
