package com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.life.retent;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */


public class filterAdapter extends ArrayAdapter<TermWrap> {
    private OnSelectedCB listener;
    private final LayoutInflater inflater;
    private final Resources res;
    private final int itemLayout;
    public static final String TAG = "selection list adapter";
    public String filter_tag = "";

    public filterAdapter(Context context, int itemLayout, ArrayList<TermWrap> src) {
        super(context, itemLayout, R.id.label_field, src);
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        this.itemLayout = itemLayout;
    }

    public filterAdapter(Context context, int itemLayout, ArrayList<TermWrap> src, OnSelectedCB module_listener) {
        super(context, itemLayout, R.id.label_field, src);
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        this.itemLayout = itemLayout;
        listener = module_listener;
    }

    public void changeList(String tag) {
        filter_tag = tag;
        notifyDataSetChanged();
    }

    private void feedData(final ViewHolder vh, final int position) {
        final TermWrap p = getItem(position);
        vh.text.setText(p.toString());
        //   int colorResId = position % 2 == 0 ? R.color.even : R.color.odd;
        //   holder.text.setBackgroundColor(res.getColor(colorResId));
        if (p.isEnabled()) {
            vh.backdrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retent.msubmissionfilter.reset();
                    retent.msubmissionfilter.setFilterOption(filter_tag, p.toString(), position);
                    if (listener != null) listener.select_choice();
                }
            });
        } else {

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(itemLayout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        feedData(holder, position);
        return convertView;
    }


}