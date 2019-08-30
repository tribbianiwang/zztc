package com.yascn.smartpark.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.LAYOUTID;

public class UserGuideDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getIntent().getIntExtra(LAYOUTID,0);
        setContentView(layoutId);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {

        indexToolbar.setNavigationIcon(R.drawable.icon_back);

        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
