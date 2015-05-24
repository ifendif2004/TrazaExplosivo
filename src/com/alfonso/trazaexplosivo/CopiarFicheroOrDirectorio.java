package com.alfonso.trazaexplosivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CopiarFicheroOrDirectorio {

	void copiar(String srcDir, String dstDir) throws IOException {
		System.out.println("En CopiarFicheroOrDirectorio srcDir: " + srcDir);
		System.out.println("En CopiarFicheroOrDirectorio dstDir: " + dstDir);

		try {
			File src = new File(srcDir);
			System.out.println("En CopiarFicheroOrDirectorio src.getName(): " + src.getName());
			File dst = new File(dstDir, src.getName());

			if (src.isDirectory()) {

				String files[] = src.list();
				int filesLength = files.length;
				for (int i = 0; i < filesLength; i++) {
					String src1 = (new File(src, files[i]).getPath());
					String dst1 = dst.getPath();
					System.out.println("[" + i + "] " + src1 + " " + dst1);
					copiar(src1, dst1);
				}
			} else {
				copyFile(src, dst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void copyFile(File sourceFile, File destFile) throws IOException {

		if (!destFile.getParentFile().exists())
			destFile.getParentFile().mkdirs();

		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}

	}

}
