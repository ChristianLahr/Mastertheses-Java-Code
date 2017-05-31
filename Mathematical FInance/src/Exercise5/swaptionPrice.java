package Exercise5;

import Exercise4.BootStrapAndCap;

import net.finmath.functions.AnalyticFormulas;

public class swaptionPrice {

	public static double calculateSwapValueBlack(
			double initialSwapRate,
			double swapVolatility,
			double strike, 
			double swapEnterTime, 
			double annuityValue,
			double notional){
		return notional*annuityValue*AnalyticFormulas.blackScholesOptionValue(initialSwapRate, 0, swapVolatility, swapEnterTime, strike);
		
	} 
	
	
	
	public static void main(String[] args) {
		
		double[] zcb=new BootStrapAndCap().getZcb();  //get the term structure from exercise 4. The constructor calls main; a bit rough, better is make main() into a method which 
		
		//returns zcb
		
		System.out.print("\n");
		
		System.out.println("Exercise 4");
		
		double annuityValue=zcb[5]+zcb[6]+zcb[7];  //numeraire: annuity value
		
		double swapRate= (zcb[5] - zcb[7])/(zcb[5]+zcb[6]+zcb[7]);           //find the required swap rate
		System.out.println("The required swap rate from year 3 to year 5 is :" + swapRate);
		
		double swapVolatility=0.2;
		double strike=0.01;  
		
		double notional=10000;
		
		double swapEnterTime=3.0;  //maturity only enters in the annuity pricing

		
		//apply Black formula. 1 over the present value of the annuity ("numeraire") is the discount factor; current swap rate is the spot price
		
		System.out.println("The swap value is: " + calculateSwapValueBlack(
				swapRate,
			     swapVolatility,
				strike, 
				swapEnterTime, 
				annuityValue,
				notional));
		
		
		System.out.println("Using the finmath librry: "     + AnalyticFormulas.blackModelSwaptionValue(
				swapRate,
				swapVolatility,
				swapEnterTime,
				strike,
				notional*annuityValue) );
		
		
	}
	
} 
