package com.hb.hkm.hypebeaststore.fragments.brandpage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.filterAdapter;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.onApplyFilterTrigger;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;

import java.util.ArrayList;

/**
 * Created by hesk on 2/24/15.
 */
public class FilterOldFragment extends Fragment {
    private View groupview_footer;
    private JazzyGridView search_list;
    private filterAdapter madapter;
    private ArrayList<String> selections;
    public static final String
            ARRAYLIST = "ARRAY_VAL",
            CHOICE = "CHOICE_VAL";

/*

singleton

    public static FilterOldFragment getInstance(String[] val, int which) {
        FilterOldFragment instance = new FilterOldFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARRAYLIST, val);
        args.putInt(CHOICE, which);
        instance.setArguments(args);
        return instance;
    }

*/


    public FilterOldFragment() {

    }

    private String[] listval;
    private int m;

    public void setContent(String[] val, int which) {
        listval = val;
        m = which;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    public void updateNewList(String tag, ArrayList<TermWrap> l) {
        wordList.clear();
        wordList.addAll(l);
        madapter.changeList(tag);
    }

    public void setHeight(int resId) {
        final LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(-1, resId);
        // search_list.setLayoutParams(l);
        // search_list.requestLayout();
    }


    private final ArrayList<TermWrap> wordList = new ArrayList<>();
    private onApplyFilterTrigger trigger;

    public void setTriggerApplier(onApplyFilterTrigger e) {
        trigger = e;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        search_list = (JazzyGridView) v.findViewById(android.R.id.list);
        madapter = new filterAdapter(getActivity(), R.layout.item_filter_, wordList);

        search_list.setAdapter(madapter);
        search_list.setTransitionEffect(JazzyHelper.SLIDE_IN);
        search_list.setShouldOnlyAnimateNewItems(false);

    }


}
