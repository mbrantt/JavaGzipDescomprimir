package cl.gtd.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

import org.apache.commons.compress.utils.IOUtils;

public class Decompress {

	public static void main(String[] args) throws IOException {
		
		String compressedText = "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\r\n"
				+ "AA==";
		String archivo = gzipDecompress(compressedText);
		System.out.println(archivo);
	}
	
	public static final String gzipDecompress(String compressedText) throws IOException {
		byte[] compressed = Decodificar(compressedText);
	    ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
	    GZIPInputStream gis = new GZIPInputStream(bis);
	    byte [] bytes = IOUtils.toByteArray(gis);
	    return Base64.getEncoder().encodeToString(bytes);
	}
	
	private static String Codificar(String XML) throws IOException{
		
		//Codificar
		
		XML = XML.replaceAll("\\s+","");	
		String Codificacion = Base64.getEncoder().encodeToString(XML.getBytes());
		return Codificacion ;
	}
	
	private static byte[] Decodificar(String XML) throws IOException{
		
		XML = XML.replaceAll("\\s+","");
		byte[] decodedBytes = Base64.getDecoder().decode(XML);
		//String decodedString = new String(decodedBytes);
		return decodedBytes;
		
	}
	

}


