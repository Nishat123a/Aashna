package aashna.com.aashna.H_WaterReminder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("**********************","AlarmReciever");
        //FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(new Intent(context, ShowHomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
