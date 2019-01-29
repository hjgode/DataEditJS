package hsm.dataeditjs;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataEditJS extends BroadcastReceiver {
    final String TAG = "DataEditJS";
    public static final String BROADCAST_ACTION = "hsm.dataeditjs.displayevent";
    StringBuilder logText = new StringBuilder();  //text to send to main activity

    @Override
    public void onReceive(Context context, Intent intent) {
        String input = intent.getStringExtra("data");//Read the scan result from the Intent
        Log.d(TAG, "Package" + intent.getPackage() + ", " + intent.toString());
//        this.myContext = context;//you can retrieve context from onReceive argument
//        this.myIntent = new Intent(BROADCAST_ACTION);
        logText.append(input);
        doLog(input, context);

        /*
        codeId b (java.lang.String)
        dataBytes [B@c9a8a48 ([B)
        data 10110 (java.lang.String)
        timestamp 2016-09-17T09:05:27.619+2:00 (java.lang.String)
        aimId ]A0 (java.lang.String)
        version 1 (java.lang.Integer)
        charset ISO-8859-1 (java.lang.String)
        */

        String codeId=intent.getStringExtra("codeId");
        String aimID=intent.getStringExtra("aimId");
        String timeStamp=intent.getStringExtra("timestamp");
        String charSet=intent.getStringExtra("charset");
        String myTimeStamp="---";
        Date myDate=new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            myDate = sdf.parse(timeStamp);
            Log.d(TAG, "Date: "+myDate.toString());
            sdf.applyPattern("yyyy-MM-dd hh:mm");
            myTimeStamp=(sdf.format(myDate));
        }catch (RuntimeException ex){
            Log.e(TAG, "SimpleDateFormat: "+ex.toString());
        }
        catch (ParseException ex) {
            Log.e(TAG, "SimpleDateFormat: "+ex.toString());
        }

        doUpdate(String.format("ScanData IN: '%s'", hgutils.getHexedString(input)),context);
        doUpdate(String.format("aimId='%s'",aimID),context);
        doUpdate(String.format("date='%s'",myTimeStamp),context);

        //remove CR and LF
        String formattedOutput = input.replace("\n","").replace("\r",""); //what to return

        EditingEngine engine=new EditingEngine(context);
        formattedOutput = engine.evaluate(context, input, codeId);
        Log.i(TAG, "formattedOutput= "+formattedOutput);

        //return edited data as bundle
        Bundle bundle = new Bundle();
        //Return the Modified scan result string
        bundle.putString("data", formattedOutput);
        setResultExtras(bundle);

    }

    void doUpdate(String s, Context context_) {
        class updateUI implements Runnable {
            String str;
            Context _context;
            updateUI(String s, Context c) {
                str = s;
                _context=c;
            }
            public void run() {
                Intent _intent=new Intent(BROADCAST_ACTION);
                _intent.putExtra("text", str);
                _context.sendBroadcast(_intent);
            }
        }
        Thread t = new Thread(new updateUI(s, context_));
        t.start();
    }
    void doLog(String s, Context context){
        Log.d(TAG, s);
        doUpdate(s, context);
    }

}
