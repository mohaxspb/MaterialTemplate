package ru.kuchanov.material.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ru.kuchanov.material.R;

/**
 * Created by Юрий on 21.09.2015 16:36.
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 */
public class FragmentPreferenceDesign  extends PreferenceFragment
{
    private final static String LOG = FragmentPreferenceDesign.class.getSimpleName();

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_design);
        }
}
