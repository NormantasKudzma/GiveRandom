package com.nk.giverandom;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	private static final String CACHE_NUM_DICE = "num_dices";
	private static final String CACHE_HISTORY = "past_rolls";

	private final int MAX_HISTORY_ENTRIES = 10;

	private int mNumDices = 1;
	private SharedPreferences mCache;
	private ArrayList<String> mHistory;
	private HashMap<View, DiceRoller> mDiceRollers = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCache = getPreferences(Context.MODE_PRIVATE);

		mNumDices = mCache.getInt(CACHE_NUM_DICE, 1);
		((TextView)findViewById(R.id.textView_numDices)).setText(String.format("%d", mNumDices));

		mHistory = new ArrayList<>(JsonUtils.toStringArray(mCache.getString(CACHE_HISTORY, "")));
		refreshHistory();

		mDiceRollers.put(findViewById(R.id.button_rolld10), new DiceRoller(new Dice(10, 8, true)));
		mDiceRollers.put(findViewById(R.id.button_rolld8), new DiceRoller(new Dice(8)));
		mDiceRollers.put(findViewById(R.id.button_rolld5), new DiceRoller(new Dice(5)));
	}

	public void onPlusDicesClicked(View v){
		mNumDices = Math.max(Math.min(15, mNumDices + 1), 1);
		((TextView)findViewById(R.id.textView_numDices)).setText(String.format("%d", mNumDices));
		mCache.edit().putInt(CACHE_NUM_DICE, mNumDices).apply();
	}

	public void onMinusDicesClicked(View v){
		mNumDices = Math.max(Math.min(15, mNumDices - 1), 1);
		((TextView)findViewById(R.id.textView_numDices)).setText(String.format("%d", mNumDices));
		mCache.edit().putInt(CACHE_NUM_DICE, mNumDices).apply();
	}

	public void onRollClicked(View v){
		DiceRoller roller = mDiceRollers.get(v);
		if (roller != null){
			RollResult result = roller.roll(mNumDices);
			addToHistory(result);
			showResult(result);
		}
	}

	public void addToHistory(RollResult result){
		JSONObject json = result.toJson();
		if (json != null){
			mHistory.add(0, json.toString());
		}

		mCache.edit().putString(CACHE_HISTORY, JsonUtils.asJsonArray(mHistory).toString()).apply();
		refreshHistory();
	}

	public void showResult(RollResult result){
		Spanned formattedResult = ResultFormatter.format(result);
		TextView resultTextView = findViewById(R.id.textView_result);
		resultTextView.setText(formattedResult);
	}

	private void refreshHistory(){
		while (mHistory.size() > MAX_HISTORY_ENTRIES){
			mHistory.remove(mHistory.size() - 1);
		}

		LinearLayout historyView = findViewById(R.id.layout_history);
		historyView.removeAllViews();

		for (String json : mHistory){
			RollResult result = RollResult.fromJson(json);
			if (result == null){
				continue;
			}
			TextView item = (TextView)View.inflate(this, R.layout.history_item, null);
			Spanned formattedText = ResultFormatter.formatHistory(result);
			item.setText(formattedText);
			historyView.addView(item);
		}
	}
}
