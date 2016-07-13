package br.pos.trabalho.appdatebook2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;
import br.pos.trabalho.appdatebook2.R;

public class MainActivity extends Activity {

	CalendarView calendar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		calendar = (CalendarView) findViewById(R.id.calendarView);
		calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
			
				if(month >= 0 &&  month <= 12){
					month= month+1;
				}
				Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT)
						.show();
				/**
				 * Enviando parametros para outra activity
				 */
				setParametros(dayOfMonth, month, year);	
			}
		});
	}

	public void setParametros(int dayOfMonth, int month, int year) {
		Intent it = new Intent(MainActivity.this, ListarActivity.class);
		it.putExtra("dia", dayOfMonth);
		it.putExtra("mes", month);
		it.putExtra("ano", year);
		startActivity(it);
	}
	
	//menu
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.sobre) {
			Intent intent = new Intent(this, Sobre.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
