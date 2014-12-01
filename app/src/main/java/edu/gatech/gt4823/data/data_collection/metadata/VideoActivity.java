package edu.gatech.gt4823.data.data_collection.metadata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * Created by Demerrick on 11/19/2014.
 */
public class VideoActivity extends Activity{

    private static final int RECORD_VIDEO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordVideo();
    }

    @Override
    protected void onResume() {
        super.onResume(); // return to SplashScreen
        finish();
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, RECORD_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_VIDEO_REQUEST && resultCode == RESULT_OK) {
            // TODO: Process the metadata of the video
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
