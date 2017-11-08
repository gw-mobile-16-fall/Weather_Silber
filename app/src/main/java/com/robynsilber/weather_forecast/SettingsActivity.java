package com.robynsilber.weather_forecast;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        attachPreference(findPreference(getString(R.string.pref_temp_units_key)));
        attachPreference(findPreference(getString(R.string.pref_num_days_key)));
        attachPreference(findPreference(getString(R.string.pref_loc_zip_key)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = newValue.toString();

        if(preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(value);
            if(index >= 0){
                preference.setSummary(listPreference.getEntries()[index]);
            }
        }else{
            preference.setSummary(value);
        }

        return true;
    }

    private void attachPreference(Preference preference){
        preference.setOnPreferenceChangeListener(this); // set listener to listen for change in value

        onPreferenceChange(
                preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), "")
        );
    }
}
