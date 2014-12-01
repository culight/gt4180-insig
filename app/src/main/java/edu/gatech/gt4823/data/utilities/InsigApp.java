package edu.gatech.gt4823.data.utilities;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Demerrick on 11/29/2014.
 */
public class InsigApp extends Application {

    private static InsigApp singleton;
    private static final String TAG = "InsigApp: ";
    public boolean isValid = false;
    public EditText savedVitalsData[];
    public EditText savedInfoData[];
    public EditText savedStatusData[];

    public InsigApp getInstance() {
        return singleton;
    }

    public void setSavedVitalsData( EditText bp, EditText pls,
                                    EditText res, EditText sao2) {
        savedVitalsData[0] = bp;
        savedVitalsData[1] = pls;
        savedVitalsData[2] = res;
        savedVitalsData[3] = sao2;
        Log.d(TAG, "Initiated: ");
    }

    public void setSavedInfoData(EditText id, EditText name,
                                 EditText num) {
        savedInfoData[0] = id;
        savedInfoData[1] = name;
        savedInfoData[2] = num;
    }

    public void setSavedStatusData(EditText comp, EditText inj) {
        savedStatusData[0] = comp;
        savedStatusData[1] = inj;
    }

    public EditText[] getSavedVitalsData() {
        if(savedVitalsData.length < 1) {
            Log.d(TAG, "no vitals data");
            return null;
        }
        return savedVitalsData;
    }

    public EditText[] getSavedInfoData() {
        if(savedInfoData.length < 1) {
            Log.d(TAG, "no info data");
            return null;
        }
        return savedInfoData;
    }

    public EditText[] getSavedStatusData() {
        if(savedStatusData.length < 1) {
            Log.d(TAG, "no status data");
            return null;
        }
        return savedStatusData;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        savedVitalsData = new EditText[4];
        savedInfoData = new EditText[3];
        savedStatusData = new EditText[2];
        singleton = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
