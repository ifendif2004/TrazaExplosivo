package com.alfonso.trazaexplosivo;

import com.alfonso.modelo.CargarBD;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CargarXml extends Activity {

	private String ficheroOrigen;
	private String ficheroDestino;
	private String directorioXml;
	private String directorioApp;
	private List<String> items;
	private File directorio;
	private TextView ruta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cargar_xml);
		items = new ArrayList<String>();
		SharedPreferences prefs = getSharedPreferences("PreferenciasTrazaExplosivo", Context.MODE_PRIVATE);
		directorioXml = prefs.getString("directorioXml", "");
		directorioApp = prefs.getString("directorioApp", "");

		// directorio = new File(directorioApp);
		directorio = new File(directorioXml);

		if (directorio.listFiles() == null) {
			Toast.makeText(this, "No se encuentra ningún fichero Xml para validar", Toast.LENGTH_LONG).show();
		} else {
			File[] files = directorio.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.toString().endsWith(".xml")) {
					items.add(file.getName());
				}
			}
		}
		// Mostramos la ruta en pantalla
		ruta = (TextView) findViewById(R.id.ruta);
		ruta.setText(directorio.getAbsolutePath().toString());

		// Localizamos y llenamos la lista
		ListView lstOpciones = (ListView) findViewById(R.id.listaFiles);
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lstOpciones.setAdapter(fileList);

		// Accion para realizar al pulsar sobre la lista
		lstOpciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				// String fechaActual = sdf.format(new Date());

				ficheroOrigen = (directorio.getAbsolutePath()) + "/" + items.get(position);
				ficheroDestino = directorioApp + "/" + items.get(position);
				// ficheroDestino = directorioApp + "/" + fechaActual + "_" + items.get(position);

				CopiarFicheroOrDirectorio copiarFichero = new CopiarFicheroOrDirectorio();
				try {
					copiarFichero.copiar(ficheroOrigen, directorioApp);
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), "No se ha podido copiar el fichero Xml en el directorio de la App", Toast.LENGTH_LONG).show();
				}

				CargarBD cBD = new CargarBD(ficheroDestino, CargarXml.this);
				cBD.execute(ficheroDestino);

			}
		});
	}

}
