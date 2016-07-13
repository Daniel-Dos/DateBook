package br.pos.trabalho.appdatebook2.activity;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import br.pos.trabalho.appdatebook2.R;
import br.pos.trabalho.appdatebook2.model.table.CompromissoTable;
import br.pos.trabalho.appdatebook2.provider.CompromissosProvider;

public class ListarActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	//private static final int ACTIVITY_CREATE = 0;
	//private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	protected int dia, mes, ano;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_compromissos);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		dia = bundle.getInt("dia");
		mes = bundle.getInt("mes");
		ano = bundle.getInt("ano");

		this.getListView().setDividerHeight(2);
		preencherDados();
		registerForContextMenu(getListView());

		Button btnSalvarNovo = (Button) findViewById(R.id.btnSalvar);
		btnSalvarNovo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ListarActivity.this, CadastrarActivity.class);
				it.putExtra("dia", dia);
				it.putExtra("mes", mes);
				it.putExtra("ano", ano);
				startActivity(it);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lista_exemplo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.btnSalvar:
			createCompromisso();
			return true;
		case R.id.telaPrinciapal:
			menuPrincipal();
			return true;
		}
		
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			Uri uri = Uri.parse(CompromissosProvider.CONTENT_URI + "/" + info.id);
			getContentResolver().delete(uri, null, null);
			preencherDados();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void createCompromisso() {
		Intent i = new Intent(this, CadastrarActivity.class);
		startActivity(i);
	}
	
	private void menuPrincipal() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, CompromissoAction.class);
		Uri compromissoUri = Uri.parse(CompromissosProvider.CONTENT_URI + "/" + id);
		i.putExtra(CompromissosProvider.CONTENT_ITEM_TYPE, compromissoUri);
		startActivity(i);
	}

	private void preencherDados() {

		String[] from = new String[] { CompromissoTable.COLUMN_TITULO };
		// Fields on the UI to which we map
		int[] to = new int[] { R.id.label };
		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.compromisso_linha, null, from, to, 0);
		setListAdapter(adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { CompromissoTable.COLUMN_ID, CompromissoTable.COLUMN_TITULO };
		CursorLoader cursorLoader = new CursorLoader(this, CompromissosProvider.CONTENT_URI, projection, null, null,
				null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
	
	
	
}
