package edu.gatech.gt4823.data.data_collection.metadata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.glass.content.Intents;

import edu.gatech.gt4823.data.utilities.InsigApp;

/**
 * Created by Demerrick on 11/19/2014.
 */
public class VideoActivity extends Activity{

    private static final int RECORD_VIDEO_REQUEST = 1;
    private Intent intent;
    private InsigApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (InsigApp)getApplicationContext();
        recordVideo();
    }

    @Override
    protected void onResume() {
        super.onResume(); // return to SplashScreen

    }

    private void recordVideo() {
        intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, RECORD_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_VIDEO_REQUEST && resultCode == RESULT_OK) {
            String thumbnailPath = data.getStringExtra(Intents.EXTRA_THUMBNAIL_FILE_PATH);
            String filePath = data.getStringExtra(Intents.EXTRA_VIDEO_FILE_PATH);
            myApp.setVideoData(thumbnailPath,filePath);
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
