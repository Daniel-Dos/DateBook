package br.pos.trabalho.appdatebook2.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.pos.trabalho.appdatebook2.R;
import br.pos.trabalho.appdatebook2.model.Compromisso;
import br.pos.trabalho.appdatebook2.model.table.CompromissoTable;
import br.pos.trabalho.appdatebook2.provider.CompromissosProvider;
import br.pos.trabalho.appdatebook2.util.TimeMask;

public class CadastrarActivity extends Activity {

	TextView data;
	EditText titulo, horaInicio, horaTermino; 
	int dia, mes, ano;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastrar_compromisso);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		dia = bundle.getInt("dia");
		mes = bundle.getInt("mes");
		ano = bundle.getInt("ano");
		data = (TextView) findViewById(R.id.dataDoCadastro);
		String dataCadastro = String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(ano);
		data.setText(dataCadastro);
		titulo = (EditText) findViewById(R.id.inputTitulo);
		horaInicio = (EditText) findViewById(R.id.inicio);
		horaInicio.setOnKeyListener(new TimeMask());
		horaTermino = (EditText) findViewById(R.id.termino);
		horaTermino.setOnKeyListener(new TimeMask());

		Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Compromisso compromisso = new Compromisso();
				compromisso.setTitulo(titulo.getText().toString());
				compromisso.setData(data.getText().toString());
				compromisso.setHoraInicio(horaInicio.getText().toString());
				compromisso.setHoraTermino(horaTermino.getText().toString());
				boolean sucesso = adicionar(compromisso);
				if (sucesso) {
					Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG)
							.show();
					setResult(RESULT_OK);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Não foi possivel realizar o cadastro!", Toast.LENGTH_LONG)
							.show();
					finish();
				}
			}
		});
	}

	public boolean adicionar(Compromisso compromisso) {
		ContentValues values = new ContentValues();
		values.put(CompromissoTable.COLUMN_TITULO, compromisso.getTitulo().toString());
		values.put(CompromissoTable.COLUMN_DATA, compromisso.getData().toString());
		values.put(CompromissoTable.COLUMN_HORAINICIO, compromisso.getHoraInicio().toString());
		values.put(CompromissoTable.COLUMN_HORATERMINO, compromisso.getHoraTermino().toString());
		getContentResolver().insert(CompromissosProvider.CONTENT_URI, values);
		return true;
	}
}
