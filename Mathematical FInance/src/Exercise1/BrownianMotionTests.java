/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christian-fries.de.
 *
 * Created on 10.02.2004
 */
package Exercise1;


import java.text.DecimalFormat;

import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationInterface;

/**
 * 
 * Example of how Brownian Motion Works.
 * 
 */

public class BrownianMotionTests {

	static final DecimalFormat formatterReal2	= new DecimalFormat("0.00");
	static final DecimalFormat formatterSci4	= new DecimalFormat(" 0.0000;-0.0000");
	
	
	public static void main(String[] args)
	{
		// The parameters
		int numberOfPaths	= 500000;
		int seed			= 1234;

		double lastTime = 1.0;
		double dt = 0.1;

		// Create the time discretization, nmber of steps deduced from finaltime and delta
		TimeDiscretizationInterface timeDiscretization = new TimeDiscretization(0.0, (int)(lastTime/dt), dt);

		//Generating a Two-dimensional Brownian motion
		BrownianMotion brownian = new BrownianMotion(
				timeDiscretization,
				2,//Bi-Dimensional Brownian Motion!
				numberOfPaths,
				seed
		);
		
		System.out.println("Average, variance and other properties of a BrownianMotion.\nTime step size (dt): " + dt + "  Number of path: " + numberOfPaths + "\n");

		System.out.println("      " + "\t" + "  int dW_1 " + "\t" + "int dW_1 dW_1" + "\t" + "int dW_1 dW_2" + "\t" );
		System.out.println("time" + "\t" + " mean" + "\t" + " var" + "\t" + " mean" + "\t" + " var" + "\t" + " mean" + "\t" + " var");


		
		RandomVariableInterface[] firstBrownianMotionPath=new RandomVariableInterface[timeDiscretization.getNumberOfTimes()+1];
		RandomVariableInterface[] secondBrownianMotionPath=new RandomVariableInterface[timeDiscretization.getNumberOfTimes()+1];
		
		
		RandomVariableInterface[] firstQuadraticVariationPath=new RandomVariableInterface[timeDiscretization.getNumberOfTimes()+1];
		RandomVariableInterface[] quadraticCovariationPath=new RandomVariableInterface[timeDiscretization.getNumberOfTimes()+1];
		
		
		//Build an ACTUAL brownian motion from the class Brownian Motion (which produces brownian Increments)
		
		firstBrownianMotionPath[0]=new RandomVariable(0.0, 0.0);
		firstQuadraticVariationPath[0]=new RandomVariable(0.0, 0.0);
		quadraticCovariationPath[0]=new RandomVariable(0.0, 0.0);
		
		for(int timeIndex=1; timeIndex<timeDiscretization.getNumberOfTimeSteps(); timeIndex++) {
			// Calculate W(t+dt) from dW
			
			firstBrownianMotionPath[timeIndex]=firstBrownianMotionPath[timeIndex-1].add(brownian.getBrownianIncrement(timeIndex, 0));
			secondBrownianMotionPath[timeIndex]=firstBrownianMotionPath[timeIndex-1].add(brownian.getBrownianIncrement(timeIndex, 1));
			
		
			// Calculate x = \int dW_1(t) * dW_1(t), the quadratic variation process
		
			firstQuadraticVariationPath[timeIndex]=firstQuadraticVariationPath[timeIndex-1].add(brownian.getBrownianIncrement(timeIndex, 0).squared());
		
			// Calculate x = \int dW_1(t) * dW_2(t), the quadratic covariation process
			
			quadraticCovariationPath[timeIndex]=quadraticCovariationPath[timeIndex-1].add(brownian.getBrownianIncrement(timeIndex, 0).mult(brownian.getBrownianIncrement(timeIndex, 1)));
			
			
			System.out.println(  //formatting in a table the data gathered for the given time instant (no need of the full power of RVs arrays here)
					formatterReal2.format(timeDiscretization.getTime(timeIndex)) + "\t" +
					formatterSci4.format(firstBrownianMotionPath[timeIndex].getAverage()) + "\t" +
					formatterSci4.format(firstBrownianMotionPath[timeIndex].getVariance()) + "\t" +
					formatterSci4.format(firstQuadraticVariationPath[timeIndex].getAverage()) + "\t" +
					formatterSci4.format(firstQuadraticVariationPath[timeIndex].getVariance())+ "\t" +
					formatterSci4.format(quadraticCovariationPath[timeIndex].getAverage())+ "\t" +
					formatterSci4.format(quadraticCovariationPath[timeIndex].getVariance())+ "\t" +
					
					""
			);
			
		}
	    System.out.println("\n");
		//if the tables above confuse you, one can just pick a given time and analyse dtatistic by statistic, since we stored everything in an array:
		
		
		double time=0.5;
		
		//mean of the process at some time
		System.out.println("Mean of the Brownian Motion at time " + time + " : " + firstBrownianMotionPath[timeDiscretization.getTimeIndexNearestGreaterOrEqual(time)].getAverage());
		System.out.println("Variance of the Brownian Motion at time " + time + " : " + firstBrownianMotionPath[timeDiscretization.getTimeIndexNearestGreaterOrEqual(time)].getVariance());
		
		//mean of the process at some time
				System.out.println("Mean of the Quadratic Variation at time " + time + " : " + firstQuadraticVariationPath[timeDiscretization.getTimeIndexNearestGreaterOrEqual(time)].getAverage());
				System.out.println("Variance of the Quadratic Variation at time " + time + " : " + firstQuadraticVariationPath[timeDiscretization.getTimeIndexNearestGreaterOrEqual(time)].getVariance());
				
				System.out.println("Mean of the Quadratic Covariation at time " + time + " : " + quadraticCovariationPath[timeDiscretization.getTimeIndexNearestGreaterOrEqual(time)].getAverage());
				System.out.println("Variance of the Quadratic Covariation at time " + time + " : " + quadraticCovariationPath[timeDiscretization.getTimeIndexNearestGreaterOrEqual(time)].getVariance());
		
	
		
	}	
}
