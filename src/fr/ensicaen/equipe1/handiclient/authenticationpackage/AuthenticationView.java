package fr.ensicaen.equipe1.handiclient.authenticationpackage;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import fr.ensicaen.equipe1.handiclient.R;
import fr.ensicaen.equipe1.handiclient.viewpackage.AudioView;
import fr.ensicaen.equipe1.handiclient.viewpackage.IView;

public class AuthenticationView implements IView{
	private AuthenticationActivity _authenticationActivity;
	private IView _view;
	
	private String _pinCodeDisplayed = "";
	private String _tooMuchEntries = "Le code � d�j� compos� de 4 caract�res.";
	private String _errorCode = "Code erron�!";

	public AuthenticationView(AuthenticationActivity authenticationActivity,String viewType) {
		_authenticationActivity = authenticationActivity;
		if(viewType.equals("AUDIO_MODE")) {
			_view = new AudioView(_authenticationActivity,R.id.layoutAuthentication);
		}
		/*
		else {
			break;
		}
		*/
	}

	public void describe() {
		_view.describe();
	}
	
	@Override
	public void reactOnNumberButtons(Button button) {
		_view.reactOnNumberButtons(button);
	}
	
	@Override
	public void reactOnSecretNumberButtons(Button button) {
		_view.reactOnSecretNumberButtons(button);
	}

	@Override
	public void reactOnCancelButton(Button button) {
		_view.reactOnCancelButton(button);
	}

	@Override
	public void reactOnValidateButton(Button button) {
		_view.reactOnValidateButton(button);
	}
	
	public void describeActivity() {
		_view.describe(_authenticationActivity.getModel().getActivityDescription());
	}
	
	public void addStarToPinField() {
		_pinCodeDisplayed += "*";
		updatePinField();
	}
	
	public void removeStarFromPinField() {
		int length = _pinCodeDisplayed.length();
		_pinCodeDisplayed = _pinCodeDisplayed.substring(0, length - 1);
		updatePinField();
	}
	
	private void updatePinField() {
		TextView pinField = (TextView) _authenticationActivity.findViewById(R.id.pinField);
		pinField.setText(_pinCodeDisplayed);
	}
	
	public void tooMuchEntries() {
		_view.describe(_tooMuchEntries);
	}
	
	public void errorInCode() {
		_view.describe(_errorCode);
		_authenticationActivity.findViewById(R.id.pinField).setBackgroundColor(Color.RED);
	}

	@Override
	public void describe(String speech) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyTTS() {
		_view.destroyTTS();
		
	}
}
