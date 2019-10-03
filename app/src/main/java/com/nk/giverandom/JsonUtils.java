package com.nk.giverandom;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

public class JsonUtils {
	private static Object unbox(Object obj){
		return obj;
	}

	private static int unbox(Integer obj){
		return obj;
	}

	public static <T> JSONArray asJsonArray(Collection<T> collection){
		JSONArray jsonArr = new JSONArray();
		if (collection == null) { return jsonArr; }

		for (T item : collection){
			jsonArr.put(unbox(item));
		}

		return jsonArr;
	}

	public static ArrayList<Integer> toIntArray(JSONArray jsonArray){
		ArrayList<Integer> array = new ArrayList<>();
		if (jsonArray == null) { return array; }

		for (int i = 0; i < jsonArray.length(); ++i){
			array.add(jsonArray.optInt(i, 0));
		}
		return array;
	}

	public static ArrayList<String> toStringArray(String data){
		try {
			return toStringArray(new JSONArray(data));
		}
		catch (Exception e){
			Log.e(JsonUtils.class.getName(), e.toString());
			return new ArrayList<>();
		}
	}

	public static ArrayList<String> toStringArray(JSONArray jsonArray){
		ArrayList<String> array = new ArrayList<>();
		if (jsonArray == null) { return array; }

		for (int i = 0; i < jsonArray.length(); ++i){
			array.add(jsonArray.optString(i, ""));
		}

		return array;
	}
}
