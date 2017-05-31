package Exercise4;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import net.finmath.functions.AnalyticFormulas;

public class BachelierCapVolSurface {

	
	
		
		public static double calculateCapletValue(
				double initialForwardLibor,
				double liborVolatility,
				double strike, 
				double maturity, 
				double fixing, 
				double maturityDiscountFactor,
				double notional){
			double timeToMaturity=maturity-fixing;
		
			return 
					AnalyticFormulas.
					bachelierOptionValue(initialForwardLibor, liborVolatility, fixing, strike, notional*
							maturityDiscountFactor*
							timeToMaturity );
			
			
			
		}  //compare with last week exercise. Here the distribution is normal so we take Bachelier formula (see finamth lib)
		

		
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
	        double initialForwardLibor=0.008;
	        double[] strikes=new double[5];
	        double liborVolatility=0.35*initialForwardLibor;   //0.35% BS volatility. L_0*sigma, bchelier volatility
	        double fixing=0.5;
	        double maturity=1.0;
	        double notional=10000;
	        double maturityBondYield=0.085;
	       
	        for(int strikeIndex=0; strikeIndex < strikes.length; strikeIndex++  ){
	        	strikes[strikeIndex]=initialForwardLibor*0.8+0.1*initialForwardLibor*strikeIndex;
	        }
	      System.out.println(Arrays.toString(strikes));
	        double maturityDiscountFactor=1.0/(1.0+(maturity)*maturityBondYield); //calculate the discount factor from the bond yield
	        
	        System.out.println("Discount factor: " + maturityDiscountFactor);
		
			NumberFormat formatDec4 = new DecimalFormat("00.00000");
			 
			double[] capletPricesBachelier=new double[strikes.length]; //array of caplet prices
			double[] capletPricesBS=new double[strikes.length]; //array of caplet prices
			
			double[] capletImpliedVolatilities=new double[strikes.length]; //array of caplet prices
			double payoffUnit=(maturity-fixing)*notional*maturityDiscountFactor;
			System.out.println("Payoff unit: " +payoffUnit);
			
			
			System.out.println("Strike" + "\t" +  "       Price Bachelier" + "\t"+ " BS  Implied Vol"); 
			
			for(int strikeIndex=0; strikeIndex <strikes.length; strikeIndex++){
				capletPricesBachelier[strikeIndex]=BachelierCapVolSurface.calculateCapletValue(
		        		initialForwardLibor,
					 liborVolatility,
					 strikes[strikeIndex], 
					 maturity, 
				      fixing, 
					 maturityDiscountFactor,
					notional);
				
		    		
						
						
						
						
			
				
	
				capletImpliedVolatilities[strikeIndex]=AnalyticFormulas.blackScholesOptionImpliedVolatility(initialForwardLibor,
						fixing, strikes[strikeIndex], payoffUnit , capletPricesBachelier[strikeIndex]);  //note tha the payoff unit is not just the discount factor, 
			
				
				
				//as in the Black-SCHOLES model
				
				
			System.out.println(formatDec4.format(strikes[strikeIndex]) + "\t" +  formatDec4.format(capletPricesBachelier[strikeIndex]) + "\t" +
					formatDec4.format(capletImpliedVolatilities[strikeIndex]));	
			}
	    
			
		}

	}
