package com.hb.hkm.hypebeaststore.expansionmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.hb.hkm.hypebeaststore.R;

/**
 * Created by hesk on 2/12/15.
 */

public class Main extends Fragment {
    public Main() {

    }

    private TabHost mTabHost;

    public Main addMenu() {

        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.l_top_menu, container, false);
        //TabHost mTabHost = getTabHost();

        return rv;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        // mTabHost = (TabHost) v.findViewById(R.id.)

     /*   mTabHost.addTab(mTabHost.newTabSpec("first").setIndicator("First").setContent(new Intent(this, FirstActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("second").setIndicator("Second").setContent(new Intent(this, SecondActivity.class)));
     */
        mTabHost.setCurrentTab(0);
    }
}
