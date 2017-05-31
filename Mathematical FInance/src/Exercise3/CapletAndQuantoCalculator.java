package Exercise3;

import net.finmath.functions.AnalyticFormulas;

public class CapletAndQuantoCalculator {

	
	public static double calculateCapletValue(
			double initialForwardLibor,
			double liborVolatility,
			double strike, 
			double maturity, 
			double fixing, 
			double maturityDiscountFactor,
			double notional){
		double timeToMaturity=maturity-fixing;
		return notional*
				maturityDiscountFactor*
				timeToMaturity*
				AnalyticFormulas.
				blackScholesOptionValue(initialForwardLibor, 0, liborVolatility, fixing, strike);
	}  //the disount factor is the bond maturing at the maturity date; the libor rate as no drift because we are changing to a certain equivalent measure
	//unde which it exhibits martingale dynamics.
	
	
	public static double calculateQuantoValue(
			double initialForwardLibor,
			double liborForeignVolatility,
			double fxVolatility,
			double correlationFxLibor,
			double conversionRate, 
			double strike, 
			double maturity, 
			double fixing,  
			double maturityDiscountFactor,
			double notional){
		double timeToMaturity=maturity-fixing;
		return notional*conversionRate*maturityDiscountFactor*timeToMaturity*
				AnalyticFormulas.blackScholesOptionValue(initialForwardLibor, correlationFxLibor*liborForeignVolatility*fxVolatility*timeToMaturity,
				 liborForeignVolatility,fixing, strike);
	}  //the disount factor is the bond maturing at the maturity date; the libor rate has no drift because we are changing to a certain equivalent measure
	//under which it exhibits martingale dynamics.
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        double initialForwardLibor=0.015;
        double strike=0.01;
        double liborVolatility=0.3;
        double fixing=0.5;
        double maturity=1;
        double notional=10000;
        double maturityDiscountFactor=0.99; //current value  of a ZCB with maturity "maturity"
        
        System.out.println("Price of the given caplet " + CapletAndQuantoCalculator.calculateCapletValue(
        		initialForwardLibor,
			 liborVolatility,
			 strike, 
			 maturity, 
		      fixing, 
			 maturityDiscountFactor,
			notional));
        
        System.out.println("Price of the given cap using the finath library " + 
          notional*AnalyticFormulas.blackModelCapletValue(initialForwardLibor, liborVolatility, fixing, strike, maturity-fixing, maturityDiscountFactor)
        		);
        
    	double liborForeignVolatility=liborVolatility;
		double fxVolatility=0.2;
		double correlationFxLibor=0.3;
		double conversionRate=0.8;
        
		System.out.println("Price of a FX Quanto: " + CapletAndQuantoCalculator.calculateQuantoValue(
				initialForwardLibor,
				liborForeignVolatility,
				fxVolatility,
				correlationFxLibor,
				conversionRate, 
				strike, 
				maturity, 
				fixing,  
				maturityDiscountFactor,
				notional));
        
		
	}

}
