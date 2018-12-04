package com.example.javi.practica_11.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.javi.practica_11.base.Articulo;
import com.example.javi.practica_11.R;
import com.example.javi.practica_11.bbdd.Database;
import com.example.javi.practica_11.util.ArticulosAdapter;
import com.example.javi.practica_11.util.Util;

import java.util.ArrayList;

import static com.example.javi.practica_11.util.Constantes.*;

public class ListadoArticulos extends AppCompatActivity implements AdapterView.OnItemClickListener {
	// Views
	private ListView lvArticulos;

	// Datos
	private ArrayList<Articulo> articulos = new ArrayList<>();
	private Database db;

	// Otros
	private ArticulosAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listado_articulos);

		db = new Database(this);
		articulos = db.getArticulos();
		adapter = new ArticulosAdapter(this, R.layout.articulo_item, articulos);

		lvArticulos = findViewById(R.id.lvArticulos);
		lvArticulos.setAdapter(adapter);
		lvArticulos.setOnItemClickListener(this);
		registerForContextMenu(lvArticulos);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent intent = new Intent(this, ArticuloExpandido.class);
		Articulo articulo = articulos.get(i);
		intent.putExtra(EXTRA_ARTICULO, articulo);
		intent.putExtra(EXTRA_IMAGEN, Util.getBytes(articulo.getImagen()));
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_listado, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menu_nuevo_articulo:
				Intent intent = new Intent(this, NuevoArticulo.class);
				intent.putExtra(EXTRA_ACCION, NUEVO);
				startActivity(intent);
				return true;
			case R.id.menu_acerca_de:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Práctica 1.1 de Programación multimedia y dispositivos móviles" +
						"\nProfesor: Santiago Faci" + "\nAlumno: Javier Ojeda");
				builder.create().show();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		articulos = db.getArticulos();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.list_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		Database db = new Database(this);
		switch(item.getItemId()) {
			case R.id.menu_eliminar:
				db.eliminarArticulo(articulos.get(info.position));
				articulos = db.getArticulos();
				adapter.notifyDataSetChanged();
				return true;
			case R.id.menu_modificar:
				Intent intent = new Intent(this, NuevoArticulo.class);
				intent.putExtra(EXTRA_ACCION, MODIFICAR);
				intent.putExtra(EXTRA_ARTICULO, articulos.get(info.position));
				startActivity(intent);
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
}
