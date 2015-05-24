package com.alfonso.trazaexplosivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.alfonso.modelo.TrzExplosivoHandler;

import android.content.Context;

public class LeerXmlParserSax {
	private String fichero;
	private Context context;

	public LeerXmlParserSax(String fichero, Context context) {
		this.fichero = fichero;
		this.context = context;
		System.out.println("En LeerXmlParserSax: " + fichero);
	}

	public boolean parse() {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			System.out.println("En LeerXmlParserSax parse(): " + fichero);
			SAXParser parser = factory.newSAXParser();
			LeerXmlHandler handler = new LeerXmlHandler(context);
			parser.parse(this.getInputStream(), handler);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream getInputStream() {
		try {
			System.out.println("En LeerXmlParserSax getInputStream(): " + fichero);
			File file = new File(fichero);
			InputStream ie = new FileInputStream(file);
			return ie;
			// return rssUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
