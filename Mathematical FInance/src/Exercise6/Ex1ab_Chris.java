package Exercise6;

import Exercise4.BootStrapAndCap;
import Exercise5.CompareSwaptionAndOptionOnSwap_Chris;
import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationInterface;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloBlackScholesModel;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationInterface;

public class Ex1ab_Chris {

	public static void main(String[] args) throws CalculationException {

		double notional = 1;
		
		double P01 = 0.993542;
		double P02 = 0.983157;
		
		double valueNaturalFloater = P01 - P02;
		
		System.out.println("1a):  " + valueNaturalFloater * notional);
		
		double initial = 0.0;
		int numberOfTimeSteps = 10;
		double deltaT = 0.1;
		
		TimeDiscretizationInterface timeDiscretization = new TimeDiscretization(initial, numberOfTimeSteps, deltaT);
		
		int numberOfPaths = 1000;
		double initialValue = (P01 - P02) / P02;
		double riskFreeRate = 0.0;
		double volatility = 0.25;
				
		AssetModelMonteCarloSimulationInterface black = new MonteCarloBlackScholesModel(timeDiscretization, numberOfPaths, initialValue, riskFreeRate, volatility);
 
		System.out.println("1b):  " + P02 * black.getAssetValue(10, 0).getAverage() * notional);   //getAverage = Erwartung 
		
		double calc = P02 * initial + P02 * black.getAssetValue(10, 0).squared().getAverage() * notional;
		System.out.println("1c):  " +  calc ); 
		
		
		
		
		
	}
}
