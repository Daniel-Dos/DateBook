package br.pos.trabalho.appdatebook2.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.pos.trabalho.appdatebook2.model.table.CompromissoTable;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMPROMISSOS = "compromissos";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITULO = "titulo";
	public static final String COLUMN_DATA = "data";
	public static final String COLUMN_HORAINICIO = "hora_inicio";
	public static final String COLUMN_HORATERMINO = "hora_termino";

	public static final String DATABASE_NAME = "compromissos.db";
	public static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CompromissoTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		CompromissoTable.onUpgrade(db, oldVersion, newVersion);
	}

}
