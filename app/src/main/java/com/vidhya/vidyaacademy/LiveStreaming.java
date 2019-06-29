package com.vidhya.vidyaacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wowza.gocoder.sdk.api.WowzaGoCoder;
import com.wowza.gocoder.sdk.api.configuration.WOWZMediaConfig;
import com.wowza.gocoder.sdk.api.player.WOWZPlayerConfig;
import com.wowza.gocoder.sdk.api.player.WOWZPlayerView;
import com.wowza.gocoder.sdk.api.status.WOWZStatus;
import com.wowza.gocoder.sdk.api.status.WOWZStatusCallback;

public class LiveStreaming extends AppCompatActivity {
    WOWZPlayerView mStreamPlayerView;
    WOWZPlayerConfig mStreamPlayerConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_streaming);
      WowzaGoCoder goCoder = WowzaGoCoder.init(getApplicationContext(), "GOSK-8F46-010C-A40A-557F-A500");
    if(goCoder!=null){
        mStreamPlayerView = (WOWZPlayerView) findViewById(R.id.vwStreamPlayer);
        mStreamPlayerConfig = new WOWZPlayerConfig();
        mStreamPlayerConfig.setIsPlayback(true);
        mStreamPlayerConfig.setHostAddress("4ad1eb.entrypoint.cloud.wowza.com");
        mStreamPlayerConfig.setApplicationName("app-7ab6");
        mStreamPlayerConfig.setStreamName("347c5290");
        mStreamPlayerConfig.setPortNumber(1935);
        mStreamPlayerConfig.setUsername("client43304");
        mStreamPlayerConfig.setPassword("135fe208");
        mStreamPlayerConfig.setHLSEnabled(true);
        mStreamPlayerConfig.setHLSBackupURL("https://wowzaprod144-i.akamaihd.net/hls/live/825716/747a543f/playlist.m3u8");
        mStreamPlayerConfig.setVideoEnabled(true);
        mStreamPlayerView.setScaleMode(WOWZMediaConfig.FILL_VIEW);

        WOWZStatusCallback statusCallback = new StatusCallback();
        mStreamPlayerView.play(mStreamPlayerConfig, statusCallback);

    }


    }
    class StatusCallback implements WOWZStatusCallback {
        @Override
        public void onWZStatus(WOWZStatus wzStatus) {
            Log.e("Master",wzStatus.toString());
        }
        @Override
        public void onWZError(WOWZStatus wzStatus) {
            Log.e("Master2",wzStatus.toString());

        }
    }



}
