package ru.kuchanov.material;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

public class NavigationViewOnNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerUpdateSelected callbackDrawerSelected;
    private DrawerLayout drawerLayout;
    private ViewPager pager;

    public NavigationViewOnNavigationItemSelectedListener(DrawerUpdateSelected callbackDrawerSelected, DrawerLayout drawerLayout, ViewPager pager)
    {
        this.callbackDrawerSelected = callbackDrawerSelected;
        this.drawerLayout = drawerLayout;
        this.pager = pager;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        int checkedDrawerItemId = menuItem.getItemId();

        int colorId;
        int[] attrs = new int[]{android.R.attr.colorPrimaryDark};
        TypedArray ta = this.pager.getContext().obtainStyledAttributes(attrs);
        colorId = ta.getColor(0, Color.GRAY);
        ta.recycle();

        Snackbar snackbar;
        String snackBarMsg = "";
        switch (menuItem.getItemId())
        {
            case R.id.tab_1:
                snackBarMsg = "tab_1";
                pager.setCurrentItem(0, true);
                break;
            case R.id.tab_2:
                snackBarMsg = "tab_2";
                pager.setCurrentItem(1, true);
                break;
            case R.id.tab_3:
                snackBarMsg = "tab_3";
                pager.setCurrentItem(2, true);
                break;
        }
        snackbar = Snackbar.make(pager, snackBarMsg, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(colorId);
        snackbar.show();

        callbackDrawerSelected.updateNavigationViewState(checkedDrawerItemId);

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}