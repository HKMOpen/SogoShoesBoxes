package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hb.hkm.hypebeaststore.endpointmanagers.BrandList;
import com.hb.hkm.hypebeaststore.endpointmanagers.asyclient;
import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;
import com.hb.hkm.hypebeaststore.fragments.brandpage.BrandListManager;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

/**
 * Created by hesk on 3/17/15.
 */
public class SimpleBrands extends BasicSupportActionBarHKM implements BrandListManager.Callbacks, asyclient.callback {
    private CircleProgressBar circle;
    private static final String TAG = "simple brands";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple_brands);
        circle = (CircleProgressBar) findViewById(R.id.circlepb);
        if (findViewById(R.id.item_list) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            // mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            //  ((ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.item_list)).setActivateOnItemClick(true);
        }
        // TODO: If exposing deep links into your app, handle intents here.
        try {
            if (retent.brand_list.size() == 0)
                new BrandList(this, this, getIntent().getExtras().getString(StoreFrontV1.APP_INTENT_URI)).execute();
            else circle.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     * bring to another view
     */
    private void triggerStoreFront(String location) {

        Log.d(TAG, "trigger location url :" + location);
        final Intent intent;
        final Bundle f = new Bundle();

        intent = new Intent(this, StoreFrontHB.class);
        f.putString(StoreFrontV1.APP_INTENT_URI, location);
        intent.putExtras(f);

        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_scale_to_small);
        finish();
    }

    @Override
    public void onItemSelected(int position) {
        //Tool.trace(this, position);
        String slug = retent.brand_list.get(position).getslug();
        triggerStoreFront(Config.by_brand + slug);
    }

    @Override
    public void onSuccess(String data) {
        circle.setVisibility(View.GONE);
        // getFragmentManager().getBackStackEntryAt()
        BrandListManager b = ((BrandListManager) getFragmentManager().findFragmentById(R.id.item_list));
        try {
            b.setActivateOnItemClick(true);
            b.update();
        } catch (Exception e) {
        }
    }

    @Override
    public void onFailure(String message, int code) {
        circle.setVisibility(View.GONE);
    }

    @Override
    public void beforeStart(asyclient task) {

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        actionAsUp();

    }
}
