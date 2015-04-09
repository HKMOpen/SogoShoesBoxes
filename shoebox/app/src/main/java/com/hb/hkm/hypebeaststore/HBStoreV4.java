package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hb.hkm.hypebeaststore.endpointmanagers.HomePageJQuery;
import com.parse.ParseAnalytics;

/**
 * this is the testing V4 home page
 * Created by hesk on 3/2/15.
 */
public class HBStoreV4 extends ActionBarActivity {
    private LinearLayout b;
    private String[] links = new String[]{
            "http://store.hypebeast.com/new-arrivals/page/2",
            "http://store.hypebeast.com/brands/adidas-originals?utm_source=store.hypebeast.com&utm_medium=Feature%20Banner&utm_content=adidas%20Originals%20x%20RAF.%20February%2026&utm_campaign=adidas%20Originals%20x%20RAF.%20February%2026",
            "http://store.hypebeast.com/categories/accessories/eyewear?utm_source=store.hypebeast.com&utm_medium=Feature%20Banner&utm_content=Eyewear.%20February%2026&utm_campaign=Eyewear.%20February%2026",
            "http://store.hypebeast.com/categories/clothing/shorts",
            "http://store.hypebeast.com/brands/craft-design-technology",
            "http://store.hypebeast.com/categories/home-tech/photography"
    };
    private String[] link_labels = new String[]{
            "New arrivals",
            "Promotion adidas",
            "Promotion eyewear",
            "Shorts",
            "Craft and Design",
            "Photography"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.act_home_debug_v4);
        // Track app opens.
        ParseAnalytics.trackAppOpened(getIntent());
        b = (LinearLayout) findViewById(R.id.listitems);
        initButtons();
    }

    private void onSliderClicked(final String uri, final String title) {
        Log.d(HomePageJQuery.TAG, "Home Page here: " + uri);
        Intent intent = new Intent(this, StoreFrontV1.class);
        // intent.setData(Uri.parse(url));
        final Bundle f = new Bundle();
        f.putString(StoreFrontV1.APP_INTENT_URI, uri);
        f.putString(StoreFrontV1.APP_INTENT_TITLE, title);
        intent.putExtras(f);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_scale_to_small);
    }


    protected void initButtons() {
        for (int i = 0; i < link_labels.length; i++) {
            Button fb = new Button(getApplicationContext());
            fb.setLayoutParams(new LinearLayout.LayoutParams(-1, ViewGroup.LayoutParams.WRAP_CONTENT));
            final int finalI = i;
            fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSliderClicked(links[finalI], link_labels[finalI]);
                }
            });
            fb.setText(link_labels[i]);
            b.addView(fb);
        }
        b.requestLayout();
    }
}
