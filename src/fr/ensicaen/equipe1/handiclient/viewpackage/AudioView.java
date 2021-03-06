package fr.ensicaen.equipe1.handiclient.viewpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

public class AudioView implements IView, TextToSpeech.OnInitListener {
	private TextToSpeech _tts;
	private Activity _activity;
	private int _layoutID;
	private boolean _hasInitializedTTS = false;
	private ArrayList<String> _awaitingSpeeches = new ArrayList<String>();
	private ToneGenerator _toneGenerator= new ToneGenerator(AudioManager.STREAM_ALARM, 100);

	public AudioView(Activity activity, int layoutID) {
		_activity = activity;
		_layoutID = layoutID;
		_tts = new TextToSpeech(_activity, this);
	}

	@Override
	public void describe() {
		new Thread(new Runnable() {
			public void run() {
				ViewGroup layout = (ViewGroup) _activity
						.findViewById(_layoutID);
				for (int i = 0; i < layout.getChildCount(); i++) {
					View v = layout.getChildAt(i);
					if (v.getClass() == Button.class
							|| v.getClass() == TextView.class) {
						readDescription((TextView) v);
					}
				}
			}
		}).start();
	}

	private void readDescription(View v) {
		String text = v.getContentDescription().toString();
		if (_hasInitializedTTS) {
			_tts.speak(text, TextToSpeech.QUEUE_ADD, null);
		} else {
			_awaitingSpeeches.add(text);
		}
	}

	@Override
	public void reactOnNumberButtons(Button button) {
		/* Animation */
		animateButton(button);
		
		/*TTS*/
		if(_tts != null)
			_tts.stop();
		
		/* Sound */
		readDescription(button);
	}
	
	@Override
	public void reactOnSecretNumberButtons(Button button) {
		/* Animation */
		animateButton(button);
		
		/* Sound */
		_toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,200); 
		
		/*TTS*/
		if(_tts != null)
			_tts.stop();
	}
	
	@Override
	public void reactOnCancelButton(Button button) {
		/* Animation */
		animateButton(button);
		
		/* Sound */
		_toneGenerator.startTone(ToneGenerator.TONE_SUP_ERROR,200); 
		
		/*TTS*/
		if(_tts != null)
			_tts.stop();
	}

	@Override
	public void reactOnValidateButton(Button button) {
		/* Animation */
		animateButton(button);
		
		/* Sound */
		_toneGenerator.startTone(ToneGenerator.TONE_SUP_PIP,200); 
		
		/*TTS*/
		if(_tts != null)
			_tts.stop();
	}
	
	private void animateButton (Button button) {
		Animation animation = new ScaleAnimation(1, 0.8f, 1, 0.8f,
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(200);
		animation.setInterpolator(new AccelerateInterpolator());
		animation.setRepeatCount(1);
		animation.setRepeatMode(Animation.REVERSE);
		button.startAnimation(animation);
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int resultLocalization = _tts.setLanguage(Locale.FRENCH);
			if (resultLocalization == TextToSpeech.LANG_MISSING_DATA
					|| resultLocalization == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} 
		} else {
			Log.e("TTS", "Initilization Failed!");
		}
		_hasInitializedTTS = true;
		if (!_awaitingSpeeches.isEmpty()) {
			Iterator<String> it = _awaitingSpeeches.iterator();
			while (it.hasNext()) {
				_tts.speak(it.next(), TextToSpeech.QUEUE_ADD, null);
			}
		}
	}

	@Override
	public void describe(final String speech) {
		new Thread(new Runnable() {
			public void run() {
				if (_hasInitializedTTS) {
					_tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
				} else {
					_awaitingSpeeches.add(speech);
				}
			}
		}).start();
	}
	
	@Override
	public void destroyTTS(){
		if(_tts != null){
			_tts.stop();
			_tts.shutdown();
			_tts = null;
		}
	}
}
