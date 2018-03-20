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
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.view.adapters.ViewPageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
// Todo : create abstract class fragment
// Todo : display a snackbar or personalised toast message
// to alert there is no connection or that user have to select something
// Todo : search activity use fragment to show recycler view list
// Todo : create a class to handle search and configuration (edit text and checkbox)
// Todo : manage to build a generic recyclerview and use the same view to display the web page
// Todo : check if the connection is ok otherwise display a message OK
// Todo : build a view pager with fragment and tablayout if the generic rv doesn't work
// Todo : add two textview in the search layout OK
// Todo : create a fragment or an activity for the configuration option and reuse graphic fragment
// Todo : add test unit
// Todo : try to respect the material design specification
// Todo : add the menu drawer in main activity
// Todo : check the styles and @dimen values
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
            //Open a fragment
            case R.id.param_notifications:
                Toast.makeText(this, "Test menu",Toast.LENGTH_SHORT).show();
                return true;
            //Open searchActivity
            case R.id.menu_activity_main_search:
                launchSearchArticlesActivity();
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
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
            this.drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

//    private void showParamMenu() {
//
//        PopupMenu popupMenu = new PopupMenu(MainActivity.this, toolbar);
//        popupMenu.getMenuInflater().inflate(R.menu.menu_activity_main, popupMenu.getMenu());
//        popupMenu.show();
//    }

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

