package edu.gatech.gt4823.presentation.data_collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.gt4823.R;
import edu.gatech.gt4823.data.data_collection.ListenerActivity;
import edu.gatech.gt4823.data.utilities.InsigApp;

/**
 * Created by Demerrick Moton on 11/17/2014.
 * Sets up the menu items for the activity and handles
 * and displays user input
 */

public class PatientProfileActivity extends Activity {

    private static final String TAG = "PatientProfileActivity: ";
    public static final String PREFS_NAME = "MyPrefsFile";

    private String speechResult[];
    private String compArray[], injArray[];
    private EditText bp, pls, res, sao2;
    private EditText patId, patName, dispNum;
    private EditText patComp, patInj;

    private int entryId = 0, numOfResults;
    private int compEntry = 0, injEntry = 0;

    private Intent intent;
    private InsigApp myApp;

    private CardScrollView mCardScroller;
    private List<CardBuilder> mCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myApp = (InsigApp)getApplicationContext();

        speechResult = new String[10];
        compArray = new String[10];
        injArray = new String[10];

        createCards();

        // set menu for this activity
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // set up the cards for the patient info, patient status, and vitals
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new PatientProfileScrollAdapter());
        mCardScroller.activate();

        // set the view
        setContentView(mCardScroller);

        bp = (EditText) findViewById(R.id.bloodPressureText);
        pls = (EditText) findViewById(R.id.pulseText);
        res = (EditText) findViewById(R.id.respirationText);
        sao2 = (EditText) findViewById(R.id.sao2Text);

        patId = (EditText) findViewById(R.id.idField);
        patName = (EditText) findViewById(R.id.nameField);
        dispNum = (EditText) findViewById(R.id.dispatchField);

        patComp = (EditText) findViewById(R.id.compField);
        patInj = (EditText) findViewById(R.id.injField);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed here it is 2
        if(requestCode==2)
        {
            String message[] = data.getStringArrayExtra("SPEECH");
            speechResult = message;
            setTextToEntry();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeSavedData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApp.setSavedVitalsData(bp,pls,res,sao2);
        myApp.setSavedInfoData(patId,patName,dispNum);
        myApp.setSavedStatusData(patComp,patInj);
    }

    private void createCards() {
        mCards = new ArrayList<CardBuilder>();

        mCards.add(new CardBuilder(this, CardBuilder.Layout.EMBED_INSIDE)
                .setEmbeddedLayout(R.layout.patient_info_card));

        mCards.add(new CardBuilder(this, CardBuilder.Layout.EMBED_INSIDE)
                .setEmbeddedLayout(R.layout.patient_status_card));

        mCards.add(new CardBuilder(this, CardBuilder.Layout.EMBED_INSIDE)
                .setEmbeddedLayout(R.layout.patient_vitals_card));
    }

    private void initializeSavedData() {
        EditText[] vitalsArray, infoArray, statusArray;
        vitalsArray = myApp.getSavedVitalsData();
        infoArray = myApp.getSavedInfoData();
        statusArray = myApp.getSavedStatusData();

        if(vitalsArray[0] != null)
            bp.setText(vitalsArray[0].getText());
        if(vitalsArray[1] != null)
            pls.setText(vitalsArray[1].getText());
        if(vitalsArray[2] != null)
            res.setText(vitalsArray[2].getText());
        if(vitalsArray[3] != null)
            sao2.setText(vitalsArray[3].getText());

        if(infoArray[0] != null)
            patId.setText(infoArray[0].getText());
        if(infoArray[1] != null)
            patName.setText(infoArray[1].getText());
        if(infoArray[2] != null)
            dispNum.setText(infoArray[2].getText());

        if(statusArray[0] != null)
            patComp.setText(statusArray[0].getText());
        if(statusArray[1] != null)
            patInj.setText(statusArray[1].getText());
    }

    private void setTextToEntry() {
        switch(entryId) {
            case 1:
                // pulse
                pls.setText(speechResult[0]);
                break;
            case 2:
                // blood pressure
                bp.setText(speechResult[0]);
                break;
            case 3:
                // respiration
                res.setText(speechResult[0]);
                break;
            case 4:
                // oxygen saturation
                sao2.setText(speechResult[0]);
                break;
            case 5:
                // vitals
                bp.setText(speechResult[4]);
                pls.setText(speechResult[3]);
                res.setText(speechResult[2]);
                sao2.setText(speechResult[1]);
                break;

            case 6:
                // patient id
                patId.setText(speechResult[0]);
                break;
            case 7:
                // patient name
                patName.setText(speechResult[0]);
                break;
            case 8:
                // dispatch number
                dispNum.setText(speechResult[0]);
                break;
            case 9:
                // patient id (all)
                patId.setText(speechResult[3]);
                patName.setText(speechResult[2]);
                dispNum.setText(speechResult[1]);
                break;
            case 10:
                // patient complaints
                compArray[compEntry] = "- " + speechResult[0] + "\n";
                patComp.setText(compArray[compEntry]);
                compEntry++;
                break;
            case 11:
                // patient injuries
                injArray[injEntry] = "- " + speechResult[0] + "\n";
                patInj.setText(injArray[injEntry]);
                injEntry++;
                break;

            default:
                Log.d(TAG,"not a valid entry");
        }
    }

    private class PatientProfileScrollAdapter extends CardScrollAdapter {

        @Override
        public int getPosition(Object item) {
            return mCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return CardBuilder.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position){
            return mCards.get(position).getItemViewType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).getView(convertView, parent);
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.menu_patient_profile, menu);
            return true;
        }
        Log.d(TAG, "panel menu inflated");
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        numOfResults = 0;
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {

                // App Navigation
                case R.id.go_back:
                    break;
                case R.id.back_to_main:
                    finish();
                    break;

                // Vitals Individually
                case R.id.hr_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 1;
                    break;
                case R.id.bp_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 2;
                    break;
                case R.id.res_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 3;
                    break;
                case R.id.sao2_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 4;
                    break;

                // Vitals Grouped
                case R.id.vitals_menu_item:
                    numOfResults = 4;
                    intent = new Intent(this, ListenerActivity.class);
                    intent.putExtra("NUM_OF_RESULTS",numOfResults);
                    startActivityForResult(intent,2);
                    entryId = 5;
                    break;

                case R.id.id_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 6;
                    break;

                case R.id.name_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 7;
                    break;

                case R.id.dispatch_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 8;
                    break;

                case R.id.patient_info_menu_item:
                    numOfResults = 3;
                    intent = new Intent(this, ListenerActivity.class);
                    intent.putExtra("NUM_OF_RESULTS",numOfResults);
                    startActivityForResult(intent,2);
                    entryId = 9;
                    break;

                case R.id.complaints_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 10;
                    break;
                case R.id.injury_menu_item:
                    intent = new Intent(this, ListenerActivity.class);
                    startActivityForResult(intent,2);
                    entryId = 11;
                    break;
                default:
                    return true;
            }
            return true;
        }
        Log.d(TAG,"returning");
        return super.onMenuItemSelected(featureId, item);
    }


}
