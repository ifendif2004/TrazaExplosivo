package com.alfonso.trazaexplosivo;

import com.alfonso.modelo.IGestionBD;
import com.alfonso.modelo.SqliteBD;
import com.alfonso.trazaexplosivo.R.color;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Resumen extends Activity {
	private Button btGenerarXml;
	private String directorioXml;
	private String directorioApp;
	private String fichero;
	private TextView tvXmlValidado;
	private TextView tvQRValidado;
	private IGestionBD sqlite;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_resumen);
		btGenerarXml = (Button) findViewById(R.id.btGenerarXml);
		tvXmlValidado=(TextView)findViewById(R.id.tvXmlValidado);
		tvQRValidado=(TextView)findViewById(R.id.tvQRValidado);
		
		tvQRValidado.setTextColor(color.blanco);
		
		
		configurarXmlValidado();
		configurarQRValidado();
		configurarGenerarXml();
		
		// Recuperamos las preferencias almacenadas
		SharedPreferences prefs = getSharedPreferences("PreferenciasTrazaExplosivo", Context.MODE_PRIVATE);
		System.out.println("prefs.getString(ficheroOrigen: " + prefs.getString("ficheroOrigen", ""));
		System.out.println("prefs.getString(ficheroDestino: " + prefs.getString("ficheroDestino", ""));
		System.out.println("prefs.getString(directorioXml: " + prefs.getString("directorioXml", ""));
		System.out.println("prefs.getString(directorioApp: " + prefs.getString("directorioApp", ""));
		directorioXml = prefs.getString("directorioXml", "");
		directorioApp = prefs.getString("directorioApp", "");
		fichero = "resultado_captura.xml";

	}

	
	@Override
	protected void onResume() {
		super.onResume();
		
		Boolean existe;
		sqlite = new SqliteBD(this);
		sqlite.crearBD();
		existe = sqlite.hayDatosPendientes("TABLA1");
		if (existe) {
			tvXmlValidado.setTextColor(color.rojo);
			tvXmlValidado.setText(R.string.txt_XmlNoValidado);
		} else {
			tvXmlValidado.setTextColor(color.blanco);
			tvXmlValidado.setText(R.string.txt_XmlValidado);
		}
		existe = sqlite.hayDatosPendientes("TABLA2");
		if (existe) {
			tvQRValidado.setTextColor(color.rojo);
			tvQRValidado.setText(R.string.txt_QRNoValidado);
		} else {
			tvQRValidado.setTextColor(color.blanco);
			tvQRValidado.setText(R.string.txt_QRValidado);
		}


	}


	private void configurarQRValidado() {
		
		
		
		tvQRValidado.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(Resumen.this, Lista_captura_QR.class);
				intent.putExtra("tabla", "TABLA2");

				startActivity(intent);

				
			}
		});
		
	}

	private void configurarXmlValidado() {
		tvXmlValidado.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(Resumen.this, Lista_captura_QR.class);
				intent.putExtra("tabla", "TABLA1");

				startActivity(intent);

				
			}
		});
		
	}
	private void configurarGenerarXml() {
		btGenerarXml.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GenerarFicheroXml gfx = new GenerarFicheroXml(Resumen.this, directorioXml, directorioApp, fichero);
				gfx.execute(fichero);

			}
		});

	}

}
