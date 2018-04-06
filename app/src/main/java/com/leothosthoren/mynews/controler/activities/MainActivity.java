package com.leothosthoren.mynews.controler.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.ModelTools;
import com.leothosthoren.mynews.view.adapters.ViewPageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private ModelTools mTools = new ModelTools();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();
        configureViewPagerAndTabs();
        configureDrawerLayout();
        configureNavigationView();

    }

    /*
    * @method onCreateOptionsMenu
    * @param menu use to inflate a menu layout
     *
     * This method create a menu option in the toolbar when the activity is launched
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    /*
    * @method configureToolbar
    *
    * This method call the toolbar layout and display it in the actionbar
    * */
    private void configureToolbar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /*
    * @method onOptionsItemSelected
    * @param item the MenuItem type allows to call getItemId method
    *
    * This method perform an action when user select one item among multiple choice
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Open NotificationActivity
            case R.id.param_notifications:
                Toast.makeText(this, "Test menu", Toast.LENGTH_SHORT).show();
                return true;
            //Open SearchActivity
            case R.id.menu_activity_main_search:
                launchSearchArticlesActivity();
                return true;
            //Open browser...
            case R.id.param_about:
                mTools.openActivityAsBrowser("https://www.android.com", this, WebViewActivity.class);
                return true;
            case R.id.param_help:
                mTools.openActivityAsBrowser("https://www.google.fr", this, WebViewActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    * @method onBackPressed
    *
    * when user click on the back pressed button the menu drawer is closed (if it's open)
    * */
    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START))
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    /*
    * @launchSearchArticlesActivity
    *
    * Simple method which start of a new activity
    * */
    private void launchSearchArticlesActivity() {
        Intent intent = new Intent(MainActivity.this, SearchArticlesActivity.class);
        this.startActivity(intent);
    }

    /*
    * @method configureViewPagerAndTabs
    *
    * This method configure the tabLayout and his adapter
    * */
    private void configureViewPagerAndTabs() {

        //Get viewpager from layout
        ViewPager pager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        //Set Adapter ViewPageAdapter and glue it together
        pager.setAdapter(new ViewPageAdapter(getSupportFragmentManager()) {
        });
        //Tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tab);
        //Glue Tab et viewpager together
        tabLayout.setupWithViewPager(pager);
        //Handle the tab's width and mobility
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    /*
    * @method onNavigationItemSelected
    * @param item
    *
    * This method allow to choose between the different option offered in a menu
     * Ever item perform a new action
    * */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_news:
                launchSearchArticlesActivity();
                break;
            case R.id.activity_main_drawer_notifications:
                //launch search notification activity
                break;
            case R.id.activity_main_drawer_help:
                mTools.openActivityAsBrowser("https://www.google.com", this, WebViewActivity.class);
                break;
            case R.id.activity_main_drawer_about:
                mTools.openActivityAsBrowser("https://www.android.com", this, WebViewActivity.class);
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    private void configureDrawerLayout() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        this.mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        assert mNavigationView != null;
        this.mNavigationView.setNavigationItemSelectedListener(this);
    }


}

