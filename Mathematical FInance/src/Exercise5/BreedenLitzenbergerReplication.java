package Exercise5;


import java.util.Arrays;

import Exercise1.EulerSchemeExample;

import net.finmath.functions.AnalyticFormulas;

public class BreedenLitzenbergerReplication {
     
	public static void main(String[] args) {
	
	double spotPrice=100;
    double maturity=1.0;
    double volatility=0.3;
    double interestRate=0.0;
    
	double[] strikes=new double[21];
     double initialStrike=0.5*spotPrice;
     double strikeStep=0.05*spotPrice;
     
     for (int strikIndex=0; strikIndex<strikes.length; strikIndex++){
    	 strikes[strikIndex]=initialStrike+strikeStep*strikIndex;
     }
     System.out.println(Arrays.toString(strikes));
     
     
   //  double forwardValue=spotPrice-;
   
     
     //x^3: f´(x)=3 x^2, f´´(x)=6 x
     
     double cashPosition=spotPrice*spotPrice*spotPrice;
     double forwardPosition=0;
     
     double optionPosition=0.0; 
     //calculate the call portfolio
     
     for (int strikIndex=0; strikIndex<(int) strikes.length/2; strikIndex++){
    	 optionPosition+=6*strikes[strikIndex]*AnalyticFormulas.blackScholesOptionValue(spotPrice, interestRate, volatility, maturity, strikes[strikIndex], false)*strikeStep;
     }
    
     for (int strikIndex=(int) strikes.length/2 +1; strikIndex<strikes.length; strikIndex++){
     	 optionPosition+=6*strikes[strikIndex]*AnalyticFormulas.blackScholesOptionValue(spotPrice, interestRate, volatility, maturity, strikes[strikIndex], true)*strikeStep;
     }
    
     double replicatedValue= cashPosition + forwardPosition + optionPosition;
     
     System.out.println("Total replicated value: " + replicatedValue);
 	
     //Euler simulation for benchmark
     
     int numberOfTimeSteps=100;
 	int numberOfPaths=10000;
 	
       EulerSchemeExample eulerScheme=new EulerSchemeExample(
 			numberOfTimeSteps,
 			maturity/numberOfTimeSteps,
 			numberOfPaths,
 			spotPrice,
 			volatility,
 			0);
     
       double simulatedValue=eulerScheme.getFinalPrice().mult(eulerScheme.getFinalPrice()).mult(eulerScheme.getFinalPrice()).getAverage();
       
       System.out.println("Monte Carlo Value: " +  simulatedValue );
       System.out.println("Error: " +  (simulatedValue/replicatedValue-1) );
   	
       //Now go back at the beginning, double the option number and half the stepsize!
     
	}
	

	
}
