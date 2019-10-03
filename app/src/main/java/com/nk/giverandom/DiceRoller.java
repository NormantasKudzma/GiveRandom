package com.nk.giverandom;

public class DiceRoller {
	private Dice mDice;

	public DiceRoller(Dice dice){
		mDice = dice;
	}

	public RollResult roll(int numDices){
		RollResult result = new RollResult();
		result.mDice = mDice;
		result.mNumDice = numDices;

		if (numDices <= 0){ return result; }

		int numCrits = 0;
		for (int i = 0; i < numDices; ++i) {
			final int rolled = mDice.roll();
			result.mRolls.add(rolled);
			result.mSum += rolled;

			if (mDice.hasOutcome()){
				result.mNumSuccess += mDice.isSuccess(rolled) ? 1 : 0;
			}

			if (mDice.canCrit()){
				numCrits += mDice.isCrit(rolled) ? 1 : 0;
			}
		}

		if (numCrits > 0){
			while (numCrits > 0){
				--numCrits;
				final int rolled = mDice.roll();
				result.mExtras.add(rolled);
				result.mSum += rolled;

				if (mDice.hasOutcome()){
					result.mNumSuccess += mDice.isSuccess(rolled) ? 1 : 0;
				}

				if (mDice.canCrit()){
					numCrits += mDice.isCrit(rolled) ? 1 : 0;
				}
			}
		}

		return result;
	}
}
