package Exercise4;



import net.finmath.functions.AnalyticFormulas;
import net.finmath.rootfinder.BisectionSearch;

public class BootStrapAndCap {
	
     //computes the forward difference equation for bootstrapping
		public static double forwardDifferenceSwapBootstrap(double swapRate,
				double yearFraction, double enterTimeDiscountFactor, double[] computedDiscountFactors ){
			double sumOfDiscountFactors=0.0;
					for(int i=0; i<computedDiscountFactors.length; i++){
						sumOfDiscountFactors+=yearFraction*swapRate*computedDiscountFactors[i];
					}
					return (enterTimeDiscountFactor-sumOfDiscountFactors)/(1+swapRate*yearFraction);
		}
		
		
		
		
		//assumes the yearFraction of the swap payments is half the size of the actual maturity of available data 
		
		 public static double interpolate( double T0, double T1, double t, double disountFactorT0, double discountFactorT1){  //DeltaT is the distance from the interpolation point to T0  
	                
	   	  return Math.exp( (T1-t)/(T1-T0)*Math.log(disountFactorT0) + Math.log(discountFactorT1)*(t-T0)/(T1-T0)   );
	    
		 }
	
		 
		 
		  
		  public static double evaluateSwapAtMissingRate(double T0, double T1, double t,  double swapRate,   	double yearFraction,
				  double enterTimeDiscountFactor , double[] computedDiscountFactors, double missingDiscountFactor ){
				double sumOfDiscountFactors=0.0;
				for(int i=0; i<computedDiscountFactors.length; i++){
					sumOfDiscountFactors+=computedDiscountFactors[i];
				}
			     return  (enterTimeDiscountFactor-missingDiscountFactor)/(yearFraction*(sumOfDiscountFactors+
			    		 interpolate(T0, T1, t, computedDiscountFactors[computedDiscountFactors.length-1], missingDiscountFactor) + missingDiscountFactor ) )-swapRate;
			}
		  
		 
	public static double[] getZcb(){
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
	    
	    
	     
		    bootstrappedSwaps[0]=forwardDifferenceSwapBootstrap( swapRates[0],
		    		swapYearFraction, bootstrappedLibor[1], new double[]{0.0}   );
	
		    double[] interpolatedSwapDF= new double[3];  //missing factors
			  
		
		     // interpolate the 1.5 discount factor
		     
			  interpolatedSwapDF[0]=interpolate(1.0, 2.0, 1.5, bootstrappedLibor[1] , bootstrappedSwaps[0]); //P(0,1.5)
                 
			  
	
	    
	    //Bootstrap P(0,3) from the semiannually payed swap S3
	    
	     swapYearFraction=0.5;
	     
	     
	     BisectionSearch rootFinder=new BisectionSearch(0.0001, 1.1); 
		    
		    
		    while(rootFinder.getAccuracy() > 1E-11 && !rootFinder.isDone()) { 
				double x = rootFinder.getNextPoint();
      		double y = evaluateSwapAtMissingRate(2.0, 3.0, 2.5, swapRates[1],  
      				swapYearFraction, 1.0, new double[]{ bootstrappedLibor[0]  , 
      						bootstrappedLibor[1], interpolatedSwapDF[0], bootstrappedSwaps[0] }, x );

				rootFinder.setValue(y);
			}
		    
		    bootstrappedSwaps[1]=rootFinder.getBestPoint();  //P(0,3)
		    
		    
		    interpolatedSwapDF[1]=interpolate(2.0, 3.0, 2.5,  bootstrappedSwaps[0] , bootstrappedSwaps[1]); //P(0,2.5)
 
		    //bootstrap P(0,5) from the annually payed swap of rate S5
		    
		    swapYearFraction=1.0;
	         
		  rootFinder=new BisectionSearch(0.0001, 1.1);   //reset the rootfinder
			 
		    
		    while(rootFinder.getAccuracy() > 1E-11 && !rootFinder.isDone()) { 
				double x = rootFinder.getNextPoint();
      		double y = evaluateSwapAtMissingRate(3.0,5.0, 4.0, swapRates[2],  
      				swapYearFraction, bootstrappedLibor[1],   new double[]{ 
      						 bootstrappedSwaps[0], bootstrappedSwaps[1] }, x );

				rootFinder.setValue(y);
			}
		    
		    
		    bootstrappedSwaps[2]=rootFinder.getBestPoint();  //P(0,5)
		   
		   
		    
		    interpolatedSwapDF[2]=interpolate(3.0, 5.0, 4.0, bootstrappedSwaps[1] , bootstrappedSwaps[2]); //P(0,4)
		     
		    //optional: find P(0, 3.5), P(0,4.5). We do not need them for the present assignment
		    
		    double[] zcb={bootstrappedLibor[0],  bootstrappedLibor[1], interpolatedSwapDF[0], bootstrappedSwaps[0], interpolatedSwapDF[1],  bootstrappedSwaps[1], interpolatedSwapDF[2],bootstrappedSwaps[2] };
		    
		return zcb;
		    
	}

	public static void main(String[] args) {
		
			double[] zcb = getZcb();
		
		    System.out.println("ZCB curve");
		    
		    System.out.print("\n");
		    
		    for(double df : zcb){
		    	System.out.println(df);
		    }
		    
		    System.out.print("\n");
		    
		    //We have the ZCB P(0,0.5),  P(0,1),  P(0,1.5), P(0, 2), P(0,2.5), P(0,3), P(0,4), P(0,5) which we now use to price the cap
	  
		    //cap pricing
		    
		    double[] capletValues=new double [3];
		    double capValue=0.0;
		    double strike=0.01;
		    double notional=10000;
		    double[] liborVolatilities={0.15, 0.25, 0.4};
		    double swapYearFraction=1;

		    
		    //initial forward rates for black formula
		    
		    double[] initialLiborForwards={1.0/swapYearFraction*(zcb[3]/zcb[5]-1  ), 1.0/swapYearFraction*(zcb[5]/zcb[6]-1  ), 1.0/swapYearFraction*(zcb[6]/zcb[7]-1  )    };
		    
		    
		    
		    for(int capletIndex=0; capletIndex <capletValues.length; capletIndex++){
		    	capletValues[capletIndex]=notional*AnalyticFormulas.blackModelCapletValue(initialLiborForwards[capletIndex], 
		    			liborVolatilities[capletIndex], 
		    			2+capletIndex, strike, 
		    			swapYearFraction , zcb[5+capletIndex]);
		    	capValue+=capletValues[capletIndex];
		    }
	
 System.out.println("Caplet Values");
		    
 System.out.print("\n");
 
		    for(double value : capletValues){
		    	System.out.println(value);
		    }
	
		    System.out.print("\n");
		    

		    
		    System.out.println("Cap total Value : " +capValue	);
			
	}
	
	
}
