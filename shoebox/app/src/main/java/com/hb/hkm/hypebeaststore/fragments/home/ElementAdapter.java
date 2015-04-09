package com.hb.hkm.hypebeaststore.fragments.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.comcast.freeflow.core.FreeFlowItem;
import com.comcast.freeflow.core.Section;
import com.comcast.freeflow.core.SectionedAdapter;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.elementHome;
import com.neopixl.pixlui.components.imageview.ImageView;
import com.neopixl.pixlui.components.textview.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hesk on 3/16/15.
 */
public class ElementAdapter implements SectionedAdapter {

    public static final String TAG = "DribbbleDataAdapter";
    private Context context;
    private Section section;
    final private Picasso pic;
    private int[] colors = new int[]{0xcc152431, 0xff264C58, 0xffF5C543,
            0xffE0952C, 0xff9A5325, 0xaaE0952C, 0xaa9A5325, 0xaa152431,
            0xaa264C58, 0xaaF5C543, 0x44264C58, 0x44F5C543, 0x44152431};

    private boolean hideImages = false;

    public ElementAdapter(Context context) {
        this.context = context;
        section = new Section();
        section.setSectionTitle("Pics");
        pic = Picasso.with(context);
    }

    public void update(ArrayList<elementHome> feed) {

        for (elementHome o : feed) {
            section.getData().add(o);
        }

        Log.d(TAG, "Data updated to: " + section.getDataCount());
    }

    @Override
    public long getItemId(int section, int position) {
        return section * 1000 + position;
    }

    @Override
    public View getItemView(int sectionIndex, int position, View convertView,
                            ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_home_element, parent, false);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.bpImageView);
        //CircleProgressBar cb = (CircleProgressBar) convertView.findViewById(R.id.progressBar);
        TextView tv = (TextView) convertView.findViewById(R.id.text_title);
        if (hideImages) {
            int idx = position % colors.length;
            img.setBackgroundColor(colors[idx]);
        } else {
            elementHome s = (elementHome) (this.section.getData().get(position));
            pic.load(s.getThumbnail()).into(img);
            tv.setText(s.getName());

        }
        // cb.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public View getHeaderViewForSection(int section, View convertView,
                                        ViewGroup parent) {
        return null;
    }

    @Override
    public int getNumberOfSections() {
        if (section.getData().size() == 0) return 0;
        return 1;
    }

    @Override
    public Section getSection(int index) {
        return section;
    }

    @Override
    public Class[] getViewTypes() {
        return new Class[]{LinearLayout.class};
    }

    @Override
    public Class getViewType(FreeFlowItem proxy) {
        return LinearLayout.class;
    }

    @Override
    public boolean shouldDisplaySectionHeaders() {
        return false;
    }

}