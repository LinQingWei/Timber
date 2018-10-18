package cn.way.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

import cn.way.sample.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private ListView mLvSample;
    private SampleAdapter mSampleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        mLvSample = findViewById(R.id.lv_sample);
        mSampleAdapter = new SampleAdapter(this);
        mLvSample.setAdapter(mSampleAdapter);
        mLvSample.setOnItemClickListener(mSampleAdapter.getOnItemClickListener());
    }

    @Override
    protected void updateActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.show();
        super.updateActionBar(actionBar);
    }
}
