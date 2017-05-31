package Exercise5;

import Exercise3.CapletAndQuantoCalculator;

import net.finmath.functions.AnalyticFormulas;

public class CapletInArrears {
	
	public static double calculateCapletInArrearsBlack(
			double initialForwardLibor,
			double liborVolatility,
			double strike, 
			double maturity, 
			double fixing, 
			double maturityDiscountFactor,
			double notional){
		double timeToMaturity=maturity-fixing;
		return notional*
				maturityDiscountFactor*timeToMaturity*(
				AnalyticFormulas.
				blackScholesOptionValue(initialForwardLibor, 0, liborVolatility, fixing, strike)  //formula for the caplet in arrears
				
				+
			timeToMaturity*initialForwardLibor*
				AnalyticFormulas.
				blackScholesOptionValue(initialForwardLibor, liborVolatility*liborVolatility, liborVolatility, fixing, strike)
				)
				;
	} 
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        double initialForwardLibor=0.008;
        double strike=0.0064;
        double liborVolatility=0.35*initialForwardLibor;
        double fixing=0.5;
        double maturity=1;
        double notional=10000;
     
        
        double maturityBondYield=0.085;
	    
       double maturityDiscountFactor=1.0/(1.0+(maturity)*maturityBondYield); //calculate the discount factor from the bond yield
      
       double capletInArrears= calculateCapletInArrearsBlack(
       		initialForwardLibor,
			 liborVolatility,
			 strike, 
			 maturity, 
		      fixing, 
			 maturityDiscountFactor,
			notional); 
       
       double caplet= CapletAndQuantoCalculator.calculateCapletValue(   //from exercise 3
       		initialForwardLibor,
			 liborVolatility,
			 strike, 
			 maturity, 
		      fixing, 
			 maturityDiscountFactor,
			notional);
       
        System.out.println("Price of the caplet in arrears " + capletInArrears);
        
        System.out.println("Price of the caplet " + caplet);
      
	System.out.println("Convexity adjustment: " + ( capletInArrears- caplet) );  
	}
}
