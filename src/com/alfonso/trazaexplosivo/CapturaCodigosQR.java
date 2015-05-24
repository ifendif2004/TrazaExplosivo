package com.alfonso.trazaexplosivo;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.alfonso.modelo.DatoQR;
import com.alfonso.modelo.IGestionBD;
import com.alfonso.modelo.SqliteBD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CapturaCodigosQR extends Activity {

	private TextView tvResult;
	private TextView tvFormat;
	private Button guardar;
	private Button inicializarCapturas;
	private DatoQR dqr;
	private SimpleDateFormat sdf;
	private String currentDateandTime;
	private IGestionBD sqlite;
	private Context context;
	private ImageButton botonCaptura;
	private GPSTracker gps;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_captura_codigos_qr);

		tvFormat = (TextView) findViewById(R.id.tvFormat);
		tvFormat.setText("");
		tvResult = (TextView) findViewById(R.id.tvResult);
		tvResult.setText("");

		gps = new GPSTracker(this);
		context = this;
		dqr = new DatoQR();
		dqr.setCodigo("");
		dqr.setFecha("");
		dqr.setLatitud(0);
		dqr.setLongitud(0);
		sqlite = new SqliteBD(this);
		sqlite.crearBD();
		configurarBotonCaptura();
		configureButtonInicializarCapturas();
		configurarBotonGuardar();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		String res1 = savedInstanceState.getString("tvformat");
		String res2 = savedInstanceState.getString("tvresult");
		String res3 = savedInstanceState.getString("dqr_Codigo");
		double res4= (double)savedInstanceState.getDouble("dqr_Latitud");
		double res5= (double)savedInstanceState.getDouble("dqr_Longitud");
		String res6= savedInstanceState.getString("dqr_Fecha");
		dqr.setLongitud(1);

		if (res1 == null) {
			tvFormat.setText("");
		} else {
			tvFormat.setText(res1.toString());
		}
		if (res2 == null) {
			tvResult.setText("");
		} else {
			tvResult.setText(res2.toString());
		}
		if (res3 == null) {
			dqr.setCodigo("");
		} else {
			dqr.setCodigo(res3.toString());
		}
		if (res4 == 0) {
			dqr.setLatitud(0);
		} else {
			dqr.setLatitud(Double.valueOf(res4));
		}
		if (res5 == 0l) {
			dqr.setLongitud(0);
		} else {
			dqr.setLongitud(Double.valueOf(res5));
		}
		if (res6 == null) {
			dqr.setFecha("");
		} else {
			dqr.setFecha(res6.toString());
		}

		 System.out.println("--- onRestoreInstanceState." + " res1: " + res1 + " res2: " + res2);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tvformat", tvFormat.getText().toString());
		outState.putString("tvresult", tvResult.getText().toString());
		outState.putString("dqr_Codigo", dqr.getCodigo().toString());
		outState.putDouble("dqr_Latitud", dqr.getLatitud());
		outState.putDouble("dqr_Longitud", dqr.getLongitud());
		outState.putString("dqr_Fecha", dqr.getFecha());
		System.out.println("--- onSaveInstanceState." + " tvformat: " + tvFormat.getText().toString() + " tvresult: " + tvResult.getText().toString());
	}

	private void configureButtonInicializarCapturas() {

		inicializarCapturas = (Button) findViewById(R.id.btInicializar);

		inicializarCapturas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sqlite.inicializarBD("TABLA2");
				Toast.makeText(context, "BD inicializada", Toast.LENGTH_SHORT).show();
			}
		});

	}

	// **________________________________________________________________________________________
	private void configurarBotonCaptura() {
		
		botonCaptura = (ImageButton) findViewById(R.id.btCaptura);
		botonCaptura.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new IntentIntegrator(CapturaCodigosQR.this).initiateScan();
			}
		});
	}

	// **________________________________________________________________________________________
	private void configurarBotonGuardar() {

		guardar = (Button) findViewById(R.id.btGuardar);
		guardar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Boolean resultado;
				Boolean encontrado;
				resultado = sqlite.consultaRegistro("TABLA2", dqr.getCodigo());
				if (resultado) {
					Toast.makeText(getApplicationContext(), "Este código QR ya existe en la BD", Toast.LENGTH_LONG).show();
				} else {
					encontrado = sqlite.consultaRegistro("TABLA1", dqr.getCodigo());
					resultado = sqlite.altaBD("TABLA2", dqr.getCodigo(), dqr.getLatitud(), dqr.getLongitud(), dqr.getFecha(), encontrado);
					if (resultado) {
						Toast.makeText(getApplicationContext(), "Código QR grabado en la BD", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "No se ha grabado el Código QR en la BD", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

	// **________________________________________________________________________________________

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		final IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult.getContents() != null) {
			updateUITextViews(scanResult.getContents(), scanResult.getFormatName());
		} else {
			Toast.makeText(this, "No se ha leído ningún código QR", Toast.LENGTH_LONG).show();
		}

	}

	// **________________________________________________________________________________________

	private void updateUITextViews(String scan_result, String scan_result_format) {
		
		if (gps.canGetLocation) {
			dqr.setLatitud(gps.getLatitude());
			dqr.setLongitud(gps.getLongitude());
		} else {
			dqr.setLatitud(0);
			dqr.setLongitud(0);
		}
		dqr.setCodigo(quitarInvalidChars(scan_result));
		sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		long ahora = System.currentTimeMillis();
		Date fecha = new Date(ahora);
		currentDateandTime = sdf.format(fecha);
		dqr.setFecha(currentDateandTime);

		tvFormat.setText(scan_result_format);
		tvResult.setText(scan_result);
		Linkify.addLinks(tvResult, Linkify.ALL);
	}

	
	private String quitarInvalidChars(String text) {
		char out[] = new char[text.length()];
		int i, o, l;
		for (i = 0, o = 0, l = text.length(); i < l; i++) {
			char c = text.charAt(i);
			if ((c >= 0x20 && c <= 0xd7ff) || (c >= 0xe000 && c <= 0xfffd)) {
				out[o++] = c;
			} else {
				out[o++] = (char) ' ';
			}
		}
		if (i == o) {
			// return text;
			return new String(out, 0, o);
		} else { // We removed some characters, create the new string
			return new String(out, 0, o);
		}
	}
}
