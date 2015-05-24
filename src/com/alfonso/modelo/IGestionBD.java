package com.alfonso.modelo;

import java.util.ArrayList;

public interface IGestionBD {

	public abstract boolean crearBD();

	public abstract boolean altaBD(String tabla, String codigoQR, double latitud, double longitud, String fechaActual, boolean encontrado);

	public abstract boolean altaCabeceraBD(String tabla, String s1, String s2, String s3, String s4);

	public abstract boolean inicializarBD(String tabla);

	public abstract boolean updateBD(String tabla, String codigo, boolean encontrado);

	public abstract boolean hayDatos(String tabla);

	public abstract boolean consultaRegistro(String tabla, String codigoqr);

	public abstract void closeBD();

	public abstract String[] leerBD(String tabla);

	public abstract ArrayList<DatoQR> leerTabla(String tabla);

	public abstract String leerBDCabecera(String tabla);

}