package cn.way.sample.material_icon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.way.material_icon.MaterialDrawableBuilder;
import cn.way.sample.R;
import cn.way.sample.base.BaseAdapter;
import cn.way.sample.base.ViewHolder;

/**
 * Owner: way
 * 18-10-18
 */
public class ImageAdapter<T extends MaterialDrawableBuilder.IconValue> extends BaseAdapter<T> {

    public ImageAdapter(Context context, List<T> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder holder;
        if (convertView == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.image_list_item, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }

        MaterialDrawableBuilder.IconValue iconValue = getItem(position);
        if (iconValue != null) {
            ImageView iv = holder.getView(R.id.iv_sample_icon);
            iv.setImageDrawable(
                    MaterialDrawableBuilder.with(mContext)
                            .setIcon(iconValue)
                            .setColor(Color.BLACK)
                            .setSizePx(parent.getWidth() / 5)
                            .build());

            TextView tv = holder.getView(R.id.tv_sample_title);
            tv.setText(iconValue.name());
        }

        return v;
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        // DO NOTHING.
        return null;
    }

    @Override
    protected void setItem(ViewHolder holder, T data, int position) {
        // DO NOTHING.
    }
}
