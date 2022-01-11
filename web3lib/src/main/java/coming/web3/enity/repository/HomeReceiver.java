package coming.web3.enity.repository;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


import coming.web3.C;

public class HomeReceiver extends BroadcastReceiver
{
    private final HomeCommsInterface homeCommsInterface;
    public HomeReceiver(Activity ctx,HomeCommsInterface homeCommsInterface)
    {
        ctx.registerReceiver(this, new IntentFilter(C.DOWNLOAD_READY));
        ctx.registerReceiver(this, new IntentFilter(C.REQUEST_NOTIFICATION_ACCESS));
        ctx.registerReceiver(this, new IntentFilter(C.BACKUP_WALLET_SUCCESS));
        ctx.registerReceiver(this, new IntentFilter(C.CHANGED_LOCALE));
        ctx.registerReceiver(this, new IntentFilter(C.WALLET_CONNECT_REQUEST));
        this.homeCommsInterface = homeCommsInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        switch (intent.getAction())
        {

        }
    }
}
