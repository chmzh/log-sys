package com.cndw.statistics.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class CommonUtil {
	public static String exception(Throwable t) {
		if(t == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			t.printStackTrace(new PrintStream(baos));
		}finally{
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baos.toString();
	}
}
