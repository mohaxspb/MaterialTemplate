package ru.kuchanov.material;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

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

        Snackbar snackbar;
        switch (menuItem.getItemId())
        {
            case R.id.tab_1:
                snackbar = Snackbar.make(drawerLayout, "tab_1", Snackbar.LENGTH_SHORT);
                snackbar.show();
                pager.setCurrentItem(0, true);
                break;
            case R.id.tab_2:
                snackbar = Snackbar.make(drawerLayout, "tab_2", Snackbar.LENGTH_SHORT);
                snackbar.show();
                pager.setCurrentItem(1, true);
                break;
            case R.id.tab_3:
                snackbar = Snackbar.make(drawerLayout, "tab_3", Snackbar.LENGTH_SHORT);
                snackbar.show();
                pager.setCurrentItem(2, true);
                break;
        }

        callbackDrawerSelected.updateNavigationViewState(checkedDrawerItemId);

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
