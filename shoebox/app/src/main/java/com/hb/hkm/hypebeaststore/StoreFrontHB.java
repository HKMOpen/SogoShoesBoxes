package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.StoreFrontHBUISupport;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.slidinglayer.SlidLayer;
import com.neopixl.pixlui.components.button.Button;

import it.neokree.materialtabs.MaterialTabHost;

/**
 * Created by hesk on 3/18/15.
 */
public class StoreFrontHB extends BaseStoreFront implements View.OnClickListener {

    private MaterialTabHost mTab;
    private SlidLayer mSlidingLayer;
    private StoreFrontHBUISupport supportMtab;

    @Override
    protected void setMainLayoutXML(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setContentView(R.layout.act_advanced_store_front);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNewList(true);



        if (getIntent().getExtras() != null) {
            Bundle bun = getIntent().getExtras();
            String url = bun.getString(APP_INTENT_URI, "");
            String titie = bun.getString(APP_INTENT_TITLE, "");
            retent.queryBaseUrl = url.equalsIgnoreCase("") ? default_url : url;
            retent.current_product_list2.clear();
            mdisplay.notifyList();
            setTitle(titie.toUpperCase());
        } else {
            if (retent.queryBaseUrl == null) {
                retent.queryBaseUrl = default_url;
                Log.d(TAG, "default is taken placed.");
            }

        }
        mdisplay.setGridEvents(getGridEvent());
        mTab = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mSlidingLayer = (SlidLayer) findViewById(R.id.sliding_out_layer);
        mdisplay.setSwipeRefresherHandle(getSwipeComponent());
        //setup the main catalog grid view
        setFragmentAt(R.id.container_jazz_list_view, mdisplay);
        Button b1 = (Button) findViewById(R.id.clear_filter);
        Button b2 = (Button) findViewById(R.id.view_wish_list);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);


        getWindowManager().getDefaultDisplay().getSize(screen_size);
        mSlidingLayer.setViewPort(screen_size);
        mSlidingLayer.setPreviewOffsetFillScreen();
       // mSlidingLayer.useShadowPadding(false);
        //main ui controller on the main thread
        supportMtab = StoreFrontHBUISupport.with(this).setElements(mTab, mSlidingLayer, mdisplay, getSwipeComponent());
        if (retent.current_product_list2.size() == 0 || isNewlist()) {
            load_fetched_content(1);
        } else {
            supportMtab.init();
        }


        NotifyCountShoppingItems(retent.appSettings.getShopping_bag_current_item());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_filter:
                retent.msubmissionfilter.reset();
                retent.msubmissionfilter.filterapply();
                break;
            case R.id.view_wish_list:
                startActivity(new Intent(this, HBWishList.class));
                break;
        }
    }

    public void buttonClicked(View v) {
        switch (v.getId()) {
         /*   case R.id.buttonOpen:
                mSlidingLayer.openLayer(true);
                break;
            case R.id.buttonClose:
                mSlidingLayer.closeLayer(true);
                break;*/
        }
    }


    /**
     * when the new tab is initialized.
     */
    @Override
    protected void on_new_tab_init() {
        Log.d(TAG, "go go go now...");
        supportMtab.init();
    }

}
