package Exercise4;

import java.text.DecimalFormat;

import net.finmath.functions.AnalyticFormulas;

public class Ex1_Chris {

	public static void main(String[] args) {
		
		double bachelierOptVal;
		double[] blackImpliedVola;
		
		double initialForwardRate = 0.008;
		double vola = 0.35 * initialForwardRate;
		double maturity = 1;
		double atTheMoneyStrike = 0.008;
		double periodLength = 0.5;
		double numerair = 10000;
		double interestOfZeroCouponBondToMaturity = 1/ ( 1 + 1 * 0.008);
		double payoffUnit = numerair * periodLength * interestOfZeroCouponBondToMaturity;
		
		double[] strikeRates = {0.8, 0.9, 1.0, 1.1, 1.2};
		
		blackImpliedVola = new double[5];
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00000######" );

		for(int i = 0; i< strikeRates.length ; i++){
			bachelierOptVal = AnalyticFormulas.bachelierOptionValue(initialForwardRate, vola, 0.5, atTheMoneyStrike * strikeRates[i], payoffUnit);
			blackImpliedVola[i] = AnalyticFormulas.blackScholesOptionImpliedVolatility(initialForwardRate, 0.5, atTheMoneyStrike * strikeRates[i], payoffUnit, bachelierOptVal);

			System.out.println(bachelierOptVal);
			System.out.println(df2.format(blackImpliedVola[i]));
			System.out.println("####################");
			
		}
		
			
	}

}
