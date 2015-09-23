package ru.kuchanov.material;

import android.support.v4.view.ViewPager;

public class PagerAdapterOnPageChangeListener implements ViewPager.OnPageChangeListener
{
    //private final static String LOG = PagerAdapterOnPageChangeListener.class.getSimpleName();

    DrawerUpdateSelected drawerUpdateSelected;
    ImageChanger imageChanger;


    public PagerAdapterOnPageChangeListener(DrawerUpdateSelected drawerUpdateSelected, ImageChanger imageChanger)
    {
        this.drawerUpdateSelected = drawerUpdateSelected;
        this.imageChanger=imageChanger;
    }

    @Override
    public void onPageSelected(int position)
    {
        int checkedDrawerItemId = R.id.tab_1;
        switch (position)
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
        this.imageChanger.updateImage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }
}