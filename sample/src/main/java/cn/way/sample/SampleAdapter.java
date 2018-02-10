package cn.way.sample;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.way.sample.material_dialog.MaterialDialogActivity;
import cn.way.sample.material_progressbar.MaterialProgressbarActivity;
import cn.way.sample.util.AppUtils;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.07
 *     desc  :
 * </pre>
 */

public class SampleAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, String>> mSampleList;
    private SampleOnItemClickListener mOnItemClickListener;

    private static final String KEY_SAMPLE_ICON = "sample_icon";
    private static final String KEY_SAMPLE_TITLE = "sample_title";
    private static final int KEY_SAMPLE_ITEMS_COUNT = 2;

    public SampleAdapter(Context context) {
        mContext = context;
        load();
        mOnItemClickListener = new SampleOnItemClickListener();
    }

    public void load() {
        SampleTask sampleTask = new SampleTask(this);
        sampleTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void update(List<Map<String, String>> sampleList) {
        mSampleList = sampleList;
        notifyDataSetChanged();
    }

    public SampleOnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    @Override
    public int getCount() {
        return mSampleList != null ? mSampleList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mSampleList != null ? mSampleList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item_sample, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map<String, String> sampleMap = (Map<String, String>) getItem(position);
        viewHolder.bindView(sampleMap);

        return convertView;
    }

    static class ViewHolder {
        ImageView mIvSampleIcon;
        TextView mTvSampleTitle;

        private ViewHolder(View convertView) {
            mIvSampleIcon = convertView.findViewById(R.id.iv_sample_icon);
            mTvSampleTitle = convertView.findViewById(R.id.tv_sample_title);
        }

        public void bindView(Map<String, String> sampleMap) {
            String iconStr = sampleMap.get(KEY_SAMPLE_ICON);
            final int iconRes = TextUtils.isEmpty(iconStr) ?
                    R.mipmap.ic_launcher : Integer.valueOf(sampleMap.get(KEY_SAMPLE_ICON));
            mIvSampleIcon.setImageResource(iconRes);

            String title = sampleMap.get(KEY_SAMPLE_TITLE);
            if (TextUtils.isEmpty(title)) {
                mTvSampleTitle.setText(R.string.app_name);
            } else {
                mTvSampleTitle.setText(title);
            }
        }
    }

    static class SampleTask extends AsyncTask<Void, Void, List<Map<String, String>>> {
        private SampleAdapter mSampleAdapter;

        public SampleTask(SampleAdapter sampleAdapter) {
            mSampleAdapter = sampleAdapter;
        }

        @Override
        protected List<Map<String, String>> doInBackground(Void... voids) {
            Resources res = mSampleAdapter.mContext.getResources();

            String[] sampleIcons = getSampleIcons(res);
            String[] sampleTitle = res.getStringArray(R.array.sample_title);

            final int iconSize = sampleIcons.length;
            final int titleSize = sampleTitle.length;
            final int size = Math.max(iconSize, titleSize);
            List<Map<String, String>> sampleList = null;
            if (size > 0) {
                sampleList = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    Map<String, String> sampleMap = new HashMap<>(KEY_SAMPLE_ITEMS_COUNT);
                    String iconRes = i < iconSize ? sampleIcons[i] : null;
                    String titleRes = i < titleSize ? sampleTitle[i] : null;
                    sampleMap.put(KEY_SAMPLE_ICON, iconRes);
                    sampleMap.put(KEY_SAMPLE_TITLE, titleRes);
                    sampleList.add(sampleMap);
                }
            }


            return sampleList;
        }

        private String[] getSampleIcons(Resources res) {
            String[] sampleIcons = null;
            TypedArray typedArray = res.obtainTypedArray(R.array.sample_icon);
            int len = typedArray.length();
            if (len > 0) {
                sampleIcons = new String[len];

                for (int i = 0; i < len; i++) {
                    sampleIcons[i] = Integer.toString(typedArray.getResourceId(i, 0));
                }
            }

            return sampleIcons;
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> mapList) {
            super.onPostExecute(mapList);
            mSampleAdapter.update(mapList);
        }
    }

    private class SampleOnItemClickListener implements AdapterView.OnItemClickListener {
        private static final int INTENT_START_MATERIAL_PROGRESSBAR = 0x00;
        private static final int INTENT_START_MATERIAL_DIALOG = 0x01;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case INTENT_START_MATERIAL_PROGRESSBAR:
                    AppUtils.launch(mContext, MaterialProgressbarActivity.class);
                    break;
                case INTENT_START_MATERIAL_DIALOG:
                    AppUtils.launch(mContext, MaterialDialogActivity.class);
                default:
                    break;
            }
        }
    }
}
