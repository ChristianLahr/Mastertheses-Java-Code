
package Exercise1;


import net.finmath.functions.AnalyticFormulas;
import net.finmath.functions.NormalDistribution;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionInterface;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;

import java.text.DecimalFormat;

import org.apache.commons.math3.random.MersenneTwister;

import cern.jet.random.engine.MersenneTwister64;

/**
 * This is an experiment to test the quality of the random number generator
 * and the discretization scheme. (taken from an Example of C. Fries)
 */



 public class EulerSchemeExample
{
	private int		numberOfTimeSteps;
	private double	deltaT;
	private int		numberOfPaths;
	private double	initialValue;
	private double	volatility;
	private TimeDiscretization times;
	private double drift;
		
	private RandomVariableInterface[]	process = null;
	private RandomVariableInterface[]	logProcess = null;
 
	/**
	 * @param numberOfTimeSteps
	 * @param deltaT
	 * @param paths
	 * @param initialValue
	 * @param sigma
	 */
	public EulerSchemeExample(
			int numberOfTimeSteps,
			double deltaT,
			int numberOfPaths,
			double initialValue,
			double volatility,
			double drift) {
		super();
		this.numberOfTimeSteps = numberOfTimeSteps;
		this.deltaT = deltaT;
		this.numberOfPaths = numberOfPaths;
		this.initialValue = initialValue;
		this.volatility = volatility;
		this.drift=drift;
		process = new RandomVariableInterface[getNumberOfTimeSteps()+1];

		logProcess = new RandomVariableInterface[getNumberOfTimeSteps()+1];
		times=new TimeDiscretization(0.0, numberOfTimeSteps , deltaT);
	}
	
	public RandomVariableInterface getProcessValue(int timeIndex)
	{
		if(process == null)
		{
			generateProcess();
		}
		
				
		// Return value of process
		return process[timeIndex];
	}

 
	
	// Main mehtod that Calculates the  process and the log-process
	 
	
	private void generateProcess() {
		
		BrownianMotionInterface	brownianMotion	= new BrownianMotion(            //creates the Brownian Motion underlying the process
				times,
				1,						// numberOfFactors of the Brownian Motion: 1, we are in a single asset case.
				getNumberOfPaths(),
				1234					// seed
				);
		
		process[0]=new RandomVariable(0.0, initialValue);  //initialise first random varible as a constant
		logProcess[0]=new RandomVariable(0.0, Math.log(initialValue));  //initialise first random varible as a constant
	    
		
		
		
		for(int timeIndex = 1; timeIndex < getNumberOfTimeSteps()+1; timeIndex++)
		{
			double[] newRealization = new double[numberOfPaths];   //reset the 
			double[] newLogRealization = new double[numberOfPaths];
			
				
				// The numerical scheme

				// Generate values 
				for (int componentIndex = 1; componentIndex < numberOfPaths; componentIndex++ )
				{
					double previousValue = process[timeIndex-1].get(componentIndex);    
					//must be reset at all times! problem is : these are object- and as such they get passed by reference. if you just overwrite in the new assignment below 
					//ALL of the process will be overwritten!
					double previousLogValue = logProcess[timeIndex-1].get(componentIndex);
					
					
					// Diffusion
					double diffusion = volatility * brownianMotion.getBrownianIncrement(timeIndex-1, 0).get(componentIndex);
					
					
					// stores new realisations

					newRealization[componentIndex] = previousValue + previousValue * drift * deltaT + previousValue * diffusion;
					newLogRealization[componentIndex] = previousLogValue +  (drift - volatility*volatility/2) * deltaT + diffusion; //Ito´s lemma!

			}
			
			// Store values
			process[timeIndex] = new RandomVariable(times.getTime(timeIndex), newRealization);
			logProcess[timeIndex] = new RandomVariable(times.getTime(timeIndex), newLogRealization);
			
		}
	}


	
	 //some GETTERS
	
	
	//averages and variances
	
		public double getAverage(int timeIndex)
		{
			RandomVariableInterface randomVariable = getProcessValue(timeIndex);
			return randomVariable.getAverage();
		}
		
		public double getAverageOfLog(int timeIndex)
		{
			RandomVariableInterface randomVariable = getProcessValue(timeIndex);
			return randomVariable.log().getAverage();
		}

		public double getVariance(int timeIndex)
		{
			RandomVariableInterface randomVariable = getProcessValue(timeIndex);
			return randomVariable.getVariance();
		}
		
		public double getVarianceOfLog(int timeIndex)
		{
			RandomVariableInterface randomVariable = getProcessValue(timeIndex);
			return randomVariable.log().getVariance();
		}
	
	//the time step
	public double getDeltaT() {
		return deltaT;
	}
	
	//the whole time discretisation 
		public TimeDiscretization getTimeDiscretiztion() {
			return times;
		}

// initial value
	public double getInitialValue() {
		return initialValue;
	}
	
	//get log initial value
	public double getLogInitialValue() {
		return Math.log(initialValue);
	}


//number of paths
	public int getNumberOfPaths() {
		return numberOfPaths;
	}

//number of time steps
	public int getNumberOfTimeSteps() {
		return numberOfTimeSteps;
	}

	//volatility
	public double getVolatility() {
		return volatility;
	}

	//drift
	public double getDrift(){
		return drift;
	}
	
	// price and log-price in the time horizon
	
	public RandomVariableInterface getPriceAtSomeInstant(int timeIndex){
		return process[timeIndex];
	}
	
	public RandomVariableInterface getLogPriceAtSomeInstant(int timeIndex){
		return logProcess[timeIndex];
	}
	
	//Final price and log-price
	
	public RandomVariableInterface getFinalPrice(){
		return process[times.getNumberOfTimes()-1];
	}
	
	public RandomVariableInterface getFinalLogPrice(){
		return logProcess[times.getNumberOfTimes()-1];
	}
	
	
	
	
	
	public static void main(String[] args)
	{
		double initialValue = 100.0;
		double volatility		= 0.2;		// Note: Try different sigmas: 0.2, 0.5, 0.7, 0.9		
		int numberOfPaths	=100000;
		double drift=0.05;
		double deltaT=0.01;
		int numberOfTimeSteps=100;
		
		double finalTime=deltaT*numberOfTimeSteps;
		
		//market parameters
		double strike=100.0;
		double interestRate=drift;
		
//Option prices using the euler scheme above for the Log.returns
		
    EulerSchemeExample eulerScheme=new EulerSchemeExample(numberOfTimeSteps, deltaT, numberOfPaths, initialValue, volatility, drift);
	
    eulerScheme.generateProcess(); //not Lazy initialization
	System.out.println("Some testing of option prices");
	System.out.println("\n");
	
    
    System.out.println("Option price using the Euler scheme generator: "+ eulerScheme.getFinalPrice().sub(strike).floor(0.0).getAverage()*Math.exp(-interestRate*finalTime)   );
    
    RandomVariableInterface exponentialTransformOfLogPrice=eulerScheme.getFinalLogPrice().exp();
    
    System.out.println("Option price using the Log-Euler scheme generator: "+ exponentialTransformOfLogPrice.sub(strike).floor(0.0).getAverage()*Math.exp(-interestRate*finalTime)   );
    
    
    System.out.println("Analytical price: " + AnalyticFormulas.blackScholesOptionValue( initialValue, interestRate, volatility, finalTime, strike));
	
    int seed=1234;
    double[] arrayOfTerminalPrices=new double[numberOfPaths];
    MersenneTwister mersenneTwister=new MersenneTwister(seed);
    double uniformVariate;
    double normalVariate;
   
    for(int sampleIndex=0; sampleIndex<arrayOfTerminalPrices.length; sampleIndex++){  //generating the lognormally distributed values for the stocprice
		 uniformVariate=mersenneTwister.nextDouble();
		 normalVariate= (interestRate-volatility*volatility/2)*finalTime + Math.sqrt(finalTime)*volatility*NormalDistribution.inverseCumulativeDistribution(uniformVariate);
		 arrayOfTerminalPrices[sampleIndex]=initialValue*Math.exp(normalVariate);  
    }
	   
    
    RandomVariable samplesFromTerminalStock=new RandomVariable(finalTime, arrayOfTerminalPrices); //Wrapping in an array
        
   
    System.out.println("More monte Carlo: price from sampling the final distribution: " + samplesFromTerminalStock.sub(strike).floor(0.0).getAverage()*Math.exp(-interestRate*finalTime));
    
    System.out.println("\n");
    
    System.out.println("Cool bonus: a verification of ito´s Lemma:");
    
    System.out.println("\n");
    
    System.out.println("time  " + "\t" + "Process  " + "\t" + " Log-Process  " + "\t" + "Taking the Logarithm of the process  ");

     final DecimalFormat formatterReal2	= new DecimalFormat("0.00");
	 final DecimalFormat formatterSci4	= new DecimalFormat(" 0.0000;-0.0000");
	 
	 int testPathIndex=2; // we test the ito s lemma at some path
	
	 for(int timeIndex=0; timeIndex<eulerScheme.getNumberOfTimeSteps() ; timeIndex++){
		System.out.println(  //formatting in a table the data gathered for the given time instant (no need of the full power of RVs arrays here)
				formatterReal2.format(eulerScheme.getTimeDiscretiztion().getTime(timeIndex)) + "\t"  +
				formatterSci4.format(eulerScheme.getPriceAtSomeInstant(timeIndex).get(testPathIndex)) + "\t" +
				formatterSci4.format(eulerScheme.getLogPriceAtSomeInstant(timeIndex).get(testPathIndex)) + "\t" +   "        " +
 				formatterSci4.format(Math.log(eulerScheme.getPriceAtSomeInstant(timeIndex).get(testPathIndex)) ) + "\t" +
				
				
				""
		);
	 }
	}
}