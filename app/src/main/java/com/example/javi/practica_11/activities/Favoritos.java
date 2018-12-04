package com.example.javi.practica_11.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.javi.practica_11.R;
import com.example.javi.practica_11.base.Articulo;
import com.example.javi.practica_11.util.ArticulosAdapter;
import com.example.javi.practica_11.util.Util;

import java.util.ArrayList;

import static com.example.javi.practica_11.util.Constantes.FAVORITOS;
import static com.example.javi.practica_11.util.Constantes.FAVORITOS_IMAGENES;

public class Favoritos extends AppCompatActivity {
	private ArrayList<Articulo> favoritos;
	private ArrayList<byte[]> favoritosImagenes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoritos);

		Intent intent = getIntent();
		favoritos = (ArrayList<Articulo>) intent.getSerializableExtra(FAVORITOS);
		favoritosImagenes = (ArrayList<byte[]>) intent.getSerializableExtra(FAVORITOS_IMAGENES);

		for (int i = 0; i < favoritos.size(); i++) {
			favoritos.get(i).setImagen(Util.getBitmap(favoritosImagenes.get(i)));
			System.out.println(favoritos);
		}

		ListView lvFavoritos = findViewById(R.id.lvFavoritos);
		ArticulosAdapter adapter = new ArticulosAdapter(this, R.layout.articulo_item, favoritos);
		lvFavoritos.setAdapter(adapter);
	}
}
