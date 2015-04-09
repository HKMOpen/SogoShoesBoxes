package com.hb.hkm.hypebeaststore.fragments.brandpage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.OnSelectedCB;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.filterAdapter;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.onApplyFilterTrigger;
import com.hb.hkm.hypebeaststore.life.retent;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;

import java.util.ArrayList;

/**
 * normal selection list for a single item.
 *
 * Created by hesk on 2/24/15.
 */
public class FilterNormalListFragment extends Fragment implements OnSelectedCB {
    private JazzyGridView search_list;
    private filterAdapter madapter;
    private String Tag;
    public static final String
            ARRAYLIST = "ARRAY_VAL",
            CHOICE = "CHOICE_VAL";
    private final ArrayList<TermWrap> wordList = new ArrayList<>();
    private onApplyFilterTrigger trigger;

    protected ArrayList<TermWrap> getSource() {
        return wordList;
    }

    /**
     * singleton
     * <p/>
     */
    public static FilterNormalListFragment getInstance(String tag, int which, ArrayList<TermWrap> src) {
        FilterNormalListFragment instance = new FilterNormalListFragment();
        instance.updateNewList(tag, src);
        return instance;
    }

    public FilterNormalListFragment() {

    }

    @Override
    public void onDestroy() {

        if (getChildFragmentManager().isDestroyed()) {
        }
        super.onDestroy();
    }

    private void updateNewList(String tag, ArrayList<TermWrap> l) {
        wordList.clear();
        wordList.addAll(l);
        Tag = tag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }


    public void setHeight(int resId) {
        final LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(-1, resId);
    }


    public FilterNormalListFragment setTriggerApplier(onApplyFilterTrigger e) {
        trigger = e;
        return this;
    }


    @Override
    public void onViewCreated(View v, Bundle b) {
        search_list = (JazzyGridView) v.findViewById(android.R.id.list);
        madapter = new filterAdapter(getActivity(), R.layout.item_filter_, getSource(), this);
        madapter.changeList(Tag);
        search_list.setAdapter(madapter);
        search_list.setTransitionEffect(JazzyHelper.FADE);
        search_list.setShouldOnlyAnimateNewItems(false);
    }

    @Override
    public void select_choice() {
        retent.msubmissionfilter.filterapply();
        if (trigger != null) trigger.apply_action();
    }
}
