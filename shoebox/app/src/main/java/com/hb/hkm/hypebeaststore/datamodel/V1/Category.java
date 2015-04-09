package com.hb.hkm.hypebeaststore.datamodel.V1;

/**
 * Created by hesk on 2/3/15.
 */
public class Category {
    private int id;
    private String name;
    private String slug;
    private String permalink;
    private String description;
    private int level;
    private int parent_id;
    private int taxonomy_id;

    public Category() {
    }

    public String getNameStr() {
        return name;
    }

    public String getSlug() {
        return slug;
    }
}
