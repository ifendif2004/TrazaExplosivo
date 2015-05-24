package com.alfonso.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.content.Context;

import com.alfonso.modelo.TrzExplosivoHandler;

public class TrzExplosivoParserSax {
	private String fichero;
	private Context context;
	private URL rssUrl;

	public TrzExplosivoParserSax(String fichero, Context context) {
		this.fichero = fichero;
		this.context = context;
	}

	public boolean parse() {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser parser = factory.newSAXParser();
			TrzExplosivoHandler handler = new TrzExplosivoHandler(context);
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
