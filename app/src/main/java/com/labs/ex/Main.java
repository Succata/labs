package com.labs.ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.PrimitiveElement;

import java.util.regex.Pattern;

public class Main extends AppCompatActivity {

	TextView editText = null;
	Toast toast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = findViewById(R.id.textView);
		findViewById(R.id.clear).setOnLongClickListener(v -> {
			editText.setText("");
			return true;
		});
		toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
	}

	public void buttons(View view) {
		switch (view.getId()) {
			case R.id.zero:
			case R.id.one:
			case R.id.two:
			case R.id.three:
			case R.id.four:
			case R.id.five:
			case R.id.six:
			case R.id.seven:
			case R.id.eight:
			case R.id.nine:
				addNumeral(((TextView) view).getText());
				break;
			case R.id.divide:
			case R.id.multiply:
			case R.id.add:
			case R.id.subtract:
				addOperation(((TextView) view).getText());
				break;
			case R.id.clear:
				clear();
				break;
			case R.id.equal:
				equal();
				break;
		}
	}

	private void addOperation(CharSequence text) {
		if (text.equals("-")) {
			if (Pattern.matches(".*[+-]$", editText.getText())) return;
		} else if (Pattern.matches(".*([*/+-])|(^)$", editText.getText())) return;
		editText.setText(editText.getText() + text.toString());

	}

	private void equal() {
		Expression e = new Expression(editText.getText().toString());
		double dbl = e.calculate();
		if (String.valueOf(dbl).equals("NaN")) {
			if (toast.getView().getWindowVisibility() != View.VISIBLE) {
				toast.setText(getResources().getString(R.string.expression_exception));
				toast.show();
			}
		} else {
			String result = Math.round(dbl) == dbl ? "" + Math.round(dbl) : Double.toString(dbl);
			editText.setText(result);
		}
	}

	private void clear() {
		CharSequence text = editText.getText();
		if (text.length() != 0) editText.setText(text.subSequence(0, text.length() - 1));
	}

	private void addNumeral(CharSequence text) {
		if (text.equals("0") && ! Pattern.matches(".*[0-9]$", editText.getText())) return;
		editText.setText(editText.getText() + text.toString());
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		editText.setText(savedInstanceState.getCharSequence("editTextValue"));
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putCharSequence("editTextValue", editText.getText());
	}
}