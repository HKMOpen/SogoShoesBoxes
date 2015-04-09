package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.comcast.freeflow.core.AbsLayoutContainer;
import com.comcast.freeflow.core.FreeFlowContainer;
import com.comcast.freeflow.core.FreeFlowItem;
import com.comcast.freeflow.layouts.FreeFlowLayout;
import com.comcast.freeflow.layouts.HLayout;
import com.comcast.freeflow.layouts.VGridLayout;
import com.comcast.freeflow.layouts.VLayout;
import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.elementHome;
import com.hb.hkm.hypebeaststore.endpointmanagers.HomePageV1JSON;
import com.hb.hkm.hypebeaststore.endpointmanagers.asyclient;
import com.hb.hkm.hypebeaststore.fragments.home.ElementAdapter;
import com.hb.hkm.hypebeaststore.fragments.home.HBHLayout;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.LifeCycleApp;
import com.hb.hkm.hypebeaststore.life.retent;
import com.parse.ParseAnalytics;
import com.squareup.picasso.Picasso;

/**
 * Created by hesk on 3/10/15.
 * This is the native home page V1
 */
public class HBStoreV1 extends ActionBarActivity
        implements asyclient.callback, View.OnClickListener {

    public static final String TAG = "HBSTOREV1";
    private LifeCycleApp appcontext;
    private Picasso pic;
    private View loader;
    private HomePageV1JSON render;
    private RelativeLayout hbbar;
    private FreeFlowContainer container;
    private VGridLayout grid;
    private HBHLayout custom;
    private FreeFlowLayout[] layouts;
    private int currLayoutIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        appcontext = (LifeCycleApp) getApplication();
        setContentView(R.layout.act_home_v1);
        // Track app opens.
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        loader = findViewById(R.id.loader);
      /*  hbbar = (RelativeLayout) findViewById(R.id.load_more);
        hbbar.setOnClickListener(this);*/


        pic = appcontext.getInstancePicasso();
        if (retent.home_elements.size() > 0) {
            constructViewFreeFlowBase();
        } else {
            /**
             * there is no data from the home page cache
             */
            render = new HomePageV1JSON(this, this);
            render.execute();
        }
    }

    protected void buildLayouts() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        container = (FreeFlowContainer) findViewById(R.id.container);
        container.setFinger_touch_tap_distance_torlance(99d);
        //Our new layout
        //  custom = new HBHLayout(new Point(0, (int) ViewUtils.dipToPixels(this, 30.0f)));
        custom = new HBHLayout(new Point(0, 0));
        //Grid Layout
        grid = new VGridLayout();
        VGridLayout.LayoutParams params = new VGridLayout.LayoutParams(size.x / 2, size.x / 2);
        grid.setLayoutParams(params);
        //Vertical Layout
        VLayout vlayout = new VLayout();
        VLayout.LayoutParams params2 = new VLayout.LayoutParams(size.x);
        vlayout.setLayoutParams(params2);

        //HLayout
        HLayout hlayout = new HLayout();
        hlayout.setLayoutParams(new HLayout.LayoutParams(size.x));

        //layout for freeflowlayout
        layouts = new FreeFlowLayout[]{custom, grid, vlayout, hlayout};
    }

    final Handler b = new Handler();


    protected void constructViewFreeFlowBase() {

        buildLayouts();
        ElementAdapter adapter = new ElementAdapter(this);
        container.setLayout(layouts[currLayoutIndex]);
        container.setAdapter(adapter);
        adapter.update(retent.home_elements);
        loader.setVisibility(View.GONE);
        container.dataInvalidated();
        /**

         b.postDelayed(new Runnable() {
        @Override public void run() {
        HBStoreV1.this.container.dataInvalidated(true);
        }
        }, 1000);

         */
        container.setOnItemClickListener(new AbsLayoutContainer.OnItemClickListener() {
            @Override
            public void onItemClick(AbsLayoutContainer parent, FreeFlowItem proxy) {
                // Tool.trace(HBStoreV1.this, "index: " + proxy.data);
                triggerStoreFront((elementHome) proxy.data);
            }
        });
        container.addScrollListener(new FreeFlowContainer.OnScrollListener() {
            @Override
            public void onScroll(FreeFlowContainer container) {
                Log.d(TAG, "scroll percent " + container.getScrollPercentY());
            }
        });
    }

    @Override
    public void onSuccess(String data) {
        constructViewFreeFlowBase();
    }

    @Override
    public void onFailure(String message, int code) {

    }

    @Override
    public void beforeStart(asyclient task) {

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Loading data");
        //  pageIndex++;
        currLayoutIndex++;
        container.setLayout(layouts[currLayoutIndex % layouts.length]);
        container.dataInvalidated();
        //fetch.load(this, itemsPerPage, pageIndex);
    }

    /**
     * click from the login button
     */
    public void login_where_is(View v) {
        Intent intent = new Intent(this, HBLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_scale_to_small);
    }

    /**
     * bring to another view
     *
     * @param eH
     */
    private void triggerStoreFront(elementHome eH) {
        Intent intent;
        final Bundle f = new Bundle();
        if (eH.getSlug().equalsIgnoreCase("brands")) {
            intent = new Intent(this, SimpleBrands.class);
            f.putString(StoreFrontV1.APP_INTENT_URI, getFullPath(eH));
            intent.putExtras(f);
        } else {
            //  intent = new Intent(this, StoreFrontV1.class);
            intent = new Intent(this, StoreFrontHB.class);
            f.putString(StoreFrontV1.APP_INTENT_URI, getFullPath(eH));
            f.putString(StoreFrontV1.APP_INTENT_TITLE, eH.getName());
            intent.putExtras(f);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_scale_to_small);
    }

    private String getFullPath(elementHome element) {
        return Config.wv.domain_home + element.getSlug();
    }
}
