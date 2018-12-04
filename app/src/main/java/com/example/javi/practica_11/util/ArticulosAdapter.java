package com.example.javi.practica_11.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javi.practica_11.base.Articulo;
import com.example.javi.practica_11.R;

import java.util.List;

public class ArticulosAdapter extends BaseAdapter {

	@NonNull
	private final Context context;
	private final int layout;
	private final List<Articulo> articulos;

	public ArticulosAdapter(@NonNull Context context, @LayoutRes int layout, List<Articulo> articulos) {
		this.context = context;
		this.layout = layout;
		this.articulos = articulos;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		NoticiasViewHolder viewHolder = null;

		if (view == null) {
			view = LayoutInflater.from(context).inflate(layout, null);

			viewHolder = new NoticiasViewHolder();
			// TODO: probar con view, si no
			viewHolder.ivImagen = view.findViewById(R.id.ivImagen);
			viewHolder.tvTitulo = view.findViewById(R.id.tvTitulo);
			viewHolder.tvFecha = view.findViewById(R.id.tvFecha);
			viewHolder.tvAutor = view.findViewById(R.id.tvAutor);

			view.setTag(viewHolder);
		} else {
			viewHolder = (NoticiasViewHolder) view.getTag();
		}

		Articulo articulo = articulos.get(i);
		viewHolder.tvTitulo.setText(articulo.getTitulo());
		viewHolder.ivImagen.setImageBitmap(articulo.getImagen());
		viewHolder.tvFecha.setText(Util.formatearFecha(articulo.getFecha()));
		viewHolder.tvAutor.setText(articulo.getAutor());

		return view;
	}

	static class NoticiasViewHolder {
		ImageView ivImagen;
		TextView tvTitulo;
		TextView tvFecha;
		TextView tvAutor;
	}

	@Override
	public int getCount() {
		return articulos.size();
	}

	@Override
	public Object getItem(int i) {
		return articulos.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

}

