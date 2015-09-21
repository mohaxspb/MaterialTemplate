package ru.kuchanov.material;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class ActivitySettings extends PreferenceActivity
{

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener()
    {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value)
        {
            String stringValue = value.toString();

            if (preference instanceof ListPreference)
            {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            }
            else if (preference instanceof RingtonePreference)
            {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue))
                {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                }
                else
                {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null)
                    {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    }
                    else
                    {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            }
            else
            {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference)
    {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.setTheme(R.style.Theme_Preference_Light);
        super.onCreate(savedInstanceState);

        ActionBar actionBar = this.getActionBar();
        if (null != actionBar)
        {
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane()
    {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName)
                || FragmentPreferenceAbout.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    public static class GeneralPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("example_text"));
            bindPreferenceSummaryToValue(findPreference("example_list"));
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    public static class NotificationPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
        }
    }

    public static class FragmentPreferenceAbout extends PreferenceFragment
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
                    int[] attrs = new int[]{android.R.attr.colorPrimaryDark};
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
            bindPreferenceSummaryToValue(findPreference(this.getString(R.string.pref_about_list_key)));
            //set onClickListeners
            Preference prefVersion = this.findPreference(this.getString(R.string.pref_about_version_key));
            prefVersion.setOnPreferenceClickListener(easterEggCL);

            Preference prefGitHub = this.findPreference(this.getString(R.string.pref_about_github_key));
            prefGitHub.setOnPreferenceClickListener(gitHubCL);
        }
    }
}