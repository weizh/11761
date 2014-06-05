package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class GetReader {

	public static BufferedReader getFileReader(String filename)
			throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File(filename);
		BufferedReader bin = new BufferedReader(new InputStreamReader(
				new FileInputStream(file),"utf-8"));
		return bin;
	}
	
}
