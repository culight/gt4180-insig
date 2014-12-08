package edu.gatech.gt4823.presentation.splash_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.glass.view.WindowUtils;

import edu.gatech.gt4823.R;
import edu.gatech.gt4823.data.data_collection.metadata.AudioActivity;
import edu.gatech.gt4823.data.data_collection.metadata.PhotoActivity;
import edu.gatech.gt4823.data.data_collection.metadata.VideoActivity;
import edu.gatech.gt4823.presentation.data_collection.PatientProfileActivity;
import edu.gatech.gt4823.presentation.patient_story.PatientStoryActivity;

/**
 * Created by Demerrick Moton on 11/8/2014.
 */
public final class SplashScreenLayoutActivity extends Activity {
    private String TAG = "SplashScreenLayoutActivity: ";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // set the menu and
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.splash_screen_card);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.d(TAG,"menu item selected");
        Class run = null;
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {
                case R.id.patient_profile:
                    //Log.d(TAG,"patient profile");
                    run = PatientProfileActivity.class;
                    break;
                case R.id.take_picture:
                    //Log.d(TAG,"take picture");
                    run = PhotoActivity.class;
                    break;
                case R.id.record_video:
                    //Log.d(TAG,"record video");
                    run = VideoActivity.class;
                    break;
                case R.id.record_audio:
                    //Log.d(TAG,"send a message");
                    run = AudioActivity.class;
                    break;
                case R.id.metadata_profile:
                    //Log.d(TAG,"metadata");
                    run = PatientStoryActivity.class;
                    break;
                case R.id.transfer:
                    //Log.d(TAG,"transfer patient");
                    run = AudioActivity.class;
                    break;
                case R.id.go_back:
                    //Log.d(TAG,"never mind");
                    break;
                default:
                    return true;
            }
            Intent intent = new Intent(this, run);
            startActivity(intent);
            return true;
        }
        // Good practice to pass through to super if not handled
        return super.onMenuItemSelected(featureId, item);
    }

}
