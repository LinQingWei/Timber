package cn.way.sample.material_icon;

import android.graphics.Color;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.GridView;

import java.util.Arrays;
import java.util.List;

import cn.way.material_icon.MaterialDrawableBuilder;
import cn.way.material_icon.MaterialMenuInflater;
import cn.way.sample.base.BaseActivity;
import cn.way.sample.R;

/**
 * Owner: way
 * 18-10-18
 */
public class MaterialIconActivity extends BaseActivity {
    GridView mGridView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_material_icon;
    }

    @Override
    protected void initContentView() {
        mGridView = findViewById(R.id.grid_view);
        List<MaterialDrawableBuilder.IconValue> vals =
                Arrays.asList(MaterialDrawableBuilder.IconValue.values());

        ImageAdapter adapt = new ImageAdapter(this, vals);
        mGridView.setAdapter(adapt);

        final Toolbar toolbar1 = findViewById(R.id.toolbar_1);
        MaterialMenuInflater.with(toolbar1.getContext(),
                new SupportMenuInflater(toolbar1.getContext())).inflate(R.menu.menu_nocolor,
                toolbar1.getMenu());

        // Activity Theme materialIconColor attribute (lowest priority default color)
        // View specific Theme e.g. app:theme="..."   (2nd lowest in priority)
        // setDefaultColor(Resource) methods (highest priority default color)
        // app:materialIconColor set on an <item> tag in the menu XML file (overrides any other color choice)
        MaterialMenuInflater
                .with(this)
                .setDefaultColor(Color.BLUE)
                .inflate(R.menu.menu_nocolor, toolbar1.getMenu());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater.with(this).inflate(R.menu.menu_main, menu);
        return true;
    }
}
