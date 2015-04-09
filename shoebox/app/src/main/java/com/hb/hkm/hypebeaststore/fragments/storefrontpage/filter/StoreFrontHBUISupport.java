package com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Display;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;
import com.hb.hkm.hypebeaststore.fragments.brandpage.FilterNormalListFragment;
import com.hb.hkm.hypebeaststore.fragments.brandpage.filterBrandItemList;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.slidinglayer.SlidLayer;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by hesk on 3/18/15.
 */
public class StoreFrontHBUISupport implements onApplyFilterTrigger, MaterialTabListener, SlidLayer.OnInteractListener, filterBrandItemList.Callbacks {
    public final static String
            brand_key = "Brands",
            cate_key = "Category",
            size_key = "Size",
            price_key = "Price",
            TAG = "MaterialTabHostSupport";
    //pansy.hui@101medialab.com
    private MaterialTabHost tab;
    final ArrayList<String> items_list = new ArrayList<String>();
    private boolean notify = false;
    private FilterNormalListFragment filterfragment;
    private SlidLayer dtoplayout;
    private GridDisplay griddisplay;
    private SwipeRefreshLayout swip;


    private ArrayList<TermWrap> brand = new ArrayList<TermWrap>(), cat = new ArrayList<TermWrap>(), size = new ArrayList<TermWrap>(), price = new ArrayList<TermWrap>();

    public StoreFrontHBUISupport() {
    }

    /**
     * create just one line component
     *
     * @param con
     * @return
     */
    public static StoreFrontHBUISupport with(Activity con) {
        StoreFrontHBUISupport sp = new StoreFrontHBUISupport();
        sp.setOnAttach(con);

        return sp;
    }

    /**
     * to control the fragments
     */
    private Activity activity;

    @SuppressLint("ResourceAsColor")
    public StoreFrontHBUISupport setElements(MaterialTabHost m, SlidLayer d, GridDisplay display, SwipeRefreshLayout swipe) {
        tab = m;
        filterfragment = new FilterNormalListFragment();
        dtoplayout = d;
        griddisplay = display;
        swip = swipe;
        display.setEnableFloatingButton(false);
        m.setAccentColor(R.color.primary_pref_v2);
        d.setSlidingEnabled(false);
        d.setOnInteractListener(this);

        return this;
    }

    private void setOnAttach(Activity a) {
        activity = a;
    }

    private void setFragment(Fragment fd, String tag) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // if (ft.isEmpty())
        //    ft.add(R.id.filter_layouts, fd).commit();
        // else

        ft.replace(R.id.filter_layouts, fd).addToBackStack(null).commit();

    }

    private int times_init = 0;

    /**
     * after retrieved data from the server endpoint and this is going to start rendering the framework on the UI
     */
    @SuppressLint("ResourceAsColor")
    public void init() {
        dtoplayout.setSlidingEnabled(true);
        tab.setBorderReferenceColor(1, R.color.divider_press);
        tab.setCustomBackground(R.drawable.tab_host_bottom_line);
        if (tab.getChildCount() == 0) {
            if (retent.filter_list_brand.size() > 1) {
                items_list.add(brand_key);
                brand.addAll(retent.filter_list_brand);
            }
            if (retent.filter_list_cat.size() > 0) {
                items_list.add(cate_key);
                cat.addAll(retent.filter_list_cat);
            }
            if (retent.filter_list_size.size() > 0) {
                items_list.add(size_key);
                size.addAll(retent.filter_list_size);
            }
            if (retent.filter_list_price.size() > 0) {
                items_list.add(price_key);
                price.addAll(retent.filter_list_price);
            }

            for (int i = 0; i < items_list.size(); i++) {
                String txt = items_list.get(i);
                /*
                tab.addTab(tab.createTab(
                        new tabBuilder(tabBuilder.layout.TAB_CLASSIC).with(activity).initInstance()
                ).setTabListener(this).setText(txt));*/
                tab.addTab(tab.createCustomTextTab(R.layout.item_tab, txt, false).setTabListener(this));
                notify = true;
            }
            // Log.d(TAG, items_list.size() + " completed request for top menu");
            // Tool.trace(this, TAG + "completed request for the top menu. there are : " + items_list.size() + " items");

            if (notify) {
                if (dtoplayout != null && griddisplay != null) {
                    //  if (dtoplayout.getState() == DragTopLayout.PanelState.COLLAPSED)
                    // griddisplay.setListEnable(true);
                    //  if (dtoplayout.getState() == DragTopLayout.PanelState.EXPANDED)
                    // griddisplay.setListEnable(false);
                }
                tab.notifyDataSetChanged();
                notify = false;
            }

        } else {
            // mTab.removeAllViews();
            // mTab.notifyDataSetChanged();
        }

    }

    @Override
    public void apply_action() {
        dtoplayout.closeLayer(true);
    }


    @Override
    public void onTabSelected(MaterialTab materialTab) {
        //pager.setCurrentItem(tab.getPosition());
        tab_selected_final(materialTab);
        if (!dtoplayout.isOpened())
            dtoplayout.openLayer(true);
    }


    @Override
    public void onTabReselected(MaterialTab materialTab) {
        if (dtoplayout.isOpened())
            dtoplayout.closeLayer(true);
        else
            dtoplayout.openLayer(true);

        if (times_init == 0) {
            times_init++;
            tab_selected_final(materialTab);
        }

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {
        //  tab_selected_final(materialTab);

    }

    private ArrayList<TermWrap> getList(String t) {
        if (t.equalsIgnoreCase(brand_key)) return brand;
        else if (t.equalsIgnoreCase(cate_key)) return cat;
        else if (t.equalsIgnoreCase(size_key)) return size;
        else if (t.equalsIgnoreCase(price_key)) return price;
        else return null;
    }

    private void tab_selected_final(MaterialTab materialTab) {
        // Tool.trace(this, materialTab.getView().getId());
        int tabPos = materialTab.getPosition();
        tab.setSelectedNavigationItem(tabPos);
        final String t = items_list.get(tabPos);

        if (t.equalsIgnoreCase("brands")) {
            setFragment(filterBrandItemList.newInstance(this), t);
        } else
            setFragment(FilterNormalListFragment.getInstance(t, -1, getList(t))
                    .setTriggerApplier(this), t);

        retent.msubmissionfilter.setOperation(t);
        //Tool.trace(getApplicationContext(), "pos=" + tabPos + ". pressed title = " + title);
    }


    /**
     * This method is called when an attempt is made to open the current <code>SlidLayer</code>. Note
     * that because of animation, the <code>SlidLayer</code> may not be visible yet.
     */
    @Override
    public void onOpen() {
        swip.setRefreshing(false);
        swip.setEnabled(false);
        dtoplayout.setSlidingEnabled(true);
        griddisplay.setListEnable(false);
    }

    /**
     * This method is called when an attempt is made to show the preview mode in the current
     * <code>SlidLayer</code>. Note that because of animation, the <code>SlidLayer</code> may not be
     * visible yet.
     */
    @Override
    public void onShowPreview() {

    }

    /**
     * This method is called when an attempt is made to close the current <code>SlidLayer</code>. Note
     * that because of animation, the <code>SlidLayer</code> may still be visible.
     */
    @Override
    public void onClose() {

    }

    /**
     * this method is executed after <code>onOpen()</code>, when the animation has finished.
     */
    @Override
    public void onOpened() {
        dtoplayout.setSlidingEnabled(false);

    }

    /**
     * this method is executed after <code>onShowPreview()</code>, when the animation has finished.
     */
    @Override
    public void onPreviewShowed() {

    }

    /**
     * this method is executed after <code>onClose()</code>, when the animation has finished and the
     * <code>SlidLayer</code> is
     * therefore no longer visible.
     */
    @Override
    public void onClosed() {
        swip.setEnabled(true);
        dtoplayout.setSlidingEnabled(true);
        griddisplay.setListEnable(true);
    }

    /**
     * Callback for when an item has been selected.
     *
     * @param position
     */
    @Override
    public void onItemSelected(int position) {
        TermWrap p = retent.filter_list_brand.get(position);
        retent.msubmissionfilter.reset();
        retent.msubmissionfilter.setFilterOption(brand_key, p.toString(), position);
        retent.msubmissionfilter.filterapply();
        apply_action();
    }
}
