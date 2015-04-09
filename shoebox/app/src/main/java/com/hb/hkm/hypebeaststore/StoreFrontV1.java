package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hb.hkm.hypebeaststore.fragments.brandpage.FilterOldFragment;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.StoreFrontV1UISupport;
import com.hb.hkm.hypebeaststore.life.retent;

import github.chenupt.dragtoplayout.DragTopLayout;
import it.neokree.materialtabs.MaterialTabHost;


public class StoreFrontV1 extends BaseStoreFront {

    private MaterialTabHost mTab;
    private DragTopLayout slidelayouttop;
    private StoreFrontV1UISupport supportMtab;

    @Override
    protected void setMainLayoutXML(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setContentView(R.layout.act_store_front);
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


        // progress2.
        mdisplay.setGridEvents(getGridEvent());
        mTab = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        slidelayouttop = (DragTopLayout) this.findViewById(R.id.draglayout);
        mdisplay.setSwipeRefresherHandle(getSwipeComponent());

        setFragmentAt(R.id.container, mdisplay);
        //final FilterOldFragment filter = FilterOldFragment.getInstance(Filtering.CAT_ORDER_REF, 0);
        final FilterOldFragment filter = new FilterOldFragment();
        //   setFragmentAt(R.id.filter_layouts, filter);
        supportMtab = new StoreFrontV1UISupport(mTab, filter, slidelayouttop, mdisplay);

        if (retent.current_product_list2.size() == 0 || isNewlist()) {
            //if (DataBank.current_product_list.size() == 0) {
            load_fetched_content(1);
            getSwipeComponent().setRefreshing(true);
        } else {
            supportMtab.init();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_front, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.view_wish_list) {
            startActivity(new Intent(this, HBWishList.class));
            // finish();
            return true;
        } else if (id == R.id.action_clear_all) {
            if (retent.msubmissionfilter.reset()) {
                load_fetched_content(1);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void on_new_tab_init() {
        supportMtab.init();
    }

    private void startPage(Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
