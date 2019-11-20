package hsm.dataeditjs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.intentfilter.androidpermissions.PermissionManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import static java.util.Collections.singleton;

public class hgutils {
    static final String TAG="hgutils";
    static boolean bPermissionsOK=false;

    static void checkPermissions(final Context context){
        PermissionManager permissionManager = PermissionManager.getInstance(context);
        permissionManager.checkPermissions(singleton(Manifest.permission.WRITE_EXTERNAL_STORAGE), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Read external storage granted");
                bPermissionsOK=true;
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(context, "DataEditJS: External Storage Permissions Denied!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String getScriptFile(Context context){
        checkPermissions(context);
        if(!bPermissionsOK) {
            Toast.makeText(context, "Permissions denied. Cannot read external storage", Toast.LENGTH_LONG);
            Log.d(TAG,"Permissions denied. Cannot read external storage");
        }
        File _directory;
        File _file;// = new File(contextWrapper.getExternalFilesDir(null), "state.txt");
        String _filename=Const.JS_FILE_NAME;
        String lines = "";
        try {
            Log.d(TAG, "testing external storage is writeable...");
            if (isExternalStorageWritable()) {
                Log.d(TAG, "external storage writeable");
                _directory = getDocumentsStorageDir();
                if(_directory.mkdir())
                    Log.d(TAG, "Created directory: "+_directory.getName());
                Log.d(TAG, "using directory '" + _directory.toString() + "', file='" + _filename.toString() + "'");
                _file = new File(_directory, _filename); //concats dir and file name
                if (!_file.exists()) {
                    try {
                        if(!_file.createNewFile()) {
                            Log.d(TAG, "File already exists: ");
                        }
                    } catch (IOException ex) {
                        Log.d(TAG, "createNewFile() exception: "+ex.getMessage());
                    }
                    FileWriter fileWriter=new FileWriter(_file, false);
                    String test="function dataEdit(inStr, sAimID) { \n" +
                            "  var v2 = inStr.replace(/\\x1D/g,\"@\");\n" +
                            "  return v2;\n" +
                            "}";
                    fileWriter.write(test);
                    fileWriter.flush();
                    fileWriter.close();
                    lines=test;
                }
                else if (_file.exists()) {
                    //binary read
                    FileInputStream fileInputStream = new FileInputStream(_file);
                    FileChannel fileChannel = fileInputStream.getChannel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
                    fileChannel.read(byteBuffer);
                    byteBuffer.flip();  //change ByteBuffer from read to write and vice versa
                    lines = new String(byteBuffer.array(), Charset.forName(myCharset()));
                    fileChannel.close();
                }
            }
        }catch(FileNotFoundException ex){
            Log.d(TAG, "getScriptFile FileNotFoundException: "+ex.getMessage());
        }catch(java.io.IOException ex){
            Log.d(TAG, "getScriptFile IOException: "+ex.getMessage());
        }
        return lines;
    }
    public static void updateMTP(File _f, Context _context, String TAG){
        Log.d(TAG, "sending Boradcast about file change to MTP...");
        //make the file visible for PC USB attached MTP
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(_f));
        _context.sendBroadcast(intent);

    }

    public static File getDocumentsStorageDir() {
        //Starting with Android 6 you need to set the permissions in Settings-Apps-TotalFreedom-Permissions
        // Get the directory for the user's public pictures directory.
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!file.mkdirs()) {
            Log.d(TAG, "Directory not created");
        }
        Log.d(TAG, "getDocumentsStorageDir() return with " + file.toString());
        return file;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getHexedString(String input) {
        if(input==null)
            return "";
        StringBuilder buffer = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            if ((int) input.charAt(i) > 256) {
                buffer.append("\\u").append(Integer.toHexString((int) input.charAt(i)));
            } else {
                if((int)input.charAt(i)<0x20)
                    buffer.append("<x"+String.format("%02x",(int)input.charAt(i))+">");
                else
                    buffer.append(input.charAt(i));
            }
        }
        return buffer.toString();
    }

    public static void doBeepWarn(){
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }

    public static String myCharset(){
        return "ISO-8859-1";
    }

    public static String readAssetFile(Context context, String sFile){
        StringBuilder buf=new StringBuilder("");
        try {
            InputStream json = context.getAssets().open("book/contents.json");
            BufferedReader in=
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        }catch (IOException ex){
            Log.e(TAG, "readAssetsFile: "+ sFile + ": "+ex.getMessage());
        }
        return buf.toString();
    }

    public static BufferedReader readAssetReader(Context context, String sFile){
        BufferedReader in=null;
        try {
            InputStream json = context.getAssets().open("book/contents.json");
            in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
        }catch (IOException ex){
            Log.e(TAG, "readAssetsFile: "+ sFile + ": "+ex.getMessage());
        }
        return in;
    }
}
