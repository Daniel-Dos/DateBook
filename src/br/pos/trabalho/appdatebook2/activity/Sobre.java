package br.pos.trabalho.appdatebook2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.pos.trabalho.appdatebook2.R;

public class Sobre extends Activity {

	private ListView listaDeAlunos;
	private static final String[] ALUNOS ={
			"Alan navarro Mendes",
			"Daniel Dias dos Santos matricula: 201410029522",
			"Paulo Rogerio Rodrigues de Araujo matricula: 201411086643",
			"Rafael Lourenco Junior",
			"Thiago Guimaraes Pereira"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ALUNOS);
		listaDeAlunos = (ListView) findViewById(R.id.alunos);
		listaDeAlunos.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sobre, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.telaPrincipal) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}