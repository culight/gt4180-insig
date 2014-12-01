package edu.gatech.gt4823.data.data_collection.metadata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.glass.touchpad.GestureDetector;

import java.util.ArrayList;

import edu.gatech.gt4823.R;

/**
 * Created by Demerrick on 11/19/2014.
 */
public class AudioActivity extends Activity {

    private static final String TAG = "AudioActivity: ";
    private GestureDetector mGestureDetector;
    private SpeechRecognizer audioRecorder;

    private EditText resultField;
    private RadioButton indicatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_card);

        resultField = (EditText) findViewById(R.id.editText);
        indicatorButton = (RadioButton) findViewById(R.id.audioRadioButton);
        indicatorButton.setButtonDrawable(R.drawable.radio_on);

        audioRecorder = SpeechRecognizer.createSpeechRecognizer(this);
        audioRecorder.setRecognitionListener(new listener());
        startRecording();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioRecorder != null)
            audioRecorder.destroy();
    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
            indicatorButton.setButtonDrawable(R.drawable.radio_off);
        }

        public void onError(int error) {
            Log.d(TAG, "error " + error);
        }

        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            resultField.setText(str);
            audioRecorder.stopListening();
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    private void startRecording() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        audioRecorder.startListening(intent);
    }

}


