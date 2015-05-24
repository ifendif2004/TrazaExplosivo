package com.alfonso.trazaexplosivo;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.alfonso.modelo.DatoQR;
import com.alfonso.modelo.GrabarXml;
import com.alfonso.modelo.IGestionBD;
import com.alfonso.modelo.SqliteBD;
import com.alfonso.modelo.TrzExplosivoParserSax;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class GenerarFicheroXml extends AsyncTask<String, Integer, Boolean> {

	private static ArrayList<DatoQR> mArray = new ArrayList<DatoQR>();
	private IGestionBD sqlite;
	private String fichero;
	private String directorioXml;
	private String directorioApp;
	private GrabarXml gxml;
	private ProgressDialog dialog;
	private Context context;

	public GenerarFicheroXml(Context applicationContext, String directorioXml, String directorioApp, String fichero) {
		this.context = applicationContext;
		this.directorioApp = directorioApp;
		this.directorioXml = directorioXml;
		this.fichero = fichero;

		System.out.println("En GenerarFichero   directorioApp: " + directorioApp);
		System.out.println("En GenerarFichero   directorioXml: " + directorioXml);
		System.out.println("En GenerarFichero   fichero: " + fichero);
		dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		System.out.println("onPreExecute");
		dialog.setMessage("Generando fichero xml...");
		dialog.show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		System.out.println("En GenerarFichero   doInBackground " + params[0]);
		sqlite = new SqliteBD(context);
		sqlite.crearBD();
		mArray = sqlite.leerTabla("TABLA2");
		gxml = new GrabarXml(context, directorioXml, directorioApp, fichero);
		gxml.grabarXml_startTag("codigosQR");
		for (Iterator<DatoQR> iterator = mArray.iterator(); iterator.hasNext();) {
			DatoQR datoQR = (DatoQR) iterator.next();
			gxml.grabarXml_tag("alfonso", datoQR.getCodigo(), datoQR.getLatitud(), datoQR.getLongitud(), datoQR.getFecha());
		}
		gxml.grabarXml_endTag("codigosQR");
		gxml.grabarXml_fin();
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
			Toast.makeText(context, "Fichero Xml generado", Toast.LENGTH_LONG).show();

		}
	}
}
