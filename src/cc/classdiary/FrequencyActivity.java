package cc.classdiary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import cc.classdiary.model.Frequency;
import cc.classdiary.task.CheckInFrequencyTask;

public class FrequencyActivity extends PreferenceActivity {

	private static final boolean ALWAYS_SIMPLE_PREFS = false;
	int sendFrequency = Menu.FIRST;
	private int group1Id = 1;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    menu.add(group1Id, sendFrequency, sendFrequency, "Check-in");
	    return super.onCreateOptionsMenu(menu); 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			String studentId = ((EditTextPreference) findPreference("student_id")).getText();
			String disciplineId = ((EditTextPreference) findPreference("discipline_id")).getText();
			String classNumber = ((EditTextPreference) findPreference("classNumber_id")).getText();
			
			Frequency frequency = new Frequency(Long.parseLong(studentId),Long.parseLong(disciplineId), Long.parseLong(classNumber));
			CheckInFrequencyTask syncTask = new CheckInFrequencyTask(frequency);
			syncTask.execute();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}

		addPreferencesFromResource(R.xml.pref_general);

		bindPreferenceSummaryToValue(findPreference("student_id"));
		bindPreferenceSummaryToValue(findPreference("discipline_id"));
		bindPreferenceSummaryToValue(findPreference("classNumber_id"));
	}

	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(context);
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			loadHeadersFromResource(R.xml.pref_headers, target);
		}
	}

	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();

			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			} else if (preference instanceof RingtonePreference) {
				// For ringtone preferences, look up the correct display value
				// using RingtoneManager.
				if (TextUtils.isEmpty(stringValue)) {
					// Empty values correspond to 'silent' (no ringtone).
					preference.setSummary(R.string.pref_ringtone_silent);

				} else {
					Ringtone ringtone = RingtoneManager.getRingtone(
							preference.getContext(), Uri.parse(stringValue));

					if (ringtone == null) {
						// Clear the summary if there was a lookup error.
						preference.setSummary(null);
					} else {
						// Set the summary to reflect the new ringtone display
						// name.
						String name = ringtone
								.getTitle(preference.getContext());
						preference.setSummary(name);
					}
				}

			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);
			}
			return true;
		}
	};

	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}

}
