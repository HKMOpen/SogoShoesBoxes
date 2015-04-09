package com.hb.hkm.hypebeaststore;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.hb.hkm.hypebeaststore.components.dialogcom.RunLDialogs;
import com.hb.hkm.hypebeaststore.endpointmanagers.ListQueryManager;
import com.hb.hkm.hypebeaststore.endpointmanagers.asyclient;
import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.SubmissionFilter;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.dmfs.android.retentionmagic.RetentionMagic;
import org.dmfs.android.retentionmagic.annotations.Parameter;
import org.dmfs.android.retentionmagic.annotations.Retain;


/**
 * licensed and copied to work with API
 * Created by hesk on 3/18/15.
 */
abstract public class BaseStoreFront extends BasicSupportActionBarHKM implements asyclient.callback {
    private ListQueryManager sync;
    private GrideDisplayEvent mgriddisplayevent;
    public static String TAG = "store front here";
    final GridDisplay mdisplay = new GridDisplay();
    public static String APP_INTENT_URI = "uri";
    public static String APP_INTENT_TITLE = "title";
    private SwipeRefreshLayout swipcontainer;
    protected CircleProgressBar circlepb;
    private boolean newlist = false;
    protected static final String default_url = Config.newarrivals;
    public static final String EXTRA_VALUE = "com.hb.hkm.hypebeaststore.QUERYURL";

    @Retain
    @Parameter(key = EXTRA_VALUE)
    private String query_url = default_url;
    @Retain
    private Bundle mBundle;

    private SharedPreferences mPrefs;

    protected final Point screen_size = new Point();

    /**
     * successfully retrieve data from the API endpoint
     *
     * @param data
     */
    @Override
    public void onSuccess(String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mdisplay.notifyList();
                if (retent.result_current_page == 1) {
                    if (newlist) {
                        newlist = false;
                        on_new_tab_init();
                    }
                    //   Log.d(TAG, "request init tab");
                }
                swipcontainer.setRefreshing(false);
                mdisplay.extraLoaderSet(false);
                circlepb.setVisibility(View.GONE);
            }
        });
    }

    /**
     * when the new tab is initialized.
     */
    abstract protected void on_new_tab_init();

    /**
     * failure in retrieve data from the API endpoint
     *
     * @param message
     * @param code
     */
    @Override
    public void onFailure(String message, int code) {
        RunLDialogs.strDemo2(this, message);
    }

    /**
     * before start the API request
     *
     * @param task
     */
    @Override
    public void beforeStart(asyclient task) {
        swipcontainer.setRefreshing(true);
        mdisplay.extraLoaderSet(true);
    }

    /**
     * loading and fetching the data into the container
     *
     * @param page_at
     */
    protected void load_fetched_content(final int page_at) {
        // final Bundle instance = savedInstanceState;
        try {
            sync = new ListQueryManager(this);
            sync.setPageView(page_at)
                    .setURL(retent.queryBaseUrl)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        RetentionMagic.store(this, outState);
    }

    /**
     * transition started
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            RetentionMagic.persist(this, mPrefs);
        }
    }

    /**
     * transition ended
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RetentionMagic.persist(this, mPrefs);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotifyShoppingCount();
    }

    private void initRetentionMagic(Bundle savedInstanceState) {
        mPrefs = getSharedPreferences(getPackageName() + ".sharedPrefences", 0);
        RetentionMagic.init(this, getIntent().getExtras());
        if (savedInstanceState == null) {
            RetentionMagic.init(this, mPrefs);
        } else {
            RetentionMagic.restore(this, savedInstanceState);
        }
    }

    abstract protected void setMainLayoutXML(Bundle saveb);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();

        display.getSize(screen_size);
        mdisplay.setViewPort(screen_size);
        initRetentionMagic(savedInstanceState);
        mgriddisplayevent = new GrideDisplayEvent() {
            @Override
            public void requestmoreitems(int page) {
                if (sync != null) {
                    if (sync.getStatus() == AsyncTask.Status.FINISHED) {
                        load_fetched_content(page);
                    }
                } else {
                    sync = new ListQueryManager(getApplicationContext());
                    load_fetched_content(page);
                }
            }
        };
        retent.msubmissionfilter.reset();
        setMainLayoutXML(savedInstanceState);
        retent.msubmissionfilter.setOnFilterApply(new SubmissionFilter.callbackfiltertrigger() {
            @Override
            public void applyfilter() {
                mdisplay.notifyList();
                load_fetched_content(1);
            }
        });

        try {
            /**
             * start coding each component
             */
            swipcontainer = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
            swipcontainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    retent.current_product_list2.clear();
                    mdisplay.notifyList();
                    load_fetched_content(1);
                }
            });
            /**
             * get the control of the loading circle
             */
            circlepb = (CircleProgressBar) this.findViewById(R.id.circlepb);
            if (retent.current_product_list2.size() > 0) {
                circlepb.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected GrideDisplayEvent getGridEvent() {
        return mgriddisplayevent;
    }

    protected SwipeRefreshLayout getSwipeComponent() {
        return swipcontainer;
    }

    /**
     * get the set of the new list
     *
     * @param b
     */
    protected void setNewList(boolean b) {
        newlist = b;
    }

    /**
     * if the list is on NEW status
     *
     * @return
     */
    protected boolean isNewlist() {
        return newlist;
    }

    /**
     * override animation on transition
     */
    @Override
    public void onBackPressed() {
        actionAsUp();
        //this.finish();
        // overridePendingTransition(0, 0);
    }


    @Override
    protected void transitionOut() {
        overridePendingTransition(R.anim.enter_from_small, R.anim.exit_out_right);
    }

    protected void setFragmentAt(int ResPositionId, Fragment gd) {
        getFragmentManager()
                .beginTransaction()
                .add(ResPositionId, gd)
                .commit();
    }

    @Override
    protected int getMenuId() {
        return R.menu.nagbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Log.d(TAG, requestCode + requestCode + data.toString());
        } catch (Exception e) {
            Log.d(TAG, "this error from result" + e.getMessage());
            e.printStackTrace();
        }
    }

}
