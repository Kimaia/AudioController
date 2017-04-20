package com.course.mobilemaster.audiocontroller;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by haggai on 31/01/2017.
 */

public class TestControllerActivity extends Activity implements ServiceConnection {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_controller_layout);

//        ActivityManager.RunningServiceInfo nativeMusicService = null;
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices (10000);
        for(ActivityManager.RunningServiceInfo info : services){
            if( info.process.contains("music") || info.process.equals(MediaPlaybackService.class.getPackage().getName())){
                Log.d("SERVICE", "Service Found !!!!!!!!! : "+info.process);
                return;
            }
        }
        MusicUtils.bindToService(this,this);

    }

    public void prev(View v){
        sendCommand(MediaPlaybackService.CMDPREVIOUS);
    }

    public void play(View v){
        sendCommand(MediaPlaybackService.CMDPLAY);
    }

    public void pause(View v){
        sendCommand(MediaPlaybackService.CMDPAUSE);
    }

    public void next(View v){
        sendCommand(MediaPlaybackService.CMDNEXT);
    }


    private void sendCommand(String command){
        Intent i = new Intent(MediaPlaybackService.SERVICECMD);

        i.putExtra(MediaPlaybackService.CMDNAME, command);
        sendBroadcast(i);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d("Music", "TestControllerActivity: service connected - "+name);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d("Music", "TestControllerActivity: service disconnected - "+name);
    }
}
