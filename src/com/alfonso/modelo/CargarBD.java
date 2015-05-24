package com.alfonso.modelo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class CargarBD extends AsyncTask<String, Integer, Boolean> {

	private String fichero;
	private Context context;
	private ProgressDialog dialog;

	public CargarBD(String fichero, Context context) {
		this.fichero = fichero;
		this.context = context;
		System.out.println("En CargarBD... fichero: " + fichero);
		dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Leyendo fichero xml...");
		dialog.show();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Boolean doInBackground(String... params) {

		CargarXmlParserSax saxparser = new CargarXmlParserSax(fichero, context);
		return saxparser.parse();

	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
			Toast.makeText(context, "Proceso terminado", Toast.LENGTH_LONG).show();

		}
	}

}
