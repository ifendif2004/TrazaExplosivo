package com.alfonso.modelo;

import android.content.Context;

public class GrabarQR {

	private Context contexto;
	private IGestionBD sqlite;

	public GrabarQR(Context cont, IGestionBD sqlite) {
		this.sqlite = sqlite;
		contexto = cont;
	}

	public void grabarBD(String string, String codigo, double latitud, double longitud, String fecha) {
		// TODO Auto-generated method stub

	}
}
