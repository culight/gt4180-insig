package edu.gatech.gt4823.data.data_collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import edu.gatech.gt4823.R;

/**
 * Created by Demerrick on 11/22/2014.
 */
public class ListenerActivity extends Activity {

    private String TAG = "ListenerActivity: ";

    private int resultsCount = 0, resultsTotal;

    private SpeechRecognizer voicePrompt;
    private String[] speechResultListener;
    private String resultString;

    private TextView indicatorText;
    private RadioButton indicatorButton;

    Intent intent;

    //TODO: Replace progress bar with a red/green recording icon
    //TODO: Clean up Listener Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listener_card);
        speechResultListener = new String[10];

        // receive extra for number of results
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resultsCount = extras.getInt("NUM_OF_RESULTS");
            resultsTotal = resultsCount;
        }

        // initial view setup
        indicatorText = (TextView) findViewById(R.id.textView);
        indicatorButton = (RadioButton) findViewById(R.id.radioButton);
        indicatorButton.setButtonDrawable(R.drawable.radio_off);

        // initial speech recognizer setup
        voicePrompt = SpeechRecognizer.createSpeechRecognizer(this);
        voicePrompt.setRecognitionListener(new Listener());

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "edu.gatech.gt4823.presentation.data_collection");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        runVoiceRecognition();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        voicePrompt.stopListening();
        if(voicePrompt != null)
            voicePrompt.destroy();
        super.onDestroy();
    }

    private void resultHandler() {
        if(resultsCount > 1) {
            Log.d(TAG,"count is" + resultsCount);

            resultsCount--;

            // restart speech recognizer
            voicePrompt.stopListening();
            if(voicePrompt != null)
                voicePrompt.destroy();

            voicePrompt = SpeechRecognizer.createSpeechRecognizer(this);
            voicePrompt.setRecognitionListener(new Listener());

            intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    "edu.gatech.gt4823.presentation.data_collection");
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

            Log.d(TAG,"count is" + resultsCount);

            runVoiceRecognition();
        }
        else {
            voicePrompt.stopListening();
            // Copy result to string and send to previous activity (patient profile)
            Intent intent=new Intent();
            intent.putExtra("SPEECH",speechResultListener);
            setResult(2,intent);
            if(voicePrompt != null)
                voicePrompt.destroy();
            finish();
        }
    }

    private void indicatorHandler() {
        switch(resultsTotal) {
            // vitals have 4 parameters
            case 4:
                switch(resultsCount) {
                    case 4:
                        indicatorText.setText("Enter Blood Pressure");
                        break;
                    case 3:
                        indicatorText.setText("Enter Pulse");
                        break;
                    case 2:
                        indicatorText.setText("Enter Respiration");
                        break;
                    case 1:
                        indicatorText.setText("Enter SaO2");
                        break;
                    default:
                        Log.d(TAG,"invalid vitals parameter");
                        break;
                }
                break;

            // patient id has 3 parameters
            case 3:
                switch(resultsCount) {
                    case 3:
                        indicatorText.setText("Enter Patient ID");
                        break;
                    case 2:
                        indicatorText.setText("Enter Patient Name");
                        break;
                    case 1:
                        indicatorText.setText("Enter Dispatch Number");
                        break;
                    default:
                        Log.d(TAG,"invalid vitals parameter");
                        break;
                }
                break;

            // patient status has 2 parameters
            case 2:
                switch(resultsCount) {
                    case 2:
                        indicatorText.setText("Enter Patient Complaint");
                        break;
                    case 1:
                        indicatorText.setText("Enter Patient Injury");
                        break;
                    default:
                        Log.d(TAG,"invalid vitals parameter");
                        break;
                }
                break;

            default:
                Log.d(TAG,"result type not valid");
                break;
        }
    }

    private void runVoiceRecognition() {
        indicatorHandler();
        voicePrompt.startListening(intent);
    }

    public class Listener implements RecognitionListener
    {
        String TAG = "Listener: ";

        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
            indicatorButton.setButtonDrawable(R.drawable.radio_on);
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            //Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
            indicatorButton.setButtonDrawable(R.drawable.radio_off);
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            indicatorButton.setButtonDrawable(R.drawable.radio_off);
            voicePrompt.stopListening();
        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            speechResultListener[resultsCount] = str;
            resultHandler();
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
