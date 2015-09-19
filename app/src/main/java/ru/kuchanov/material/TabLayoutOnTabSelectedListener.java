package ru.kuchanov.material;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public class TabLayoutOnTabSelectedListener implements TabLayout.OnTabSelectedListener
{
    DrawerUpdateSelected drawerUpdateSelected;
    ViewPager pager;

    public TabLayoutOnTabSelectedListener(DrawerUpdateSelected drawerUpdateSelected, ViewPager pager)
    {
        this.drawerUpdateSelected = drawerUpdateSelected;
        this.pager = pager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        int checkedDrawerItemId = R.id.tab_1;

        pager.setCurrentItem(tab.getPosition());
        switch (tab.getPosition())
        {
            case 0:
                checkedDrawerItemId = R.id.tab_1;
                break;
            case 1:
                checkedDrawerItemId = R.id.tab_2;
                break;
            case 2:
                checkedDrawerItemId = R.id.tab_3;
                break;
        }
        drawerUpdateSelected.updateNavigationViewState(checkedDrawerItemId);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {

    }
}
