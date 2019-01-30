package hsm.dataeditjs;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;

import android.content.BroadcastReceiver;

import static android.support.v4.content.ContextCompat.startActivity;


/**
 * This class can be used to evaluate any string expression using the open source,
 * RHINO javascript engine.
 * <p>
 * Add this line - compile 'org.mozilla:rhino:1.7R4'
 * To your module app dependency gradle to install the jar library.
 * <p>
 * Follow my tutorial at
 * {@link} https://github.com/brionsilva/Android-Rhino-Example
 *
 * @author Brion Mario
 * @version 1.0
 * @since 2017-03-08
 */

public class EditingEngine {

    private Context rhino;
    private Scriptable scope;
    private android.content.Context _context;
    final String TAG = "EditingEngine";

    boolean bUseJS = true;
    boolean bReplaceCrLf = true;

    String answer;

    public EditingEngine(android.content.Context context) {
        _context = context;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        bUseJS = true;
        if (!SP.getBoolean("use_js", false))
            bUseJS = false;
        bReplaceCrLf = true;
        if (!SP.getBoolean("convert_crlf", false))
            bReplaceCrLf = false;
        Log.i(TAG, "sharedPrefs: " + SP.getAll().toString());
        Log.d(TAG, "bUseJS, bReplaceCrLf " + bUseJS + ", " + bReplaceCrLf);
    }

    /**
     * This function evaluates the string when it is passed as a parameter.
     *
     * @param sInput The expression is passed to the method
     * @return Returns the evaluated answer in a double variable
     */
    public String evaluate(android.content.Context context, String sInput, String sCodeID) {

        Log.i(TAG, "evaluate called with: " + hgutils.getHexedString(sInput) + ", codeId=" + sCodeID);
        sendLog("evaluate called with: " + hgutils.getHexedString(sInput) + ", codeId=" + sCodeID);

        if (!bUseJS) {
            sendLog("bUseScript is OFF. No data change.");
            return sInput;
        }

        if (bReplaceCrLf) {
            sendLog("bReplaceCrLf is on. Replacing \\r  and \\n before dataEdit.");
            sInput = sInput.replace("\r", "\\r");
            sInput = sInput.replace("\n", "\\n");
        }
        Object[] functionParams = new Object[]{sInput, sCodeID};

        //The js function
        String script = "function dataEdit(inStr, start, end) { " +
                "  return inStr.substring(start,end);" +
                "}";

        script = hgutils.getScriptFile(context);

        if (script.length() == 0 || script.isEmpty())
            return "no script: " + sInput;

        Context rhino = Context.enter();

        //disabling the optimizer to better support Android.
        rhino.setOptimizationLevel(-1);

        try {

            Scriptable scope = rhino.initStandardObjects();

            /**
             * evaluateString(Scriptable scope, java.lang.String source, java.lang.String sourceName,
             * int lineno, java.lang.Object securityDomain)
             *
             */
            rhino.evaluateString(scope, script, "JavaScript", 1, null);

            Function function = (Function) scope.get("dataEdit", scope);

            answer = (String) function.call(rhino, scope, scope, functionParams);
            if (bReplaceCrLf) {
                answer = answer.replace("\\r", "\r");
                answer = answer.replace("\\n", "\n");
                sendLog("bReplaceCrLf is on. Replacing \\r  and \\n after dataEdit.");
            }

        } catch (RhinoException e) {

            Log.e(TAG, "function.call failed: " + e.getMessage());
            sendLog("function.call failed: " + e.getMessage());

        } finally {
            Context.exit();
        }
        Log.i(TAG, "eveluate return with: " + hgutils.getHexedString(answer));
        sendLog("eveluate return with: " + hgutils.getHexedString(answer));
        return answer;
    }

    void sendLog(String s) {
        Log.d(TAG, "sendLog: "+s);
        Intent intent=new Intent();
        intent.setAction(Const.EXTRA_MY_INTENT);
        intent.putExtra(Const.EXTRA_DOLOG, s);
        //LocalBroadcastManager.getInstance(this._context).sendBroadcast(intent);
        //startActivity(_context, intent, null);
        _context.sendBroadcast(intent);
    }
}
