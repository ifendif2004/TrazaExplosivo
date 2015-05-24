package com.alfonso.modelo;

import java.util.ArrayList;

import android.R.bool;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqliteBD implements IGestionBD {

	// DB name
	private static final String DB_NAME = "TrazaExplosivo.sqlite";
	private final String tableName0 = "TABLA0";
	private final String tableName1 = "TABLA1";
	private final String tableName2 = "TABLA2";

	private Context context;
	// Declare SQLiteDatabase object
	SQLiteDatabase sqliteDB = null;

	public SqliteBD(Context context) {
		super();
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#crearBD()
	 */
	@Override
	public boolean crearBD() {

		try {
			// Instantiate sampleDB object
			sqliteDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
			// Create table using execSQL
			sqliteDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName0 + " (SHIPMENT TEXT, "
					+ " SENDER TEXT,"
					+ " SHIPPER TEXT,"
					+ " RECEIVER TEXT);");

			sqliteDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName1 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " CODIGOQR TEXT,"
					+ " LATITUD DOUBLE NOT NULL,"
					+ " LONGITUD DOUBLE NOT NULL,"
					+ " FECHA DATETIME DEFAULT CURRENT_TIMESTAMP,"
					+ " ENCONTRADO BOOL);");

			sqliteDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName2 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " CODIGOQR TEXT,"
					+ " LATITUD DOUBLE NOT NULL,"
					+ " LONGITUD DOUBLE NOT NULL,"
					+ " FECHA DATETIME DEFAULT CURRENT_TIMESTAMP,"
					+ " ENCONTRADO BOOL);");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#altaBD(java.lang.String, java.lang.String, double, double, java.lang.String, boolean)
	 */
	@Override
	public boolean altaBD(String tabla, String codigoQR, double latitud, double longitud, String fechaActual, boolean encontrado) {

		String sentencia = "INSERT INTO " + tabla + "(CODIGOQR, LATITUD, LONGITUD, FECHA, ENCONTRADO)" + " Values ('" + codigoQR + "','" + latitud + "','" + longitud + "','" + fechaActual + "','"
				+ encontrado + "')";
		System.out.println("Sql: " + sentencia);
		try {
			sqliteDB.execSQL(sentencia + ";");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#altaCabeceraBD(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean altaCabeceraBD(String tabla, String s1, String s2, String s3, String s4) {
		String sentencia = "INSERT INTO " + tabla + " (SHIPMENT, SENDER, SHIPPER, RECEIVER)" + " Values ('" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "')";
		System.out.println("Sql: " + sentencia);
		try {
			sqliteDB.execSQL(sentencia + ";");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#inicializarBD(java.lang.String)
	 */
	@Override
	public boolean inicializarBD(String tabla) {
		String sentencia = "DELETE FROM " + tabla;
		System.out.println("Sql: " + sentencia);
		try {
			sqliteDB.execSQL(sentencia + ";");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#updateBD(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean updateBD(String tabla, String codigo, boolean encontrado) {
		String sentencia = "UPDATE " + tabla + " SET ENCONTRADO = " + encontrado + " WHERE CODIGOQR = '" + codigo;
		System.out.println("Sql: " + sentencia);
		try {
			sqliteDB.execSQL(sentencia + ";");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#hayDatos(java.lang.String)
	 */
	@Override
	public boolean hayDatos(String tabla) {
		Boolean retorno;
		Cursor c;
		String sentencia = "SELECT COUNT(*) FROM " + tabla;
		System.out.println("En SqliteBD hayDatos Sql: " + sentencia);
		try {
			c = sqliteDB.rawQuery(sentencia, null);
			c.moveToFirst();
			System.out.println("En SqliteBD  c.getCount(): " + c.getCount() + "  c.getString(0): " + c.getString(0));
			if (Integer.valueOf(c.getString(0)) != 0) {
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception e) {
			return false;
		}
		c.close();
		return retorno;
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#consultaRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean consultaRegistro(String tabla, String codigoqr) {
		Boolean retorno;
		Cursor c;
		String sentencia = "SELECT CODIGOQR FROM " + tabla + " WHERE CODIGOQR = '" + codigoqr + "'";
		System.out.println("En SqliteBD consultaRegistro Sql: " + sentencia);
		try {
			c = sqliteDB.rawQuery(sentencia, null);
			c.moveToFirst();
			System.out.println("En SqliteBD  c.getCount(): " + c.getCount() + "  c.getString(0): " + c.getString(0));
			if (c.getCount() != 0) {
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception e) {
			return false;
		}
		c.close();
		return retorno;
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#closeBD()
	 */
	@Override
	public void closeBD() {
		System.out.println("Cerrar base de datos");
		sqliteDB.close();
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#leerBD(java.lang.String)
	 */
	@Override
	public String[] leerBD(String tabla) {
		String datos[] = null;
		Cursor c = null;
		String sentencia = "SELECT CODIGOQR, ENCONTRADO FROM " + tabla;
		System.out.println("leerBD Sql: " + sentencia);
		try {
			c = sqliteDB.rawQuery(sentencia, null);
			System.out.println("---c.getCount(): " + c.getCount());
			// c.moveToFirst();
			System.out.println("2---c.getCount(): " + c.getCount());
			if (c.getCount() == 0) {
				datos = new String[1];
				datos[0] = "No hay datos";
			} else {
				datos = new String[c.getCount()];

				int i = 0;
				while (c.moveToNext()) {
					datos[i] = c.getString(0).trim();
					System.out.println(i + ": c.getString(0)" + c.getString(0) + " c.getString(1)" + c.getString(1));
					i++;
				}
			}
		} finally {

		}
		c.close();
		System.out.println("vuelta del SqliteDB.leerBD");
		return datos;
	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#leerTabla(java.lang.String)
	 */
	@Override
	public ArrayList<DatoQR> leerTabla(String tabla) {

		ArrayList<DatoQR> datos = new ArrayList<DatoQR>();
		Cursor c = null;
		String sentencia = "SELECT CODIGOQR, LATITUD, LONGITUD, FECHA, ENCONTRADO FROM " + tabla;
		System.out.println("leerTabla Sql: " + sentencia);
		try {
			c = sqliteDB.rawQuery(sentencia, null);
			System.out.println("---c.getCount(): " + c.getCount());
			// c.moveToFirst();
			System.out.println("2---c.getCount(): " + c.getCount());
			if (c.getCount() == 0) {
				DatoQR dqr = new DatoQR();
				dqr.setCodigo("No hay datos");
				dqr.setLatitud(0);
				dqr.setLongitud(0);
				dqr.setFecha("");
				dqr.setValidado(false);
				datos.add(dqr);
			} else {

				while (c.moveToNext()) {
					DatoQR dqr = new DatoQR();
					dqr.setCodigo(c.getString(0).trim());
					dqr.setLatitud(Double.valueOf(c.getString(1)));
					dqr.setLongitud(Double.valueOf(c.getString(2)));
					dqr.setFecha(c.getString(3).trim());
					dqr.setValidado(Boolean.valueOf(c.getString(4)));
					datos.add(dqr);
				}
			}
		} finally {

		}
		c.close();
		return datos;

	}

	/* (non-Javadoc)
	 * @see com.alfonso.modelo.IGestionBD#leerBDCabecera(java.lang.String)
	 */
	@Override
	public String leerBDCabecera(String tabla) {
		String retorno;
		Cursor c;
		String sentencia = "SELECT SHIPMENT, SENDER, SHIPPER FROM " + tabla;
		System.out.println("En SqliteBD leerBDCabecera Sql: " + sentencia);
		try {
			c = sqliteDB.rawQuery(sentencia, null);
			c.moveToFirst();
			System.out.println("En SqliteBD  c.getCount(): " + c.getCount() + "  c.getString(0): " + c.getString(0));
			if (c.getCount() != 0) {
				retorno = c.getString(0).trim() + "\n" + c.getString(1).trim() + "\n" + c.getString(2).trim();
			} else {
				retorno = "";
			}
		} catch (Exception e) {
			return "";
		}
		c.close();
		System.out.println("En SqliteBD  retorno: " + retorno);
		return retorno;
	}

}
