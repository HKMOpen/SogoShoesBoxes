package com.hb.hkm.hypebeaststore;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neopixl.pixlui.components.textview.TextView;

/**
 * Created by hesk on 2/11/15.
 */
public class HBLogin extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener {
    /**
     * A handler object, used for deferring UI operations.
     */
    private Handler mHandler = new Handler();

    /**
     * Whether or not we're showing the back of the card (otherwise showing the front).
     */
    private boolean mShowingBack = false;


    /**
     * A fragment representing the front of the card.
     */
    @SuppressLint("ValidFragment")
    public static class CardFrontFragment extends Fragment {
        private HBLogin controllor;

        public CardFrontFragment(HBLogin c) {
            controllor = c;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_front_login, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            TextView regTv = (TextView) view.findViewById(R.id.switch_to_registration);
            regTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controllor.flipCard();
                }
            });
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    @SuppressLint("ValidFragment")
    public static class CardBackFragment extends Fragment {
        public CardBackFragment(HBLogin c) {
            controllor = c;
        }

        private HBLogin controllor;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_back_registration, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            TextView regTv = (TextView) view.findViewById(R.id.switch_to_login);
            regTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controllor.flipCard();
                }
            });
        }
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();
    }

    public void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        getFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.container, new CardBackFragment(this))

                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();

        // Defer an invalidation of the options menu (on modern devices, the action bar). This
        // can't be done immediately because the transaction may not yet be committed. Commits
        // are asynchronous in that they are posted to the main thread's message loop.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        if (savedInstanceState == null) {
            // If there is no saved instance state, add a fragment representing the
            // front of the card to this activity. If there is saved instance state,
            // this fragment will have already been added to the activity.
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment(this))
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the appropriate
        // button (either "photo" or "info").
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    /*
      static class clientWebInterface {
          private Context ctx;
          public clientWebInterface(Context ctx) {
              this.ctx = ctx;
          }
          public void showHTML(String html) {
              new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                      .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
          }
      }
      @SuppressLint("AddJavascriptInterface")
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.fragment_store_demo_webview);
          final WebView webview = (WebView) findViewById(R.id.store_webview);
          webview.getSettings().setJavaScriptEnabled(true);
          webview.addJavascriptInterface(new clientWebInterface(this), "HtmlViewer");
          webview.setWebViewClient(new com.hb.hkm.hypebeaststore.components.webviewclients.HBLogin(webview));
          webview.loadUrl(Config.wv.hb_login);
      }

    public void onCardClick(View view){
        flipCard();
    }
    */
    /*private void flipCard() {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);
        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    private View rootLayout, cardFace, cardBack;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        rootLayout = (View) findViewById(R.id.main_activity_root);
        cardFace = (View) findViewById(R.id.main_activity_card_face);
        cardBack = (View) findViewById(R.id.main_activity_card_back);
        b = (Button) findViewById(R.id.new_item_here);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
    }*/
}
