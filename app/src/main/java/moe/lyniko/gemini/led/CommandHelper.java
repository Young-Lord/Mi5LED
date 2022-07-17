package moe.lyniko.gemini.led;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandHelper {
    @SuppressLint("StaticFieldLeak")
    private final static CommandHelper instance = new CommandHelper();
    private DataOutputStream dos;
    private static final String TAG = "LYNIKO";
    private Activity mainActivity;
    private final ExecutorService pool = Executors.newSingleThreadExecutor();
    public boolean no_su = false;

    private CommandHelper() {
    }

    public static CommandHelper getInstance() {
        return instance;
    }

    public static void setMainActivity(Activity activity) {
        instance.mainActivity = activity;
    }

    public void executeCommand(@NonNull final String cmd) {
        try {
            dos.writeBytes(cmd + "\n");
            dos.flush();
        } catch (Exception e) {
            {
                new AlertDialog.Builder(mainActivity).setTitle("错误").setMessage("无法得到 Root 权限").setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                }).show();
            }
        }
    }
        public void init () {
            try {
                Process p = Runtime.getRuntime().exec("su");
                dos = new DataOutputStream(p.getOutputStream());
                dos.writeBytes("\n");
                dos.flush();
            } catch (Exception e) {
//貌似没有什么用
            }
        }
    }
