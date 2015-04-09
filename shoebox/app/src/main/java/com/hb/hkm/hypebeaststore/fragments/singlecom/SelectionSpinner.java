package com.hb.hkm.hypebeaststore.fragments.singlecom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.SelectView;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Product;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Variant;
import com.hkm.ui.processbutton.ProcessButton;
import com.hkm.ui.processbutton.iml.ActionProcessButton;


import org.jsoup.parser.Tag;

import java.util.ArrayList;

import hkm.ui.ddbox.lib.Droptouch;

/**
 * Created by hesk on 2/11/15.
 */
public class SelectionSpinner implements View.OnClickListener {
    private ProductSingle mcontext;
    private int chk_color = 0, chk_size = 0, chk_qty = 0;
    private Button sp_variants, sp_size, sp_qty;
    private String[] color, size, qty;
    private ArrayList<Variant> variances = new ArrayList<>();
    private ArrayList<Droptouch> listopt = new ArrayList<>();
    private int choice_of_variance = 0, choice_of_quantity = 0;
    private ActionProcessButton add_bag, add_wish;
    private LinearLayout container;
    public final static String TAG = "showlist";
    public final static int T_COLOR = 11, T_SIZE = 12, T_QTY = 13;
    private static float[] setting1 = {1.0f};
    private static float[] setting2 = {0.6f, 0.4f};
    private static float[] setting3 = {0.5f, 0.3f, 0.1f};
    private static int BEGINNING = 0, LAST = -1;

    enum supporttype {
        VARIANT, SIZE, QTY
    }

    public SelectionSpinner(final ProductSingle act) {
        mcontext = act;
        container = (LinearLayout) act.findViewById(R.id.l_variants);
        add_bag = (ActionProcessButton) act.findViewById(R.id.add_to_bag);
    }

    private void addSpace() {
        final Droptouch dt = new Droptouch(mcontext);
        container.addView(dt.getSpaceDimenId(R.dimen.button_margin_horizontal));
    }

    private void buttonConstruct(int tag_integer, String label, float portion) {
        final Droptouch dt = new Droptouch(mcontext);
        dt.setLabel(label);
        dt.setTag(tag_integer);
        if (portion < 1.0f) {
            dt.setPortionAuto(portion);
        }
        dt.setOnClickListener(this);
        listopt.add(dt);
        container.addView(dt);
    }


    public SelectionSpinner addVariance(ArrayList<Variant> list) {
        final ArrayList<String> colorlist = new ArrayList<>();
        final ArrayList<String> sizelist = new ArrayList<>();
        for (Variant t : list) {
            final String r = t.getMetaValueFromType() + t.stock_logic_configuration();
            if (t.getType().equalsIgnoreCase("color")) {
                colorlist.add(r);
            } else if (t.getType().equalsIgnoreCase("size")) {
                sizelist.add(r);
            }
        }

        if (colorlist.size() > 0) {
            color = colorlist.toArray(new String[colorlist.size()]);
        }
        if (sizelist.size() > 0) {
            size = sizelist.toArray(new String[sizelist.size()]);
        }
        variances.clear();
        variances.addAll(list);
        return this;
    }

    private void addQtybutton(float portion) {
        qty = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };
        buttonConstruct(T_QTY, "Qty", portion);
    }

    public SelectionSpinner noVariance(Product p) {
        choice_of_variance = p.getVariantID(0);
        return this;
    }

    public void init() {
        chk_color = color == null ? -1 : 1;
        chk_size = size == null ? -1 : 1;
        listopt.clear();
        if (color == null && size == null) {
            addQtybutton(setting1[0]);
        }
        if (color != null && size != null) {
            buttonConstruct(T_COLOR, "Color", setting3[0]);
            addSpace();
            buttonConstruct(T_SIZE, "Size", setting3[1]);
            addSpace();
            addQtybutton(setting2[1]);

        }
        if (color != null && size == null) {
            buttonConstruct(T_COLOR, "Color", setting2[0]);
            addSpace();
            addQtybutton(setting2[1]);
        } else {
            buttonConstruct(T_SIZE, "Size", setting2[0]);
            addSpace();
            addQtybutton(setting2[1]);
        }


        setQty("1");
        add_bag.setEnabled(false);
        container.setWeightSum(1.0f);
        container.requestLayout();
    }

    public boolean foundSize() {
        return chk_size != -1;
    }


    private boolean instocksize = true, instockcolor = true;

    private void set_value_option() {
        add_bag.setEnabled(instocksize && instockcolor);
    }

    public boolean setOption(String set_value, int selected_pos, int kind) {
        String search = "";
        if (T_SIZE == kind) {
            search = size[selected_pos];
        } else if (T_COLOR == kind) {
            search = color[selected_pos];
        }

        for (final Variant t : variances) {

            final String r = t.getMetaValueFromType() + t.stock_logic_configuration();
            if (search.equalsIgnoreCase(r)) {
                choice_of_variance = t.getId();
                if (T_SIZE == kind) {
                    instocksize = t.instock();
                    setSize(set_value);
                    set_value_option();
                    return true;
                } else if (T_COLOR == kind) {
                    instockcolor = t.instock();
                    setColor(color[selected_pos]);
                    set_value_option();
                    return true;
                }

            }
        }
        return false;
    }

    public int theVarianceChoice() {
        return choice_of_variance;
    }

    /**
     * to get the current number of item that selected.
     *
     * @return
     */
    public int theQtyChoice() {
        return choice_of_quantity;
    }

    public SelectionSpinner setSize(String t) {
        Droptouch f = findByTag(T_SIZE);
        f.setLabel("S:" + t);
        return this;
    }

    public SelectionSpinner setColor(String t) {
        Droptouch f = findByTag(T_COLOR);
        f.setLabel("C: " + t);
        return this;
    }

    public SelectionSpinner setQty(String t) {
        Droptouch c = findByTag(T_QTY);
        c.setLabel("Qty: " + t);
        choice_of_quantity = Integer.parseInt(t);
        if (size != null && !instocksize) {
            add_bag.setEnabled(false);
        } else if (color != null && !instockcolor) {
            add_bag.setEnabled(false);
        }
        return this;
    }

    private SelectionSpinner setQty(int t) {
        Droptouch c = findByTag(T_QTY);
        c.setLabel("Qty: " + t);
        choice_of_quantity = t;
        if (size != null && !instocksize) {
            add_bag.setEnabled(false);
        } else if (color != null && !instockcolor) {
            add_bag.setEnabled(false);
        }
        return this;
    }

    private Droptouch findByTag(int OJECT_ID) {
        for (Droptouch t : listopt) {
            if ((int) t.getTag() == OJECT_ID) {
                return t;
            }
        }
        return null;
    }

    public void setEnabled(boolean b) {
        for (Droptouch t : listopt) {
            t.setEnabled(b);
        }
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case T_COLOR:
                if (color != null)
                    // start_intent(SelectView.COLOR, color);
                    showListChoices("Color", color, T_COLOR);
                break;

            case T_SIZE:
                if (size != null)
                    //    start_intent(SelectView.SIZE, size);
                    showListChoices("Size", size, T_SIZE);

                break;

            case T_QTY:
                if (qty != null)
                    // start_intent(SelectView.QTY, qty);
                    showDialogPickerNumber();
                break;
        }
    }

    /**
     * shows the number picker dialog at the screen
     */
    private void showDialogPickerNumber() {
        final Dialog d = new Dialog(mcontext);
        d.setTitle("Quantity");
        d.setContentView(R.layout.dialog_picker);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        ProcessButton b = (ProcessButton) d.findViewById(R.id.set_process_button);
        np.setMaxValue(99); // max value 100
        np.setMinValue(1);   // min value 0
        np.setWrapSelectorWheel(false);
        /*np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, oldVal + " : " + newVal);
            }
        });*/
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "display now" + " : " + np.getValue());
                //  tv.setText(String.valueOf(np.getValue())); //set the value to textview
                setQty(np.getValue());
                d.dismiss();
            }
        });
        d.show();
    }

    /**
     * show the list choices now
     *
     * @param title
     * @param items
     */
    private void showListChoices(final String title, final String[] items, final int symbo) {
        final AlertDialog.Builder listchoices = new AlertDialog.Builder(mcontext);
        listchoices.setTitle(title).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(mcontext, "U clicked " + items[which], Toast.LENGTH_LONG).show();
                switch (symbo) {
                    case T_COLOR:
                        setOption(items[which], which, T_COLOR);
                        break;
                    case T_SIZE:
                        setOption(items[which], which, T_SIZE);
                        break;
                }
            }
        });
        listchoices.show();
    }

    /**
     * start with the new intent with the special list items
     *
     * @param action_type
     * @param stringlist
     */
    private void start_intent(final int action_type, String[] stringlist) {
        final Intent it = new Intent(mcontext, SelectView.class);
        final Bundle f = new Bundle();
        f.putInt(SelectView.selection_view, action_type);
        if (stringlist != null) {
            if (stringlist.length > 0) {
                f.putStringArray(SelectView.arraystring, stringlist);
            }
        }

        it.putExtras(f);
        mcontext.startActivityForResult(it, SelectView.ACTION_ON_SELECT);
    }
}
