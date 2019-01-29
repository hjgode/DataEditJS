package hsm.dataeditjs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {
    String TAG="SettingsActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);
        Log.d(TAG,"SettingsActivity onCreate");
        SettingsFragment settingsFragment=new SettingsFragment().newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(android.R.id.content, new hsm.dataeditjs.SettingsFragment().newInstance())
                .commit();
    }

}
