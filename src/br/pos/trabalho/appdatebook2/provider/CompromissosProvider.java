package br.pos.trabalho.appdatebook2.provider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import br.pos.trabalho.appdatebook2.helpers.DatabaseHelper;
import br.pos.trabalho.appdatebook2.model.table.CompromissoTable;

public class CompromissosProvider extends ContentProvider {

	private DatabaseHelper database;

	private static final int COMPROMISSOS = 10;
	private static final int COMPROMISSOS_ID = 20;

	private static final String AUTHORITY = "br.pos.trabalho.appdatebook2.provider";
	private static final String BASE_PATH = "compromissos";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/compromissos";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/compromissos";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, COMPROMISSOS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", COMPROMISSOS_ID);
	}

	@Override
	public boolean onCreate() {
		database = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		checkColumns(projection);
		queryBuilder.setTables(DatabaseHelper.TABLE_COMPROMISSOS);
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case COMPROMISSOS:
			break;
		case COMPROMISSOS_ID:
			// adding the ID to the original query
			queryBuilder.appendWhere(CompromissoTable.COLUMN_ID + "=" + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case COMPROMISSOS:
			rowsDeleted = sqlDB.delete(CompromissoTable.TABLE_COMPROMISSOS, selection, selectionArgs);
			break;
		case COMPROMISSOS_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(CompromissoTable.TABLE_COMPROMISSOS, CompromissoTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(CompromissoTable.TABLE_COMPROMISSOS, CompromissoTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Desconhecido URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case COMPROMISSOS:
			id = sqlDB.insert(CompromissoTable.TABLE_COMPROMISSOS, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case COMPROMISSOS:
			rowsUpdated = sqlDB.update(CompromissoTable.TABLE_COMPROMISSOS, values, selection, selectionArgs);
			break;
		case COMPROMISSOS_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(CompromissoTable.TABLE_COMPROMISSOS, values, CompromissoTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(CompromissoTable.TABLE_COMPROMISSOS, values,
						CompromissoTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;

	}

	private void checkColumns(String[] projection) {
		String[] available = { CompromissoTable.COLUMN_TITULO, CompromissoTable.COLUMN_DATA, CompromissoTable.COLUMN_HORAINICIO,CompromissoTable.COLUMN_HORATERMINO ,
				CompromissoTable.COLUMN_ID };
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
		
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Colunas desconhecidas!");
			}
		}
	}

}
