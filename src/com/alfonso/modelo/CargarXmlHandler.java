package com.alfonso.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

public class CargarXmlHandler extends DefaultHandler {
	private Context contexto;

	private DatoQR datoQRActual;
	private StringBuilder sbTexto;
	private IGestionBD sqlite;
	private String tratar = "";
	private String s1, s2, s3, s4;
	private String fechaActual;
	
	public CargarXmlHandler(Context context) {
		super();
		contexto = context;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();

		System.out.println("En LeerXmlHandler -- startDocument");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		fechaActual = sdf.format(new Date());

		sqlite = new SqliteBD(contexto);
		sqlite.crearBD();
		sqlite.inicializarBD("TABLA0");
		sqlite.inicializarBD("TABLA1");
		sbTexto = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		String valor = null;
		// System.out.println("----startElement: " + uri + " localName: " + localName + " name: " + name + " Attributes: " + attributes.getLength() + " " + attributes.getLocalName(0));

		if (localName.equals("Shipment")) {
			s1 = attributes.getValue(0);
			s2 = "";
			s3 = "";
			s4 = "";
			System.out.println("Shipment: " + attributes.getValue(0));
		}
		if (localName.equals("Sender") || localName.equals("Shipper") || localName.equals("Receiver")) {
			tratar = localName.toString();
		}

		if (localName.equals("Unit")) {

			System.out.println("----startElement: " + uri + " localName: " + localName + " name: " + name + " Attributes: " + attributes.getLength() + " " + attributes.getLocalName(0));
			// datoQRActual = new DatoQR();
			valor = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				valor = valor + " " + attributes.getValue(i);
				System.out.println("               [" + i + "]: " + attributes.getQName(i) + " " + attributes.getValue(i));
			}
			// System.out.println("valor: " + valor);

			sqlite.altaBD("TABLA1", valor.trim(), 0, 0, fechaActual, false);
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		System.out.println("endElement. " + " tratar: " + tratar + " localName: " + localName + "  name: " + name + " sbTexto: " + sbTexto);

		if (tratar.equals("Sender")) {
			if (localName.equals("Name")) {
				s2 = sbTexto.toString();
				tratar = "";
			}
		}
		if (tratar.equals("Shipper")) {
			if (localName.equals("Name")) {
				s3 = sbTexto.toString();
				tratar = "";
			}
		}
		if (tratar.equals("Receiver")) {
			if (localName.equals("Name")) {
				s4 = sbTexto.toString();
				sqlite.altaCabeceraBD("TABLA0", s1, s2, s3, s4);
				tratar = "";
			}
		}

		sbTexto.setLength(0);

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		super.characters(ch, start, length);

		sbTexto.append(ch, start, length);
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
