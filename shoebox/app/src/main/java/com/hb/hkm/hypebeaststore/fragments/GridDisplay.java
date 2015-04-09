package com.hb.hkm.hypebeaststore.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.hb.hkm.hypebeaststore.CartWebView;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom.FooterComponent;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom.HBResultAdapter;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hkm.ui.BounceScoller.BounceScroller;
import com.melnykov.fab.FloatingActionButton;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;


/**
 * Created by hesk on 2/3/15.
 */
public class GridDisplay extends ListFragment implements AbsListView.OnScrollListener {
    private static String TAG = "GridDisplay";

    public GridDisplay() {
    }

    private Point msize;

    private GrideDisplayEvent event;
    private JazzyGridView mGrid;
    private View groupview_footer;
    private BounceScroller scroller;
    private FooterComponent ftv;
    private HBResultAdapter madapter;
    private FloatingActionButton mfab;
    private View screen;
    private boolean floating_bag = true;

    public void setGridEvents(GrideDisplayEvent m_event) {
        event = m_event;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rv = inflater.inflate(R.layout.fragment_store_item_collection, container, false);
        // LayoutInflater lf = getLayoutInflater(savedInstanceState);
        groupview_footer = inflater.inflate(R.layout.l_loading_footer, null);
        return rv;
    }

    public void setListEnable(boolean b) {
       /* if (screen != null)
            screen.setVisibility(b ? View.GONE : View.VISIBLE);*/
        mGrid.setScrollContainer(b);
    }

    public void setViewPort(Point size) {
        msize = size;
    }

    public void notifyList() {
        if (madapter != null)
            madapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View v, Bundle b) {

        mfab = (FloatingActionButton) v.findViewById(R.id.fab);
        mGrid = (JazzyGridView) v.findViewById(R.id.list_jazzgrid);
        madapter = new HBResultAdapter(getActivity(), R.layout.item_jazzgrid_catalog, msize);
        screen = v.findViewById(R.id.screen_dark);
        mGrid.setAdapter(madapter);
        mGrid.setTransitionEffect(JazzyHelper.FADE);
        mGrid.setShouldOnlyAnimateNewItems(false);
        mGrid.setOnScrollListener(this);

        if (floating_bag) {
            mfab.attachToListView(mGrid, null, this);
        } else {
            mfab.setVisibility(View.GONE);
        }


        //need some more resources to find out the problem on this line
        // extraloader(groupview_footer);
        // mGrid.setOnScrollListener(this);
        //     scroller = (BounceScroller) v.findViewById(R.id.pc_root);
        //     ftv = new FooterComponent(scroller, groupview_footer, getActivity());
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(v.getContext(), CartWebView.class);
                v.getContext().startActivity(h);
            }
        });

    }

    public void setEnableFloatingButton(boolean b) {
        floating_bag = b;
    }

    public void extraLoaderSet(boolean b) {
        if (b) {
            // extraloader.setVisibility(View.VISIBLE);
            // extraloadertv.setText(getResources().getString(R.string.wait_button));
        } else {
            // extraloader.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {

        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

        }
    }

   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getResources().getDisplayMetrics().widthPixels / COLUMN_COUNT;
        int height = getResources().getDisplayMetrics().heightPixels / ROW_COUNT;
        setMeasuredDimension(width, height);
    }*/


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        double b = (double) firstVisibleItem / (double) totalItemCount;
        //  Log.d(TAG, firstVisibleItem + "/" + totalItemCount + " : " + b + "");
        double k = Config.setting.list_expand_factor + (double) totalItemCount / (double) Config.setting.target_total * (100 - Config.setting.list_expand_factor * 100) / 100d;
        // Log.d(TAG, k + " : " + b + "");
        if (b > k) {
            Log.d(TAG, "trigger run request. ");
            if (retent.result_total_pages > retent.result_current_page) {
                if (event != null) event.requestmoreitems(retent.result_current_page + 1);
            }
        }
        if (swipeContainer != null) {
            boolean getviewa = view.getChildCount() > 0 ? view.getChildAt(0).getTop() == 0 : true;
            swipeContainer.setEnabled(firstVisibleItem == 0 && getviewa);
        }
    }

    private SwipeRefreshLayout swipeContainer;

    public void setSwipeRefresherHandle(SwipeRefreshLayout swipe) {
        swipeContainer = swipe;
    }
}
