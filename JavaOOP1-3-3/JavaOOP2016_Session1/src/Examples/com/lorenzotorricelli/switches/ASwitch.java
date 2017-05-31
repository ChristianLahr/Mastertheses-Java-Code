package com.lorenzotorricelli.switches;

public class ASwitch {
	public static void main(String[] args){
		for(int i =1; i<7; i++){ // Change to 9!
			switch(i){
			case 1 : System.out.println("Switch is equal to " + 1);   //eliminate breaks, see what happens
			
			case 2 : System.out.println("Switch is equal to " + 2); break;

			case 3 : System.out.println("Switch is equal to " + 3); break;

			case 4 : System.out.println("Switch is equal to " + 4); break;

			case 5 : System.out.println("Switch is equal to " + 5); break;

			case 6 : System.out.println("Switch is equal to " + 6);  break;
			
            default: System.out.println("Not a valid selection ");  break;
             
			}
		}
	}
}

