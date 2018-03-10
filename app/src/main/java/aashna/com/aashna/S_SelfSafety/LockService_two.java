package aashna.com.aashna.S_SelfSafety;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class LockService_two extends Service {
    @Override
    // A client is binding to the service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    //Service is started
    public int onStartCommand(Intent intent, int flags, int startId) {
        //initilizing reciver
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        final BroadcastReceiver mReceiver = new ScreenReciver_two();
        registerReceiver(mReceiver, filter);

        return super.onStartCommand(intent, flags, startId);
    }
    //Component binds to service
    public class LocalBinder extends Binder {
        LockService_two getService() {
            return LockService_two.this;
        }
    }
}
