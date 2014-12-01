package edu.gatech.gt4823.data.data_collection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.lang.ref.WeakReference;

public class VoiceRecognizerService extends Service {
	private static String TAG = "VoiceRecognitionService";
	protected static AudioManager mAudioManager;
	protected SpeechRecognizer mSpeechRecognizer;
	protected Intent mSpeechRecognizerIntent;
	protected final Messenger mServerMessenger = new Messenger(
			new IncomingHandler(this));

	protected boolean mIsListening;
	protected volatile boolean mIsCountDownOn;
	private static boolean mIsStreamSolo;

	public static final int MSG_RECOGNIZER_START_LISTENING = 1;
	public static final int MSG_RECOGNIZER_CANCEL = 2;

	@Override
	public void onCreate() {
		super.onCreate();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		mSpeechRecognizer
				.setRecognitionListener(new SpeechRecognitionListener());
		mSpeechRecognizerIntent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		mSpeechRecognizerIntent.putExtra(
				RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
	}

	protected static class IncomingHandler extends Handler {
		private WeakReference<VoiceRecognizerService> mtarget;

		IncomingHandler(VoiceRecognizerService target) {
			mtarget = new WeakReference<VoiceRecognizerService>(target);
		}

		@Override
		public void handleMessage(Message msg) {
			final VoiceRecognizerService target = mtarget.get();

			switch (msg.what) {
			case MSG_RECOGNIZER_START_LISTENING:

				if (!target.mIsListening) {
					target.mSpeechRecognizer
							.startListening(target.mSpeechRecognizerIntent);
					target.mIsListening = true;
					Log.d(TAG, "Listening");
					//Log.d(TAG, "message start listening"); //$NON-NLS-1$
				}
				break;

			case MSG_RECOGNIZER_CANCEL:
				if (mIsStreamSolo) {
					mAudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL,
							false);
					mIsStreamSolo = false;
				}
				target.mSpeechRecognizer.cancel();
				target.mIsListening = false;
				//Log.d(TAG, "message canceled recognizer"); //$NON-NLS-1$
				break;
			}
		}
	}

	// Count down timer for Jelly Bean work around
	protected CountDownTimer mNoSpeechCountDown = new CountDownTimer(5000, 5000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish() {
			mIsCountDownOn = false;
			Message message = Message.obtain(null, MSG_RECOGNIZER_CANCEL);
			try {
				mServerMessenger.send(message);
				message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
				mServerMessenger.send(message);
			} catch (RemoteException e) {
				Log.e(TAG, "Exception: ", e);
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mIsCountDownOn) {
			mNoSpeechCountDown.cancel();
		}
		if (mSpeechRecognizer != null) {
			mSpeechRecognizer.destroy();
		}
	}

	protected class SpeechRecognitionListener implements RecognitionListener {

		@Override
		public void onBeginningOfSpeech() {
			// speech input will be processed, so there is no need for count
			// down anymore
			if (mIsCountDownOn) {
				mIsCountDownOn = false;
				mNoSpeechCountDown.cancel();
			}
			//Log.d(TAG, "onBeginingOfSpeech"); //$NON-NLS-1$
		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			Log.d(TAG, "BUFFER RECEIVED");
		}

		@Override
		public void onEndOfSpeech() {
			Log.d(TAG, "End of speech");
			//Log.d(TAG, "onEndOfSpeech"); //$NON-NLS-1$
		}

		@Override
		public void onError(int error) {
			if (mIsCountDownOn) {
				mIsCountDownOn = false;
				mNoSpeechCountDown.cancel();
			}
			mIsListening = false;
			Message message = Message.obtain(null,
					MSG_RECOGNIZER_START_LISTENING);
			try {
				mServerMessenger.send(message);
			} catch (RemoteException e) {

			}
			//Log.d(TAG, "error = " + error); //$NON-NLS-1$
		}

		@Override
		public void onEvent(int eventType, Bundle params) {
			Log.d(TAG, "event");
		}

		@Override
		public void onPartialResults(Bundle partialResults) {

		}

		@Override
		public void onReadyForSpeech(Bundle params) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				mIsCountDownOn = true;
				mNoSpeechCountDown.start();

			}
			Log.d(TAG, "onReadyForSpeech"); //$NON-NLS-1$
		}

		@Override
		public void onResults(Bundle results) {
			//Log.d(TAG, "onResults"); //$NON-NLS-1$

		}

		@Override
		public void onRmsChanged(float rmsdB) {

		}

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}