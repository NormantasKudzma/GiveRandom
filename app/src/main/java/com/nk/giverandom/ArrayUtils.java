package com.nk.giverandom;

import java.util.Collection;

public class ArrayUtils {
	public static <T> String join(Collection<T> collection, String separator){
		StringBuilder builder = new StringBuilder();
		for (T item : collection){
			builder.append(item.toString());
			builder.append(separator);
		}

		if (builder.length() > separator.length()){
			builder.setLength(builder.length() - separator.length());
		}

		return builder.toString();
	}
}
