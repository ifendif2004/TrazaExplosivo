package com.alfonso.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

public class GrabarXml {
	// private final static String FICHERO = "resultado_captura.xml";
	private String fichero;
	private Context contexto;
	private FileOutputStream fileos = null;
	// we create a XmlSerializer in order to write xml data
	private XmlSerializer serializer;
	private String directorioXml;
	private String directorioApp;

	public GrabarXml(Context context, String directorioXml, String directorioApp, String fichero) {

		this.directorioApp = directorioApp;
		this.directorioXml = directorioXml;
		contexto = context;
		this.fichero = fichero;

		String filename = directorioXml + "/" + fichero;
		System.out.println("fichero a crear: " + filename);
		File file = new File(filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			Log.e("IOException", "exception in createNewFile() method");
		}

		// we have to bind the new file with a FileOutputStream
		// FileOutputStream fileos = null;
		try {
			fileos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", "can't create FileOutputStream");
		}

		// we create a XmlSerializer in order to write xml data
		serializer = Xml.newSerializer();
		try {
			// serializer.setOutput(fileos, "ISO-8859-1");
			serializer.setOutput(fileos, "UTF-16");
			// Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
			serializer.startDocument(null, true);
			// set indentation option
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
			// start a tag called "root"
			serializer.startTag(null, "root");

		} catch (Exception e) {
			Log.e("Exception", "error occurred while creating xml file");
			Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	public boolean grabarXml_tag(String nombre, String codigo, double latitud, double longitud, String fecha) {

		try {
			System.out.println("grabarXml_tag: " + nombre + "  codigo: " + codigo);
			System.out.println("grabarXml_tag: " + nombre + "  -odigo: " + quitarInvalidChars(codigo));

			serializer.startTag(null, "codigoQR");
			serializer.text(quitarInvalidChars(codigo));
			serializer.endTag(null, "codigoQR");

			serializer.startTag(null, "latitud");
			serializer.text(String.valueOf(latitud));
			serializer.endTag(null, "latitud");

			serializer.startTag(null, "longitud");
			serializer.text(String.valueOf(longitud));
			serializer.endTag(null, "longitud");

			serializer.startTag(null, "fecha");
			serializer.text(String.valueOf(fecha));
			serializer.endTag(null, "fecha");

			return true;
		} catch (Exception e) {
			Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_LONG).show();
			return false;
		}
	}

	public boolean grabarXml_startTag(String tag) {

		try {
			serializer.startTag(null, tag);
			return true;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean grabarXml_endTag(String tag) {

		try {
			serializer.endTag(null, tag);
			return true;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean grabarXml_fin() {

		try {

			serializer.endTag(null, "root");
			serializer.endDocument();
			// write xml data into the FileOutputStream
			serializer.flush();
			// finally we close the file stream
			fileos.close();

			System.out.println("grabarXml_fin()");
			return true;
		} catch (Exception e) {
			Log.e("Exception", "error occurred while grabarXml_tag");
			return false;
		}
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
