package com.hb.hkm.hypebeaststore.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hb.hkm.hypebeaststore.R;

/**
 * Created by hesk on 2/2/15.
 */
public class MenuList extends ListFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.l_list, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SampleAdapter adapter = new SampleAdapter(getActivity());
        for (int i = 0; i < 20; i++) {
            adapter.add(new SampleItem("Sample List", android.R.drawable.ic_menu_search));
        }
        setListAdapter(adapter);
    }

    private class SampleItem {
        public String tag;
        public int iconRes;

        public SampleItem(String tag, int iconRes) {
            this.tag = tag;
            this.iconRes = iconRes;
        }
    }

    public class SampleAdapter extends ArrayAdapter<SampleItem> {
        private final static String TAG = "ArrayAdapter_sample";

        public SampleAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private int get_item_layout(final int pos) {
            return R.layout.l_row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(get_item_layout(position), null);
            }
            // ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
            // icon.setImageResource(getItem(position).iconRes);
            TextView title = (TextView) convertView.findViewById(R.id.tv);
            title.setText(getItem(position).tag);

            Log.d(TAG, position + " -item number; ");

            return convertView;
        }

    }
}
