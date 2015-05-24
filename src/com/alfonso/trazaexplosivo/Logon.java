package com.alfonso.trazaexplosivo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Logon extends Activity {

	static String NAME = "admin";
	static String PASS = "1234";
	private String name;
	private String pass;
	private EditText userEditText;
	private EditText passEditText;
	private TextView webEmpresa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_logon);

		webEmpresa=(TextView)findViewById(R.id.txtEmpresa);
		userEditText = (EditText) findViewById(R.id.etUser);
		passEditText = (EditText) findViewById(R.id.etPass);
		Button btEntrar = (Button) findViewById(R.id.btLogin);
		// Recuperamos las preferencias almacenadas
		SharedPreferences prefs = getSharedPreferences("PreferenciasTrazaExplosivo", Context.MODE_PRIVATE);
		name = prefs.getString("name", "");
		pass = prefs.getString("pass", "");

		btEntrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (name.equals(NAME) && pass.equals(PASS)) {
					Intent mainIntent = new Intent().setClass(Logon.this, MainTrazaExplosivo.class);
					startActivity(mainIntent);
					finish();
				}else
				if (userEditText.getText().toString().equals(NAME) && passEditText.getText().toString().equals(PASS)) {
					// Si el usuario escrito es correcto, almacenamos la preferencia y entramos en la app
					SharedPreferences settings = getSharedPreferences("PreferenciasTrazaExplosivo", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("name", userEditText.getText().toString());
					editor.putString("pass", passEditText.getText().toString());

					// Confirmamos el almacenamiento.
					editor.commit();

					// Entramos en la app
					Intent mainIntent = new Intent().setClass(Logon.this, MainTrazaExplosivo.class);
					startActivity(mainIntent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "El usuario introducido no es correcto", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		webEmpresa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.intracex.com"));
				startActivity(intent);
				
			}
		});
	}

}
