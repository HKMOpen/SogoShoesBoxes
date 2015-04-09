package com.hb.hkm.hypebeaststore.fragments.home;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.comcast.freeflow.core.FreeFlowItem;
import com.comcast.freeflow.core.Section;
import com.comcast.freeflow.layouts.FreeFlowLayout;
import com.comcast.freeflow.layouts.FreeFlowLayoutBase;
import com.comcast.freeflow.utils.ViewUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hesk on 3/16/15.
 */
public class HBHLayout extends FreeFlowLayoutBase implements FreeFlowLayout {

    private static final String TAG = "ArtBookLayout";
    private int item_height;
    private int largeItemSide;
    private int regularItemSide;
    private int rightside;
    private int half_w;
    private Point display_size_patched;

    public HBHLayout(Point size) {
        display_size_patched = size;
    }

    @Override
    public void setDimensions(int measuredWidth, int measuredHeight) {
        // super.setDimensions(display_size_patched.x, display_size_patched.y);
        super.setDimensions(measuredWidth, measuredHeight - display_size_patched.y);
        largeItemSide = measuredWidth / 2;
        regularItemSide = measuredWidth / 4;
        item_height = measuredWidth / 2;
        rightside = measuredWidth;
        half_w = measuredWidth / 2;
        Log.d(TAG, "tag: " + item_height + " right: " + rightside);
    }

    private HashMap<Object, FreeFlowItem> map;
    private Section s;
    private int pattern_total = 8;

    @Override
    public void prepareLayout() {
        Log.d(TAG, "prepare layout!!!");
        map = new HashMap<Object, FreeFlowItem>();
        s = itemsAdapter.getSection(0);
        int rowIndex;
        Log.d(TAG, "prepare layout for: " + s.getDataCount());
        for (int i = 0; i < s.getDataCount(); i++) {
            rowIndex = i / pattern_total;

            FreeFlowItem p = new FreeFlowItem();
            p.isHeader = false;
            p.itemIndex = i;
            p.itemSection = 0;
            p.data = s.getDataAtIndex(i);

            Rect r = new Rect();
            Log.d(TAG, "prepare rowIndex: " + rowIndex);
            switch (i) {
                case (0):
                    r.left = 0;
                    r.top = i * item_height;
                    r.right = rightside;
                    r.bottom = r.top + item_height;

                   /* if (rowIndex % 2 != 0) {
                        r.offset(largeItemSide, 0);
                    }*/
                    Log.d(TAG, "Rec: " + r.flattenToString());
                    break;

                case (1):
                    r.left = 0;
                    r.top = i * item_height;
                    r.right = rightside;
                    r.bottom = r.top + item_height;
/*
                    if (rowIndex % 2 != 0) {
                        r.offset(-largeItemSide, 0);
                    }*/
                    break;

                case (2):
                    r.left = 0;
                    r.right = half_w;
                    r.top = 2 * item_height;
                    r.bottom = r.top + item_height;
                 /*   if (rowIndex % 2 != 0) {
                        r.offset(-largeItemSide, 0);
                    }*/
                    break;

                case (3):
                    r.left = half_w;
                    r.right = r.left + half_w;
                    r.top = 2 * item_height;
                    r.bottom = r.top + item_height;
                    break;

                case (4):
                    r.left = 0;
                    r.right = rightside;
                    //   r.top = rowIndex * largeItemSide + regularItemSide;
                    r.top = 3 * item_height;
                    r.bottom = r.top + item_height;
                     /* if (rowIndex % 2 != 0) {
                        r.offset(-largeItemSide, 0);
                    }*/
                    break;

                case (5):
                    r.left = 0;
                    r.right = rightside;
                    r.top = 4 * item_height;
                    r.bottom = r.top + item_height;
                    break;
                default:
                    break;
            }
            p.frame = r;
            map.put(s.getDataAtIndex(i), p);
        }
    }

    @Override
    public HashMap<Object, FreeFlowItem> getItemProxies(int viewPortLeft, int viewPortTop) {
        final Rect viewport = new Rect(viewPortLeft,
                viewPortTop,
                viewPortLeft + width,
                viewPortTop + height);
        Log.d(TAG, "Viewport: " + viewPortLeft + ", " + viewPortTop + ", " + viewport.width() + "," + viewport.height());
        HashMap<Object, FreeFlowItem> ret = new HashMap<Object, FreeFlowItem>();
        Iterator<Map.Entry<Object, FreeFlowItem>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, FreeFlowItem> pairs = it.next();
            FreeFlowItem p = (FreeFlowItem) pairs.getValue();
            if (Rect.intersects(p.frame, viewport)) {
                ret.put(pairs.getKey(), p);
            }
        }
        return ret;
    }

    @Override
    public FreeFlowItem getFreeFlowItemForItem(Object item) {
        Log.d(TAG, " returing item: " + map.get(item));
        return map.get(item);
    }

    @Override
    public int getContentWidth() {
        return 0;
    }

    @Override
    public int getContentHeight() {
        return (s.getDataCount() - 1) * item_height;
    }

    @Override
    public FreeFlowItem getItemAt(float x, float y) {
        return (FreeFlowItem) ViewUtils.getItemAt(map, (int) x, (int) y);
    }

    @Override
    public void setLayoutParams(FreeFlowLayoutParams params) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean verticalScrollEnabled() {
        return true;
    }

    @Override
    public boolean horizontalScrollEnabled() {
        return false;
    }
}
