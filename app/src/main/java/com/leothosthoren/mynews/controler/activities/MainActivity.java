package com.leothosthoren.mynews.controler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.view.adapters.ViewPageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();
        configureViewPagerAndTabs();
//        configureDrawerLayout();
//        configureNavigationView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void configureToolbar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.param_notifications:
                return true;
            case R.id.menu_activity_main_search:
                launchSearchArticlesActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
            this.drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    private void showParamMenu() {

        PopupMenu popupMenu = new PopupMenu(MainActivity.this, toolbar);
        popupMenu.getMenuInflater().inflate(R.menu.menu_activity_main, popupMenu.getMenu());
        popupMenu.show();
    }

    private void launchSearchArticlesActivity() {
        Intent intent = new Intent(MainActivity.this, SearchArticlesActivity.class);
        this.startActivity(intent);
    }

    private void configureViewPagerAndTabs() {

        //Get viewpager from layout
        ViewPager pager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        // 2 - Set Adapter ViewPageAdapter and glue it together
        pager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(),
                getResources().getIntArray(R.array.colorPagesViewPager)) {
        });
        //Tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tab);
        //Glue Tab et viewpager together
        tabLayout.setupWithViewPager(pager);
        //Width equals with tab
        tabLayout.setTabMode(TabLayout.GRAVITY_FILL);
    }


    //Menu drawer method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_news:
                break;
            case R.id.activity_main_drawer_profile:
                break;
            case R.id.activity_main_drawer_settings:
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


//    private void configureDrawerLayout() {
//        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
//                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//    }
//
//    private void configureNavigationView() {
//        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
//        assert navigationView != null;
//        navigationView.setNavigationItemSelectedListener(this);
//    }


}

