package Exercise1;

import java.util.Arrays;

import cern.jet.random.engine.MersenneTwister64;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.time.TimeDiscretization;

// we now create a simple class by structuring the the last part of the example, which produces a uniform process generating

class UniformStochasticProcess{
	private TimeDiscretization times;
	private RandomVariable[] path=null;
	private int numberOfPaths;
	private final int seed;

	public UniformStochasticProcess(int numberOfPaths, 
			double initialTime, int numberOfTimeSteps, 
			double timeStep, int seed) {
		this.numberOfPaths=numberOfPaths;
		times=new TimeDiscretization(initialTime, numberOfTimeSteps, timeStep);
		path=new RandomVariable[times.getNumberOfTimes()];
		this.seed=seed;
	}

	private void generateProcess(){
		MersenneTwister64	mersenneTwister	= new MersenneTwister64(seed);
		double[][] 			toBeWrapped		= new double[times.getNumberOfTimes()][numberOfPaths]; 
		
		for(int timeIndex=0; timeIndex < times.getNumberOfTimes(); timeIndex++){
			for(int componentIndex=0; componentIndex < numberOfPaths; componentIndex++) {    // generating a stochastic process
				// toBeWrapped[componentIndex]=rand.nextDouble();		  //using the lower performing method from the random class in java.util
				toBeWrapped[timeIndex][componentIndex]=mersenneTwister.nextDouble();                      //at the given time instant generates a path
			}
		}

		for(int timeIndex=0; timeIndex < path.length; timeIndex++){

			path[timeIndex]=new RandomVariable(times.getTime(timeIndex), toBeWrapped[timeIndex]);  //constructing RV from an array: "WRAPPING" the array toBeWrapped in a RandomVariable construct
			System.out.println(path[timeIndex].toString());

		}

	}
	//GETTERS

	int getNumberOfPaths(){  //get numbers of paths
		return numberOfPaths;  
	}

	int getNumberOfTimes(){
		return times.getNumberOfTimes();  //delegation
	}

	double getInitialTime(){
		return times.getTime(0);
	}

	double getFinalTime(){
		return times.getTime(times.getNumberOfTimes()-1);
	}
	
	double getTimeStep(){
		return times.getTimeStep(times.getTimeIndex(0.0)); //an use 0.0 because by definition the time discretisation is equally spaced. 
		//This looks very artificial and involuted, doesn´t it? The alternative is to define time step as a member field and return that directly. This is less elegant but more readble. Which one do you prefer?
	}

	int getNumberOfTime(){
		return times.getNumberOfTimes();
	}

	RandomVariable[] getProcess(){   //"lazy" initialization. the process is generated ONLY when we ask to produce results.
		
		generateProcess();
		
		return path;
	}

	RandomVariable getProcessAtATimeInstant(int timeIndex){   //"lazy" initialization. the process is generated ONLY when we ask to produce results.
		return new RandomVariable(times.getTime(timeIndex), path[timeIndex].getRealizations() );

	}

	void printProcessAtAPoint(int timeIndex){
		System.out.println(Arrays.toString(path[timeIndex].getRealizations()));

	}

}