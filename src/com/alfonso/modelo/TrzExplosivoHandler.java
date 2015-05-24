package com.alfonso.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

public class TrzExplosivoHandler extends DefaultHandler {

	private Context contexto;

	private DatoQR datoQRActual;
	private StringBuilder sbTexto;
	private IGestionBD sqlite;

	public TrzExplosivoHandler(Context context) {
		super();
		contexto = context;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();

		sqlite = new SqliteBD(contexto);
		sqlite.crearBD();
		sqlite.inicializarBD("TABLA1");
		sbTexto = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (localName.equals("capturaQR")) {
			datoQRActual = new DatoQR();
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		if (this.datoQRActual != null) {

			if (localName.equals("codigoQR")) {
				datoQRActual.setCodigo(sbTexto.toString());
			} else if (localName.equals("latitud")) {
				datoQRActual.setLatitud(Double.parseDouble(sbTexto.toString()));
			} else if (localName.equals("longitud")) {
				datoQRActual.setLongitud(Double.parseDouble(sbTexto.toString()));
			} else if (localName.equals("fecha")) {
				datoQRActual.setFecha(sbTexto.toString());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String fechaActual = sdf.format(new Date());
				sqlite.altaBD("TABLA1", datoQRActual.getCodigo(), datoQRActual.getLatitud(), datoQRActual.getLongitud(), fechaActual, false);
			}
			sbTexto.setLength(0);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		super.characters(ch, start, length);

		if (this.datoQRActual != null)
			sbTexto.append(ch, start, length);
		// builder.append(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		sqlite.closeBD();
	}
	
}
