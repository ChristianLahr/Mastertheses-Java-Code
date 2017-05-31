package Exercise1;

import java.util.Arrays;
import java.util.Random;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;
import cern.jet.random.engine.MersenneTwister64;

public class RandomVariableTimeDiscretizationExample {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("An examplec ombining RandomVariable, TimeDiscretization and generation of a Stochastic Process");
		

		int randomVariableSize=10;
		double randomVariableConstantValue=0.5;
		double randomVariableTimeInstant=1;

		RandomVariable x=new RandomVariable(randomVariableConstantValue); //constructor, creates a constant array
		RandomVariable y=new RandomVariable(randomVariableTimeInstant, randomVariableConstantValue); //overloaded constructor creates a constant array with an associated time
		RandomVariable z=new RandomVariable(randomVariableTimeInstant, randomVariableSize, randomVariableConstantValue);  //other overloaded
		
		
		System.out.println("\n"); //newline

		
		
		System.out.println("Size of X:" + x.size());   
		System.out.println("Size of Y:" + y.size());
		System.out.println("Size of Z:" + z.size());

		System.out.println("\n"); //newline

		int aPosition=2;

		System.out.println("Value in the position number "+ aPosition + " of Z: " + z.get(aPosition));
		System.out.println("Value in the position number "+ aPosition + " of X: " + x.get(aPosition));

		System.out.println("\n"); //newline


		System.out.println("Getting the whole realisations of Y: " + z.getRealizations()); //Oooops..this gives a memory address. I have to use a method that prints the values in this memory address!   
		System.out.println("Getting the whole realisations of Y: " + Arrays.toString(y.getRealizations()));  // now it is ok

		System.out.println("Getting the time values of X: " + x.getFiltrationTime() + "and Y: " + y.getFiltrationTime()); //getter. The fields realisation and timeinstant cannot be accessed!
		//note Y has the minimum value

		System.out.println("\n"); //newline

		System.out.println("A printout of the whole Random Variable Structure of Z " + z.toString()); //to string method; prints realisation and timeinstant together!

		System.out.println("\n"); //newline

		System.out.println(" Wrapping a ranodm array in a random Variable compatible with Z ");


		final int seed=1234; //final declaration cannot be changed

		MersenneTwister64		mersenneTwister		= new MersenneTwister64(seed);
		Random rand=new Random(seed);  //creating an object that makes possible pseudo random simulations. no parameter () specifies no seed and results will be different in each run



		double[] anArray={1.0, 2.3, 4.5, 3.9 , 67, 3.2, 1.1, -0.56, 0, 0.0}; // an array initialised by a sequence of numbers

		RandomVariable w=new RandomVariable(z.getFiltrationTime(), anArray);  //other overloaded constructor. Inputs the realisation as an array and gets time instant form that of Z.

		//RandomVariableInterface t=new RandomVariable(randomVariableTimeInstant, randomVariableSize, randomVariableConstantValue); //Must declare as a RandomVariable Interface
		RandomVariableInterface t=	 w.add(y);  //Here it is important that t is an interface; we are doing an ASSIGNMENT. Type compatibility

		System.out.println("A printout of the whole Random Variable Structure of w " + w.toString());
		System.out.println("Summing the random Variables W and Z "+ Arrays.toString(t.getRealizations()) );  //   arr1.add(arr2)
		System.out.println("\n"); //newline

		System.out.println(" Square root and exponential of W "+ Arrays.toString(x.exp().getRealizations()) + " " + Arrays.toString(x.sqrt().getRealizations())); //without defining a "Sum" variable
		System.out.println("\n"); //newline

		System.out.println(" Absolute value of W-Z: "+  Arrays.toString(w.sub(z).abs().getRealizations()));
		System.out.println("\n"); //newline

		System.out.println(" Flooring W-Z at 0: "+  Arrays.toString(w.sub(z).floor(0).getRealizations()));
		System.out.println("\n"); //newline


		System.out.println(" Maximum of the values of W: " + x.getMax() );
		System.out.println("\n"); //newline

		System.out.println("Linear combination between a random variable and a double"+  w.add(z.mult(2)).toString());  
		System.out.println("\n"); //newline

		double initialTime=0.0;
		double deltaT=0.1;
		int numberOfTimeSteps=10;

		TimeDiscretization t1=new TimeDiscretization(initialTime, numberOfTimeSteps, deltaT); //a time discretization. A class creating a mesh of point representing a finite set of time instants with methods to get relevant quantities
		//this constructor takes the form initial point, timestep, final point. There are other possibilities.		


		double[][] toBeWrapped=new double[t1.getNumberOfTimes()][randomVariableSize]; 

		RandomVariable[] uniformStochasticProcess=new RandomVariable[t1.getNumberOfTimes()];  //using getter for the number of times

		// generating a stochastic process

		for(int timeIndex=0; timeIndex<t1.getNumberOfTimes(); timeIndex++){
			for(int componentIndex=0; componentIndex <toBeWrapped[timeIndex].length; componentIndex++) {    
				// toBeWrapped[componentIndex]=rand.nextDouble();		  //using the lower performing method from the random class in java.util
				toBeWrapped[timeIndex][componentIndex]=mersenneTwister.nextDouble();                      //at the given time instant generates a path
			}
		};

		for(int timeIndex=0; timeIndex<t1.getNumberOfTimes(); timeIndex++){ 
			uniformStochasticProcess[timeIndex]=new RandomVariable(t1.getTime(timeIndex), toBeWrapped[timeIndex]);     //one dimension of toBeWrapped (paths) goes, it is now providing the basic data for a RandomVariable object              

		};

		UniformStochasticProcess uniformStochasticProcessFromClass=new UniformStochasticProcess(randomVariableSize, initialTime, numberOfTimeSteps, deltaT, seed);
		
		System.out.println("\n \n");
		System.out.println("A process generated in a real OOP way \n \n"+  Arrays.toString(uniformStochasticProcessFromClass.getProcess()));

		//	System.out.println(uniformStochasticProcessNew.getFinalTime());
		//	System.out.println(uniformStochasticProcessNew.getInitialTime());

		double[] averageProcess=new double[t1.getNumberOfTimes()];

		for(int timeIndex=0; timeIndex<t1.getNumberOfTimes(); timeIndex++){
			averageProcess[timeIndex]=uniformStochasticProcessFromClass.getProcessAtATimeInstant(timeIndex).getAverage();  //calculating an "average" process

		}
		System.out.println("\n");
		System.out.println("A process for the averages " +  Arrays.toString(averageProcess));
	}
}




	

