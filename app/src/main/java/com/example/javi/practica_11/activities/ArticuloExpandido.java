package com.example.javi.practica_11.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javi.practica_11.R;
import com.example.javi.practica_11.base.Articulo;
import com.example.javi.practica_11.util.Util;

import static com.example.javi.practica_11.util.Constantes.EXTRA_IMAGEN;
import static com.example.javi.practica_11.util.Constantes.EXTRA_ARTICULO;

public class ArticuloExpandido extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articulo_expandido);

		Intent intent = getIntent();
		Articulo articulo = (Articulo) intent.getSerializableExtra(EXTRA_ARTICULO);
		articulo.setImagen(Util.getBitmap(intent.getByteArrayExtra(EXTRA_IMAGEN)));

		TextView tvTitulo = findViewById(R.id.tvTitulo);
		ImageView ivImagen = findViewById(R.id.ivImagen);
		TextView tvFecha = findViewById(R.id.tvFecha);
		TextView tvAutor = findViewById(R.id.tvAutor);
		TextView tvContenido = findViewById(R.id.tvContenido);
		Button btVolver = findViewById(R.id.btVolver);

		tvTitulo.setText(articulo.getTitulo());
		ivImagen.setImageBitmap(articulo.getImagen());
		tvFecha.setText(Util.formatearFecha(articulo.getFecha()));
		tvAutor.setText(articulo.getAutor());
		tvContenido.setText(articulo.getContenido());
		btVolver.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}


}
