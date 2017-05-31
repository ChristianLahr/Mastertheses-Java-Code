package Exercise7;

import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionInterface;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloBlackScholesModel;
import net.finmath.montecarlo.process.ProcessEulerScheme;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;

public class Ex1_Chris {

	public static void main(String[] args) {

		double sigma23 = 0.25;
		double sigma34 = 0.3;
		double roh = 0.5;
		
		double T2 = 2.0;
		double T3 = 3.0;
		double T4 = 4.0;
				
		int numberOfPaths=100;
		int numberOfTimeSteps=100;
		double firstFixingTime = T3;
		double secondfixingTime = T4;
		double stepSize = firstFixingTime/numberOfTimeSteps;
		int seed = 1234;
		TimeDiscretization timeDiscretization = new TimeDiscretization(0.0, numberOfTimeSteps, stepSize);
			
		
		BrownianMotionInterface brownianMotion = new BrownianMotion(timeDiscretization, 1, numberOfPaths, seed);
		
		ProcessEulerScheme eulerScheme= new ProcessEulerScheme(brownianMotion); //process for cretaing the model

		// zuerst L34 berechnen
		MonteCarloBlackScholesModel L34 = new MonteCarloBlackScholesModel(timeDiscretization, numberOfPaths, 1.0, 0.0, sigma34);
		
		Libor_MC_DriftAdjusted_Chris L23 = new Libor_MC_DriftAdjusted_Chris(100, roh, sigma23, sigma34, T3-T2, T4-T3, L34);

		
		double optionValue;
		
		optionValue = L23.getProcessValue(timeDiscretization.getTimeIndex(T4)))-L34.getAssetValue
		
		
	}

}
