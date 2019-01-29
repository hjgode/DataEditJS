package hsm.dataeditjs;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.intentfilter.androidpermissions.PermissionManager;

import static java.util.Collections.singleton;

public class DataEditJS_Main extends AppCompatActivity {

    TextView textView;
    Context context=this;
    boolean bPermissionsOK=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions(); //refresh
            }
        });
        checkPermissions();

    }

    void checkPermissions(){
        PermissionManager permissionManager = PermissionManager.getInstance(context);
        permissionManager.checkPermissions(singleton(Manifest.permission.READ_EXTERNAL_STORAGE), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                bPermissionsOK=true;
                //Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
                final String sJscript=hgutils.getScriptFile(context);
                if(textView!=null)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                textView.setText(sJscript);
                        }
                    });
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(context, "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
