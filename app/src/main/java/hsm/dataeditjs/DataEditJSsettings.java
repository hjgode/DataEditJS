package hsm.dataeditjs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataEditJSsettings extends BroadcastReceiver {

    final static String TAG = "DataEditSettings: ";
    public DataEditJSsettings() {
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive intent "+ intent.toString());
    }
}
