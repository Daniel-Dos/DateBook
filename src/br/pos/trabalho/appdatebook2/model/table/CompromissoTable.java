package br.pos.trabalho.appdatebook2.model.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CompromissoTable {

	  // Database table
	public static final String TABLE_COMPROMISSOS = "compromissos";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITULO = "titulo";
	public static final String COLUMN_DATA = "data";
	public static final String COLUMN_HORAINICIO = "hora_inicio";
	public static final String COLUMN_HORATERMINO = "hora_termino";

	public static final String DATABASE_NAME = "compromissos.db";
	public static final int DATABASE_VERSION = 1;
	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_COMPROMISSOS + "(" + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_TITULO + " TEXT NOT NULL, " + COLUMN_DATA + " TEXT NOT NULL, " + COLUMN_HORAINICIO
				+ " TEXT NOT NULL, " + COLUMN_HORATERMINO + " TEXT NOT NULL );";

	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	      int newVersion) {
		  Log.w(CompromissoTable.class.getName(), " Atualizando a base de dados de  " + oldVersion + " para" + newVersion
					+ ", que irá destruir todos os dados antigos");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPROMISSOS);
			onCreate(db);
	  }
	} 