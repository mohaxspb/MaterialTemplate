package ru.kuchanov.material;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Method;

public class ActivityMain extends AppCompatActivity implements DrawerUpdateSelected
{
    protected static final String NAV_ITEM_ID = "NAV_ITEM_ID";
    private final static String LOG = ActivityMain.class.getSimpleName();
    protected Toolbar toolbar;
    protected NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected boolean drawerOpened;
    protected ViewPager pager;
    protected int checkedDrawerItemId;

    protected SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOG, "onCreate");

        //set theme before super and set content to apply it
//get default settings to get all settings later
        PreferenceManager.setDefaultValues(this, R.xml.pref_design, true);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, true);
        PreferenceManager.setDefaultValues(this, R.xml.pref_about, true);
        this.pref = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = (pref.getBoolean(ActivitySettings.PREF_KEY_NIGHT_MODE, false)) ? R.style.My_Theme_Dark : R.style.My_Theme_Light;
        this.setTheme(themeId);

        //call super after setTheme to set it 0_0


        super.onCreate(savedInstanceState);

        if (null == savedInstanceState)
        {
            checkedDrawerItemId = R.id.tab_1;
        }
        else
        {
            checkedDrawerItemId = savedInstanceState.getInt(NAV_ITEM_ID, R.id.tab_1);
        }

        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        this.pager = (ViewPager) this.findViewById(R.id.pager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
        {
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R.string.hello_world)
            {

                public void onDrawerClosed(View view)
                {
                    supportInvalidateOptionsMenu();
                    drawerOpened = false;
                }

                public void onDrawerOpened(View drawerView)
                {
                    drawerOpened = true;
                    updateNavigationViewState(checkedDrawerItemId);
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(mDrawerToggle);
        }
        NavigationViewOnNavigationItemSelectedListener navList;
        navList = new NavigationViewOnNavigationItemSelectedListener(this, drawerLayout, pager);
        navigationView.setNavigationItemSelectedListener(navList);

        this.updateNavigationViewState(this.checkedDrawerItemId);


        this.pager.setAdapter(new ru.kuchanov.material.PagerAdapter(this.getSupportFragmentManager(), 3));
        this.pager.addOnPageChangeListener(new ru.kuchanov.material.PagerAdapterOnPageChangeListener(this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 111111111111"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 222222222222"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 333333333333"));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new ru.kuchanov.material.TabLayoutOnTabSelectedListener(this, pager));

        final CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(this.getResources().getString(R.string.app_name));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Called whenever we call supportInvalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
//        Log.d(LOG, "onPrepareOptionsMenu called");

        //recreate navigationView's menu, uncheck all items and set new checked item
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.drawer);
        navigationView.getMenu().findItem(R.id.tab_1).setChecked(false);
        navigationView.getMenu().findItem(R.id.tab_2).setChecked(false);
        navigationView.getMenu().findItem(R.id.tab_3).setChecked(false);
        navigationView.setCheckedItem(checkedDrawerItemId);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
//        Log.d(LOG, "onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(LOG, "onOptionsItemSelected");

        int id = item.getItemId();

        boolean nightModeIsOn = this.pref.getBoolean(ActivitySettings.PREF_KEY_NIGHT_MODE, false);

        switch (id)
        {
            case R.id.action_settings:
                Intent intent = new Intent(this, ActivitySettings.class);
                this.startActivity(intent);
                return true;
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.night_mode_switcher:
                if (nightModeIsOn)
                {
                    this.pref.edit().putBoolean(ActivitySettings.PREF_KEY_NIGHT_MODE, false).commit();
                }
                else
                {
                    this.pref.edit().putBoolean(ActivitySettings.PREF_KEY_NIGHT_MODE, true).commit();
                }
                this.recreate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, this.checkedDrawerItemId);
    }

    @Override
    public void updateNavigationViewState(int checkedDrawerItemId)
    {
        this.checkedDrawerItemId = checkedDrawerItemId;
        supportInvalidateOptionsMenu();
    }

    //workaround from http://stackoverflow.com/a/30337653/3212712
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu)
    {
        if (menu != null)
        {
            if (menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch (Exception e)
                {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }

        boolean nightModeIsOn = this.pref.getBoolean(ActivitySettings.PREF_KEY_NIGHT_MODE, false);
        MenuItem themeMenuItem = menu.findItem(R.id.night_mode_switcher);
        if (nightModeIsOn)
        {
            themeMenuItem.setChecked(true);
        }

        return super.onPrepareOptionsPanel(view, menu);
    }
}