package com.nk.giverandom;

import android.util.Log;

import org.json.JSONObject;

public class Dice {
	private static final int VERSION = 1;

	private static final String JSON_VERSION = "version";
	private static final String JSON_SIDES = "sides";
	private static final String JSON_TO_SUCCEED = "toSuceed";
	private static final String JSON_CAN_CRIT = "canCrit";

	private final int mSides;
	private final int mToSucceed;
	private final boolean mCanCrit;

	public Dice(int sides){
		this(sides, 0, false);
	}

	public Dice(int sides, int toSucceed, boolean canCrit){
		mSides = sides;
		mCanCrit = canCrit;
		mToSucceed = toSucceed;
	}

	public int roll(){
		final int range = getMax() - getMin() + 1;
		return (int)(Math.random() * range) + getMin();
	}

	public int getSides(){
		return mSides;
	}

	public boolean canCrit(){
		return mCanCrit;
	}

	public int getMax(){
		return mSides;
	}

	public int getMin(){
		return 1;
	}

	public boolean hasOutcome(){
		return mToSucceed > 0;
	}

	public boolean isSuccess(int num){
		return num > mToSucceed;
	}

	public boolean isCrit(int num){
		return num == mSides;
	}

	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		try {
			json.put(JSON_VERSION, VERSION);
			json.put(JSON_SIDES, mSides);
			json.put(JSON_CAN_CRIT, mCanCrit);
			json.put(JSON_TO_SUCCEED, mToSucceed);
		}
		catch (Exception e){
			Log.e(getClass().getName(), e.toString());
		}

		return json;
	}

	public static Dice fromJson(JSONObject json){
		if (json == null) { return null; }

		return new Dice(json.optInt(JSON_SIDES, 0), json.optInt(JSON_TO_SUCCEED, 0), json.optBoolean(JSON_CAN_CRIT, false));
	}
}
