package hsm.dataeditjs;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.intentfilter.androidpermissions.PermissionManager;

import static java.util.Collections.singleton;

public class DataEditJS_Main extends AppCompatActivity {

    TextView txtJSsrc;
    TextView txtLog;
    TextView txtInput;
    Button btnVerifyJS;
    Spinner spinnerCodeIDs;

    Context context=this;
    boolean bPermissionsOK=false;
    final String TAG ="DataEditJS_Main";

    MyReceiver receiver=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCodeIDs = (Spinner) findViewById(R.id.spinnerCodeIDs);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.code_id_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCodeIDs.setAdapter(adapter);

        txtLog=(TextView)findViewById(R.id.txtLog);
        //make the textview be scrollable
        txtLog.setMovementMethod(new ScrollingMovementMethod());

        txtInput =(TextView)findViewById(R.id.txtInput);

        btnVerifyJS=(Button)findViewById(R.id.btnTest);
        btnVerifyJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtJSsrc.getText().length()>0)
                    doTest();
            }
        });

        txtJSsrc =(TextView)findViewById(R.id.txtJSsrc);
        txtJSsrc.setMovementMethod(new ScrollingMovementMethod());
        txtJSsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions(); //refresh
            }
        });
        checkPermissions();
        receiver=new MyReceiver();

        //restore log text and others
        restoreState(savedInstanceState);
    }

    @Override
    protected void onResume(){
        Log.d(TAG, "onResume: registerReceiver...");
        super.onResume();
        //for receiving background messages
        registerReceiver(receiver, new IntentFilter(Const.EXTRA_MY_INTENT));
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("logtext", txtLog.getText().toString());
        state.putString("testvalue", txtInput.getText().toString());
        state.putInt("selectedcodeid", spinnerCodeIDs.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreState(savedInstanceState);
    }

    void restoreState(Bundle savedInstanceState){
        //restore log text and others
        if (    (savedInstanceState != null) &&
                (savedInstanceState.getString("logtext") != null) &&
                (savedInstanceState.getString("testvalue") != null) &&
                (savedInstanceState.getString("selectedcodeid") != null)
        ) {
            txtLog.setText(savedInstanceState.getString("logtext"));
            txtInput.setText(savedInstanceState.getString("testvalue"));
            spinnerCodeIDs.setSelection(savedInstanceState.getInt("selectedcodeid"));
        }

    }
    void checkPermissions(){
        PermissionManager permissionManager = PermissionManager.getInstance(context);
        permissionManager.checkPermissions(singleton(Manifest.permission.READ_EXTERNAL_STORAGE), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                bPermissionsOK=true;
                //Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
                final String sJscript=hgutils.getScriptFile(context);
                if(txtJSsrc !=null)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                txtJSsrc.setText(sJscript);
                        }
                    });
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(context, "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //use BroadcastReceiver as a sub class to be able to update the UI
    public class MyReceiver extends BroadcastReceiver {

        final static String TAG ="MyReceiver";
        DataEditJS_Main myMain=null;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            updateUI(intent);
        }
    }

    void doTest(){
        String inStr= txtInput.getText().toString();
        String sCodeID= spinnerCodeIDs.getSelectedItem().toString();
        sCodeID=sCodeID.substring(sCodeID.length()-1);

        addLog("Testing input '"+ hgutils.getHexedString(inStr) + "' ");
        EditingEngine editingEngine=new EditingEngine(context);
        String outStr = editingEngine.evaluate(context, inStr, sCodeID);
        addLog("Result= '"+ hgutils.getHexedString(outStr) + "' ");
    }

    void addLog(String s){
        StringBuilder sb=new StringBuilder(txtLog.getText().toString());
        sb.append(s+"\n");
        final String sNew=sb.toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtLog.setText(sNew);
            }
        });
    }


    private void updateUI(Intent intent) {
        String text = intent.getStringExtra(Const.EXTRA_DOLOG);
        txtLog.setText(txtLog.getText().toString() + "\n" + text);
    }

}
