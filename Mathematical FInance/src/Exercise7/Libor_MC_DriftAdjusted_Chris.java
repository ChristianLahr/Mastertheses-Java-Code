package Exercise7;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.BrownianBridge;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionInterface;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloBlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloBlackScholesModelTest;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;

public class Libor_MC_DriftAdjusted_Chris {
	
	private TimeDiscretization timeDiscretization;
	private RandomVariableInterface[] process;
	private RandomVariableInterface[] logProcess;
	private double initialValue;
	private double correlation;
	private double volatility;
	private double otherVolatility;
	private double timeStep;
	private double deltaT;
	private MonteCarloBlackScholesModel model;
	private double otherProcess;
	
	public Libor_MC_DriftAdjusted_Chris(double initialValue, double correlation, double volatility, double otherVolatility, double timeStep, double deltaT, MonteCarloBlackScholesModel model){
		this.initialValue = initialValue;
		this.correlation = correlation;
		this.volatility = volatility;
		this.otherVolatility = otherVolatility;
		this.timeStep = timeStep;
		this.deltaT = deltaT;
		this.model = model;		
	}
	
	
	public void generateProcess() throws CalculationException {
	
		int numberOfPaths = 100;
		int seed = 1234;
		int numberOfFactors = 1;
		
		BrownianMotionInterface brownianMotion = new BrownianMotion(timeDiscretization, numberOfFactors, numberOfPaths, seed);

		process = new RandomVariableInterface[timeDiscretization.getNumberOfTimeSteps()];
		
		process[0] 		= new RandomVariable(0.0, initialValue);		
		logProcess[0]	= new RandomVariable(0.0, Math.log(initialValue));
		
		for(int timeIndex = 1; timeIndex < timeDiscretization.getNumberOfTimeSteps(); timeIndex++){
			
 			double[] newRealization = new double[numberOfPaths];   //reset the 
 			double[] newLogRealization = new double[numberOfPaths];
 			double[] drift= new double[numberOfPaths];
 				
 				for (int componentIndex = 1; componentIndex < numberOfPaths; componentIndex++ ){
 					
 					otherProcess = model.getAssetValue(timeIndex - 1, 0).get(componentIndex);
 					
 					double previousValue = process[timeIndex-1].get(componentIndex);    
 					double previousLogValue = logProcess[timeIndex-1].get(componentIndex);

 					double previousDrift=-correlation*timeStep*volatility*otherVolatility* otherProcess/(1+timeStep* otherProcess);

 					double diffusion = volatility * brownianMotion.getBrownianIncrement(timeIndex-1, 0).get(componentIndex);

 					newRealization[componentIndex] = previousValue + previousValue * previousDrift * deltaT + previousValue * diffusion;
 					newLogRealization[componentIndex] = previousLogValue +  (previousDrift - volatility*volatility/2) * deltaT + diffusion; //Ito´s lemma!
 
 				}
		}
		
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
	
	public double getAverage(int timeIndex)
	{
		RandomVariableInterface randomVariable = getProcessValue(timeIndex);
		return randomVariable.getAverage();
	}
	
	
}
