package com.hb.hkm.hypebeaststore.fragments.brandpage;

import android.util.Log;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hesk on 30/3/15.
 */
public class SortSimpleAdapterData<T extends itemDisplay> {

    public final static String pattern = "^([^A-Za-z]).+";
    public Pattern compiledMatcher;
    public final static String TAG = "_m_";
    public static final long[] SHARPGROUP = {
            48, 40, 51, 49, 52, 169, 40, 201
    };

    public SortSimpleAdapterData() {
        compiledMatcher = Pattern.compile(pattern);
    }

    public boolean isSharpGroup(char h) {
        Matcher m = compiledMatcher.matcher(h + "");
        while (m.find()) {
            return true;
        }

        return false;
    }

    public static int getFirstCharIndex(String e) {
        CharSequence charc = ((CharSequence) e.toUpperCase()).subSequence(0, 1);
        return charc.charAt(0);
    }

    public static String filterOutSharpGroup(String e) {
        int b = getFirstCharIndex(e);
        for (int i = 0; i < SortSimpleAdapterData.SHARPGROUP.length; i++) {
            if (SortSimpleAdapterData.SHARPGROUP[i] == b || b < 65) {
                return "#";
            }
        }
        return e;
    }

    public void sortA2Za2z(ArrayList<T> listingnow) {
        Collections.sort(listingnow, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                final String h1 = filterOutSharpGroup(lhs.toString().toUpperCase());
                final String h2 = filterOutSharpGroup(rhs.toString().toUpperCase());
                int order = h1.compareTo(h2);
                return order;
            }
        });
    }

   /*


    const int ColumnBase = 26;
    const int DigitMax = 7;

    // ceil(log26(Int32.Max))

    const String Digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String IndexToColumn(int index)
    {
        if (index <= 0)
            throw new IndexOutOfRangeException("index must be a positive number");

        if (index <= ColumnBase)
            return Digits[index - 1].ToString();

        StringBuilder sb = new StringBuilder().Append(' ', DigitMax);
        int current = index;
        var offset = DigitMax;
        while (current > 0)
        {
            sb[--offset] = Digits[--current % ColumnBase];
            current /= ColumnBase;
        }
        return sb.ToString(offset, DigitMax - offset);
    }


    */
}
