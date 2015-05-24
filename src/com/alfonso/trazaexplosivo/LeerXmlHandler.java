package com.alfonso.trazaexplosivo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

import com.alfonso.modelo.DatoQR;
import com.alfonso.modelo.IGestionBD;

public class LeerXmlHandler extends DefaultHandler {

	private Context contexto;

	private DatoQR datoQRActual;
	private StringBuilder sbTexto;
	private IGestionBD sqlite;

	public LeerXmlHandler(Context context) {
		super();
		contexto = context;
		System.out.println("En LeerXmlHandler ");

	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();

		System.out.println("En LeerXmlHandler -- startDocument");
		// sqlite = new SqliteBD(contexto);
		// sqlite.crearBD();
		// sqlite.inicializarBD("TABLA1");
		sbTexto = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		System.out.println("----startElement: " + uri + " localName: " + localName + " name: " + name + " Attributes: " + attributes.getLength() + " " + attributes.getLocalName(0));
		for (int i = 0; i < attributes.getLength(); i++) {
			System.out.println("               [" + i + "]: " + attributes.getQName(i) + " " + attributes.getValue(i));
		}
		// if (localName.equals("capturaQR")) {
		// datoQRActual = new DatoQR();
		// }
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		System.out.println("    endElement: " + uri + " localName: " + localName + " name: " + name);
		// if (this.datoQRActual != null) {
		//
		// if (localName.equals("codigoQR")) {
		// datoQRActual.setCodigo(sbTexto.toString());
		// } else if (localName.equals("latitud")) {
		// datoQRActual.setLatitud(Double.parseDouble(sbTexto.toString()));
		// } else if (localName.equals("longitud")) {
		// datoQRActual.setLongitud(Double.parseDouble(sbTexto.toString()));
		// } else if (localName.equals("fecha")) {
		// datoQRActual.setFecha(sbTexto.toString());
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		// String fechaActual = sdf.format(new Date());
		// sqlite.altaBD("TABLA1", datoQRActual.getCodigo(), datoQRActual.getLatitud(), datoQRActual.getLongitud(), fechaActual, false);
		// }
		// sbTexto.setLength(0);
		// }
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
		// sqlite.closeBD();
	}

}
