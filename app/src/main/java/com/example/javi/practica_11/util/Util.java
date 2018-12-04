package com.example.javi.practica_11.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {


	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
		return bos.toByteArray();
	}

	public static String formatearFecha(Date fecha) {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
		return format.format(fecha);
	}

	public static Bitmap getBitmap(byte[] bytes) {
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	public static Date parsearFecha(String fecha) throws ParseException {
		SimpleDateFormat parser = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
		return parser.parse(fecha);
	}
}
