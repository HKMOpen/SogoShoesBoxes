package net.giancarlofusiello.materialtabslibrary;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    private Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    private FragmentManager mFragmentManager;
    private MyAdapter mMyAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);

        mFragmentManager = getSupportFragmentManager();
        mMyAdapter = new MyAdapter(mFragmentManager);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mMyAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < mMyAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost
                            .createInteractiveTab(mMyAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {

        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {


    }
}
