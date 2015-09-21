package ru.kuchanov.material.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ru.kuchanov.material.R;

/**
 * Created by Юрий on 21.09.2015 18:01.
 * For ExpListTest.
 */
public class FragmentPreferenceNotifications extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_notification);
    }
}