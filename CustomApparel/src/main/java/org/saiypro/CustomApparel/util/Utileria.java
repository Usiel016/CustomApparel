package org.saiypro.CustomApparel.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {
	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		// Obtenemos el nombre original del archivo.
		String nombreOriginal = multiPart.getOriginalFilename();
		try {
			// Formamos el nombre del archivo para guardarlo en el disco duro.
			// File imageFile = new File(ruta + nombreOriginal);
			System.out.println("Archivo: " + new File(ruta + nombreOriginal).getAbsolutePath());
			// Guardamos fisicamente el archivo en HD.
			byte[] bytes = multiPart.getBytes();
			Path path = Paths.get(ruta+nombreOriginal);
			Files.write(path, bytes);
			// multiPart.transferTo(imageFile);
			return nombreOriginal;
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
			return null;
		}
	}
}
