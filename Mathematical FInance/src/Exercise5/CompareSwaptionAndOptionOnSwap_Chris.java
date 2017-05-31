package Exercise5;

import net.finmath.functions.AnalyticFormulas;
import net.finmath.rootfinder.BisectionSearch;
import Exercise4.BootStrapAndCap;

public class CompareSwaptionAndOptionOnSwap_Chris {

	public static void main(String[] args) {
		
	    double[] liborDates={0.5, 1};
	    double[] liborRates={0.003, 0.0065};
	    double[] bootstrappedLibor=new double[2];  
		
	    double[] swapRates={0.009, 0.012, 0.0155};  //the forward rate is here. It is the rate from a 1 period swap, i.e. a FRA (forward rate agreement)
	    
	    double swapYearFraction=1;

	    double[] bootstrappedSwaps=new double[3];
		
	    
	   
	    for(int i=0; i<liborRates.length; i++){   //Bootstrapping from the Libor
	    	bootstrappedLibor[i]=1/(1+liborDates[i]*liborRates[i]);
	    }
	    
	    //BootStrap From the forward rate: produced P(0,2)
	    
	    
	     
		    bootstrappedSwaps[0]=BootStrapAndCap.forwardDifferenceSwapBootstrap( swapRates[0],
		    		swapYearFraction, bootstrappedLibor[1], new double[]{0.0}   );
	
		    double[] interpolatedSwapDF= new double[3];  //missing factors
			  
		
		     // interpolate the 1.5 discount factor
		     
			  interpolatedSwapDF[0]=BootStrapAndCap.interpolate(1.0, 2.0, 1.5, bootstrappedLibor[1] , bootstrappedSwaps[0]); //P(0,1.5)
                 
			  
	
	    
	    //Bootstrap P(0,3) from the semiannually payed swap S3
	    
	     swapYearFraction=0.5;
	     
	     
	     BisectionSearch rootFinder=new BisectionSearch(0.0001, 1.1); 
		    
		    
		    while(rootFinder.getAccuracy() > 1E-11 && !rootFinder.isDone()) { 
				double x = rootFinder.getNextPoint();
      		double y = BootStrapAndCap.evaluateSwapAtMissingRate(2.0, 3.0, 2.5, swapRates[1],  
      				swapYearFraction, 1.0, new double[]{ bootstrappedLibor[0]  , 
      						bootstrappedLibor[1], interpolatedSwapDF[0], bootstrappedSwaps[0] }, x );

				rootFinder.setValue(y);
			}
		    
		    bootstrappedSwaps[1]=rootFinder.getBestPoint();  //P(0,3)
		    
		    
		    interpolatedSwapDF[1]=BootStrapAndCap.interpolate(2.0, 3.0, 2.5,  bootstrappedSwaps[0] , bootstrappedSwaps[1]); //P(0,2.5)
 
		    //bootstrap P(0,5) from the annually payed swap of rate S5
		    
		    swapYearFraction=1.0;
	         
		  rootFinder=new BisectionSearch(0.0001, 1.1);   //reset the rootfinder
			 
		    
		    while(rootFinder.getAccuracy() > 1E-11 && !rootFinder.isDone()) { 
				double x = rootFinder.getNextPoint();
      		double y = BootStrapAndCap.evaluateSwapAtMissingRate(3.0,5.0, 4.0, swapRates[2],  
      				swapYearFraction, bootstrappedLibor[1],   new double[]{ 
      						 bootstrappedSwaps[0], bootstrappedSwaps[1] }, x );

				rootFinder.setValue(y);
			}
		    
		    
		    bootstrappedSwaps[2]=rootFinder.getBestPoint();  //P(0,5)
		   
		   
		    
		    interpolatedSwapDF[2]=BootStrapAndCap.interpolate(3.0, 5.0, 4.0, bootstrappedSwaps[1] , bootstrappedSwaps[2]); //P(0,4)
		     
		    //optional: find P(0, 3.5), P(0,4.5). We do not need them for the present assignment
		    
		    double[] zcb={bootstrappedLibor[0],  bootstrappedLibor[1], interpolatedSwapDF[0], bootstrappedSwaps[0], interpolatedSwapDF[1],  bootstrappedSwaps[1], interpolatedSwapDF[2],bootstrappedSwaps[2] };
		    //We have the ZCB P(0,0.5),  P(0,1),  P(0,1.5), P(0, 2), P(0,2.5), P(0,3), P(0,4), P(0,5) which we now use to price the cap

		    System.out.println("ZCB curve");
		    
		    System.out.print("\n");
		    
		    for(double df : zcb){
		    	System.out.println(df);
		    }
		    
		    System.out.print("\n");
		    
		    double swapAnnuity = (zcb[6]+zcb[7]);
		    double swapRate = (zcb[5] - zcb[7])/swapAnnuity;
		    double swaptionValue = AnalyticFormulas.blackModelSwaptionValue(swapRate, 0.2, 3, 0.008, swapAnnuity);
		    
		    System.out.print(swaptionValue);
		    
		    
			
	}
	
	
	
}
