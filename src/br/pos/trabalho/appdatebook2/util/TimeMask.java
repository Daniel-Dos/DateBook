package br.pos.trabalho.appdatebook2.util;

import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

public class TimeMask implements OnKeyListener{

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.i("TIME", "KeyCode" + keyCode);
		EditText ed = (EditText) v;
		if(event.getAction() == KeyEvent.ACTION_UP && keyCode != KeyEvent.KEYCODE_DEL){
			int size = ed.getText().length();
			if(size == 2){
				ed.setTextKeepState(ed.getText() + ":");
			}
			if(size > 5){
				String sub = ed.getText().toString();
				ed.setTextKeepState(sub.substring(0, 5));
			}
		}
		/**
		 * Posiciona o cursor no fim
		 */
		Selection.setSelection(ed.getText(), ed.getText().toString()
				.length());
		return false;
	}

}
