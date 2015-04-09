package com.hb.hkm.hypebeaststore;

import android.animation.LayoutTransition;
import android.content.res.Configuration;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.LifeCycleApp;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.model.SlidrPosition;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by hesk on 3/3/15.
 */
public class zoomimage extends ActionBarActivity implements SlidrInterface {
    public static final String URLKEY = "image_url_full";
    public static final String TITLE = "title_bar_text";
    public static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";
    private static final String LOG_TAG = "image-test";
    public static final String TAG = "ZOOMIMAGE";
    private PhotoView mImage;
    private Button mButton1;
    private Button mButton2;
    private ImageView close;
    private CheckBox mCheckBox;
    static int displayTypeCount = 0;
    private PhotoViewAttacher mAttacher;
    private TextView mCurrMatrixTv;
    private com.neopixl.pixlui.components.textview.TextView mCaptv;
    private SlidrInterface slidrInf;
    private LinearLayout cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SlidrConfig config_slider = new SlidrConfig.Builder()
                .primaryColor(getResources().getColor(R.color.primary_pref))
                .secondaryColor(getResources().getColor(R.color.primary_pref_v2))
                .position(SlidrPosition.LEFT)
                .sensitivity(Config.setting.drag_sensitivity)
                .build();
        final LayoutTransition transitioner = new LayoutTransition();
        setContentView(R.layout.act_zoom_image);
        cover = (LinearLayout) findViewById(R.id.bottom_caption);
        close = (ImageView) findViewById(R.id.closethis);
        transitioner.enableTransitionType(LayoutTransition.CHANGING);
        cover.setLayoutTransition(transitioner);
        slidrInf = Slidr.attach(this, config_slider);
        slidrInf.unlock();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            onSetLayout(b.getString(zoomimage.URLKEY), b.getString(zoomimage.TITLE));

        }
    }

    private void cover_off() {
        cover.animate().alpha(0f);
        close.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                close.setEnabled(false);
            }
        });
    }

    private void cover_on() {
        cover.animate().alpha(1f);
        close.animate().alpha(1f).withEndAction(new Runnable() {
            @Override
            public void run() {
                close.setEnabled(true);
            }
        });
    }

    private void onSetLayout(final String image_url, final String cctv) {
        mImage = (PhotoView) findViewById(R.id.vi);
        mCurrMatrixTv = (TextView) findViewById(R.id.debugtv);
        // mCurrMatrixTv.setText(cctv);
        mCaptv = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.captiontv);
        mCaptv.setText(cctv);
        mCaptv.setTextColor(getResources().getColor(R.color.common_background));

        final CircleProgressBar circle = (CircleProgressBar) findViewById(R.id.loading_bar);
        circle.setCircleBackgroundEnabled(false);
        circle.setShowArrow(false);
        circle.setColorSchemeResources(R.color.second_pref);
        final Picasso pica = ((LifeCycleApp) getApplication()).getInstancePicasso();
        Log.d(LOG_TAG, "load image with url : " + image_url + " title:" + cctv);
        pica.load(image_url).into(mImage, new Callback() {
            @Override
            public void onSuccess() {
                mAttacher = new PhotoViewAttacher(mImage);
                mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
                mAttacher.setOnPhotoTapListener(new PhotoTapListener());
                circle.setVisibility(View.GONE);


                mImage.post(new Runnable() {
                    @Override
                    public void run() {
                        mAttacher.setScale(2f, mImage.getWidth() / 2, mImage.getHeight() / 2, true);
                    }
                });

                //slidrInf.unlock();
                mAttacher.setScale(1.5f);

            }

            @Override
            public void onError() {
                circle.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void lock() {
        Log.d(TAG, "lock in here");
    }

    @Override
    public void unlock() {
        Log.d(TAG, "unlock in here");
    }

    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;
            //    Tool.trace(zoomimage.this,  String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }

    private class MatrixChangeListener implements PhotoViewAttacher.OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
            try {
                if (mAttacher.getScale() > 1.5f) cover_on();
                else cover_off();

                if (mAttacher.getScale() == 1.0f) slidrInf.unlock();
                else slidrInf.lock();

            } catch (Exception e) {
                Log.d(TAG, "onMatrix Changed" + e.getMessage());
            }
        }
    }

}
