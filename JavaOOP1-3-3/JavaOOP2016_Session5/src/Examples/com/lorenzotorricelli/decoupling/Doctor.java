package com.lorenzotorricelli.decoupling;

class Doctor extends Cure{ //implements cure
	public void diagnose(){
		System.out.println("You have a cancer");
	}
	public void heal(){
	 System.out.println("I can´t, you´re done!");
	 }
	}

