package fr.ensicaen.equipe1.handiclient.withdrawmoneypackage;

import android.widget.Button;
import android.widget.TextView;
import fr.ensicaen.equipe1.handiclient.R;
import fr.ensicaen.equipe1.handiclient.viewpackage.AudioView;
import fr.ensicaen.equipe1.handiclient.viewpackage.IView;

public class WithdrawMoneyView implements IView{
	
	private WithdrawMoneyActivity _withdrawMoneyActivity;
	private IView _view;
	
	private String _amountDisplayed = "";
	private String _tooMuchEntries = "Impossible, le retrait doit �tre inf�rieur � 999 euros.";

	public WithdrawMoneyView(WithdrawMoneyActivity withdrawMoneyActivity, String viewType) {
		_withdrawMoneyActivity = withdrawMoneyActivity;
		if(viewType.equals("AUDIO_MODE")) {
			_view = new AudioView(_withdrawMoneyActivity,R.id.layoutWithdrawmoney);
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


	public void addNumberToAmountField(int i) {
		_amountDisplayed += i;
		updateAmountField();
		
	}


	public void removeNumberFromAmountField() {
		int length = _amountDisplayed.length();
		_amountDisplayed = _amountDisplayed.substring(0, length - 1);
		updateAmountField();
		
	}
	

	private void updateAmountField() {
		TextView amountField = (TextView) _withdrawMoneyActivity.findViewById(R.id.amountField);
		amountField.setText(_amountDisplayed);	
	}

	@Override
	public void describe(String speech) {
		_view.describe(speech);
		
	}

	@Override
	public void reactOnNumberButtons(Button button) {
		_view.reactOnNumberButtons(button);
		
	}

	@Override
	public void reactOnCancelButton(Button button) {
		_view.reactOnCancelButton(button);
		
	}

	@Override
	public void reactOnValidateButton(Button button) {
		_view.reactOnValidateButton(button);
		
	}

	public void tooMuchEntries() {
		_view.describe(_tooMuchEntries);	
	}

	@Override
	public void destroyTTS() {
		_view.destroyTTS();
		
	}

	@Override
	public void reactOnSecretNumberButtons(Button button) {
		// TODO Auto-generated method stub
		
	}
}
