package moe.lyniko.gemini.led;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import moe.lyniko.gemini.led.CommandHelper;

public class LedSetter {

    private final String TAG = "";
    private final int type_Led_max = 255;
    private final int type_led_min = 0;
    private final CommandHelper commandHelper = CommandHelper.getInstance();
    private final static LedSetter instance = new LedSetter();

    private LedSetter() {}
    public static LedSetter getInstance(){
        return instance;
    }

    public synchronized  void setLightArgb(final int[] array){
        setLight(Arrays.copyOfRange(array, 1,4));//1,2,3
    }

    public synchronized  void setLight(final int[] array){
        setLight(array[0],array[1],array[2]);
    }

    public synchronized void setLight(final int r, final int g, final int b) {
//        Log.i(TAG, "setLight: ");
        setSingleLight("red", r);
        setSingleLight("green", g);
        setSingleLight("blue", b);
    }

    public synchronized void setSingleLight(final String name, final int v) {

//        Log.i(TAG, "setSingleLight: " + name + v);
        String cmd = "echo " + v + " > /sys/class/leds/" + name + "/brightness";
        commandHelper.executeCommand(cmd);
    }
}