package com.enstratus.logstash.utils;

import java.util.List;

public class StringUtils {
	public static String join(String[] throwableStrRep, String separator) {
    	StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < throwableStrRep.length; i++) {
			String line = throwableStrRep[i];
			builder.append(line);
			if(i == throwableStrRep.length-1){
				builder.append(separator);
			}
		}
		return builder.toString();
	}
	
	public static String join(List<String> throwableStrRep, String separator){
		String[] array = throwableStrRep.toArray(new String[throwableStrRep.size()]);
		return join(array,separator);
	}
	
	public static String join(List<String> throwableStrRep, char separator){
		return join(throwableStrRep,Character.toString(separator));
	}
}
