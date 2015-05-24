package com.alfonso.trazaexplosivo;

import java.util.ArrayList;

import com.alfonso.modelo.DatoQR;
import com.alfonso.modelo.IGestionBD;
import com.alfonso.modelo.SqliteBD;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Lista_captura_QR extends ListActivity {
	private IGestionBD sqlite;
	private String tabla;
	private AdaptadorListaQR mAdaptador = null;
	private static ArrayList<DatoQR> mArray = new ArrayList<DatoQR>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		tabla = bundle.getString("tabla");
		if ("TABLA1".equals(tabla)) {
			setTitle("Lista códigos Xml");
		} else {
			setTitle("Lista códigos QR");
		}

		sqlite = new SqliteBD(this);
		sqlite.crearBD();
		mArray = sqlite.leerTabla(tabla);

		mAdaptador = new AdaptadorListaQR(this, mArray);
		setListAdapter(mAdaptador);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this,mArray.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
	}

}
