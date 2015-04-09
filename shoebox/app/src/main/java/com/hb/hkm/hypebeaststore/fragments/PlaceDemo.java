package com.hb.hkm.hypebeaststore.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hb.hkm.hypebeaststore.R;

/**
 * Created by hesk on 2/2/15.
 */
public class PlaceDemo extends Fragment {
    public PlaceDemo() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.fragment_store_front, container, false);
        return rv;
    }
}
