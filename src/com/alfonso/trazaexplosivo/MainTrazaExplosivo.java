package com.alfonso.trazaexplosivo;

import com.alfonso.modelo.IGestionBD;
import com.alfonso.modelo.SqliteBD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainTrazaExplosivo extends Activity {

	private String directorioXml;
	private String directorioApp;
	private TextView tvFicheroCargado;
	private CheckBox cbCargaXml;
	private IGestionBD sqlite;
	private Button btCargarXml;
	private Button btValidarQR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		directorioXml = "/storage/sdcard0/Download";
//		 directorioXml = "/data/data/com.alfonso.trazaexplosivo";
		directorioApp = getFilesDir().getPath();

		SharedPreferences prefs = getSharedPreferences("PreferenciasTrazaExplosivo", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("directorioXml", directorioXml);
		editor.putString("directorioApp", directorioApp);
		editor.commit();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_traza_explosivo);
		cbCargaXml = (CheckBox) findViewById(R.id.cbFicheroXml);
		tvFicheroCargado = (TextView) findViewById(R.id.tvFicheroCargado);

	}

	@Override
	protected void onResume() {
		super.onResume();

		conectarSqlite();
		Boolean existe = sqlite.hayDatos("TABLA1");
		cbCargaXml.setChecked(existe);
		String resultado = sqlite.leerBDCabecera("TABLA0");
		if (resultado == "") {
			resultado = "No hay datos cargados";
		}
		tvFicheroCargado.setText(resultado);

		buscarFicheroXml();
		listarFicheroXml();
		capturarCodigosQR();
		listarFicheroQR();
		resultadoFinal();
	}

	private void conectarSqlite() {
		sqlite = new SqliteBD(this);
		sqlite.crearBD();

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	// Cargar el fichero Xml a tratar/validar -----------------------------------------------------------------------
	private void buscarFicheroXml() {
		System.out.println("buscarFicheroXml");
		
		btCargarXml = (Button) findViewById(R.id.btCargarXml);
		btCargarXml.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(MainTrazaExplosivo.this, CargarXml.class);
				startActivity(intent);
			}
		});

	}

	// Listar el fichero Xml cargado -----------------------------------------------------------------------------------
	private void listarFicheroXml() {

		Button btCargarXml = (Button) findViewById(R.id.btListarXml);
		btCargarXml.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(MainTrazaExplosivo.this, Lista_captura_QR.class);
				intent.putExtra("tabla", "TABLA1");

				startActivity(intent);
			}
		});

	}

	// Capturar códigos QR  -----------------------------------------------------------------------------------
	private void capturarCodigosQR() {
		
		Button btCargarQR = (Button) findViewById(R.id.btCargarQR);
		btCargarQR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(MainTrazaExplosivo.this, CapturaCodigosQR.class);
				startActivity(intent);
			}
		});

	}

	// Listar el fichero QR cargado -----------------------------------------------------------------------------------
	private void listarFicheroQR() {
		Button btCargarXml = (Button) findViewById(R.id.btListarQR);
		btCargarXml.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(MainTrazaExplosivo.this, Lista_captura_QR.class);
				intent.putExtra("tabla", "TABLA2");
				startActivity(intent);
			}
		});

	}

	// Resultado del proceso  --------------------------------------------------------------------------------------------
	private void resultadoFinal() {
		btValidarQR = (Button) findViewById(R.id.btValidarQR);
		btValidarQR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(MainTrazaExplosivo.this, Resumen.class);
				startActivity(intent);
			}
		});
		
	}


}
