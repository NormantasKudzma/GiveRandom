package com.nk.giverandom;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;

public class ResultFormatter {
	private static final int COLOR_BLACK = Color.BLACK;
	private static final int COLOR_RED = Color.RED;
	private static final int COLOR_GREEN = Color.GREEN;
	private static final int COLOR_BLACK_DIM = 0xff4d4d4d;
	private static final int COLOR_RED_DIM = 0xffb24d4d;
	private static final int COLOR_GREEN_DIM = 0xff4db24d;

	public static Spanned format(RollResult result){
		String htmlText = "";
		if (result.mDice.hasOutcome()){
			String outcome = color("FAILURE", COLOR_RED);
			if (result.mNumSuccess > 1){
				outcome = color(result.mNumSuccess + " SUCCESSES", COLOR_GREEN);
			}
			else if (result.mNumSuccess > 0){
				outcome = color("SUCCESS", COLOR_GREEN);
			}
			htmlText += big(outcome);
		}
		else {
			htmlText += big(color("ROLL SUM: " + result.mSum, COLOR_BLACK));
		}
		htmlText += newline();

		htmlText += ArrayUtils.join(result.mRolls, ", ");
		htmlText += newline();

		if (!result.mExtras.isEmpty()){
			htmlText += "Extras: " + ArrayUtils.join(result.mExtras, ", ");
		}

		return Html.fromHtml(htmlText);
	}

	public static Spanned formatHistory(RollResult result){
		String htmlText = "";
		if (result.mDice.hasOutcome()){
			String outcome = color("FAILURE", COLOR_RED_DIM);
			if (result.mNumSuccess > 1){
				outcome = color(result.mNumSuccess + " SUCCESSES", COLOR_GREEN_DIM);
			}
			else if (result.mNumSuccess > 0){
				outcome = color("SUCCESS", COLOR_GREEN_DIM);
			}
			htmlText += big(outcome);
		}
		else {
			htmlText += big(color("ROLL SUM: " + result.mSum, COLOR_BLACK_DIM));
		}
		htmlText += newline();

		htmlText += "Rolls: " + ArrayUtils.join(result.mRolls, ", ");

		if (!result.mExtras.isEmpty()){
			htmlText += " [Extras: " + ArrayUtils.join(result.mExtras, ", ") + "]";
		}

		return Html.fromHtml(htmlText);
	}

	private static String big(String data){
		return "<big><strong>" + data + "</strong></big>";
	}

	private static String newline(){
		return "<br>";
	}

	private static String color(String data, int c){
		String hexColor = Integer.toHexString(c).substring(2);
		return "<font color=\"#" + hexColor + "\">" + data + "</font>";
	}
}
