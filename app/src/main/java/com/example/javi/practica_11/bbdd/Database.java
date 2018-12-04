package com.example.javi.practica_11.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.javi.practica_11.base.Articulo;
import com.example.javi.practica_11.util.Util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static android.provider.BaseColumns._ID;
import static com.example.javi.practica_11.bbdd.Contract.*;

public class Database extends SQLiteOpenHelper {

	private static final int VERSION = 1;

	public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, BASE_DATOS, null, VERSION);
	}

	public Database(Context context) {
		super(context, BASE_DATOS, null, VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_ARTICULOS + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITULO + " TEXT, "
				+ IMAGEN + " BLOB, " + CONTENIDO + " TEXT, " + FECHA + " TEXT, "
				+ AUTOR + " TEXT, " + FAVORITO + " BOOLEAN)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_ARTICULOS);
		onCreate(sqLiteDatabase);
	}

	public void nuevoArticulo(Articulo articulo) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TITULO, articulo.getTitulo());
		values.put(IMAGEN, Util.getBytes(articulo.getImagen()));
		values.put(CONTENIDO, articulo.getContenido());
		values.put(FECHA, Util.formatearFecha(articulo.getFecha()));
		values.put(AUTOR, articulo.getAutor());
		values.put(FAVORITO, articulo.isFavorito()? 1 : 0);

		db.insertOrThrow(TABLA_ARTICULOS, null, values);
		db.close();
	}

	public void eliminarArticulo(Articulo articulo) {
		SQLiteDatabase db = getWritableDatabase();

		String[] argumentos = new String[]{String.valueOf(articulo.getId())};
		db.delete(TABLA_ARTICULOS, _ID + " = ?", argumentos);
		db.close();
	}

	public void modificarArticulo(Articulo articulo) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TITULO, articulo.getTitulo());
		values.put(IMAGEN, Util.getBytes(articulo.getImagen()));
		values.put(CONTENIDO, articulo.getContenido());
		values.put(FECHA, Util.formatearFecha(articulo.getFecha()));
		values.put(AUTOR, articulo.getAutor());
		values.put(FAVORITO, articulo.isFavorito() ? 1 : 0);

		String[] argumentos = new String[]{String.valueOf(articulo.getId())};
		db.update(TABLA_ARTICULOS, values, _ID + " = ?", argumentos);
		db.close();
	}

	public ArrayList<Articulo> getArticulos() {
		final String[] SELECT = {_ID, TITULO, IMAGEN, CONTENIDO, FECHA, AUTOR, FAVORITO};
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLA_ARTICULOS, SELECT, null, null, null, null,
				null);

		ArrayList<Articulo> listaArticulos = new ArrayList<>();
		Articulo articulo = null;
		while (cursor.moveToNext()) {
			articulo = new Articulo();
			articulo.setId(cursor.getLong(0));
			articulo.setTitulo(cursor.getString(1));
			articulo.setImagen(Util.getBitmap(cursor.getBlob(2)));
			articulo.setContenido(cursor.getString(3));
			try {
				articulo.setFecha(Util.parsearFecha(cursor.getString(4)));
			} catch (ParseException pe) {
				articulo.setFecha(new Date(System.currentTimeMillis()));
			}
			articulo.setAutor(cursor.getString(5));
			articulo.setFavorito(cursor.getInt(6) == 1);

			listaArticulos.add(articulo);
		}
		cursor.close();
		db.close();

		return listaArticulos;
	}
}
