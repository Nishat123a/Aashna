package aashna.com.aashna.S_SelfSafety;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReciver_two extends BroadcastReceiver {
    //setting power button count to zero
    int Count = 0;
    //boolean flag for screen on
    public static boolean wasScreenOn = true;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        //boolean flag for screen on is set false due to locked screen
        wasScreenOn = true;
        //Count for number of clicks on power button

            KeyguardManager mykm = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
            if(mykm.inKeyguardRestrictedInputMode()){
//            if count is more than four then  is AlertScreen is launched
                Intent i = new Intent(context,View_Maps.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }else {
//do nothing

                System.exit(0);
            }


            //After launch again count is set to zero


    }


}



