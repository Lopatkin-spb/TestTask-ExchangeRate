package space.lopatkin.spb.testtask_exchangerate.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.DIALOG_NO_INTERNET;

public class NetworkReceiver extends BroadcastReceiver {
    private DialogManager dialogManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetworkStatus.isNetworkConnected(context)) {

            dialogManager = new DialogManager();
            dialogManager.showDialog(context, intent, DIALOG_NO_INTERNET);

        }

    }
}
