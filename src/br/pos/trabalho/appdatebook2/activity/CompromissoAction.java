package br.pos.trabalho.appdatebook2.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.pos.trabalho.appdatebook2.R;
import br.pos.trabalho.appdatebook2.model.table.CompromissoTable;
import br.pos.trabalho.appdatebook2.provider.CompromissosProvider;

public class CompromissoAction extends Activity {

	TextView dataDoCadastro;
	EditText inputTitulo;
	EditText inicio;
	EditText termino;

	private Uri compromissoUri;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.cadastrar_compromisso);
		dataDoCadastro = (TextView) findViewById(R.id.dataDoCadastro);
		inputTitulo = (EditText) findViewById(R.id.inputTitulo);
		inicio = (EditText) findViewById(R.id.inicio);
		termino = (EditText) findViewById(R.id.termino);
		Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
		Bundle extras = getIntent().getExtras();

		compromissoUri = (bundle == null) ? null : (Uri) bundle.getParcelable(CompromissosProvider.CONTENT_ITEM_TYPE);

		if (extras != null) {
			compromissoUri = extras.getParcelable(CompromissosProvider.CONTENT_ITEM_TYPE);
			preencherDados(compromissoUri);
		}

		btnSalvar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (TextUtils.isEmpty(inputTitulo.getText().toString())) {
					makeToast();
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}

		});
	}

	private void preencherDados(Uri uri) {
		String[] projection = { CompromissoTable.COLUMN_TITULO, CompromissoTable.COLUMN_DATA,
				CompromissoTable.COLUMN_HORAINICIO, CompromissoTable.COLUMN_HORATERMINO };
		
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			String data = cursor.getString(cursor.getColumnIndexOrThrow(CompromissoTable.COLUMN_DATA));
			String titulo = cursor.getString(cursor.getColumnIndexOrThrow(CompromissoTable.COLUMN_TITULO));
			String cinicio = cursor.getString(cursor.getColumnIndexOrThrow(CompromissoTable.COLUMN_HORAINICIO));
			String ctermino = cursor.getString(cursor.getColumnIndexOrThrow(CompromissoTable.COLUMN_HORATERMINO));

			dataDoCadastro.setText(data);
			inputTitulo.setText(titulo);
			inicio.setText(cinicio);
			termino.setText(ctermino);
			cursor.close();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putParcelable(CompromissosProvider.CONTENT_ITEM_TYPE, compromissoUri);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	private void saveState() {
		String dtCadastro = dataDoCadastro.getText().toString();
		String titulo =  inputTitulo.getText().toString();
		String cinicio = inicio.getText().toString();
		String ctermino = termino.getText().toString();
		if (dtCadastro.length() == 0 && titulo.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(CompromissoTable.COLUMN_DATA, dtCadastro);
		values.put(CompromissoTable.COLUMN_TITULO, titulo);
		values.put(CompromissoTable.COLUMN_HORAINICIO, cinicio);
		values.put(CompromissoTable.COLUMN_HORATERMINO, ctermino);

		if (compromissoUri == null) {
			// Novo compromisso
			compromissoUri = getContentResolver().insert(CompromissosProvider.CONTENT_URI, values);
		} else {
			// atualizar compromisso
			getContentResolver().update(compromissoUri, values, null, null);
		}
	}

	public void inserir(ContentValues values){
		compromissoUri = getContentResolver().insert(CompromissosProvider.CONTENT_URI, values);
		
	}
	private void makeToast() {
		Toast.makeText(CompromissoAction.this, "Por favor informe o Titulo", Toast.LENGTH_LONG).show();
	}
}
