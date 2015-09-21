package ru.kuchanov.material.fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import ru.kuchanov.material.ActivitySettings;
import ru.kuchanov.material.R;

/**
 * Created by Юрий on 21.09.2015 17:57.
 * For ExpListTest.
 */
public class FragmentPreferenceAbout extends PreferenceFragment
{
    protected Preference.OnPreferenceClickListener gitHubCL = new Preference.OnPreferenceClickListener()
    {
        @Override
        public boolean onPreferenceClick(Preference preference)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/mohaxspb/MaterialTemplate")));
            return false;
        }
    };
    private int numOfClicks = 0;
    protected Preference.OnPreferenceClickListener easterEggCL = new Preference.OnPreferenceClickListener()
    {
        @Override
        public boolean onPreferenceClick(Preference preference)
        {
            if (numOfClicks > 7)
            {
                numOfClicks = 0;
                Snackbar snackbar = Snackbar.make(getView(), "Пасхальный SnackBar!", Snackbar.LENGTH_LONG);
                snackbar.setAction("Тык!", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        try
                        {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=ru.kuchanov.odnako")));
                        }
                        catch (Exception e)
                        {
                            String marketErrMsg = "Должен был запуститься Play Market, но что-то пошло не так... Так запустим же браузер!";
                            System.out.println(marketErrMsg);
                            Toast.makeText(getActivity(), marketErrMsg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://details?id=ru.kuchanov.odnako")));
                        }
                    }
                });
                View snackBarView = snackbar.getView();
                int colorId;
                int[] attrs = new int[]{R.attr.colorPrimaryDark};
                TypedArray ta = getActivity().obtainStyledAttributes(attrs);
                colorId = ta.getColor(0, Color.GRAY);
                ta.recycle();
                snackBarView.setBackgroundColor(colorId);
                snackbar.show();
            }
            else
            {
                numOfClicks++;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_about);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.
        ActivitySettings.bindPreferenceSummaryToValue(findPreference(this.getString(R.string.pref_about_list_key)));
        //set onClickListeners
        Preference prefVersion = this.findPreference(this.getString(R.string.pref_about_version_key));
        prefVersion.setOnPreferenceClickListener(easterEggCL);

        Preference prefGitHub = this.findPreference(this.getString(R.string.pref_about_github_key));
        prefGitHub.setOnPreferenceClickListener(gitHubCL);
    }
}