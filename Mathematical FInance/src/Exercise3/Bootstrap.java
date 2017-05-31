package Exercise3;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import net.finmath.rootfinder.BisectionSearch;


public class Bootstrap {
     //computes the forward difference equation for bootstrapping
	public static double forwardDifferenceSwapBootstrap(double swapRate,
														double yearFraction, 
														double[] computedDiscountFactors ){
			double sumOfDiscountFactors=0.0;
			for(int i=0; i<computedDiscountFactors.length; i++){
				sumOfDiscountFactors+=yearFraction*swapRate*computedDiscountFactors[i];
			}
			return (1-sumOfDiscountFactors)/(1+swapRate*yearFraction);
	}
	
	
	
	
	//assumes the yearFraction of the swap paymentsis half the size of the actual maturity of available data 
	
	  static double interpolate( double disountFactorT0, double discountFactorT1){
		  return Math.exp( 0.5*(Math.log(disountFactorT0) + Math.log(discountFactorT1))  );
     }
     
	  
	  
	//Valuation function for numerically finding the next rate when using interpolation 
	  
	  public static double evaluateSwapAtMissingRate(double swapRate,   	
			  										double yearFraction, 
			  										double[] computedDiscountFactors, 
			  										double missingDiscountFactor ){
			double sumOfDiscountFactors=0.0;
			for(int i=0; i<computedDiscountFactors.length; i++){
				sumOfDiscountFactors+=computedDiscountFactors[i];
			}
		    return  (1-missingDiscountFactor)/(yearFraction*(sumOfDiscountFactors+
		    		 interpolate(computedDiscountFactors[computedDiscountFactors.length-1], missingDiscountFactor) + 
		    		 missingDiscountFactor ) )-swapRate;
		}
	  
	  

		public static void main(String[] args) {
		    double[] liborDates={0.25, 0.5, 1};
		    double[] liborRates={0.002, 0.004, 0.0065};
		    double[] bootstrappedLibor=new double[3];
			
		    double[] swapRates={0.0085, 0.0095};
		;
		    double swapYearFraction=1;

		    double[] bootstrappedSwaps=new double[2];
			
		    		
		
		    
		    for(int i=0; i<liborRates.length; i++){   //Bootstrapping from the Libor
		    	bootstrappedLibor[i]=1/(1+liborDates[i]*liborRates[i]);
		    	System.out.println(" Bootstrapped discount factor from Libor number : " + (i+1) +" " + bootstrappedLibor[i]);
		    }
		    
		    
		    bootstrappedSwaps[0]=forwardDifferenceSwapBootstrap( swapRates[0],
		    		swapYearFraction, new double[]{bootstrappedLibor[2]}   );
		    		//(1-swapYearFraction*swapRates[0]*bootstrappedLibor[2])/((1+swapYearFraction*swapRates[0])); //first discount factor P(0,2) from a swap "Forward substitution"
		    
			
		    System.out.println(" Bootstrapped discount factor from Swap number 1: " + bootstrappedSwaps[0]);
			  
			
			 bootstrappedSwaps[1]=forwardDifferenceSwapBootstrap( swapRates[1],
			    		swapYearFraction, new double[]{bootstrappedLibor[2],bootstrappedSwaps[0]}   );
			    
			
	//	    
		    System.out.println(" Bootstrapped discount factor from Swap number 2: " + bootstrappedSwaps[1]);
		    
			//Interpolating the missing swaps:
		    double[] interpolatedSwapDF= new double[2];
            
		    double[] interpolatedSwapDates={1.5, 2, 2.5, 3};
			   
		    swapYearFraction=0.5;
			   
		    System.out.println("\n");
		    
		    BisectionSearch rootFinder=new BisectionSearch(0.0001, 1.1); 
		    
		    //Uses a rootfinder algorithm to find P(0,2); this is necessary because we have subsituted a nonlinear
		    //function of the rates in the forward difference method
		    
		    while(rootFinder.getAccuracy() > 1E-11 && !rootFinder.isDone()) {
				double x = rootFinder.getNextPoint();
         		double y = evaluateSwapAtMissingRate(swapRates[0],  
         				swapYearFraction, new double[]{ bootstrappedLibor[1]  , 
         						bootstrappedLibor[2]}, x );

				rootFinder.setValue(y);
			}
		    
		    bootstrappedSwaps[0]=rootFinder.getBestPoint();
		    
		    System.out.println(" Bootstrapped discount factor from Swap after interpolation number 1: " + bootstrappedSwaps[0]);
		    
		    interpolatedSwapDF[0]=interpolate( bootstrappedLibor[2] , bootstrappedSwaps[0]);

		    
		    System.out.println(" Interpolated discount factor from Swap number 1: " + interpolatedSwapDF[0]); //2.5 year zcb
		    
		    System.out.print("\n");
		    
		    rootFinder=new BisectionSearch(0.0001, 1.1);
		    
		    while(rootFinder.getAccuracy() > 1E-11 && !rootFinder.isDone()) {
				double x = rootFinder.getNextPoint();
         		double y = evaluateSwapAtMissingRate(swapRates[1],   swapYearFraction, new double[]{ bootstrappedLibor[1]  , bootstrappedLibor[2],  interpolatedSwapDF[0], bootstrappedSwaps[0]  }, x );

				rootFinder.setValue(y);
			}
		    
		    bootstrappedSwaps[1]=rootFinder.getBestPoint();
		    
		    System.out.println(" Bootstrapped discount factor from Swap after interpolation number 2: " + bootstrappedSwaps[1]);
		    
		    
		    interpolatedSwapDF[1]= interpolate( bootstrappedSwaps[0],  bootstrappedSwaps[1]) ;
		     
		   
		    System.out.println(" Interpolated discount factor from Swap number 2: " + interpolatedSwapDF[1]); //2.5 year zcb
		    
		    
//            bootstrappedSwaps[0]=(1-swapYearFraction*swapRates[0]*( bootstrappedLibor[1] + bootstrappedLibor[2] + interpolatedSwapDF[0]  )    )/((1+swapYearFraction*swapRates[0])); //first discount factor P(0,2) from a swap, semi annual settlements, interpolated
//		    
//			System.out.println(" Bootstrapped discount factor from Swap after interpolation number 1: " + bootstrappedSwaps[0]);
//		    
//		    bootstrappedSwaps[1]=(1-swapYearFraction*swapRates[1]*(bootstrappedLibor[1] +
//		    		bootstrappedLibor[2] +
//		    		interpolatedSwapDF[0] +
//		    		bootstrappedSwaps[0] +
//		    		interpolatedSwapDF[1]
//		    		))/((1+swapYearFraction*swapRates[1])); //first discount factor P(0,3) from a swap, semi annual settlements, interpolated
//		    
//		    System.out.println(" Bootstrapped discount factor from Swap after interpolation number 2: " + bootstrappedSwaps[1]);
//		    
//		    System.out.println("\n");
		    
		    System.out.print("\n");
		    
		    
		    NumberFormat format06=new DecimalFormat("0.000000"); 
		    NumberFormat format0=new DecimalFormat("0.00"); 
		    
		    
		    double[] zcb={bootstrappedLibor[0], bootstrappedLibor[1], bootstrappedLibor[2],  interpolatedSwapDF[0], 		    bootstrappedSwaps[0],  interpolatedSwapDF[1], 		    bootstrappedSwaps[1] };
		    double[] dates={liborDates[0], liborDates[1], liborDates[2], interpolatedSwapDates[0], interpolatedSwapDates[1], interpolatedSwapDates[2], interpolatedSwapDates[3]}; 
		    
		    //zero coupon bond curve and yield
		     
		    System.out.println("Year" + "\t" + "ZCB bond curve" + "\t" + "Yield Curve");
	
		    for(int index=0; index<zcb.length; index ++){
		    	
		    	System.out.println(format0.format(dates[index]) + "\t" +format06.format(zcb[index])+ "\t" + format06.format(  (1/zcb[index]-1)/dates[index] ));
		    	
		    }
		    
			   
		    
			
		}
	
	
}
