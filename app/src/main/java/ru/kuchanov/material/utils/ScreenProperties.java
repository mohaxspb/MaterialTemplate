package ru.kuchanov.material.utils;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

/**
 * Created by Юрий on 12.10.2015 20:14.
 * For ExpListTest.
 */
public class ScreenProperties
{
    public static int getWidth(AppCompatActivity act)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static int getHeight(AppCompatActivity act)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }
}
