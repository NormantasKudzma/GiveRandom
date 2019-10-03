package com.nk.giverandom;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

public class RollResult {
	private static final int VERSION = 1;

	private static final String JSON_VERSION = "version";
	public static final String JSON_TIMESTAMP = "timestamp";
	public static final String JSON_NUM_SUCC = "numSuccess";
	public static final String JSON_DICE = "dice";
	public static final String JSON_ROLLS = "rolls";
	public static final String JSON_EXTRAS = "extras";
	public static final String JSON_SUM = "rollSum";
	public static final String JSON_NUM_DICES = "numDices";

	public long mTimestamp = System.currentTimeMillis();
	public Dice mDice = null;
	public int mNumDice = 0;
	public int mSum = 0;
	public int mNumSuccess = 0;
	public ArrayList<Integer> mRolls = new ArrayList<>();
	public ArrayList<Integer> mExtras = new ArrayList<>();

	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		try {
			json.put(JSON_VERSION, VERSION);
			json.put(JSON_TIMESTAMP, mTimestamp);
			json.put(JSON_NUM_SUCC, mNumSuccess);
			json.put(JSON_NUM_DICES, mNumDice);
			json.put(JSON_DICE, mDice.toJson());
			json.put(JSON_SUM, mSum);
			json.put(JSON_ROLLS, JsonUtils.asJsonArray(mRolls));
			json.put(JSON_EXTRAS, JsonUtils.asJsonArray(mExtras));
		}
		catch (Exception e){
			Log.e(getClass().getName(), e.toString());
			return null;
		}
		return json;
	}

	public static RollResult fromJson(String data){
		try {
			return fromJson(new JSONObject(data));
		}
		catch (Exception e){
			Log.e(RollResult.class.getName(), e.toString());
			return null;
		}
	}

	public static RollResult fromJson(JSONObject json){
		RollResult result = new RollResult();

		result.mTimestamp = json.optLong(JSON_TIMESTAMP, 0);
		result.mNumSuccess = json.optInt(JSON_NUM_SUCC, 0);
		result.mNumDice = json.optInt(JSON_NUM_DICES, 0);
		result.mSum = json.optInt(JSON_SUM, 0);
		result.mDice = Dice.fromJson(json.optJSONObject(JSON_DICE));
		result.mRolls = JsonUtils.toIntArray(json.optJSONArray(JSON_ROLLS));
		result.mExtras = JsonUtils.toIntArray(json.optJSONArray(JSON_EXTRAS));

		return result;
	}
}
