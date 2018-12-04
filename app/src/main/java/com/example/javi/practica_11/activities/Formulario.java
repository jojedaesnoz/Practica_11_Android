package com.example.javi.practica_11.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.javi.practica_11.R;
import com.example.javi.practica_11.base.Articulo;
import com.example.javi.practica_11.bbdd.Database;
import com.example.javi.practica_11.util.Util;

import java.text.ParseException;
import java.util.Date;

import static com.example.javi.practica_11.util.Constantes.EXTRA_ACCION;
import static com.example.javi.practica_11.util.Constantes.EXTRA_ARTICULO;
import static com.example.javi.practica_11.util.Constantes.EXTRA_IMAGEN;
import static com.example.javi.practica_11.util.Constantes.MODIFICAR;
import static com.example.javi.practica_11.util.Constantes.NUEVO;

public class Formulario extends AppCompatActivity implements View.OnClickListener{

	private static final int CARGA_IMAGEN = 1;
	private EditText etTitulo, etFecha, etAutor, etContenido;
	private ImageButton ibImagen;
	private Button btCancelar, btGuardar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario);

		inicializarViews();
		Intent intent = getIntent();
		String accion = intent.getStringExtra(EXTRA_ACCION);
		if (accion.equals(MODIFICAR)) {
			Articulo articulo = (Articulo) intent.getSerializableExtra(EXTRA_ARTICULO);
			byte[] imagen = intent.getByteArrayExtra(EXTRA_IMAGEN);
			etTitulo.setText(articulo.getTitulo());
			ibImagen.setImageBitmap(Util.getBitmap(imagen));
			etFecha.setText(Util.formatearFecha(articulo.getFecha()));
			etAutor.setText(articulo.getAutor());
			etContenido.setText(articulo.getContenido());

			etTitulo.requestFocus();
			etTitulo.selectAll();
		}
		ponerListeners();
	}

	private void ponerListeners() {
		ibImagen.setOnClickListener(this);
		btGuardar.setOnClickListener(this);
		btCancelar.setOnClickListener(this);
	}

	private void inicializarViews() {
		etTitulo = findViewById(R.id.etTitulo);
		etFecha = findViewById(R.id.etFecha);
		etAutor = findViewById(R.id.etAutor);
		etContenido = findViewById(R.id.etContenido);
		ibImagen = findViewById(R.id.ibImagen);
		btCancelar = findViewById(R.id.btCancelar);
		btGuardar = findViewById(R.id.btGuardar);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.ibImagen:
				// Recoger la imagen
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, CARGA_IMAGEN);
				break;
			case R.id.btCancelar:
				// Volver atras
				onBackPressed();
				break;
			case R.id.btGuardar:
				// Verificar que los campos necesarios han sido introducidos
				for (EditText et: new EditText[]{etTitulo, etContenido}) {
					if (et.getText().toString().isEmpty()) {
						Toast.makeText(this, et.getHint() + " es un campo necesario",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}

				// Construir la articulo con los datos introducidos
				Articulo articulo = new Articulo();
				articulo.setTitulo(etTitulo.getText().toString());
				Bitmap imagen = ((BitmapDrawable)(ibImagen.getDrawable())).getBitmap();
				articulo.setImagen(imagen);
				try {
					articulo.setFecha(Util.parsearFecha(etFecha.getText().toString()));
				} catch (ParseException e) {
					articulo.setFecha(new Date(System.currentTimeMillis()));
				}
				articulo.setAutor(etAutor.getText().toString());
				articulo.setContenido(etContenido.getText().toString());


				// Guardar en la base de datos
				Database db = new Database(this);
				String accion = getIntent().getStringExtra(EXTRA_ACCION);
				if (accion.equals(NUEVO)) {
					db.nuevoArticulo(articulo);
				} else if (accion.equals(MODIFICAR)) {
					articulo.setId(((Articulo) getIntent().getSerializableExtra(EXTRA_ARTICULO)).getId());
					db.modificarArticulo(articulo);
				}

				// Cerrar la actividad
				finish();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if ((requestCode == CARGA_IMAGEN) && (resultCode == RESULT_OK)
				&& (data != null)) {
			Uri imagenSeleccionada = data.getData();
			String[] ruta = {MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(imagenSeleccionada, ruta, null, null, null);
			cursor.moveToFirst();

			int indice = cursor.getColumnIndex(ruta[0]);
			String picturePath = cursor.getString(indice);
			cursor.close();

			ibImagen.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		}
	}
}
