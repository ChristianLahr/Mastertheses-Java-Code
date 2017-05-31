package com.lorenzotorricelli.forsumsol;


public class ForSum {

	public static void main(String[] args){
		int M=10;
		System.out.println("Gauss once told me that, the sum of the first " + M +" numbers is " + M*(M+1)/2);
		System.out.println("Can you please check that with a loop?");
		//As an extra, also print that numbers....

		int sum=0; //A warning! Well, I guess I can live with that...
		System.out.println("Looping...");		    
		for (int i=0; i<M; i++) {
			System.out.print(i+1 + " "); //i+1 ! Loop is 0-based. I do not want linebreaks!
			sum+=i+1;
		}
		System.out.println("\n"); 
		System.out.println("The sum of the first " + M +" numbers is " + sum);


		System.out.println("Another way of doing this...");	
		Integer[] m=new Integer[M];
			for(int i=0; i < M; i++){
				m[i]=i;
			}//Introducing an array
			sum=0; //resetting variable
		for ( int j  : m) { // foreach syntax. for
			System.out.print(j+1 + " "); 
			sum+=j+1;
		}
		System.out.println("\n"); 
		System.out.println("The sum of the first " + M +" numbers is " + sum);

	}
	
}




