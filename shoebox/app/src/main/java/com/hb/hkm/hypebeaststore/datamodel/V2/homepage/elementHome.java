package com.hb.hkm.hypebeaststore.datamodel.V2.homepage;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asynhkm.utility.Measures;
import com.hb.hkm.hypebeaststore.life.Config;

import io.realm.RealmObject;

/**
 * RealmObject
 * Created by hesk on 3/10/15.
 */
public class elementHome extends RealmObject {
    private String name;
    private String slug;
    private int width;
    private int height;
    private String thumbnail;
    private String ipad_landscape_thumbnail;
    private int ipad_landscape_width;
    private int ipad_landscape_height;
    private String ipad_portrait_thumbnail;
    private int ipad_portrait_width;
    private int ipad_portrait_height;

    public elementHome() {
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getIpad_landscape_thumbnail() {
        return ipad_landscape_thumbnail;
    }

    public String getIpad_portrait_thumbnail() {
        return ipad_portrait_thumbnail;
    }

    public int getIpad_landscape_width() {
        return ipad_landscape_width;
    }

    public int getIpad_landscape_height() {
        return ipad_landscape_height;
    }

    public int getIpad_portrait_width() {
        return ipad_portrait_width;
    }

    public int getIpad_portrait_height() {
        return ipad_portrait_height;
    }

    public void setName(String b) {
        name = b;
    }

    public void setSlug(String b) {
        slug = b;
    }

    public void setWidth(int b) {
        width = b;
    }

    public void setHeight(int b) {
        height = b;
    }

    public void setThumbnail(String b) {
        thumbnail = b;
    }

    public void setIpad_landscape_thumbnail(String b) {
        ipad_landscape_thumbnail = b;
    }

    public void setIpad_portrait_thumbnail(String b) {
        ipad_portrait_thumbnail = b;
    }

    public void setIpad_landscape_width(int b) {
        ipad_landscape_width = b;
    }

    public void setIpad_landscape_height(int b) {
        ipad_landscape_height = b;
    }

    public void setIpad_portrait_width(int b) {
        ipad_portrait_width = b;
    }

    public void setIpad_portrait_height(int b) {
        ipad_portrait_height = b;
    }

/*
    private boolean fullwidth() {
        return width == 320;
    }

    public LinearLayout.LayoutParams get_element_layout_land(Context context) {
        if (fullwidth()) {
            return new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Measures.pxToDp(height, context)
                    , 1.f);
        } else {
            return new LinearLayout.LayoutParams(
                    Measures.pxToDp(width, context),
                    Measures.pxToDp(height, context)
                    , 1.f);
        }
    }*/


}
