package Testing;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.apache.commons.math3.dfp.DfpField;

import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.CorrelatedBrownianMotion;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;

public class correlatedBM {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double startTime = 0.0;
		double T = 1.0;
		double deltaT = 0.01;
		int numberOfPaths = 1;
		final int seed = 1213;
		double correlation = 0.1;
		double initialValue = 100.0;
		double drift = -0.02;
		double diffusion = 6;
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00000######" );
		
		
		TimeDiscretization timediscretization = new TimeDiscretization(startTime, (int)((T - startTime) / deltaT), deltaT);
		BrownianMotion BM = new BrownianMotion(timediscretization, 2, numberOfPaths, seed);		 
		
		double[][] correlationMatrix = {{correlation,0},{0,correlation}};
		System.out.println(Arrays.toString(correlationMatrix[0]));
		System.out.println(Arrays.toString(correlationMatrix[1]));
		
		CorrelatedBrownianMotion corrBM = new CorrelatedBrownianMotion(BM, correlationMatrix);

		System.out.print("\n");
		
		RandomVariableInterface[] aPath = new RandomVariableInterface[timediscretization.getNumberOfTimes()+1];
		aPath[0]=new RandomVariable(0.0, initialValue);
		RandomVariableInterface[] Path = new RandomVariableInterface[timediscretization.getNumberOfTimes()+1];
		Path[0]=new RandomVariable(0.0, initialValue);

		for(int i = 1; i < timediscretization.getNumberOfTimeSteps(); i++){
			
			for(int k = 0; k < numberOfPaths; k++){
				aPath[i] = aPath[i-1].add(aPath[i-1].mult(drift).mult(deltaT)).add(corrBM.getBrownianIncrement(i, 0).mult(diffusion));
				System.out.print(df2.format(aPath[i].get(k)) + "\t");
			}
			for(int k = 0; k < numberOfPaths; k++){
				Path[i] = Path[i-1].add(Path[i-1].mult(drift).mult(deltaT)).add(corrBM.getBrownianIncrement(i, 1).mult(diffusion));
				System.out.print(df2.format(Path[i].get(k)) + "\t");
			}
			
			System.out.print("\n");
		}
		
//		System.out.print(corrBM.getBrownianIncrement(1, 0));
//		System.out.print("\n");
//		System.out.print(corrBM.getBrownianIncrement(1, 0).get(0));
		
		
		
	}

}
