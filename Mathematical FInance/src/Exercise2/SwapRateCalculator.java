package Exercise2;

import javax.naming.InitialContext;

import net.finmath.time.TimeDiscretization;

public class SwapRateCalculator {
	double yearFraction;  //in principle can be an array.....
//	int numberOfPayments;
	double swapEnterTime;
	double evaluationTime;
	
	double[] zeroBondCurve;  // dicsount factors
	
	double swapRate;
	TimeDiscretization swapDates;
	
	public SwapRateCalculator(double yearFraction, double evaluationTime, double swapEnterTime,
			double[] zeroBondCurve) {
		this.yearFraction = yearFraction;
	//	this.numberOfPayments = numberOfPayments;
		this.zeroBondCurve = zeroBondCurve;
		this.swapEnterTime=swapEnterTime;
		this.evaluationTime=evaluationTime;
	}

	private void calculateSwapRate(){
//		if(zeroBondCurve.length < numberOfPayments ){
//			System.out.println("Invalid zero coupon curve input");
//			return;
//		}
		double sumOfDiscountFactors=0.0;
		
		swapDates=new TimeDiscretization(evaluationTime,zeroBondCurve.length, yearFraction);
		for(int couponIndex=swapDates.getTimeIndex(swapEnterTime)+1; couponIndex<zeroBondCurve.length; couponIndex++){
			sumOfDiscountFactors+=zeroBondCurve[couponIndex];
		
		}
		swapRate=(zeroBondCurve[swapDates.getTimeIndex(swapEnterTime)]-zeroBondCurve[zeroBondCurve.length-1])/(yearFraction*sumOfDiscountFactors);
	}
	
	public double getSwapRate(){
		
		calculateSwapRate();  //lazy
		
		return swapRate;
			
	}
	
	public TimeDiscretization getSwapDates(){
		return swapDates;
	}

	public static void main(String[] args){
	        int numberOfPayments=6;
	        double swapEnterTime=1.0;
	        double yearFraction=0.5;
	        double evaluationTime=0;
	        
	        double[] initialBondPrices={ 1, 0.9986509108, 0.9949129829, 0.9897033769, 0.9835370208, 0.9765298116,0.9689909565 };
	        
    SwapRateCalculator swapCalculator= new SwapRateCalculator(yearFraction ,  evaluationTime, swapEnterTime, initialBondPrices);
    //swapCalculator.calculateSwapRate();
    System.out.println("The required swap rates is: " +swapCalculator.getSwapRate());
    System.out.println(swapCalculator.getSwapDates());
    
	}
	
}  //this whole class can be better implemented using a STATIC METHOD; as we did in class

