package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;
import android.util.Log;

import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.sectionbind;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hesk on 2/27/15.
 */
public class HomePageJQuery extends HTMLDomReader {
    public static String TAG = "HomePageMiner";
    public static final String image_file_pattern = "(http:\\/\\/).*(700x467.jpg)";

    public HomePageJQuery(Context ccc, callback cb) {
        super(ccc, cb);
    }

    /**
     * image url / href of the product
     *
     * @return
     */
    public HashMap<String, String> getSliderImages() {
        HashMap<String, String> n = new HashMap<String, String>();
        try {
            Elements topicList = bigDoc.body().select("#homepage-banners .slides>li>a");
            // original pattern on http://www.regexr.com/ [/(http:\/\/).*(700x467.jpg)/g] - expression
            // pattern saved [(http:\/\/).*(700x467.jpg)]
            Pattern p = Pattern.compile(image_file_pattern);
            for (Element t : topicList) {
                Matcher m = p.matcher(t.select("img").attr("data-src"));
                // Log.d(TAG, "Matcher : " + m.group(1));
                while (m.find()) { // Find each match in turn; String can't do this.
                    String imageLoc = m.group(); // Access a submatch group; String can't do this.
                    String productlink = t.attr("href");
                    Log.d(TAG, imageLoc + " : " + productlink);
                    n.put(imageLoc, productlink);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error : " + e.getMessage());
        }
        return n;
    }

    public ArrayList<sectionbind> getStackItemPackages() {
        ArrayList<sectionbind> n = new ArrayList<sectionbind>();
        try {
            Elements cat = bigDoc.body().select("#container .category-section");
            for (Element element : cat) {
                String title = element.select(".heading-container>h3").text();
                String imgurl = element.select(".category-image img").attr("src");
                String link = element.select(".category-image a.category-link").attr("href");
                String banner_title = element.select(".category-image .banner-text").text();
                sectionbind s = new sectionbind();
                s.title = title;
                s.banner_url = imgurl;
                s.banner_text = banner_title;
                Elements list = element.select(".category-brands.hidden-xs ul>li>a");
                for (Element el : cat) {
                    HashMap<String, String> h = new HashMap<String, String>();
                    h.put(el.text(), el.attr("href"));
                    s.hash = h;
                }
                Log.d(TAG, title);
                n.add(s);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error : " + e.getMessage());
        }
        return n;
    }
}
