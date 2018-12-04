package com.example.javi.practica_11.base;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class Articulo implements Serializable{
	private long id;
	private String titulo;
	private transient Bitmap imagen;
	private String contenido;
	private Date fecha;
	private String autor;
	private boolean favorito;

	{
		favorito = false;
	}

	public Articulo(String titulo, Bitmap imagen, String contenido, Date fecha, String autor) {
		this.titulo = titulo;
		this.imagen = imagen;
		this.contenido = contenido;
		this.fecha = fecha;
		this.autor = autor;
	}

	public Articulo() {
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Bitmap getImagen() {
		return imagen;
	}

	public void setImagen(Bitmap imagen) {
		this.imagen = imagen;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public long getId() {
		return id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}
}
