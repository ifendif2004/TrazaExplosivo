package com.alfonso.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.content.Context;

public class CargarXmlParserSax {
	private String fichero;
	private Context context;

	public CargarXmlParserSax(String fichero, Context context) {
		this.fichero = fichero;
		this.context = context;
	}

	public boolean parse() {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser parser = factory.newSAXParser();
			CargarXmlHandler handler = new CargarXmlHandler(context);
			parser.parse(this.getInputStream(), handler);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream getInputStream() {
		try {
			File file = new File(fichero);
			InputStream ie = new FileInputStream(file);
			return ie;
			// return rssUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
