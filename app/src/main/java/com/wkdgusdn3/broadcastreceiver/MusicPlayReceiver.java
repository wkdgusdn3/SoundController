package com.wkdgusdn3.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

public class MusicPlayReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent new_intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
            new_intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
            context.sendOrderedBroadcast(new_intent, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
