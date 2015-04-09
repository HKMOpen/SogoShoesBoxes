package com.hb.hkm.hypebeaststore.fragments.brandpage;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.hkm.hypebeaststore.R;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView.OnHeaderClickListener;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView.OnHeaderLongClickListener;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleArrayAdapter;

import java.util.ArrayList;

/**
 * Created by hesk on 3/17/15.
 */
public abstract class BrandListManager<DATAMODEL extends itemDisplay> extends Fragment implements AdapterView.OnItemClickListener, OnHeaderClickListener, OnHeaderLongClickListener {
    private static final String KEY_LIST_POSITION = "key_list_position";

    abstract protected ArrayList<DATAMODEL> getSource();

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int id) {
        }
    };

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    private int mFirstVisible;

    private GridView mGridView;

    private Menu mMenu;

    private Toast mToast;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BrandListManager() {
    }

    protected void performClick(int position) {

    }

    protected void attach(Callbacks e) {
        mCallbacks = e;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Activities containing this fragment must implement its callbacks.

        if (activity instanceof Callbacks)
            mCallbacks = (Callbacks) activity;

    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_item_list, menu);
        mMenu = menu;
        menu.findItem(R.id.menu_toggle_sticky).setChecked(((StickyGridHeadersGridView) mGridView).areHeadersSticky());
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slim_item_grid, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onHeaderClick(AdapterView<?> parent, View view, long id) {
        String text = "Header " + ((TextView) view.findViewById(android.R.id.text1)).getText() + " was tapped.";
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    @Override
    public boolean onHeaderLongClick(AdapterView<?> parent, View view, long id) {
        String text = "Header " + ((TextView) view.findViewById(android.R.id.text1)).getText() + " was long pressed.";
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> gridView, View view, int position, long id) {
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(position);
        performClick(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    private StickyGridHeadersSimpleArrayAdapter adaptered;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (GridView) view.findViewById(R.id.asset_grid);
        mGridView.setOnItemClickListener(this);
        /**
         * Currently set in the XML layout, but this is how you would do it in
         * your code.
         */
        // mGridView.setColumnWidth((int) calculatePixelsFromDips(100));
        // mGridView.setNumColumns(StickyGridHeadersGridView.AUTO_FIT);
       /*
         adaptered = new StickyGridHeadersSimpleArrayAdapter<F, com.neopixl.pixlui.components.textview.TextView>(
         getActivity().getApplicationContext(), getSource(),
         R.layout.fragment_slim_header_row, R.layout.item_slim_, com.neopixl.pixlui.components.textview.TextView.class);
        */
     /*   adaptered = new StickyGridHeadersSimpleArrayAdapter
                <DATAMODEL, com.neopixl.pixlui.components.textview.TextView>
                (
                        getActivity().getApplicationContext(),
                        getSource(),
                        R.layout.fragment_slim_header_row,
                        R.layout.item_slim_
                ); */

        adaptered = new SorterAdapterNameBrand
                (
                        getActivity().getApplicationContext(),
                        getSource(),
                        R.layout.fragment_slim_header_row,
                        R.layout.item_slim_
                );

        mGridView.setAdapter(adaptered);

        if (savedInstanceState != null) {
            mFirstVisible = savedInstanceState.getInt(KEY_LIST_POSITION, 0);
        }
        if (mFirstVisible > 0)
            mGridView.setSelection(mFirstVisible);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

        ((StickyGridHeadersGridView) mGridView).setOnHeaderClickListener(this);
        ((StickyGridHeadersGridView) mGridView).setOnHeaderLongClickListener(this);

        //initiate menu option
        // setHasOptionsMenu(false);
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mGridView.setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
        }
    }

    @SuppressLint("NewApi")
    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            mGridView.setItemChecked(mActivatedPosition, false);
        } else {
            mGridView.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(int position);
    }

    /**
     * when updated here is the implementation
     */
    public void update() {
        adaptered.notifyDataSetChanged();
    }
}